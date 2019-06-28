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
 * Criteria class for the {@link fr.tycabris.domain.Parc} entity. This class is used
 * in {@link fr.tycabris.web.rest.ParcResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parcs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParcCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private LongFilter parcChevreId;

    public ParcCriteria(){
    }

    public ParcCriteria(ParcCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.parcChevreId = other.parcChevreId == null ? null : other.parcChevreId.copy();
    }

    @Override
    public ParcCriteria copy() {
        return new ParcCriteria(this);
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

    public LongFilter getParcChevreId() {
        return parcChevreId;
    }

    public void setParcChevreId(LongFilter parcChevreId) {
        this.parcChevreId = parcChevreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParcCriteria that = (ParcCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(parcChevreId, that.parcChevreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        parcChevreId
        );
    }

    @Override
    public String toString() {
        return "ParcCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (parcChevreId != null ? "parcChevreId=" + parcChevreId + ", " : "") +
            "}";
    }

}
