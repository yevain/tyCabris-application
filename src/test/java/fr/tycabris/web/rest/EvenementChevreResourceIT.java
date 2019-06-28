package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.EvenementChevre;
import fr.tycabris.domain.Evenement;
import fr.tycabris.domain.Chevre;
import fr.tycabris.repository.EvenementChevreRepository;
import fr.tycabris.service.EvenementChevreService;
import fr.tycabris.service.dto.EvenementChevreDTO;
import fr.tycabris.service.mapper.EvenementChevreMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.EvenementChevreCriteria;
import fr.tycabris.service.EvenementChevreQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.tycabris.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EvenementChevreResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class EvenementChevreResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EvenementChevreRepository evenementChevreRepository;

    @Autowired
    private EvenementChevreMapper evenementChevreMapper;

    @Autowired
    private EvenementChevreService evenementChevreService;

    @Autowired
    private EvenementChevreQueryService evenementChevreQueryService;

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

    private MockMvc restEvenementChevreMockMvc;

    private EvenementChevre evenementChevre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvenementChevreResource evenementChevreResource = new EvenementChevreResource(evenementChevreService, evenementChevreQueryService);
        this.restEvenementChevreMockMvc = MockMvcBuilders.standaloneSetup(evenementChevreResource)
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
    public static EvenementChevre createEntity(EntityManager em) {
        EvenementChevre evenementChevre = new EvenementChevre()
            .date(DEFAULT_DATE);
        // Add required entity
        Evenement evenement;
        if (TestUtil.findAll(em, Evenement.class).isEmpty()) {
            evenement = EvenementResourceIT.createEntity(em);
            em.persist(evenement);
            em.flush();
        } else {
            evenement = TestUtil.findAll(em, Evenement.class).get(0);
        }
        evenementChevre.setEvenement(evenement);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        evenementChevre.setChevre(chevre);
        return evenementChevre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvenementChevre createUpdatedEntity(EntityManager em) {
        EvenementChevre evenementChevre = new EvenementChevre()
            .date(UPDATED_DATE);
        // Add required entity
        Evenement evenement;
        if (TestUtil.findAll(em, Evenement.class).isEmpty()) {
            evenement = EvenementResourceIT.createUpdatedEntity(em);
            em.persist(evenement);
            em.flush();
        } else {
            evenement = TestUtil.findAll(em, Evenement.class).get(0);
        }
        evenementChevre.setEvenement(evenement);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createUpdatedEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        evenementChevre.setChevre(chevre);
        return evenementChevre;
    }

    @BeforeEach
    public void initTest() {
        evenementChevre = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvenementChevre() throws Exception {
        int databaseSizeBeforeCreate = evenementChevreRepository.findAll().size();

        // Create the EvenementChevre
        EvenementChevreDTO evenementChevreDTO = evenementChevreMapper.toDto(evenementChevre);
        restEvenementChevreMockMvc.perform(post("/api/evenement-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementChevreDTO)))
            .andExpect(status().isCreated());

        // Validate the EvenementChevre in the database
        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeCreate + 1);
        EvenementChevre testEvenementChevre = evenementChevreList.get(evenementChevreList.size() - 1);
        assertThat(testEvenementChevre.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEvenementChevreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evenementChevreRepository.findAll().size();

        // Create the EvenementChevre with an existing ID
        evenementChevre.setId(1L);
        EvenementChevreDTO evenementChevreDTO = evenementChevreMapper.toDto(evenementChevre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvenementChevreMockMvc.perform(post("/api/evenement-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementChevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvenementChevre in the database
        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = evenementChevreRepository.findAll().size();
        // set the field null
        evenementChevre.setDate(null);

        // Create the EvenementChevre, which fails.
        EvenementChevreDTO evenementChevreDTO = evenementChevreMapper.toDto(evenementChevre);

        restEvenementChevreMockMvc.perform(post("/api/evenement-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementChevreDTO)))
            .andExpect(status().isBadRequest());

        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvenementChevres() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenementChevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEvenementChevre() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get the evenementChevre
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres/{id}", evenementChevre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evenementChevre.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllEvenementChevresByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList where date equals to DEFAULT_DATE
        defaultEvenementChevreShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the evenementChevreList where date equals to UPDATED_DATE
        defaultEvenementChevreShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEvenementChevresByDateIsInShouldWork() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEvenementChevreShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the evenementChevreList where date equals to UPDATED_DATE
        defaultEvenementChevreShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEvenementChevresByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList where date is not null
        defaultEvenementChevreShouldBeFound("date.specified=true");

        // Get all the evenementChevreList where date is null
        defaultEvenementChevreShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvenementChevresByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList where date greater than or equals to DEFAULT_DATE
        defaultEvenementChevreShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the evenementChevreList where date greater than or equals to UPDATED_DATE
        defaultEvenementChevreShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEvenementChevresByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        // Get all the evenementChevreList where date less than or equals to DEFAULT_DATE
        defaultEvenementChevreShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the evenementChevreList where date less than or equals to UPDATED_DATE
        defaultEvenementChevreShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEvenementChevresByEvenementIsEqualToSomething() throws Exception {
        // Get already existing entity
        Evenement evenement = evenementChevre.getEvenement();
        evenementChevreRepository.saveAndFlush(evenementChevre);
        Long evenementId = evenement.getId();

        // Get all the evenementChevreList where evenement equals to evenementId
        defaultEvenementChevreShouldBeFound("evenementId.equals=" + evenementId);

        // Get all the evenementChevreList where evenement equals to evenementId + 1
        defaultEvenementChevreShouldNotBeFound("evenementId.equals=" + (evenementId + 1));
    }


    @Test
    @Transactional
    public void getAllEvenementChevresByChevreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Chevre chevre = evenementChevre.getChevre();
        evenementChevreRepository.saveAndFlush(evenementChevre);
        Long chevreId = chevre.getId();

        // Get all the evenementChevreList where chevre equals to chevreId
        defaultEvenementChevreShouldBeFound("chevreId.equals=" + chevreId);

        // Get all the evenementChevreList where chevre equals to chevreId + 1
        defaultEvenementChevreShouldNotBeFound("chevreId.equals=" + (chevreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvenementChevreShouldBeFound(String filter) throws Exception {
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenementChevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvenementChevreShouldNotBeFound(String filter) throws Exception {
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEvenementChevre() throws Exception {
        // Get the evenementChevre
        restEvenementChevreMockMvc.perform(get("/api/evenement-chevres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvenementChevre() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        int databaseSizeBeforeUpdate = evenementChevreRepository.findAll().size();

        // Update the evenementChevre
        EvenementChevre updatedEvenementChevre = evenementChevreRepository.findById(evenementChevre.getId()).get();
        // Disconnect from session so that the updates on updatedEvenementChevre are not directly saved in db
        em.detach(updatedEvenementChevre);
        updatedEvenementChevre
            .date(UPDATED_DATE);
        EvenementChevreDTO evenementChevreDTO = evenementChevreMapper.toDto(updatedEvenementChevre);

        restEvenementChevreMockMvc.perform(put("/api/evenement-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementChevreDTO)))
            .andExpect(status().isOk());

        // Validate the EvenementChevre in the database
        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeUpdate);
        EvenementChevre testEvenementChevre = evenementChevreList.get(evenementChevreList.size() - 1);
        assertThat(testEvenementChevre.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvenementChevre() throws Exception {
        int databaseSizeBeforeUpdate = evenementChevreRepository.findAll().size();

        // Create the EvenementChevre
        EvenementChevreDTO evenementChevreDTO = evenementChevreMapper.toDto(evenementChevre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvenementChevreMockMvc.perform(put("/api/evenement-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementChevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvenementChevre in the database
        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvenementChevre() throws Exception {
        // Initialize the database
        evenementChevreRepository.saveAndFlush(evenementChevre);

        int databaseSizeBeforeDelete = evenementChevreRepository.findAll().size();

        // Delete the evenementChevre
        restEvenementChevreMockMvc.perform(delete("/api/evenement-chevres/{id}", evenementChevre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvenementChevre> evenementChevreList = evenementChevreRepository.findAll();
        assertThat(evenementChevreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvenementChevre.class);
        EvenementChevre evenementChevre1 = new EvenementChevre();
        evenementChevre1.setId(1L);
        EvenementChevre evenementChevre2 = new EvenementChevre();
        evenementChevre2.setId(evenementChevre1.getId());
        assertThat(evenementChevre1).isEqualTo(evenementChevre2);
        evenementChevre2.setId(2L);
        assertThat(evenementChevre1).isNotEqualTo(evenementChevre2);
        evenementChevre1.setId(null);
        assertThat(evenementChevre1).isNotEqualTo(evenementChevre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvenementChevreDTO.class);
        EvenementChevreDTO evenementChevreDTO1 = new EvenementChevreDTO();
        evenementChevreDTO1.setId(1L);
        EvenementChevreDTO evenementChevreDTO2 = new EvenementChevreDTO();
        assertThat(evenementChevreDTO1).isNotEqualTo(evenementChevreDTO2);
        evenementChevreDTO2.setId(evenementChevreDTO1.getId());
        assertThat(evenementChevreDTO1).isEqualTo(evenementChevreDTO2);
        evenementChevreDTO2.setId(2L);
        assertThat(evenementChevreDTO1).isNotEqualTo(evenementChevreDTO2);
        evenementChevreDTO1.setId(null);
        assertThat(evenementChevreDTO1).isNotEqualTo(evenementChevreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(evenementChevreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(evenementChevreMapper.fromId(null)).isNull();
    }
}
