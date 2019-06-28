package fr.tycabris.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ParcChevre.
 */
@Entity
@Table(name = "parc_chevre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParcChevre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "entree", nullable = false)
    private LocalDate entree;

    @Column(name = "sortie")
    private LocalDate sortie;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parcChevres")
    private Parc parc;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parcChevres")
    private Chevre chevre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntree() {
        return entree;
    }

    public ParcChevre entree(LocalDate entree) {
        this.entree = entree;
        return this;
    }

    public void setEntree(LocalDate entree) {
        this.entree = entree;
    }

    public LocalDate getSortie() {
        return sortie;
    }

    public ParcChevre sortie(LocalDate sortie) {
        this.sortie = sortie;
        return this;
    }

    public void setSortie(LocalDate sortie) {
        this.sortie = sortie;
    }

    public Parc getParc() {
        return parc;
    }

    public ParcChevre parc(Parc parc) {
        this.parc = parc;
        return this;
    }

    public void setParc(Parc parc) {
        this.parc = parc;
    }

    public Chevre getChevre() {
        return chevre;
    }

    public ParcChevre chevre(Chevre chevre) {
        this.chevre = chevre;
        return this;
    }

    public void setChevre(Chevre chevre) {
        this.chevre = chevre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParcChevre)) {
            return false;
        }
        return id != null && id.equals(((ParcChevre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParcChevre{" +
            "id=" + getId() +
            ", entree='" + getEntree() + "'" +
            ", sortie='" + getSortie() + "'" +
            "}";
    }
}
