import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParc } from 'app/shared/model/parc.model';
import { ParcService } from './parc.service';

@Component({
  selector: 'jhi-parc-delete-dialog',
  templateUrl: './parc-delete-dialog.component.html'
})
export class ParcDeleteDialogComponent {
  parc: IParc;

  constructor(protected parcService: ParcService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.parcService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'parcListModification',
        content: 'Deleted an parc'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-parc-delete-popup',
  template: ''
})
export class ParcDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parc }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParcDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.parc = parc;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/parc', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/parc', { outlets: { popup: null } }]);
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
