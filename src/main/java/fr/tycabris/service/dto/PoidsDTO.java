package fr.tycabris.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tycabris.domain.Poids} entity.
 */
public class PoidsDTO implements Serializable {

    private Long id;

    @NotNull
    private Float valeur;

    @NotNull
    private LocalDate date;


    private Long chevreId;

    private String chevreNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValeur() {
        return valeur;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

        PoidsDTO poidsDTO = (PoidsDTO) o;
        if (poidsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poidsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoidsDTO{" +
            "id=" + getId() +
            ", valeur=" + getValeur() +
            ", date='" + getDate() + "'" +
            ", chevre=" + getChevreId() +
            ", chevre='" + getChevreNom() + "'" +
            "}";
    }
}
