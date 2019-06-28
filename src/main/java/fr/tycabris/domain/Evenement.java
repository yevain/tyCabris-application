package fr.tycabris.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Evenement.
 */
@Entity
@Table(name = "evenement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "occurence")
    private Integer occurence;

    @OneToMany(mappedBy = "evenement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evenement> suivants = new HashSet<>();

    @OneToMany(mappedBy = "evenement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EvenementChevre> evenementChevres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("suivants")
    private Evenement evenement;

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

    public Evenement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public Evenement occurence(Integer occurence) {
        this.occurence = occurence;
        return this;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public Set<Evenement> getSuivants() {
        return suivants;
    }

    public Evenement suivants(Set<Evenement> evenements) {
        this.suivants = evenements;
        return this;
    }

    public Evenement addSuivant(Evenement evenement) {
        this.suivants.add(evenement);
        evenement.setEvenement(this);
        return this;
    }

    public Evenement removeSuivant(Evenement evenement) {
        this.suivants.remove(evenement);
        evenement.setEvenement(null);
        return this;
    }

    public void setSuivants(Set<Evenement> evenements) {
        this.suivants = evenements;
    }

    public Set<EvenementChevre> getEvenementChevres() {
        return evenementChevres;
    }

    public Evenement evenementChevres(Set<EvenementChevre> evenementChevres) {
        this.evenementChevres = evenementChevres;
        return this;
    }

    public Evenement addEvenementChevre(EvenementChevre evenementChevre) {
        this.evenementChevres.add(evenementChevre);
        evenementChevre.setEvenement(this);
        return this;
    }

    public Evenement removeEvenementChevre(EvenementChevre evenementChevre) {
        this.evenementChevres.remove(evenementChevre);
        evenementChevre.setEvenement(null);
        return this;
    }

    public void setEvenementChevres(Set<EvenementChevre> evenementChevres) {
        this.evenementChevres = evenementChevres;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public Evenement evenement(Evenement evenement) {
        this.evenement = evenement;
        return this;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evenement)) {
            return false;
        }
        return id != null && id.equals(((Evenement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evenement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", occurence=" + getOccurence() +
            "}";
    }
}
