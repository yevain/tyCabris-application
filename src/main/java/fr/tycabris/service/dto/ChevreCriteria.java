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
 * Criteria class for the {@link fr.tycabris.domain.Chevre} entity. This class is used
 * in {@link fr.tycabris.web.rest.ChevreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chevres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChevreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter matricule;

    private StringFilter surnom;

    private LocalDateFilter naissance;

    private BooleanFilter present;

    private LongFilter pereId;

    private LongFilter mereId;

    private LongFilter poidsId;

    private LongFilter tailleId;

    private LongFilter parcChevreId;

    private LongFilter evenementChevreId;

    public ChevreCriteria(){
    }

    public ChevreCriteria(ChevreCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.surnom = other.surnom == null ? null : other.surnom.copy();
        this.naissance = other.naissance == null ? null : other.naissance.copy();
        this.present = other.present == null ? null : other.present.copy();
        this.pereId = other.pereId == null ? null : other.pereId.copy();
        this.mereId = other.mereId == null ? null : other.mereId.copy();
        this.poidsId = other.poidsId == null ? null : other.poidsId.copy();
        this.tailleId = other.tailleId == null ? null : other.tailleId.copy();
        this.parcChevreId = other.parcChevreId == null ? null : other.parcChevreId.copy();
        this.evenementChevreId = other.evenementChevreId == null ? null : other.evenementChevreId.copy();
    }

    @Override
    public ChevreCriteria copy() {
        return new ChevreCriteria(this);
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

    public StringFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
    }

    public StringFilter getSurnom() {
        return surnom;
    }

    public void setSurnom(StringFilter surnom) {
        this.surnom = surnom;
    }

    public LocalDateFilter getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDateFilter naissance) {
        this.naissance = naissance;
    }

    public BooleanFilter getPresent() {
        return present;
    }

    public void setPresent(BooleanFilter present) {
        this.present = present;
    }

    public LongFilter getPereId() {
        return pereId;
    }

    public void setPereId(LongFilter pereId) {
        this.pereId = pereId;
    }

    public LongFilter getMereId() {
        return mereId;
    }

    public void setMereId(LongFilter mereId) {
        this.mereId = mereId;
    }

    public LongFilter getPoidsId() {
        return poidsId;
    }

    public void setPoidsId(LongFilter poidsId) {
        this.poidsId = poidsId;
    }

    public LongFilter getTailleId() {
        return tailleId;
    }

    public void setTailleId(LongFilter tailleId) {
        this.tailleId = tailleId;
    }

    public LongFilter getParcChevreId() {
        return parcChevreId;
    }

    public void setParcChevreId(LongFilter parcChevreId) {
        this.parcChevreId = parcChevreId;
    }

    public LongFilter getEvenementChevreId() {
        return evenementChevreId;
    }

    public void setEvenementChevreId(LongFilter evenementChevreId) {
        this.evenementChevreId = evenementChevreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChevreCriteria that = (ChevreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(surnom, that.surnom) &&
            Objects.equals(naissance, that.naissance) &&
            Objects.equals(present, that.present) &&
            Objects.equals(pereId, that.pereId) &&
            Objects.equals(mereId, that.mereId) &&
            Objects.equals(poidsId, that.poidsId) &&
            Objects.equals(tailleId, that.tailleId) &&
            Objects.equals(parcChevreId, that.parcChevreId) &&
            Objects.equals(evenementChevreId, that.evenementChevreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        matricule,
        surnom,
        naissance,
        present,
        pereId,
        mereId,
        poidsId,
        tailleId,
        parcChevreId,
        evenementChevreId
        );
    }

    @Override
    public String toString() {
        return "ChevreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (surnom != null ? "surnom=" + surnom + ", " : "") +
                (naissance != null ? "naissance=" + naissance + ", " : "") +
                (present != null ? "present=" + present + ", " : "") +
                (pereId != null ? "pereId=" + pereId + ", " : "") +
                (mereId != null ? "mereId=" + mereId + ", " : "") +
                (poidsId != null ? "poidsId=" + poidsId + ", " : "") +
                (tailleId != null ? "tailleId=" + tailleId + ", " : "") +
                (parcChevreId != null ? "parcChevreId=" + parcChevreId + ", " : "") +
                (evenementChevreId != null ? "evenementChevreId=" + evenementChevreId + ", " : "") +
            "}";
    }

}
