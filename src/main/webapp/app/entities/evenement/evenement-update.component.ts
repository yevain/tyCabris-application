import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEvenement, Evenement } from 'app/shared/model/evenement.model';
import { EvenementService } from './evenement.service';

@Component({
  selector: 'jhi-evenement-update',
  templateUrl: './evenement-update.component.html'
})
export class EvenementUpdateComponent implements OnInit {
  isSaving: boolean;

  evenements: IEvenement[];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    occurence: [],
    evenementId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected evenementService: EvenementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evenement }) => {
      this.updateForm(evenement);
    });
    this.evenementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvenement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvenement[]>) => response.body)
      )
      .subscribe((res: IEvenement[]) => (this.evenements = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(evenement: IEvenement) {
    this.editForm.patchValue({
      id: evenement.id,
      nom: evenement.nom,
      occurence: evenement.occurence,
      evenementId: evenement.evenementId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const evenement = this.createFromForm();
    if (evenement.id !== undefined) {
      this.subscribeToSaveResponse(this.evenementService.update(evenement));
    } else {
      this.subscribeToSaveResponse(this.evenementService.create(evenement));
    }
  }

  private createFromForm(): IEvenement {
    return {
      ...new Evenement(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      occurence: this.editForm.get(['occurence']).value,
      evenementId: this.editForm.get(['evenementId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvenement>>) {
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
}
