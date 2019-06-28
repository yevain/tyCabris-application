package fr.tycabris.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tycabris.domain.ParcChevre} entity.
 */
public class ParcChevreDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate entree;

    private LocalDate sortie;


    private Long parcId;

    private String parcNom;

    private Long chevreId;

    private String chevreNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntree() {
        return entree;
    }

    public void setEntree(LocalDate entree) {
        this.entree = entree;
    }

    public LocalDate getSortie() {
        return sortie;
    }

    public void setSortie(LocalDate sortie) {
        this.sortie = sortie;
    }

    public Long getParcId() {
        return parcId;
    }

    public void setParcId(Long parcId) {
        this.parcId = parcId;
    }

    public String getParcNom() {
        return parcNom;
    }

    public void setParcNom(String parcNom) {
        this.parcNom = parcNom;
    }

    public Long getChevreId() {
        return chevreId;
    }

    public void setChevreId(Long chevreId) {
        this.chevreId = chevreId;
    }

    public String getChevreNom() {
        return chevreNom;
    }

    public void setChevreNom(String chevreNom) {
        this.chevreNom = chevreNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParcChevreDTO parcChevreDTO = (ParcChevreDTO) o;
        if (parcChevreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parcChevreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParcChevreDTO{" +
            "id=" + getId() +
            ", entree='" + getEntree() + "'" +
            ", sortie='" + getSortie() + "'" +
            ", parc=" + getParcId() +
            ", parc='" + getParcNom() + "'" +
            ", chevre=" + getChevreId() +
            ", chevre='" + getChevreNom() + "'" +
            "}";
    }
}
