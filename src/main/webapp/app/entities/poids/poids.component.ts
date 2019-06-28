import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPoids } from 'app/shared/model/poids.model';
import { AccountService } from 'app/core';
import { PoidsService } from './poids.service';

@Component({
  selector: 'jhi-poids',
  templateUrl: './poids.component.html'
})
export class PoidsComponent implements OnInit, OnDestroy {
  poids: IPoids[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected poidsService: PoidsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.poidsService
      .query()
      .pipe(
        filter((res: HttpResponse<IPoids[]>) => res.ok),
        map((res: HttpResponse<IPoids[]>) => res.body)
      )
      .subscribe(
        (res: IPoids[]) => {
          this.poids = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPoids();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPoids) {
    return item.id;
  }

  registerChangeInPoids() {
    this.eventSubscriber = this.eventManager.subscribe('poidsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
