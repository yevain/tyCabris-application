import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChevre } from 'app/shared/model/chevre.model';

@Component({
  selector: 'jhi-chevre-detail',
  templateUrl: './chevre-detail.component.html'
})
export class ChevreDetailComponent implements OnInit {
  chevre: IChevre;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chevre }) => {
      this.chevre = chevre;
    });
  }

  previousState() {
    window.history.back();
  }
}
