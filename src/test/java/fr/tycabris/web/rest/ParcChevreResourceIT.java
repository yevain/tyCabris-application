package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.ParcChevre;
import fr.tycabris.domain.Parc;
import fr.tycabris.domain.Chevre;
import fr.tycabris.repository.ParcChevreRepository;
import fr.tycabris.service.ParcChevreService;
import fr.tycabris.service.dto.ParcChevreDTO;
import fr.tycabris.service.mapper.ParcChevreMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.ParcChevreCriteria;
import fr.tycabris.service.ParcChevreQueryService;

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
 * Integration tests for the {@Link ParcChevreResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class ParcChevreResourceIT {

    private static final LocalDate DEFAULT_ENTREE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTREE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SORTIE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ParcChevreRepository parcChevreRepository;

    @Autowired
    private ParcChevreMapper parcChevreMapper;

    @Autowired
    private ParcChevreService parcChevreService;

    @Autowired
    private ParcChevreQueryService parcChevreQueryService;

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

    private MockMvc restParcChevreMockMvc;

    private ParcChevre parcChevre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParcChevreResource parcChevreResource = new ParcChevreResource(parcChevreService, parcChevreQueryService);
        this.restParcChevreMockMvc = MockMvcBuilders.standaloneSetup(parcChevreResource)
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
    public static ParcChevre createEntity(EntityManager em) {
        ParcChevre parcChevre = new ParcChevre()
            .entree(DEFAULT_ENTREE)
            .sortie(DEFAULT_SORTIE);
        // Add required entity
        Parc parc;
        if (TestUtil.findAll(em, Parc.class).isEmpty()) {
            parc = ParcResourceIT.createEntity(em);
            em.persist(parc);
            em.flush();
        } else {
            parc = TestUtil.findAll(em, Parc.class).get(0);
        }
        parcChevre.setParc(parc);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        parcChevre.setChevre(chevre);
        return parcChevre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParcChevre createUpdatedEntity(EntityManager em) {
        ParcChevre parcChevre = new ParcChevre()
            .entree(UPDATED_ENTREE)
            .sortie(UPDATED_SORTIE);
        // Add required entity
        Parc parc;
        if (TestUtil.findAll(em, Parc.class).isEmpty()) {
            parc = ParcResourceIT.createUpdatedEntity(em);
            em.persist(parc);
            em.flush();
        } else {
            parc = TestUtil.findAll(em, Parc.class).get(0);
        }
        parcChevre.setParc(parc);
        // Add required entity
        Chevre chevre;
        if (TestUtil.findAll(em, Chevre.class).isEmpty()) {
            chevre = ChevreResourceIT.createUpdatedEntity(em);
            em.persist(chevre);
            em.flush();
        } else {
            chevre = TestUtil.findAll(em, Chevre.class).get(0);
        }
        parcChevre.setChevre(chevre);
        return parcChevre;
    }

    @BeforeEach
    public void initTest() {
        parcChevre = createEntity(em);
    }

    @Test
    @Transactional
    public void createParcChevre() throws Exception {
        int databaseSizeBeforeCreate = parcChevreRepository.findAll().size();

        // Create the ParcChevre
        ParcChevreDTO parcChevreDTO = parcChevreMapper.toDto(parcChevre);
        restParcChevreMockMvc.perform(post("/api/parc-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcChevreDTO)))
            .andExpect(status().isCreated());

        // Validate the ParcChevre in the database
        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeCreate + 1);
        ParcChevre testParcChevre = parcChevreList.get(parcChevreList.size() - 1);
        assertThat(testParcChevre.getEntree()).isEqualTo(DEFAULT_ENTREE);
        assertThat(testParcChevre.getSortie()).isEqualTo(DEFAULT_SORTIE);
    }

    @Test
    @Transactional
    public void createParcChevreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parcChevreRepository.findAll().size();

        // Create the ParcChevre with an existing ID
        parcChevre.setId(1L);
        ParcChevreDTO parcChevreDTO = parcChevreMapper.toDto(parcChevre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcChevreMockMvc.perform(post("/api/parc-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcChevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParcChevre in the database
        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEntreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parcChevreRepository.findAll().size();
        // set the field null
        parcChevre.setEntree(null);

        // Create the ParcChevre, which fails.
        ParcChevreDTO parcChevreDTO = parcChevreMapper.toDto(parcChevre);

        restParcChevreMockMvc.perform(post("/api/parc-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcChevreDTO)))
            .andExpect(status().isBadRequest());

        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParcChevres() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList
        restParcChevreMockMvc.perform(get("/api/parc-chevres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcChevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].entree").value(hasItem(DEFAULT_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].sortie").value(hasItem(DEFAULT_SORTIE.toString())));
    }
    
    @Test
    @Transactional
    public void getParcChevre() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get the parcChevre
        restParcChevreMockMvc.perform(get("/api/parc-chevres/{id}", parcChevre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parcChevre.getId().intValue()))
            .andExpect(jsonPath("$.entree").value(DEFAULT_ENTREE.toString()))
            .andExpect(jsonPath("$.sortie").value(DEFAULT_SORTIE.toString()));
    }

    @Test
    @Transactional
    public void getAllParcChevresByEntreeIsEqualToSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where entree equals to DEFAULT_ENTREE
        defaultParcChevreShouldBeFound("entree.equals=" + DEFAULT_ENTREE);

        // Get all the parcChevreList where entree equals to UPDATED_ENTREE
        defaultParcChevreShouldNotBeFound("entree.equals=" + UPDATED_ENTREE);
    }

    @Test
    @Transactional
    public void getAllParcChevresByEntreeIsInShouldWork() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where entree in DEFAULT_ENTREE or UPDATED_ENTREE
        defaultParcChevreShouldBeFound("entree.in=" + DEFAULT_ENTREE + "," + UPDATED_ENTREE);

        // Get all the parcChevreList where entree equals to UPDATED_ENTREE
        defaultParcChevreShouldNotBeFound("entree.in=" + UPDATED_ENTREE);
    }

    @Test
    @Transactional
    public void getAllParcChevresByEntreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where entree is not null
        defaultParcChevreShouldBeFound("entree.specified=true");

        // Get all the parcChevreList where entree is null
        defaultParcChevreShouldNotBeFound("entree.specified=false");
    }

    @Test
    @Transactional
    public void getAllParcChevresByEntreeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where entree greater than or equals to DEFAULT_ENTREE
        defaultParcChevreShouldBeFound("entree.greaterOrEqualThan=" + DEFAULT_ENTREE);

        // Get all the parcChevreList where entree greater than or equals to UPDATED_ENTREE
        defaultParcChevreShouldNotBeFound("entree.greaterOrEqualThan=" + UPDATED_ENTREE);
    }

    @Test
    @Transactional
    public void getAllParcChevresByEntreeIsLessThanSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where entree less than or equals to DEFAULT_ENTREE
        defaultParcChevreShouldNotBeFound("entree.lessThan=" + DEFAULT_ENTREE);

        // Get all the parcChevreList where entree less than or equals to UPDATED_ENTREE
        defaultParcChevreShouldBeFound("entree.lessThan=" + UPDATED_ENTREE);
    }


    @Test
    @Transactional
    public void getAllParcChevresBySortieIsEqualToSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where sortie equals to DEFAULT_SORTIE
        defaultParcChevreShouldBeFound("sortie.equals=" + DEFAULT_SORTIE);

        // Get all the parcChevreList where sortie equals to UPDATED_SORTIE
        defaultParcChevreShouldNotBeFound("sortie.equals=" + UPDATED_SORTIE);
    }

    @Test
    @Transactional
    public void getAllParcChevresBySortieIsInShouldWork() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where sortie in DEFAULT_SORTIE or UPDATED_SORTIE
        defaultParcChevreShouldBeFound("sortie.in=" + DEFAULT_SORTIE + "," + UPDATED_SORTIE);

        // Get all the parcChevreList where sortie equals to UPDATED_SORTIE
        defaultParcChevreShouldNotBeFound("sortie.in=" + UPDATED_SORTIE);
    }

    @Test
    @Transactional
    public void getAllParcChevresBySortieIsNullOrNotNull() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where sortie is not null
        defaultParcChevreShouldBeFound("sortie.specified=true");

        // Get all the parcChevreList where sortie is null
        defaultParcChevreShouldNotBeFound("sortie.specified=false");
    }

    @Test
    @Transactional
    public void getAllParcChevresBySortieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where sortie greater than or equals to DEFAULT_SORTIE
        defaultParcChevreShouldBeFound("sortie.greaterOrEqualThan=" + DEFAULT_SORTIE);

        // Get all the parcChevreList where sortie greater than or equals to UPDATED_SORTIE
        defaultParcChevreShouldNotBeFound("sortie.greaterOrEqualThan=" + UPDATED_SORTIE);
    }

    @Test
    @Transactional
    public void getAllParcChevresBySortieIsLessThanSomething() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        // Get all the parcChevreList where sortie less than or equals to DEFAULT_SORTIE
        defaultParcChevreShouldNotBeFound("sortie.lessThan=" + DEFAULT_SORTIE);

        // Get all the parcChevreList where sortie less than or equals to UPDATED_SORTIE
        defaultParcChevreShouldBeFound("sortie.lessThan=" + UPDATED_SORTIE);
    }


    @Test
    @Transactional
    public void getAllParcChevresByParcIsEqualToSomething() throws Exception {
        // Get already existing entity
        Parc parc = parcChevre.getParc();
        parcChevreRepository.saveAndFlush(parcChevre);
        Long parcId = parc.getId();

        // Get all the parcChevreList where parc equals to parcId
        defaultParcChevreShouldBeFound("parcId.equals=" + parcId);

        // Get all the parcChevreList where parc equals to parcId + 1
        defaultParcChevreShouldNotBeFound("parcId.equals=" + (parcId + 1));
    }


    @Test
    @Transactional
    public void getAllParcChevresByChevreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Chevre chevre = parcChevre.getChevre();
        parcChevreRepository.saveAndFlush(parcChevre);
        Long chevreId = chevre.getId();

        // Get all the parcChevreList where chevre equals to chevreId
        defaultParcChevreShouldBeFound("chevreId.equals=" + chevreId);

        // Get all the parcChevreList where chevre equals to chevreId + 1
        defaultParcChevreShouldNotBeFound("chevreId.equals=" + (chevreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParcChevreShouldBeFound(String filter) throws Exception {
        restParcChevreMockMvc.perform(get("/api/parc-chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcChevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].entree").value(hasItem(DEFAULT_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].sortie").value(hasItem(DEFAULT_SORTIE.toString())));

        // Check, that the count call also returns 1
        restParcChevreMockMvc.perform(get("/api/parc-chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParcChevreShouldNotBeFound(String filter) throws Exception {
        restParcChevreMockMvc.perform(get("/api/parc-chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParcChevreMockMvc.perform(get("/api/parc-chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParcChevre() throws Exception {
        // Get the parcChevre
        restParcChevreMockMvc.perform(get("/api/parc-chevres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParcChevre() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        int databaseSizeBeforeUpdate = parcChevreRepository.findAll().size();

        // Update the parcChevre
        ParcChevre updatedParcChevre = parcChevreRepository.findById(parcChevre.getId()).get();
        // Disconnect from session so that the updates on updatedParcChevre are not directly saved in db
        em.detach(updatedParcChevre);
        updatedParcChevre
            .entree(UPDATED_ENTREE)
            .sortie(UPDATED_SORTIE);
        ParcChevreDTO parcChevreDTO = parcChevreMapper.toDto(updatedParcChevre);

        restParcChevreMockMvc.perform(put("/api/parc-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcChevreDTO)))
            .andExpect(status().isOk());

        // Validate the ParcChevre in the database
        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeUpdate);
        ParcChevre testParcChevre = parcChevreList.get(parcChevreList.size() - 1);
        assertThat(testParcChevre.getEntree()).isEqualTo(UPDATED_ENTREE);
        assertThat(testParcChevre.getSortie()).isEqualTo(UPDATED_SORTIE);
    }

    @Test
    @Transactional
    public void updateNonExistingParcChevre() throws Exception {
        int databaseSizeBeforeUpdate = parcChevreRepository.findAll().size();

        // Create the ParcChevre
        ParcChevreDTO parcChevreDTO = parcChevreMapper.toDto(parcChevre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcChevreMockMvc.perform(put("/api/parc-chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcChevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParcChevre in the database
        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParcChevre() throws Exception {
        // Initialize the database
        parcChevreRepository.saveAndFlush(parcChevre);

        int databaseSizeBeforeDelete = parcChevreRepository.findAll().size();

        // Delete the parcChevre
        restParcChevreMockMvc.perform(delete("/api/parc-chevres/{id}", parcChevre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParcChevre> parcChevreList = parcChevreRepository.findAll();
        assertThat(parcChevreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcChevre.class);
        ParcChevre parcChevre1 = new ParcChevre();
        parcChevre1.setId(1L);
        ParcChevre parcChevre2 = new ParcChevre();
        parcChevre2.setId(parcChevre1.getId());
        assertThat(parcChevre1).isEqualTo(parcChevre2);
        parcChevre2.setId(2L);
        assertThat(parcChevre1).isNotEqualTo(parcChevre2);
        parcChevre1.setId(null);
        assertThat(parcChevre1).isNotEqualTo(parcChevre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcChevreDTO.class);
        ParcChevreDTO parcChevreDTO1 = new ParcChevreDTO();
        parcChevreDTO1.setId(1L);
        ParcChevreDTO parcChevreDTO2 = new ParcChevreDTO();
        assertThat(parcChevreDTO1).isNotEqualTo(parcChevreDTO2);
        parcChevreDTO2.setId(parcChevreDTO1.getId());
        assertThat(parcChevreDTO1).isEqualTo(parcChevreDTO2);
        parcChevreDTO2.setId(2L);
        assertThat(parcChevreDTO1).isNotEqualTo(parcChevreDTO2);
        parcChevreDTO1.setId(null);
        assertThat(parcChevreDTO1).isNotEqualTo(parcChevreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parcChevreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parcChevreMapper.fromId(null)).isNull();
    }
}
