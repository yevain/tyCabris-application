package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.Parc;
import fr.tycabris.domain.ParcChevre;
import fr.tycabris.repository.ParcRepository;
import fr.tycabris.service.ParcService;
import fr.tycabris.service.dto.ParcDTO;
import fr.tycabris.service.mapper.ParcMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.ParcCriteria;
import fr.tycabris.service.ParcQueryService;

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
 * Integration tests for the {@Link ParcResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class ParcResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ParcRepository parcRepository;

    @Autowired
    private ParcMapper parcMapper;

    @Autowired
    private ParcService parcService;

    @Autowired
    private ParcQueryService parcQueryService;

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

    private MockMvc restParcMockMvc;

    private Parc parc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParcResource parcResource = new ParcResource(parcService, parcQueryService);
        this.restParcMockMvc = MockMvcBuilders.standaloneSetup(parcResource)
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
    public static Parc createEntity(EntityManager em) {
        Parc parc = new Parc()
            .nom(DEFAULT_NOM);
        return parc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parc createUpdatedEntity(EntityManager em) {
        Parc parc = new Parc()
            .nom(UPDATED_NOM);
        return parc;
    }

    @BeforeEach
    public void initTest() {
        parc = createEntity(em);
    }

    @Test
    @Transactional
    public void createParc() throws Exception {
        int databaseSizeBeforeCreate = parcRepository.findAll().size();

        // Create the Parc
        ParcDTO parcDTO = parcMapper.toDto(parc);
        restParcMockMvc.perform(post("/api/parcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcDTO)))
            .andExpect(status().isCreated());

        // Validate the Parc in the database
        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeCreate + 1);
        Parc testParc = parcList.get(parcList.size() - 1);
        assertThat(testParc.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createParcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parcRepository.findAll().size();

        // Create the Parc with an existing ID
        parc.setId(1L);
        ParcDTO parcDTO = parcMapper.toDto(parc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcMockMvc.perform(post("/api/parcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parc in the database
        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = parcRepository.findAll().size();
        // set the field null
        parc.setNom(null);

        // Create the Parc, which fails.
        ParcDTO parcDTO = parcMapper.toDto(parc);

        restParcMockMvc.perform(post("/api/parcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcDTO)))
            .andExpect(status().isBadRequest());

        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParcs() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get all the parcList
        restParcMockMvc.perform(get("/api/parcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parc.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    
    @Test
    @Transactional
    public void getParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get the parc
        restParcMockMvc.perform(get("/api/parcs/{id}", parc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parc.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getAllParcsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get all the parcList where nom equals to DEFAULT_NOM
        defaultParcShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the parcList where nom equals to UPDATED_NOM
        defaultParcShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllParcsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get all the parcList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultParcShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the parcList where nom equals to UPDATED_NOM
        defaultParcShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllParcsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get all the parcList where nom is not null
        defaultParcShouldBeFound("nom.specified=true");

        // Get all the parcList where nom is null
        defaultParcShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllParcsByParcChevreIsEqualToSomething() throws Exception {
        // Initialize the database
        ParcChevre parcChevre = ParcChevreResourceIT.createEntity(em);
        em.persist(parcChevre);
        em.flush();
        parc.addParcChevre(parcChevre);
        parcRepository.saveAndFlush(parc);
        Long parcChevreId = parcChevre.getId();

        // Get all the parcList where parcChevre equals to parcChevreId
        defaultParcShouldBeFound("parcChevreId.equals=" + parcChevreId);

        // Get all the parcList where parcChevre equals to parcChevreId + 1
        defaultParcShouldNotBeFound("parcChevreId.equals=" + (parcChevreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParcShouldBeFound(String filter) throws Exception {
        restParcMockMvc.perform(get("/api/parcs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parc.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restParcMockMvc.perform(get("/api/parcs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParcShouldNotBeFound(String filter) throws Exception {
        restParcMockMvc.perform(get("/api/parcs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParcMockMvc.perform(get("/api/parcs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParc() throws Exception {
        // Get the parc
        restParcMockMvc.perform(get("/api/parcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        int databaseSizeBeforeUpdate = parcRepository.findAll().size();

        // Update the parc
        Parc updatedParc = parcRepository.findById(parc.getId()).get();
        // Disconnect from session so that the updates on updatedParc are not directly saved in db
        em.detach(updatedParc);
        updatedParc
            .nom(UPDATED_NOM);
        ParcDTO parcDTO = parcMapper.toDto(updatedParc);

        restParcMockMvc.perform(put("/api/parcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcDTO)))
            .andExpect(status().isOk());

        // Validate the Parc in the database
        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeUpdate);
        Parc testParc = parcList.get(parcList.size() - 1);
        assertThat(testParc.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingParc() throws Exception {
        int databaseSizeBeforeUpdate = parcRepository.findAll().size();

        // Create the Parc
        ParcDTO parcDTO = parcMapper.toDto(parc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcMockMvc.perform(put("/api/parcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parc in the database
        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        int databaseSizeBeforeDelete = parcRepository.findAll().size();

        // Delete the parc
        restParcMockMvc.perform(delete("/api/parcs/{id}", parc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parc> parcList = parcRepository.findAll();
        assertThat(parcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parc.class);
        Parc parc1 = new Parc();
        parc1.setId(1L);
        Parc parc2 = new Parc();
        parc2.setId(parc1.getId());
        assertThat(parc1).isEqualTo(parc2);
        parc2.setId(2L);
        assertThat(parc1).isNotEqualTo(parc2);
        parc1.setId(null);
        assertThat(parc1).isNotEqualTo(parc2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcDTO.class);
        ParcDTO parcDTO1 = new ParcDTO();
        parcDTO1.setId(1L);
        ParcDTO parcDTO2 = new ParcDTO();
        assertThat(parcDTO1).isNotEqualTo(parcDTO2);
        parcDTO2.setId(parcDTO1.getId());
        assertThat(parcDTO1).isEqualTo(parcDTO2);
        parcDTO2.setId(2L);
        assertThat(parcDTO1).isNotEqualTo(parcDTO2);
        parcDTO1.setId(null);
        assertThat(parcDTO1).isNotEqualTo(parcDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parcMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parcMapper.fromId(null)).isNull();
    }
}
