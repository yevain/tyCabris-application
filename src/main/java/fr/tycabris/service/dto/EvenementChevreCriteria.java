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
 * Criteria class for the {@link fr.tycabris.domain.EvenementChevre} entity. This class is used
 * in {@link fr.tycabris.web.rest.EvenementChevreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evenement-chevres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvenementChevreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private LongFilter evenementId;

    private LongFilter chevreId;

    public EvenementChevreCriteria(){
    }

    public EvenementChevreCriteria(EvenementChevreCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.evenementId = other.evenementId == null ? null : other.evenementId.copy();
        this.chevreId = other.chevreId == null ? null : other.chevreId.copy();
    }

    @Override
    public EvenementChevreCriteria copy() {
        return new EvenementChevreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(LongFilter evenementId) {
        this.evenementId = evenementId;
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
        final EvenementChevreCriteria that = (EvenementChevreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(evenementId, that.evenementId) &&
            Objects.equals(chevreId, that.chevreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        evenementId,
        chevreId
        );
    }

    @Override
    public String toString() {
        return "EvenementChevreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (evenementId != null ? "evenementId=" + evenementId + ", " : "") +
                (chevreId != null ? "chevreId=" + chevreId + ", " : "") +
            "}";
    }

}
