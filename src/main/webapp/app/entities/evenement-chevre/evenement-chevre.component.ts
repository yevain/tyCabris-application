import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';
import { AccountService } from 'app/core';
import { EvenementChevreService } from './evenement-chevre.service';

@Component({
  selector: 'jhi-evenement-chevre',
  templateUrl: './evenement-chevre.component.html'
})
export class EvenementChevreComponent implements OnInit, OnDestroy {
  evenementChevres: IEvenementChevre[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected evenementChevreService: EvenementChevreService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.evenementChevreService
      .query()
      .pipe(
        filter((res: HttpResponse<IEvenementChevre[]>) => res.ok),
        map((res: HttpResponse<IEvenementChevre[]>) => res.body)
      )
      .subscribe(
        (res: IEvenementChevre[]) => {
          this.evenementChevres = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEvenementChevres();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEvenementChevre) {
    return item.id;
  }

  registerChangeInEvenementChevres() {
    this.eventSubscriber = this.eventManager.subscribe('evenementChevreListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
