/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcChevreDetailComponent } from 'app/entities/parc-chevre/parc-chevre-detail.component';
import { ParcChevre } from 'app/shared/model/parc-chevre.model';

describe('Component Tests', () => {
  describe('ParcChevre Management Detail Component', () => {
    let comp: ParcChevreDetailComponent;
    let fixture: ComponentFixture<ParcChevreDetailComponent>;
    const route = ({ data: of({ parcChevre: new ParcChevre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcChevreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParcChevreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParcChevreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parcChevre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
