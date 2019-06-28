package fr.tycabris.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fr.tycabris.domain.Evenement} entity. This class is used
 * in {@link fr.tycabris.web.rest.EvenementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evenements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvenementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private IntegerFilter occurence;

    private LongFilter suivantId;

    private LongFilter evenementChevreId;

    private LongFilter evenementId;

    public EvenementCriteria(){
    }

    public EvenementCriteria(EvenementCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.occurence = other.occurence == null ? null : other.occurence.copy();
        this.suivantId = other.suivantId == null ? null : other.suivantId.copy();
        this.evenementChevreId = other.evenementChevreId == null ? null : other.evenementChevreId.copy();
        this.evenementId = other.evenementId == null ? null : other.evenementId.copy();
    }

    @Override
    public EvenementCriteria copy() {
        return new EvenementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public IntegerFilter getOccurence() {
        return occurence;
    }

    public void setOccurence(IntegerFilter occurence) {
        this.occurence = occurence;
    }

    public LongFilter getSuivantId() {
        return suivantId;
    }

    public void setSuivantId(LongFilter suivantId) {
        this.suivantId = suivantId;
    }

    public LongFilter getEvenementChevreId() {
        return evenementChevreId;
    }

    public void setEvenementChevreId(LongFilter evenementChevreId) {
        this.evenementChevreId = evenementChevreId;
    }

    public LongFilter getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(LongFilter evenementId) {
        this.evenementId = evenementId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EvenementCriteria that = (EvenementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(occurence, that.occurence) &&
            Objects.equals(suivantId, that.suivantId) &&
            Objects.equals(evenementChevreId, that.evenementChevreId) &&
            Objects.equals(evenementId, that.evenementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        occurence,
        suivantId,
        evenementChevreId,
        evenementId
        );
    }

    @Override
    public String toString() {
        return "EvenementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (occurence != null ? "occurence=" + occurence + ", " : "") +
                (suivantId != null ? "suivantId=" + suivantId + ", " : "") +
                (evenementChevreId != null ? "evenementChevreId=" + evenementChevreId + ", " : "") +
                (evenementId != null ? "evenementId=" + evenementId + ", " : "") +
            "}";
    }

}
