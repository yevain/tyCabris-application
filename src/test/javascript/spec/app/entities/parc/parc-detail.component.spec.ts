/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcDetailComponent } from 'app/entities/parc/parc-detail.component';
import { Parc } from 'app/shared/model/parc.model';

describe('Component Tests', () => {
  describe('Parc Management Detail Component', () => {
    let comp: ParcDetailComponent;
    let fixture: ComponentFixture<ParcDetailComponent>;
    const route = ({ data: of({ parc: new Parc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParcDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParcDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
