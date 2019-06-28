import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';

@Component({
  selector: 'jhi-evenement-chevre-detail',
  templateUrl: './evenement-chevre-detail.component.html'
})
export class EvenementChevreDetailComponent implements OnInit {
  evenementChevre: IEvenementChevre;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ evenementChevre }) => {
      this.evenementChevre = evenementChevre;
    });
  }

  previousState() {
    window.history.back();
  }
}
