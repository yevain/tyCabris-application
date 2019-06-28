import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IParcChevre, ParcChevre } from 'app/shared/model/parc-chevre.model';
import { ParcChevreService } from './parc-chevre.service';
import { IParc } from 'app/shared/model/parc.model';
import { ParcService } from 'app/entities/parc';
import { IChevre } from 'app/shared/model/chevre.model';
import { ChevreService } from 'app/entities/chevre';

@Component({
  selector: 'jhi-parc-chevre-update',
  templateUrl: './parc-chevre-update.component.html'
})
export class ParcChevreUpdateComponent implements OnInit {
  isSaving: boolean;

  parcs: IParc[];

  chevres: IChevre[];
  entreeDp: any;
  sortieDp: any;

  editForm = this.fb.group({
    id: [],
    entree: [null, [Validators.required]],
    sortie: [],
    parcId: [null, Validators.required],
    chevreId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected parcChevreService: ParcChevreService,
    protected parcService: ParcService,
    protected chevreService: ChevreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ parcChevre }) => {
      this.updateForm(parcChevre);
    });
    this.parcService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IParc[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParc[]>) => response.body)
      )
      .subscribe((res: IParc[]) => (this.parcs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.chevreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe((res: IChevre[]) => (this.chevres = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(parcChevre: IParcChevre) {
    this.editForm.patchValue({
      id: parcChevre.id,
      entree: parcChevre.entree,
      sortie: parcChevre.sortie,
      parcId: parcChevre.parcId,
      chevreId: parcChevre.chevreId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const parcChevre = this.createFromForm();
    if (parcChevre.id !== undefined) {
      this.subscribeToSaveResponse(this.parcChevreService.update(parcChevre));
    } else {
      this.subscribeToSaveResponse(this.parcChevreService.create(parcChevre));
    }
  }

  private createFromForm(): IParcChevre {
    return {
      ...new ParcChevre(),
      id: this.editForm.get(['id']).value,
      entree: this.editForm.get(['entree']).value,
      sortie: this.editForm.get(['sortie']).value,
      parcId: this.editForm.get(['parcId']).value,
      chevreId: this.editForm.get(['chevreId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParcChevre>>) {
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

  trackParcById(index: number, item: IParc) {
    return item.id;
  }

  trackChevreById(index: number, item: IChevre) {
    return item.id;
  }
}
