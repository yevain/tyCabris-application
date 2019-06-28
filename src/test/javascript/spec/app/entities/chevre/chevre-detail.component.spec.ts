/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ChevreDetailComponent } from 'app/entities/chevre/chevre-detail.component';
import { Chevre } from 'app/shared/model/chevre.model';

describe('Component Tests', () => {
  describe('Chevre Management Detail Component', () => {
    let comp: ChevreDetailComponent;
    let fixture: ComponentFixture<ChevreDetailComponent>;
    const route = ({ data: of({ chevre: new Chevre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ChevreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChevreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChevreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chevre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
