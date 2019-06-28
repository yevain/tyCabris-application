import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParcChevre } from 'app/shared/model/parc-chevre.model';
import { ParcChevreService } from './parc-chevre.service';

@Component({
  selector: 'jhi-parc-chevre-delete-dialog',
  templateUrl: './parc-chevre-delete-dialog.component.html'
})
export class ParcChevreDeleteDialogComponent {
  parcChevre: IParcChevre;

  constructor(
    protected parcChevreService: ParcChevreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.parcChevreService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'parcChevreListModification',
        content: 'Deleted an parcChevre'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-parc-chevre-delete-popup',
  template: ''
})
export class ParcChevreDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parcChevre }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParcChevreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.parcChevre = parcChevre;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/parc-chevre', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/parc-chevre', { outlets: { popup: null } }]);
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
