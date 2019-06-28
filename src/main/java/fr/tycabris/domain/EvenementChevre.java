package fr.tycabris.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EvenementChevre.
 */
@Entity
@Table(name = "evenement_chevre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EvenementChevre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("evenementChevres")
    private Evenement evenement;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("evenementChevres")
    private Chevre chevre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public EvenementChevre date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public EvenementChevre evenement(Evenement evenement) {
        this.evenement = evenement;
        return this;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Chevre getChevre() {
        return chevre;
    }

    public EvenementChevre chevre(Chevre chevre) {
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
        if (!(o instanceof EvenementChevre)) {
            return false;
        }
        return id != null && id.equals(((EvenementChevre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EvenementChevre{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
