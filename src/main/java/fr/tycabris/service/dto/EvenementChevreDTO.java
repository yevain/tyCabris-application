package fr.tycabris.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tycabris.domain.EvenementChevre} entity.
 */
public class EvenementChevreDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;


    private Long evenementId;

    private String evenementNom;

    private Long chevreId;

    private String chevreNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

        EvenementChevreDTO evenementChevreDTO = (EvenementChevreDTO) o;
        if (evenementChevreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evenementChevreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvenementChevreDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", evenement=" + getEvenementId() +
            ", evenement='" + getEvenementNom() + "'" +
            ", chevre=" + getChevreId() +
            ", chevre='" + getChevreNom() + "'" +
            "}";
    }
}
