package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.Poids;
import fr.tycabris.domain.Chevre;
import fr.tycabris.repository.PoidsRepository;
import fr.tycabris.service.PoidsService;
import fr.tycabris.service.dto.PoidsDTO;
import fr.tycabris.service.mapper.PoidsMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.PoidsCriteria;
import fr.tycabris.service.PoidsQueryService;

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
 * Integration tests for the {@Link PoidsResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class PoidsResourceIT {

    private static final Float DEFAULT_VALEUR = 1F;
    private static final Float UPDATED_VALEUR = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PoidsRepository poidsRepository;

    @Autowired
    private PoidsMapper poidsMapper;

    @Autowired
    private PoidsService poidsService;

    @Autowired
    private PoidsQueryService poidsQueryService;

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

    private MockMvc restPoidsMockMvc;

    private Poids poids;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoidsResource poidsResource = new PoidsResource(poidsService, poidsQueryService);
        this.restPoidsMockMvc = MockMvcBuilders.standaloneSetup(poidsResource)
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
    public static Poids createEntity(EntityManager em) {
        Poids poids = new Poids()
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
        poids.setChevre(chevre);
        return poids;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poids createUpdatedEntity(EntityManager em) {
        Poids poids = new Poids()
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
        poids.setChevre(chevre);
        return poids;
    }

    @BeforeEach
    public void initTest() {
        poids = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoids() throws Exception {
        int databaseSizeBeforeCreate = poidsRepository.findAll().size();

        // Create the Poids
        PoidsDTO poidsDTO = poidsMapper.toDto(poids);
        restPoidsMockMvc.perform(post("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isCreated());

        // Validate the Poids in the database
        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeCreate + 1);
        Poids testPoids = poidsList.get(poidsList.size() - 1);
        assertThat(testPoids.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testPoids.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPoidsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poidsRepository.findAll().size();

        // Create the Poids with an existing ID
        poids.setId(1L);
        PoidsDTO poidsDTO = poidsMapper.toDto(poids);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoidsMockMvc.perform(post("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poids in the database
        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = poidsRepository.findAll().size();
        // set the field null
        poids.setValeur(null);

        // Create the Poids, which fails.
        PoidsDTO poidsDTO = poidsMapper.toDto(poids);

        restPoidsMockMvc.perform(post("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isBadRequest());

        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = poidsRepository.findAll().size();
        // set the field null
        poids.setDate(null);

        // Create the Poids, which fails.
        PoidsDTO poidsDTO = poidsMapper.toDto(poids);

        restPoidsMockMvc.perform(post("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isBadRequest());

        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPoids() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList
        restPoidsMockMvc.perform(get("/api/poids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poids.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPoids() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get the poids
        restPoidsMockMvc.perform(get("/api/poids/{id}", poids.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poids.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPoidsByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where valeur equals to DEFAULT_VALEUR
        defaultPoidsShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the poidsList where valeur equals to UPDATED_VALEUR
        defaultPoidsShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllPoidsByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultPoidsShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the poidsList where valeur equals to UPDATED_VALEUR
        defaultPoidsShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllPoidsByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where valeur is not null
        defaultPoidsShouldBeFound("valeur.specified=true");

        // Get all the poidsList where valeur is null
        defaultPoidsShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoidsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where date equals to DEFAULT_DATE
        defaultPoidsShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the poidsList where date equals to UPDATED_DATE
        defaultPoidsShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPoidsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where date in DEFAULT_DATE or UPDATED_DATE
        defaultPoidsShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the poidsList where date equals to UPDATED_DATE
        defaultPoidsShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPoidsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where date is not null
        defaultPoidsShouldBeFound("date.specified=true");

        // Get all the poidsList where date is null
        defaultPoidsShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoidsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where date greater than or equals to DEFAULT_DATE
        defaultPoidsShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the poidsList where date greater than or equals to UPDATED_DATE
        defaultPoidsShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPoidsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        // Get all the poidsList where date less than or equals to DEFAULT_DATE
        defaultPoidsShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the poidsList where date less than or equals to UPDATED_DATE
        defaultPoidsShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPoidsByChevreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Chevre chevre = poids.getChevre();
        poidsRepository.saveAndFlush(poids);
        Long chevreId = chevre.getId();

        // Get all the poidsList where chevre equals to chevreId
        defaultPoidsShouldBeFound("chevreId.equals=" + chevreId);

        // Get all the poidsList where chevre equals to chevreId + 1
        defaultPoidsShouldNotBeFound("chevreId.equals=" + (chevreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPoidsShouldBeFound(String filter) throws Exception {
        restPoidsMockMvc.perform(get("/api/poids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poids.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restPoidsMockMvc.perform(get("/api/poids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPoidsShouldNotBeFound(String filter) throws Exception {
        restPoidsMockMvc.perform(get("/api/poids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPoidsMockMvc.perform(get("/api/poids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPoids() throws Exception {
        // Get the poids
        restPoidsMockMvc.perform(get("/api/poids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoids() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        int databaseSizeBeforeUpdate = poidsRepository.findAll().size();

        // Update the poids
        Poids updatedPoids = poidsRepository.findById(poids.getId()).get();
        // Disconnect from session so that the updates on updatedPoids are not directly saved in db
        em.detach(updatedPoids);
        updatedPoids
            .valeur(UPDATED_VALEUR)
            .date(UPDATED_DATE);
        PoidsDTO poidsDTO = poidsMapper.toDto(updatedPoids);

        restPoidsMockMvc.perform(put("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isOk());

        // Validate the Poids in the database
        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeUpdate);
        Poids testPoids = poidsList.get(poidsList.size() - 1);
        assertThat(testPoids.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testPoids.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoids() throws Exception {
        int databaseSizeBeforeUpdate = poidsRepository.findAll().size();

        // Create the Poids
        PoidsDTO poidsDTO = poidsMapper.toDto(poids);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoidsMockMvc.perform(put("/api/poids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poidsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poids in the database
        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoids() throws Exception {
        // Initialize the database
        poidsRepository.saveAndFlush(poids);

        int databaseSizeBeforeDelete = poidsRepository.findAll().size();

        // Delete the poids
        restPoidsMockMvc.perform(delete("/api/poids/{id}", poids.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poids> poidsList = poidsRepository.findAll();
        assertThat(poidsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poids.class);
        Poids poids1 = new Poids();
        poids1.setId(1L);
        Poids poids2 = new Poids();
        poids2.setId(poids1.getId());
        assertThat(poids1).isEqualTo(poids2);
        poids2.setId(2L);
        assertThat(poids1).isNotEqualTo(poids2);
        poids1.setId(null);
        assertThat(poids1).isNotEqualTo(poids2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoidsDTO.class);
        PoidsDTO poidsDTO1 = new PoidsDTO();
        poidsDTO1.setId(1L);
        PoidsDTO poidsDTO2 = new PoidsDTO();
        assertThat(poidsDTO1).isNotEqualTo(poidsDTO2);
        poidsDTO2.setId(poidsDTO1.getId());
        assertThat(poidsDTO1).isEqualTo(poidsDTO2);
        poidsDTO2.setId(2L);
        assertThat(poidsDTO1).isNotEqualTo(poidsDTO2);
        poidsDTO1.setId(null);
        assertThat(poidsDTO1).isNotEqualTo(poidsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poidsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poidsMapper.fromId(null)).isNull();
    }
}
