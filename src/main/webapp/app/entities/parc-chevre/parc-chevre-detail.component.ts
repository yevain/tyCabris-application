import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParcChevre } from 'app/shared/model/parc-chevre.model';

@Component({
  selector: 'jhi-parc-chevre-detail',
  templateUrl: './parc-chevre-detail.component.html'
})
export class ParcChevreDetailComponent implements OnInit {
  parcChevre: IParcChevre;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parcChevre }) => {
      this.parcChevre = parcChevre;
    });
  }

  previousState() {
    window.history.back();
  }
}
