package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.Taille;
import fr.tycabris.domain.Chevre;
import fr.tycabris.repository.TailleRepository;
import fr.tycabris.service.TailleService;
import fr.tycabris.service.dto.TailleDTO;
import fr.tycabris.service.mapper.TailleMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.TailleCriteria;
import fr.tycabris.service.TailleQueryService;

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
 * Integration tests for the {@Link TailleResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class TailleResourceIT {

    private static final Float DEFAULT_VALEUR = 1F;
    private static final Float UPDATED_VALEUR = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TailleRepository tailleRepository;

    @Autowired
    private TailleMapper tailleMapper;

    @Autowired
    private TailleService tailleService;

    @Autowired
    private TailleQueryService tailleQueryService;

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

    private MockMvc restTailleMockMvc;

    private Taille taille;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TailleResource tailleResource = new TailleResource(tailleService, tailleQueryService);
        this.restTailleMockMvc = MockMvcBuilders.standaloneSetup(tailleResource)
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
    public static Taille createEntity(EntityManager em) {
        Taille taille = new Taille()
            .valeur(DEFAULT_VALEUR)
            .date(DEFAULT_DATE);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        taille.setChevre(chevre);
        return taille;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taille createUpdatedEntity(EntityManager em) {
        Taille taille = new Taille()
            .valeur(UPDATED_VALEUR)
            .date(UPDATED_DATE);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createUpdatedEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        taille.setChevre(chevre);
        return taille;
    }

    @BeforeEach
    public void initTest() {
        taille = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaille() throws Exception {
        int databaseSizeBeforeCreate = tailleRepository.findAll().size();

        // Create the Taille
        TailleDTO tailleDTO = tailleMapper.toDto(taille);
        restTailleMockMvc.perform(post("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isCreated());

        // Validate the Taille in the database
        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeCreate + 1);
        Taille testTaille = tailleList.get(tailleList.size() - 1);
        assertThat(testTaille.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testTaille.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTailleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tailleRepository.findAll().size();

        // Create the Taille with an existing ID
        taille.setId(1L);
        TailleDTO tailleDTO = tailleMapper.toDto(taille);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTailleMockMvc.perform(post("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taille in the database
        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = tailleRepository.findAll().size();
        // set the field null
        taille.setValeur(null);

        // Create the Taille, which fails.
        TailleDTO tailleDTO = tailleMapper.toDto(taille);

        restTailleMockMvc.perform(post("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isBadRequest());

        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tailleRepository.findAll().size();
        // set the field null
        taille.setDate(null);

        // Create the Taille, which fails.
        TailleDTO tailleDTO = tailleMapper.toDto(taille);

        restTailleMockMvc.perform(post("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isBadRequest());

        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTailles() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList
        restTailleMockMvc.perform(get("/api/tailles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taille.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTaille() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get the taille
        restTailleMockMvc.perform(get("/api/tailles/{id}", taille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taille.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllTaillesByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where valeur equals to DEFAULT_VALEUR
        defaultTailleShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the tailleList where valeur equals to UPDATED_VALEUR
        defaultTailleShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllTaillesByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultTailleShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the tailleList where valeur equals to UPDATED_VALEUR
        defaultTailleShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllTaillesByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where valeur is not null
        defaultTailleShouldBeFound("valeur.specified=true");

        // Get all the tailleList where valeur is null
        defaultTailleShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaillesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where date equals to DEFAULT_DATE
        defaultTailleShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the tailleList where date equals to UPDATED_DATE
        defaultTailleShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaillesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where date in DEFAULT_DATE or UPDATED_DATE
        defaultTailleShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the tailleList where date equals to UPDATED_DATE
        defaultTailleShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaillesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where date is not null
        defaultTailleShouldBeFound("date.specified=true");

        // Get all the tailleList where date is null
        defaultTailleShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaillesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where date greater than or equals to DEFAULT_DATE
        defaultTailleShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the tailleList where date greater than or equals to UPDATED_DATE
        defaultTailleShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaillesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        // Get all the tailleList where date less than or equals to DEFAULT_DATE
        defaultTailleShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the tailleList where date less than or equals to UPDATED_DATE
        defaultTailleShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllTaillesByChevreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Chevre chevre = taille.getChevre();
        tailleRepository.saveAndFlush(taille);
        Long chevreId = chevre.getId();

        // Get all the tailleList where chevre equals to chevreId
        defaultTailleShouldBeFound("chevreId.equals=" + chevreId);

        // Get all the tailleList where chevre equals to chevreId + 1
        defaultTailleShouldNotBeFound("chevreId.equals=" + (chevreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTailleShouldBeFound(String filter) throws Exception {
        restTailleMockMvc.perform(get("/api/tailles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taille.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restTailleMockMvc.perform(get("/api/tailles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTailleShouldNotBeFound(String filter) throws Exception {
        restTailleMockMvc.perform(get("/api/tailles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTailleMockMvc.perform(get("/api/tailles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTaille() throws Exception {
        // Get the taille
        restTailleMockMvc.perform(get("/api/tailles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaille() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        int databaseSizeBeforeUpdate = tailleRepository.findAll().size();

        // Update the taille
        Taille updatedTaille = tailleRepository.findById(taille.getId()).get();
        // Disconnect from session so that the updates on updatedTaille are not directly saved in db
        em.detach(updatedTaille);
        updatedTaille
            .valeur(UPDATED_VALEUR)
            .date(UPDATED_DATE);
        TailleDTO tailleDTO = tailleMapper.toDto(updatedTaille);

        restTailleMockMvc.perform(put("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isOk());

        // Validate the Taille in the database
        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeUpdate);
        Taille testTaille = tailleList.get(tailleList.size() - 1);
        assertThat(testTaille.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testTaille.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaille() throws Exception {
        int databaseSizeBeforeUpdate = tailleRepository.findAll().size();

        // Create the Taille
        TailleDTO tailleDTO = tailleMapper.toDto(taille);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTailleMockMvc.perform(put("/api/tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tailleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taille in the database
        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaille() throws Exception {
        // Initialize the database
        tailleRepository.saveAndFlush(taille);

        int databaseSizeBeforeDelete = tailleRepository.findAll().size();

        // Delete the taille
        restTailleMockMvc.perform(delete("/api/tailles/{id}", taille.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taille> tailleList = tailleRepository.findAll();
        assertThat(tailleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taille.class);
        Taille taille1 = new Taille();
        taille1.setId(1L);
        Taille taille2 = new Taille();
        taille2.setId(taille1.getId());
        assertThat(taille1).isEqualTo(taille2);
        taille2.setId(2L);
        assertThat(taille1).isNotEqualTo(taille2);
        taille1.setId(null);
        assertThat(taille1).isNotEqualTo(taille2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TailleDTO.class);
        TailleDTO tailleDTO1 = new TailleDTO();
        tailleDTO1.setId(1L);
        TailleDTO tailleDTO2 = new TailleDTO();
        assertThat(tailleDTO1).isNotEqualTo(tailleDTO2);
        tailleDTO2.setId(tailleDTO1.getId());
        assertThat(tailleDTO1).isEqualTo(tailleDTO2);
        tailleDTO2.setId(2L);
        assertThat(tailleDTO1).isNotEqualTo(tailleDTO2);
        tailleDTO1.setId(null);
        assertThat(tailleDTO1).isNotEqualTo(tailleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tailleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tailleMapper.fromId(null)).isNull();
    }
}
