package fr.tycabris.web.rest;

import fr.tycabris.TyCabrisApplicationApp;
import fr.tycabris.domain.Chevre;
import fr.tycabris.domain.Chevre;
import fr.tycabris.domain.Poids;
import fr.tycabris.domain.Taille;
import fr.tycabris.domain.ParcChevre;
import fr.tycabris.domain.EvenementChevre;
import fr.tycabris.repository.ChevreRepository;
import fr.tycabris.service.ChevreService;
import fr.tycabris.service.dto.ChevreDTO;
import fr.tycabris.service.mapper.ChevreMapper;
import fr.tycabris.web.rest.errors.ExceptionTranslator;
import fr.tycabris.service.dto.ChevreCriteria;
import fr.tycabris.service.ChevreQueryService;

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
 * Integration tests for the {@Link ChevreResource} REST controller.
 */
@SpringBootTest(classes = TyCabrisApplicationApp.class)
public class ChevreResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_SURNOM = "AAAAAAAAAA";
    private static final String UPDATED_SURNOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PRESENT = false;
    private static final Boolean UPDATED_PRESENT = true;

    @Autowired
    private ChevreRepository chevreRepository;

    @Autowired
    private ChevreMapper chevreMapper;

    @Autowired
    private ChevreService chevreService;

    @Autowired
    private ChevreQueryService chevreQueryService;

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

    private MockMvc restChevreMockMvc;

    private Chevre chevre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChevreResource chevreResource = new ChevreResource(chevreService, chevreQueryService);
        this.restChevreMockMvc = MockMvcBuilders.standaloneSetup(chevreResource)
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
    public static Chevre createEntity(EntityManager em) {
        Chevre chevre = new Chevre()
            .nom(DEFAULT_NOM)
            .matricule(DEFAULT_MATRICULE)
            .surnom(DEFAULT_SURNOM)
            .naissance(DEFAULT_NAISSANCE)
            .present(DEFAULT_PRESENT);
        return chevre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chevre createUpdatedEntity(EntityManager em) {
        Chevre chevre = new Chevre()
            .nom(UPDATED_NOM)
            .matricule(UPDATED_MATRICULE)
            .surnom(UPDATED_SURNOM)
            .naissance(UPDATED_NAISSANCE)
            .present(UPDATED_PRESENT);
        return chevre;
    }

    @BeforeEach
    public void initTest() {
        chevre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChevre() throws Exception {
        int databaseSizeBeforeCreate = chevreRepository.findAll().size();

        // Create the Chevre
        ChevreDTO chevreDTO = chevreMapper.toDto(chevre);
        restChevreMockMvc.perform(post("/api/chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chevreDTO)))
            .andExpect(status().isCreated());

        // Validate the Chevre in the database
        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeCreate + 1);
        Chevre testChevre = chevreList.get(chevreList.size() - 1);
        assertThat(testChevre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testChevre.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testChevre.getSurnom()).isEqualTo(DEFAULT_SURNOM);
        assertThat(testChevre.getNaissance()).isEqualTo(DEFAULT_NAISSANCE);
        assertThat(testChevre.isPresent()).isEqualTo(DEFAULT_PRESENT);
    }

    @Test
    @Transactional
    public void createChevreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chevreRepository.findAll().size();

        // Create the Chevre with an existing ID
        chevre.setId(1L);
        ChevreDTO chevreDTO = chevreMapper.toDto(chevre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChevreMockMvc.perform(post("/api/chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chevre in the database
        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = chevreRepository.findAll().size();
        // set the field null
        chevre.setNom(null);

        // Create the Chevre, which fails.
        ChevreDTO chevreDTO = chevreMapper.toDto(chevre);

        restChevreMockMvc.perform(post("/api/chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chevreDTO)))
            .andExpect(status().isBadRequest());

        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChevres() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList
        restChevreMockMvc.perform(get("/api/chevres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].surnom").value(hasItem(DEFAULT_SURNOM.toString())))
            .andExpect(jsonPath("$.[*].naissance").value(hasItem(DEFAULT_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].present").value(hasItem(DEFAULT_PRESENT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getChevre() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get the chevre
        restChevreMockMvc.perform(get("/api/chevres/{id}", chevre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chevre.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.surnom").value(DEFAULT_SURNOM.toString()))
            .andExpect(jsonPath("$.naissance").value(DEFAULT_NAISSANCE.toString()))
            .andExpect(jsonPath("$.present").value(DEFAULT_PRESENT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllChevresByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where nom equals to DEFAULT_NOM
        defaultChevreShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the chevreList where nom equals to UPDATED_NOM
        defaultChevreShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChevresByNomIsInShouldWork() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultChevreShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the chevreList where nom equals to UPDATED_NOM
        defaultChevreShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllChevresByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where nom is not null
        defaultChevreShouldBeFound("nom.specified=true");

        // Get all the chevreList where nom is null
        defaultChevreShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllChevresByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where matricule equals to DEFAULT_MATRICULE
        defaultChevreShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the chevreList where matricule equals to UPDATED_MATRICULE
        defaultChevreShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllChevresByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultChevreShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the chevreList where matricule equals to UPDATED_MATRICULE
        defaultChevreShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllChevresByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where matricule is not null
        defaultChevreShouldBeFound("matricule.specified=true");

        // Get all the chevreList where matricule is null
        defaultChevreShouldNotBeFound("matricule.specified=false");
    }

    @Test
    @Transactional
    public void getAllChevresBySurnomIsEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where surnom equals to DEFAULT_SURNOM
        defaultChevreShouldBeFound("surnom.equals=" + DEFAULT_SURNOM);

        // Get all the chevreList where surnom equals to UPDATED_SURNOM
        defaultChevreShouldNotBeFound("surnom.equals=" + UPDATED_SURNOM);
    }

    @Test
    @Transactional
    public void getAllChevresBySurnomIsInShouldWork() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where surnom in DEFAULT_SURNOM or UPDATED_SURNOM
        defaultChevreShouldBeFound("surnom.in=" + DEFAULT_SURNOM + "," + UPDATED_SURNOM);

        // Get all the chevreList where surnom equals to UPDATED_SURNOM
        defaultChevreShouldNotBeFound("surnom.in=" + UPDATED_SURNOM);
    }

    @Test
    @Transactional
    public void getAllChevresBySurnomIsNullOrNotNull() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where surnom is not null
        defaultChevreShouldBeFound("surnom.specified=true");

        // Get all the chevreList where surnom is null
        defaultChevreShouldNotBeFound("surnom.specified=false");
    }

    @Test
    @Transactional
    public void getAllChevresByNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where naissance equals to DEFAULT_NAISSANCE
        defaultChevreShouldBeFound("naissance.equals=" + DEFAULT_NAISSANCE);

        // Get all the chevreList where naissance equals to UPDATED_NAISSANCE
        defaultChevreShouldNotBeFound("naissance.equals=" + UPDATED_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChevresByNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where naissance in DEFAULT_NAISSANCE or UPDATED_NAISSANCE
        defaultChevreShouldBeFound("naissance.in=" + DEFAULT_NAISSANCE + "," + UPDATED_NAISSANCE);

        // Get all the chevreList where naissance equals to UPDATED_NAISSANCE
        defaultChevreShouldNotBeFound("naissance.in=" + UPDATED_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChevresByNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where naissance is not null
        defaultChevreShouldBeFound("naissance.specified=true");

        // Get all the chevreList where naissance is null
        defaultChevreShouldNotBeFound("naissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllChevresByNaissanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where naissance greater than or equals to DEFAULT_NAISSANCE
        defaultChevreShouldBeFound("naissance.greaterOrEqualThan=" + DEFAULT_NAISSANCE);

        // Get all the chevreList where naissance greater than or equals to UPDATED_NAISSANCE
        defaultChevreShouldNotBeFound("naissance.greaterOrEqualThan=" + UPDATED_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllChevresByNaissanceIsLessThanSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where naissance less than or equals to DEFAULT_NAISSANCE
        defaultChevreShouldNotBeFound("naissance.lessThan=" + DEFAULT_NAISSANCE);

        // Get all the chevreList where naissance less than or equals to UPDATED_NAISSANCE
        defaultChevreShouldBeFound("naissance.lessThan=" + UPDATED_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllChevresByPresentIsEqualToSomething() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where present equals to DEFAULT_PRESENT
        defaultChevreShouldBeFound("present.equals=" + DEFAULT_PRESENT);

        // Get all the chevreList where present equals to UPDATED_PRESENT
        defaultChevreShouldNotBeFound("present.equals=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllChevresByPresentIsInShouldWork() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where present in DEFAULT_PRESENT or UPDATED_PRESENT
        defaultChevreShouldBeFound("present.in=" + DEFAULT_PRESENT + "," + UPDATED_PRESENT);

        // Get all the chevreList where present equals to UPDATED_PRESENT
        defaultChevreShouldNotBeFound("present.in=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllChevresByPresentIsNullOrNotNull() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        // Get all the chevreList where present is not null
        defaultChevreShouldBeFound("present.specified=true");

        // Get all the chevreList where present is null
        defaultChevreShouldNotBeFound("present.specified=false");
    }

    @Test
    @Transactional
    public void getAllChevresByPereIsEqualToSomething() throws Exception {
        // Initialize the database
        Chevre pere = ChevreResourceIT.createEntity(em);
        em.persist(pere);
        em.flush();
        chevre.setPere(pere);
        chevreRepository.saveAndFlush(chevre);
        Long pereId = pere.getId();

        // Get all the chevreList where pere equals to pereId
        defaultChevreShouldBeFound("pereId.equals=" + pereId);

        // Get all the chevreList where pere equals to pereId + 1
        defaultChevreShouldNotBeFound("pereId.equals=" + (pereId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByMereIsEqualToSomething() throws Exception {
        // Initialize the database
        Chevre mere = ChevreResourceIT.createEntity(em);
        em.persist(mere);
        em.flush();
        chevre.setMere(mere);
        chevreRepository.saveAndFlush(chevre);
        Long mereId = mere.getId();

        // Get all the chevreList where mere equals to mereId
        defaultChevreShouldBeFound("mereId.equals=" + mereId);

        // Get all the chevreList where mere equals to mereId + 1
        defaultChevreShouldNotBeFound("mereId.equals=" + (mereId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByPoidsIsEqualToSomething() throws Exception {
        // Initialize the database
        Poids poids = PoidsResourceIT.createEntity(em);
        em.persist(poids);
        em.flush();
        chevre.addPoids(poids);
        chevreRepository.saveAndFlush(chevre);
        Long poidsId = poids.getId();

        // Get all the chevreList where poids equals to poidsId
        defaultChevreShouldBeFound("poidsId.equals=" + poidsId);

        // Get all the chevreList where poids equals to poidsId + 1
        defaultChevreShouldNotBeFound("poidsId.equals=" + (poidsId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByTailleIsEqualToSomething() throws Exception {
        // Initialize the database
        Taille taille = TailleResourceIT.createEntity(em);
        em.persist(taille);
        em.flush();
        chevre.addTaille(taille);
        chevreRepository.saveAndFlush(chevre);
        Long tailleId = taille.getId();

        // Get all the chevreList where taille equals to tailleId
        defaultChevreShouldBeFound("tailleId.equals=" + tailleId);

        // Get all the chevreList where taille equals to tailleId + 1
        defaultChevreShouldNotBeFound("tailleId.equals=" + (tailleId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByParcChevreIsEqualToSomething() throws Exception {
        // Initialize the database
        ParcChevre parcChevre = ParcChevreResourceIT.createEntity(em);
        em.persist(parcChevre);
        em.flush();
        chevre.addParcChevre(parcChevre);
        chevreRepository.saveAndFlush(chevre);
        Long parcChevreId = parcChevre.getId();

        // Get all the chevreList where parcChevre equals to parcChevreId
        defaultChevreShouldBeFound("parcChevreId.equals=" + parcChevreId);

        // Get all the chevreList where parcChevre equals to parcChevreId + 1
        defaultChevreShouldNotBeFound("parcChevreId.equals=" + (parcChevreId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByEvenementChevreIsEqualToSomething() throws Exception {
        // Initialize the database
        EvenementChevre evenementChevre = EvenementChevreResourceIT.createEntity(em);
        em.persist(evenementChevre);
        em.flush();
        chevre.addEvenementChevre(evenementChevre);
        chevreRepository.saveAndFlush(chevre);
        Long evenementChevreId = evenementChevre.getId();

        // Get all the chevreList where evenementChevre equals to evenementChevreId
        defaultChevreShouldBeFound("evenementChevreId.equals=" + evenementChevreId);

        // Get all the chevreList where evenementChevre equals to evenementChevreId + 1
        defaultChevreShouldNotBeFound("evenementChevreId.equals=" + (evenementChevreId + 1));
    }


    @Test
    @Transactional
    public void getAllChevresByChevreIsEqualToSomething() throws Exception {
        // Initialize the database
        Chevre chevre = ChevreResourceIT.createEntity(em);
        em.persist(chevre);
        em.flush();
         chevre.setPere(chevre);
        chevreRepository.saveAndFlush(chevre);
        Long chevreId = chevre.getId();

        // Get all the chevreList where chevre equals to chevreId
        defaultChevreShouldBeFound("chevreId.equals=" + chevreId);

        // Get all the chevreList where chevre equals to chevreId + 1
        defaultChevreShouldNotBeFound("chevreId.equals=" + (chevreId + 1));
    }


    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChevreShouldBeFound(String filter) throws Exception {
        restChevreMockMvc.perform(get("/api/chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chevre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].surnom").value(hasItem(DEFAULT_SURNOM)))
            .andExpect(jsonPath("$.[*].naissance").value(hasItem(DEFAULT_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].present").value(hasItem(DEFAULT_PRESENT.booleanValue())));

        // Check, that the count call also returns 1
        restChevreMockMvc.perform(get("/api/chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChevreShouldNotBeFound(String filter) throws Exception {
        restChevreMockMvc.perform(get("/api/chevres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChevreMockMvc.perform(get("/api/chevres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChevre() throws Exception {
        // Get the chevre
        restChevreMockMvc.perform(get("/api/chevres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChevre() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        int databaseSizeBeforeUpdate = chevreRepository.findAll().size();

        // Update the chevre
        Chevre updatedChevre = chevreRepository.findById(chevre.getId()).get();
        // Disconnect from session so that the updates on updatedChevre are not directly saved in db
        em.detach(updatedChevre);
        updatedChevre
            .nom(UPDATED_NOM)
            .matricule(UPDATED_MATRICULE)
            .surnom(UPDATED_SURNOM)
            .naissance(UPDATED_NAISSANCE)
            .present(UPDATED_PRESENT);
        ChevreDTO chevreDTO = chevreMapper.toDto(updatedChevre);

        restChevreMockMvc.perform(put("/api/chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chevreDTO)))
            .andExpect(status().isOk());

        // Validate the Chevre in the database
        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeUpdate);
        Chevre testChevre = chevreList.get(chevreList.size() - 1);
        assertThat(testChevre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testChevre.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testChevre.getSurnom()).isEqualTo(UPDATED_SURNOM);
        assertThat(testChevre.getNaissance()).isEqualTo(UPDATED_NAISSANCE);
        assertThat(testChevre.isPresent()).isEqualTo(UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void updateNonExistingChevre() throws Exception {
        int databaseSizeBeforeUpdate = chevreRepository.findAll().size();

        // Create the Chevre
        ChevreDTO chevreDTO = chevreMapper.toDto(chevre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChevreMockMvc.perform(put("/api/chevres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chevreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chevre in the database
        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChevre() throws Exception {
        // Initialize the database
        chevreRepository.saveAndFlush(chevre);

        int databaseSizeBeforeDelete = chevreRepository.findAll().size();

        // Delete the chevre
        restChevreMockMvc.perform(delete("/api/chevres/{id}", chevre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chevre> chevreList = chevreRepository.findAll();
        assertThat(chevreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chevre.class);
        Chevre chevre1 = new Chevre();
        chevre1.setId(1L);
        Chevre chevre2 = new Chevre();
        chevre2.setId(chevre1.getId());
        assertThat(chevre1).isEqualTo(chevre2);
        chevre2.setId(2L);
        assertThat(chevre1).isNotEqualTo(chevre2);
        chevre1.setId(null);
        assertThat(chevre1).isNotEqualTo(chevre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChevreDTO.class);
        ChevreDTO chevreDTO1 = new ChevreDTO();
        chevreDTO1.setId(1L);
        ChevreDTO chevreDTO2 = new ChevreDTO();
        assertThat(chevreDTO1).isNotEqualTo(chevreDTO2);
        chevreDTO2.setId(chevreDTO1.getId());
        assertThat(chevreDTO1).isEqualTo(chevreDTO2);
        chevreDTO2.setId(2L);
        assertThat(chevreDTO1).isNotEqualTo(chevreDTO2);
        chevreDTO1.setId(null);
        assertThat(chevreDTO1).isNotEqualTo(chevreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chevreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chevreMapper.fromId(null)).isNull();
    }
}
