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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link fr.tycabris.domain.Taille} entity. This class is used
 * in {@link fr.tycabris.web.rest.TailleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tailles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TailleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter valeur;

    private LocalDateFilter date;

    private LongFilter chevreId;

    public TailleCriteria(){
    }

    public TailleCriteria(TailleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valeur = other.valeur == null ? null : other.valeur.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.chevreId = other.chevreId == null ? null : other.chevreId.copy();
    }

    @Override
    public TailleCriteria copy() {
        return new TailleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getValeur() {
        return valeur;
    }

    public void setValeur(FloatFilter valeur) {
        this.valeur = valeur;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getChevreId() {
        return chevreId;
    }

    public void setChevreId(LongFilter chevreId) {
        this.chevreId = chevreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TailleCriteria that = (TailleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(date, that.date) &&
            Objects.equals(chevreId, that.chevreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valeur,
        date,
        chevreId
        );
    }

    @Override
    public String toString() {
        return "TailleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (chevreId != null ? "chevreId=" + chevreId + ", " : "") +
            "}";
    }

}
