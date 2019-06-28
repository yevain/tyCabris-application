import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEvenement } from 'app/shared/model/evenement.model';
import { AccountService } from 'app/core';
import { EvenementService } from './evenement.service';

@Component({
  selector: 'jhi-evenement',
  templateUrl: './evenement.component.html'
})
export class EvenementComponent implements OnInit, OnDestroy {
  evenements: IEvenement[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected evenementService: EvenementService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.evenementService
      .query()
      .pipe(
        filter((res: HttpResponse<IEvenement[]>) => res.ok),
        map((res: HttpResponse<IEvenement[]>) => res.body)
      )
      .subscribe(
        (res: IEvenement[]) => {
          this.evenements = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEvenements();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEvenement) {
    return item.id;
  }

  registerChangeInEvenements() {
    this.eventSubscriber = this.eventManager.subscribe('evenementListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
