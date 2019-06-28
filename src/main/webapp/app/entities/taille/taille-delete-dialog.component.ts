import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaille } from 'app/shared/model/taille.model';
import { TailleService } from './taille.service';

@Component({
  selector: 'jhi-taille-delete-dialog',
  templateUrl: './taille-delete-dialog.component.html'
})
export class TailleDeleteDialogComponent {
  taille: ITaille;

  constructor(protected tailleService: TailleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tailleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tailleListModification',
        content: 'Deleted an taille'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-taille-delete-popup',
  template: ''
})
export class TailleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taille }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TailleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.taille = taille;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/taille', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/taille', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
