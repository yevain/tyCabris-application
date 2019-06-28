import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChevre } from 'app/shared/model/chevre.model';
import { ChevreService } from './chevre.service';

@Component({
  selector: 'jhi-chevre-delete-dialog',
  templateUrl: './chevre-delete-dialog.component.html'
})
export class ChevreDeleteDialogComponent {
  chevre: IChevre;

  constructor(protected chevreService: ChevreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.chevreService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'chevreListModification',
        content: 'Deleted an chevre'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-chevre-delete-popup',
  template: ''
})
export class ChevreDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chevre }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChevreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.chevre = chevre;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/chevre', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/chevre', { outlets: { popup: null } }]);
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
