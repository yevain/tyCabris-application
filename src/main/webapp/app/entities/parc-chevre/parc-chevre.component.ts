import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParcChevre } from 'app/shared/model/parc-chevre.model';
import { AccountService } from 'app/core';
import { ParcChevreService } from './parc-chevre.service';

@Component({
  selector: 'jhi-parc-chevre',
  templateUrl: './parc-chevre.component.html'
})
export class ParcChevreComponent implements OnInit, OnDestroy {
  parcChevres: IParcChevre[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected parcChevreService: ParcChevreService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.parcChevreService
      .query()
      .pipe(
        filter((res: HttpResponse<IParcChevre[]>) => res.ok),
        map((res: HttpResponse<IParcChevre[]>) => res.body)
      )
      .subscribe(
        (res: IParcChevre[]) => {
          this.parcChevres = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInParcChevres();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IParcChevre) {
    return item.id;
  }

  registerChangeInParcChevres() {
    this.eventSubscriber = this.eventManager.subscribe('parcChevreListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
