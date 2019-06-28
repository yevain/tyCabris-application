package fr.tycabris.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chevre.
 */
@Entity
@Table(name = "chevre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chevre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "surnom")
    private String surnom;

    @Column(name = "naissance")
    private LocalDate naissance;

    @Column(name = "present")
    private Boolean present;

    @OneToOne
    @JoinColumn(unique = true)
    private Chevre pere;

    @OneToOne
    @JoinColumn(unique = true)
    private Chevre mere;

    @OneToMany(mappedBy = "chevre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Poids> poids = new HashSet<>();

    @OneToMany(mappedBy = "chevre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Taille> tailles = new HashSet<>();

    @OneToMany(mappedBy = "chevre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParcChevre> parcChevres = new HashSet<>();

    @OneToMany(mappedBy = "chevre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EvenementChevre> evenementChevres = new HashSet<>();


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Chevre nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public Chevre matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSurnom() {
        return surnom;
    }

    public Chevre surnom(String surnom) {
        this.surnom = surnom;
        return this;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public Chevre naissance(LocalDate naissance) {
        this.naissance = naissance;
        return this;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public Boolean isPresent() {
        return present;
    }

    public Chevre present(Boolean present) {
        this.present = present;
        return this;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Chevre getPere() {
        return pere;
    }

    public Chevre pere(Chevre chevre) {
        this.pere = chevre;
        return this;
    }

    public void setPere(Chevre chevre) {
        this.pere = chevre;
    }

    public Chevre getMere() {
        return mere;
    }

    public Chevre mere(Chevre chevre) {
        this.mere = chevre;
        return this;
    }

    public void setMere(Chevre chevre) {
        this.mere = chevre;
    }

    public Set<Poids> getPoids() {
        return poids;
    }

    public Chevre poids(Set<Poids> poids) {
        this.poids = poids;
        return this;
    }

    public Chevre addPoids(Poids poids) {
        this.poids.add(poids);
        poids.setChevre(this);
        return this;
    }

    public Chevre removePoids(Poids poids) {
        this.poids.remove(poids);
        poids.setChevre(null);
        return this;
    }

    public void setPoids(Set<Poids> poids) {
        this.poids = poids;
    }

    public Set<Taille> getTailles() {
        return tailles;
    }

    public Chevre tailles(Set<Taille> tailles) {
        this.tailles = tailles;
        return this;
    }

    public Chevre addTaille(Taille taille) {
        this.tailles.add(taille);
        taille.setChevre(this);
        return this;
    }

    public Chevre removeTaille(Taille taille) {
        this.tailles.remove(taille);
        taille.setChevre(null);
        return this;
    }

    public void setTailles(Set<Taille> tailles) {
        this.tailles = tailles;
    }

    public Set<ParcChevre> getParcChevres() {
        return parcChevres;
    }

    public Chevre parcChevres(Set<ParcChevre> parcChevres) {
        this.parcChevres = parcChevres;
        return this;
    }

    public Chevre addParcChevre(ParcChevre parcChevre) {
        this.parcChevres.add(parcChevre);
        parcChevre.setChevre(this);
        return this;
    }

    public Chevre removeParcChevre(ParcChevre parcChevre) {
        this.parcChevres.remove(parcChevre);
        parcChevre.setChevre(null);
        return this;
    }

    public void setParcChevres(Set<ParcChevre> parcChevres) {
        this.parcChevres = parcChevres;
    }

    public Set<EvenementChevre> getEvenementChevres() {
        return evenementChevres;
    }

    public Chevre evenementChevres(Set<EvenementChevre> evenementChevres) {
        this.evenementChevres = evenementChevres;
        return this;
    }

    public Chevre addEvenementChevre(EvenementChevre evenementChevre) {
        this.evenementChevres.add(evenementChevre);
        evenementChevre.setChevre(this);
        return this;
    }

    public Chevre removeEvenementChevre(EvenementChevre evenementChevre) {
        this.evenementChevres.remove(evenementChevre);
        evenementChevre.setChevre(null);
        return this;
    }

    public void setEvenementChevres(Set<EvenementChevre> evenementChevres) {
        this.evenementChevres = evenementChevres;
    }

 
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chevre)) {
            return false;
        }
        return id != null && id.equals(((Chevre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chevre{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", surnom='" + getSurnom() + "'" +
            ", naissance='" + getNaissance() + "'" +
            ", present='" + isPresent() + "'" +
            "}";
    }
}
