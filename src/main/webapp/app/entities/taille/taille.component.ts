import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITaille } from 'app/shared/model/taille.model';
import { AccountService } from 'app/core';
import { TailleService } from './taille.service';

@Component({
  selector: 'jhi-taille',
  templateUrl: './taille.component.html'
})
export class TailleComponent implements OnInit, OnDestroy {
  tailles: ITaille[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tailleService: TailleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tailleService
      .query()
      .pipe(
        filter((res: HttpResponse<ITaille[]>) => res.ok),
        map((res: HttpResponse<ITaille[]>) => res.body)
      )
      .subscribe(
        (res: ITaille[]) => {
          this.tailles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTailles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITaille) {
    return item.id;
  }

  registerChangeInTailles() {
    this.eventSubscriber = this.eventManager.subscribe('tailleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
