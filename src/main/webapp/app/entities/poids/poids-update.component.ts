import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPoids, Poids } from 'app/shared/model/poids.model';
import { PoidsService } from './poids.service';
import { IChevre } from 'app/shared/model/chevre.model';
import { ChevreService } from 'app/entities/chevre';

@Component({
  selector: 'jhi-poids-update',
  templateUrl: './poids-update.component.html'
})
export class PoidsUpdateComponent implements OnInit {
  isSaving: boolean;

  chevres: IChevre[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    valeur: [null, [Validators.required]],
    date: [null, [Validators.required]],
    chevreId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected poidsService: PoidsService,
    protected chevreService: ChevreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ poids }) => {
      this.updateForm(poids);
    });
    this.chevreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe((res: IChevre[]) => (this.chevres = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(poids: IPoids) {
    this.editForm.patchValue({
      id: poids.id,
      valeur: poids.valeur,
      date: poids.date,
      chevreId: poids.chevreId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const poids = this.createFromForm();
    if (poids.id !== undefined) {
      this.subscribeToSaveResponse(this.poidsService.update(poids));
    } else {
      this.subscribeToSaveResponse(this.poidsService.create(poids));
    }
  }

  private createFromForm(): IPoids {
    return {
      ...new Poids(),
      id: this.editForm.get(['id']).value,
      valeur: this.editForm.get(['valeur']).value,
      date: this.editForm.get(['date']).value,
      chevreId: this.editForm.get(['chevreId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoids>>) {
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

  trackChevreById(index: number, item: IChevre) {
    return item.id;
  }
}
