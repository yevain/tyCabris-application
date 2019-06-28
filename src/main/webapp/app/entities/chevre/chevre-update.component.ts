import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IChevre, Chevre } from 'app/shared/model/chevre.model';
import { ChevreService } from './chevre.service';

@Component({
  selector: 'jhi-chevre-update',
  templateUrl: './chevre-update.component.html'
})
export class ChevreUpdateComponent implements OnInit {
  isSaving: boolean;

  peres: IChevre[];

  meres: IChevre[];

  chevres: IChevre[];
  naissanceDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    matricule: [],
    surnom: [],
    naissance: [],
    present: [],
    pereId: [],
    mereId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected chevreService: ChevreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ chevre }) => {
      this.updateForm(chevre);
    });
    this.chevreService
      .query({ 'chevreId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe(
        (res: IChevre[]) => {
          if (!!this.editForm.get('pereId').value) {
            this.peres = res;
          } else {
            this.chevreService
              .find(this.editForm.get('pereId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IChevre>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IChevre>) => subResponse.body)
              )
              .subscribe(
                (subRes: IChevre) => (this.peres = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.chevreService
      .query({ 'chevreId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe(
        (res: IChevre[]) => {
          if (!!this.editForm.get('mereId').value) {
            this.meres = res;
          } else {
            this.chevreService
              .find(this.editForm.get('mereId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IChevre>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IChevre>) => subResponse.body)
              )
              .subscribe(
                (subRes: IChevre) => (this.meres = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.chevreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChevre[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChevre[]>) => response.body)
      )
      .subscribe((res: IChevre[]) => (this.chevres = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(chevre: IChevre) {
    this.editForm.patchValue({
      id: chevre.id,
      nom: chevre.nom,
      matricule: chevre.matricule,
      surnom: chevre.surnom,
      naissance: chevre.naissance,
      present: chevre.present,
      pereId: chevre.pereId,
      mereId: chevre.mereId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const chevre = this.createFromForm();
    if (chevre.id !== undefined) {
      this.subscribeToSaveResponse(this.chevreService.update(chevre));
    } else {
      this.subscribeToSaveResponse(this.chevreService.create(chevre));
    }
  }

  private createFromForm(): IChevre {
    return {
      ...new Chevre(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      matricule: this.editForm.get(['matricule']).value,
      surnom: this.editForm.get(['surnom']).value,
      naissance: this.editForm.get(['naissance']).value,
      present: this.editForm.get(['present']).value,
      pereId: this.editForm.get(['pereId']).value,
      mereId: this.editForm.get(['mereId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChevre>>) {
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
