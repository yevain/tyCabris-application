package fr.tycabris.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tycabris.domain.Chevre} entity.
 */
public class ChevreDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String matricule;

    private String surnom;

    private LocalDate naissance;

    private Boolean present;


    private Long pereId;

    private Long mereId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public Boolean isPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Long getPereId() {
        return pereId;
    }

    public void setPereId(Long chevreId) {
        this.pereId = chevreId;
    }

    public Long getMereId() {
        return mereId;
    }

    public void setMereId(Long chevreId) {
        this.mereId = chevreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChevreDTO chevreDTO = (ChevreDTO) o;
        if (chevreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chevreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChevreDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", surnom='" + getSurnom() + "'" +
            ", naissance='" + getNaissance() + "'" +
            ", present='" + isPresent() + "'" +
            ", pere=" + getPereId() +
            ", mere=" + getMereId() +
            "}";
    }
}
