import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParc } from 'app/shared/model/parc.model';

@Component({
  selector: 'jhi-parc-detail',
  templateUrl: './parc-detail.component.html'
})
export class ParcDetailComponent implements OnInit {
  parc: IParc;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parc }) => {
      this.parc = parc;
    });
  }

  previousState() {
    window.history.back();
  }
}
