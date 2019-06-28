package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.Evenement;
import fr.tycabris.domain.Evenement;
import fr.tycabris.domain.EvenementChevre;
import fr.tycabris.repository.EvenementRepository;
import fr.tycabris.service.EvenementService;
import fr.tycabris.service.dto.EvenementDTO;
import fr.tycabris.service.mapper.EvenementMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.EvenementCriteria;
import fr.tycabris.service.EvenementQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.tycabris.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EvenementResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class EvenementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_OCCURENCE = 1;
    private static final Integer UPDATED_OCCURENCE = 2;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EvenementMapper evenementMapper;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private EvenementQueryService evenementQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEvenementMockMvc;

    private Evenement evenement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvenementResource evenementResource = new EvenementResource(evenementService, evenementQueryService);
        this.restEvenementMockMvc = MockMvcBuilders.standaloneSetup(evenementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evenement createEntity(EntityManager em) {
        Evenement evenement = new Evenement()
            .nom(DEFAULT_NOM)
            .occurence(DEFAULT_OCCURENCE);
        return evenement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evenement createUpdatedEntity(EntityManager em) {
        Evenement evenement = new Evenement()
            .nom(UPDATED_NOM)
            .occurence(UPDATED_OCCURENCE);
        return evenement;
    }

    @BeforeEach
    public void initTest() {
        evenement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvenement() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement
        EvenementDTO evenementDTO = evenementMapper.toDto(evenement);
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementDTO)))
            .andExpect(status().isCreated());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate + 1);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEvenement.getOccurence()).isEqualTo(DEFAULT_OCCURENCE);
    }

    @Test
    @Transactional
    public void createEvenementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement with an existing ID
        evenement.setId(1L);
        EvenementDTO evenementDTO = evenementMapper.toDto(evenement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = evenementRepository.findAll().size();
        // set the field null
        evenement.setNom(null);

        // Create the Evenement, which fails.
        EvenementDTO evenementDTO = evenementMapper.toDto(evenement);

        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementDTO)))
            .andExpect(status().isBadRequest());

        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvenements() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList
        restEvenementMockMvc.perform(get("/api/evenements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].occurence").value(hasItem(DEFAULT_OCCURENCE)));
    }
    
    @Test
    @Transactional
    public void getEvenement() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", evenement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evenement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.occurence").value(DEFAULT_OCCURENCE));
    }

    @Test
    @Transactional
    public void getAllEvenementsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where nom equals to DEFAULT_NOM
        defaultEvenementShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the evenementList where nom equals to UPDATED_NOM
        defaultEvenementShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEvenementsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEvenementShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the evenementList where nom equals to UPDATED_NOM
        defaultEvenementShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEvenementsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where nom is not null
        defaultEvenementShouldBeFound("nom.specified=true");

        // Get all the evenementList where nom is null
        defaultEvenementShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvenementsByOccurenceIsEqualToSomething() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where occurence equals to DEFAULT_OCCURENCE
        defaultEvenementShouldBeFound("occurence.equals=" + DEFAULT_OCCURENCE);

        // Get all the evenementList where occurence equals to UPDATED_OCCURENCE
        defaultEvenementShouldNotBeFound("occurence.equals=" + UPDATED_OCCURENCE);
    }

    @Test
    @Transactional
    public void getAllEvenementsByOccurenceIsInShouldWork() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where occurence in DEFAULT_OCCURENCE or UPDATED_OCCURENCE
        defaultEvenementShouldBeFound("occurence.in=" + DEFAULT_OCCURENCE + "," + UPDATED_OCCURENCE);

        // Get all the evenementList where occurence equals to UPDATED_OCCURENCE
        defaultEvenementShouldNotBeFound("occurence.in=" + UPDATED_OCCURENCE);
    }

    @Test
    @Transactional
    public void getAllEvenementsByOccurenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where occurence is not null
        defaultEvenementShouldBeFound("occurence.specified=true");

        // Get all the evenementList where occurence is null
        defaultEvenementShouldNotBeFound("occurence.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvenementsByOccurenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where occurence greater than or equals to DEFAULT_OCCURENCE
        defaultEvenementShouldBeFound("occurence.greaterOrEqualThan=" + DEFAULT_OCCURENCE);

        // Get all the evenementList where occurence greater than or equals to UPDATED_OCCURENCE
        defaultEvenementShouldNotBeFound("occurence.greaterOrEqualThan=" + UPDATED_OCCURENCE);
    }

    @Test
    @Transactional
    public void getAllEvenementsByOccurenceIsLessThanSomething() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList where occurence less than or equals to DEFAULT_OCCURENCE
        defaultEvenementShouldNotBeFound("occurence.lessThan=" + DEFAULT_OCCURENCE);

        // Get all the evenementList where occurence less than or equals to UPDATED_OCCURENCE
        defaultEvenementShouldBeFound("occurence.lessThan=" + UPDATED_OCCURENCE);
    }


    @Test
    @Transactional
    public void getAllEvenementsBySuivantIsEqualToSomething() throws Exception {
        // Initialize the database
        Evenement suivant = EvenementResourceIT.createEntity(em);
        em.persist(suivant);
        em.flush();
        evenement.addSuivant(suivant);
        evenementRepository.saveAndFlush(evenement);
        Long suivantId = suivant.getId();

        // Get all the evenementList where suivant equals to suivantId
        defaultEvenementShouldBeFound("suivantId.equals=" + suivantId);

        // Get all the evenementList where suivant equals to suivantId + 1
        defaultEvenementShouldNotBeFound("suivantId.equals=" + (suivantId + 1));
    }


    @Test
    @Transactional
    public void getAllEvenementsByEvenementChevreIsEqualToSomething() throws Exception {
        // Initialize the database
        EvenementChevre evenementChevre = EvenementChevreResourceIT.createEntity(em);
        em.persist(evenementChevre);
        em.flush();
        evenement.addEvenementChevre(evenementChevre);
        evenementRepository.saveAndFlush(evenement);
        Long evenementChevreId = evenementChevre.getId();

        // Get all the evenementList where evenementChevre equals to evenementChevreId
        defaultEvenementShouldBeFound("evenementChevreId.equals=" + evenementChevreId);

        // Get all the evenementList where evenementChevre equals to evenementChevreId + 1
        defaultEvenementShouldNotBeFound("evenementChevreId.equals=" + (evenementChevreId + 1));
    }


    @Test
    @Transactional
    public void getAllEvenementsByEvenementIsEqualToSomething() throws Exception {
        // Initialize the database
        Evenement evenement = EvenementResourceIT.createEntity(em);
        em.persist(evenement);
        em.flush();
        evenement.setEvenement(evenement);
        evenementRepository.saveAndFlush(evenement);
        Long evenementId = evenement.getId();

        // Get all the evenementList where evenement equals to evenementId
        defaultEvenementShouldBeFound("evenementId.equals=" + evenementId);

        // Get all the evenementList where evenement equals to evenementId + 1
        defaultEvenementShouldNotBeFound("evenementId.equals=" + (evenementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvenementShouldBeFound(String filter) throws Exception {
        restEvenementMockMvc.perform(get("/api/evenements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].occurence").value(hasItem(DEFAULT_OCCURENCE)));

        // Check, that the count call also returns 1
        restEvenementMockMvc.perform(get("/api/evenements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvenementShouldNotBeFound(String filter) throws Exception {
        restEvenementMockMvc.perform(get("/api/evenements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvenementMockMvc.perform(get("/api/evenements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEvenement() throws Exception {
        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvenement() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Update the evenement
        Evenement updatedEvenement = evenementRepository.findById(evenement.getId()).get();
        // Disconnect from session so that the updates on updatedEvenement are not directly saved in db
        em.detach(updatedEvenement);
        updatedEvenement
            .nom(UPDATED_NOM)
            .occurence(UPDATED_OCCURENCE);
        EvenementDTO evenementDTO = evenementMapper.toDto(updatedEvenement);

        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementDTO)))
            .andExpect(status().isOk());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEvenement.getOccurence()).isEqualTo(UPDATED_OCCURENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvenement() throws Exception {
        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Create the Evenement
        EvenementDTO evenementDTO = evenementMapper.toDto(evenement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvenement() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        int databaseSizeBeforeDelete = evenementRepository.findAll().size();

        // Delete the evenement
        restEvenementMockMvc.perform(delete("/api/evenements/{id}", evenement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evenement.class);
        Evenement evenement1 = new Evenement();
        evenement1.setId(1L);
        Evenement evenement2 = new Evenement();
        evenement2.setId(evenement1.getId());
        assertThat(evenement1).isEqualTo(evenement2);
        evenement2.setId(2L);
        assertThat(evenement1).isNotEqualTo(evenement2);
        evenement1.setId(null);
        assertThat(evenement1).isNotEqualTo(evenement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvenementDTO.class);
        EvenementDTO evenementDTO1 = new EvenementDTO();
        evenementDTO1.setId(1L);
        EvenementDTO evenementDTO2 = new EvenementDTO();
        assertThat(evenementDTO1).isNotEqualTo(evenementDTO2);
        evenementDTO2.setId(evenementDTO1.getId());
        assertThat(evenementDTO1).isEqualTo(evenementDTO2);
        evenementDTO2.setId(2L);
        assertThat(evenementDTO1).isNotEqualTo(evenementDTO2);
        evenementDTO1.setId(null);
        assertThat(evenementDTO1).isNotEqualTo(evenementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(evenementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(evenementMapper.fromId(null)).isNull();
    }
}
