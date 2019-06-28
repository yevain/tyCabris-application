import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChevre } from 'app/shared/model/chevre.model';
import { AccountService } from 'app/core';
import { ChevreService } from './chevre.service';

@Component({
  selector: 'jhi-chevre',
  templateUrl: './chevre.component.html'
})
export class ChevreComponent implements OnInit, OnDestroy {
  chevres: IChevre[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected chevreService: ChevreService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.chevreService
      .query()
      .pipe(
        filter((res: HttpResponse<IChevre[]>) => res.ok),
        map((res: HttpResponse<IChevre[]>) => res.body)
      )
      .subscribe(
        (res: IChevre[]) => {
          this.chevres = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInChevres();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IChevre) {
    return item.id;
  }

  registerChangeInChevres() {
    this.eventSubscriber = this.eventManager.subscribe('chevreListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
