import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';
import { EvenementChevreService } from './evenement-chevre.service';

@Component({
  selector: 'jhi-evenement-chevre-delete-dialog',
  templateUrl: './evenement-chevre-delete-dialog.component.html'
})
export class EvenementChevreDeleteDialogComponent {
  evenementChevre: IEvenementChevre;

  constructor(
    protected evenementChevreService: EvenementChevreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.evenementChevreService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'evenementChevreListModification',
        content: 'Deleted an evenementChevre'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-evenement-chevre-delete-popup',
  template: ''
})
export class EvenementChevreDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ evenementChevre }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EvenementChevreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.evenementChevre = evenementChevre;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/evenement-chevre', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/evenement-chevre', { outlets: { popup: null } }]);
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
