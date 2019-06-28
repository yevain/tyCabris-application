package fr.tycabris.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Poids.
 */
@Entity
@Table(name = "poids")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poids implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private Float valeur;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("poids")
    private Chevre chevre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValeur() {
        return valeur;
    }

    public Poids valeur(Float valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    public LocalDate getDate() {
        return date;
    }

    public Poids date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Chevre getChevre() {
        return chevre;
    }

    public Poids chevre(Chevre chevre) {
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
        if (!(o instanceof Poids)) {
            return false;
        }
        return id != null && id.equals(((Poids) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Poids{" +
            "id=" + getId() +
            ", valeur=" + getValeur() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
