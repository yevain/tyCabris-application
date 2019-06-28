import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEvenementChevre, EvenementChevre } from 'app/shared/model/evenement-chevre.model';
import { EvenementChevreService } from './evenement-chevre.service';
import { IEvenement } from 'app/shared/model/evenement.model';
import { EvenementService } from 'app/entities/evenement';
import { IChevre } from 'app/shared/model/chevre.model';
import { ChevreService } from 'app/entities/chevre';

@Component({
  selector: 'jhi-evenement-chevre-update',
  templateUrl: './evenement-chevre-update.component.html'
})
export class EvenementChevreUpdateComponent implements OnInit {
  isSaving: boolean;

  evenements: IEvenement[];

  chevres: IChevre[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    evenementId: [null, Validators.required],
    chevreId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected evenementChevreService: EvenementChevreService,
    protected evenementService: EvenementService,
    protected chevreService: ChevreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evenementChevre }) => {
      this.updateForm(evenementChevre);
    });
    this.evenementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvenement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvenement[]>) => response.body)
      )
      .subscribe((res: IEvenement[]) => (this.evenements = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.chevreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe((res: IChevre[]) => (this.chevres = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(evenementChevre: IEvenementChevre) {
    this.editForm.patchValue({
      id: evenementChevre.id,
      date: evenementChevre.date,
      evenementId: evenementChevre.evenementId,
      chevreId: evenementChevre.chevreId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const evenementChevre = this.createFromForm();
    if (evenementChevre.id !== undefined) {
      this.subscribeToSaveResponse(this.evenementChevreService.update(evenementChevre));
    } else {
      this.subscribeToSaveResponse(this.evenementChevreService.create(evenementChevre));
    }
  }

  private createFromForm(): IEvenementChevre {
    return {
      ...new EvenementChevre(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      evenementId: this.editForm.get(['evenementId']).value,
      chevreId: this.editForm.get(['chevreId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvenementChevre>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEvenementById(index: number, item: IEvenement) {
    return item.id;
  }

  trackChevreById(index: number, item: IChevre) {
    return item.id;
  }
}
