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
 * Criteria class for the {@link fr.tycabris.domain.ParcChevre} entity. This class is used
 * in {@link fr.tycabris.web.rest.ParcChevreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parc-chevres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParcChevreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter entree;

    private LocalDateFilter sortie;

    private LongFilter parcId;

    private LongFilter chevreId;

    public ParcChevreCriteria(){
    }

    public ParcChevreCriteria(ParcChevreCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.entree = other.entree == null ? null : other.entree.copy();
        this.sortie = other.sortie == null ? null : other.sortie.copy();
        this.parcId = other.parcId == null ? null : other.parcId.copy();
        this.chevreId = other.chevreId == null ? null : other.chevreId.copy();
    }

    @Override
    public ParcChevreCriteria copy() {
        return new ParcChevreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getEntree() {
        return entree;
    }

    public void setEntree(LocalDateFilter entree) {
        this.entree = entree;
    }

    public LocalDateFilter getSortie() {
        return sortie;
    }

    public void setSortie(LocalDateFilter sortie) {
        this.sortie = sortie;
    }

    public LongFilter getParcId() {
        return parcId;
    }

    public void setParcId(LongFilter parcId) {
        this.parcId = parcId;
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
        final ParcChevreCriteria that = (ParcChevreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entree, that.entree) &&
            Objects.equals(sortie, that.sortie) &&
            Objects.equals(parcId, that.parcId) &&
            Objects.equals(chevreId, that.chevreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entree,
        sortie,
        parcId,
        chevreId
        );
    }

    @Override
    public String toString() {
        return "ParcChevreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entree != null ? "entree=" + entree + ", " : "") +
                (sortie != null ? "sortie=" + sortie + ", " : "") +
                (parcId != null ? "parcId=" + parcId + ", " : "") +
                (chevreId != null ? "chevreId=" + chevreId + ", " : "") +
            "}";
    }

}
