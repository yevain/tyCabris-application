package fr.tycabris.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Parc.
 */
@Entity
@Table(name = "parc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @OneToMany(mappedBy = "parc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParcChevre> parcChevres = new HashSet<>();

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

    public Parc nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<ParcChevre> getParcChevres() {
        return parcChevres;
    }

    public Parc parcChevres(Set<ParcChevre> parcChevres) {
        this.parcChevres = parcChevres;
        return this;
    }

    public Parc addParcChevre(ParcChevre parcChevre) {
        this.parcChevres.add(parcChevre);
        parcChevre.setParc(this);
        return this;
    }

    public Parc removeParcChevre(ParcChevre parcChevre) {
        this.parcChevres.remove(parcChevre);
        parcChevre.setParc(null);
        return this;
    }

    public void setParcChevres(Set<ParcChevre> parcChevres) {
        this.parcChevres = parcChevres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parc)) {
            return false;
        }
        return id != null && id.equals(((Parc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Parc{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
