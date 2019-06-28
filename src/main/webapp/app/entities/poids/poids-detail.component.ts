import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoids } from 'app/shared/model/poids.model';

@Component({
  selector: 'jhi-poids-detail',
  templateUrl: './poids-detail.component.html'
})
export class PoidsDetailComponent implements OnInit {
  poids: IPoids;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poids }) => {
      this.poids = poids;
    });
  }

  previousState() {
    window.history.back();
  }
}
