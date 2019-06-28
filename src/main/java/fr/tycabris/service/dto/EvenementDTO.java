package fr.tycabris.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tycabris.domain.Evenement} entity.
 */
public class EvenementDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private Integer occurence;


    private Long evenementId;

    private String evenementNom;

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

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public String getEvenementNom() {
        return evenementNom;
    }

    public void setEvenementNom(String evenementNom) {
        this.evenementNom = evenementNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvenementDTO evenementDTO = (EvenementDTO) o;
        if (evenementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evenementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvenementDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", occurence=" + getOccurence() +
            ", evenement=" + getEvenementId() +
            ", evenement='" + getEvenementNom() + "'" +
            "}";
    }
}
