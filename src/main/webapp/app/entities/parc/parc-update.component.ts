import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IParc, Parc } from 'app/shared/model/parc.model';
import { ParcService } from './parc.service';

@Component({
  selector: 'jhi-parc-update',
  templateUrl: './parc-update.component.html'
})
export class ParcUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]]
  });

  constructor(protected parcService: ParcService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ parc }) => {
      this.updateForm(parc);
    });
  }

  updateForm(parc: IParc) {
    this.editForm.patchValue({
      id: parc.id,
      nom: parc.nom
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const parc = this.createFromForm();
    if (parc.id !== undefined) {
      this.subscribeToSaveResponse(this.parcService.update(parc));
    } else {
      this.subscribeToSaveResponse(this.parcService.create(parc));
    }
  }

  private createFromForm(): IParc {
    return {
      ...new Parc(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParc>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
