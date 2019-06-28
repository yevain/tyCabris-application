import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoids } from 'app/shared/model/poids.model';
import { PoidsService } from './poids.service';

@Component({
  selector: 'jhi-poids-delete-dialog',
  templateUrl: './poids-delete-dialog.component.html'
})
export class PoidsDeleteDialogComponent {
  poids: IPoids;

  constructor(protected poidsService: PoidsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.poidsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'poidsListModification',
        content: 'Deleted an poids'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-poids-delete-popup',
  template: ''
})
export class PoidsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poids }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PoidsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.poids = poids;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/poids', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/poids', { outlets: { popup: null } }]);
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
