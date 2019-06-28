/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { EvenementChevreDetailComponent } from 'app/entities/evenement-chevre/evenement-chevre-detail.component';
import { EvenementChevre } from 'app/shared/model/evenement-chevre.model';

describe('Component Tests', () => {
  describe('EvenementChevre Management Detail Component', () => {
    let comp: EvenementChevreDetailComponent;
    let fixture: ComponentFixture<EvenementChevreDetailComponent>;
    const route = ({ data: of({ evenementChevre: new EvenementChevre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [EvenementChevreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EvenementChevreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvenementChevreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evenementChevre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
