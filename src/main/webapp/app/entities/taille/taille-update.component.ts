import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITaille, Taille } from 'app/shared/model/taille.model';
import { TailleService } from './taille.service';
import { IChevre } from 'app/shared/model/chevre.model';
import { ChevreService } from 'app/entities/chevre';

@Component({
  selector: 'jhi-taille-update',
  templateUrl: './taille-update.component.html'
})
export class TailleUpdateComponent implements OnInit {
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
    protected tailleService: TailleService,
    protected chevreService: ChevreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taille }) => {
      this.updateForm(taille);
    });
    this.chevreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe((res: IChevre[]) => (this.chevres = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(taille: ITaille) {
    this.editForm.patchValue({
      id: taille.id,
      valeur: taille.valeur,
      date: taille.date,
      chevreId: taille.chevreId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const taille = this.createFromForm();
    if (taille.id !== undefined) {
      this.subscribeToSaveResponse(this.tailleService.update(taille));
    } else {
      this.subscribeToSaveResponse(this.tailleService.create(taille));
    }
  }

  private createFromForm(): ITaille {
    return {
      ...new Taille(),
      id: this.editForm.get(['id']).value,
      valeur: this.editForm.get(['valeur']).value,
      date: this.editForm.get(['date']).value,
      chevreId: this.editForm.get(['chevreId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaille>>) {
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
