import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParc } from 'app/shared/model/parc.model';
import { AccountService } from 'app/core';
import { ParcService } from './parc.service';

@Component({
  selector: 'jhi-parc',
  templateUrl: './parc.component.html'
})
export class ParcComponent implements OnInit, OnDestroy {
  parcs: IParc[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected parcService: ParcService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.parcService
      .query()
      .pipe(
        filter((res: HttpResponse<IParc[]>) => res.ok),
        map((res: HttpResponse<IParc[]>) => res.body)
      )
      .subscribe(
        (res: IParc[]) => {
          this.parcs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInParcs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IParc) {
    return item.id;
  }

  registerChangeInParcs() {
    this.eventSubscriber = this.eventManager.subscribe('parcListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
