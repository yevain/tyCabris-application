import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaille } from 'app/shared/model/taille.model';

@Component({
  selector: 'jhi-taille-detail',
  templateUrl: './taille-detail.component.html'
})
export class TailleDetailComponent implements OnInit {
  taille: ITaille;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taille }) => {
      this.taille = taille;
    });
  }

  previousState() {
    window.history.back();
  }
}
