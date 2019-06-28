/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { PoidsDetailComponent } from 'app/entities/poids/poids-detail.component';
import { Poids } from 'app/shared/model/poids.model';

describe('Component Tests', () => {
  describe('Poids Management Detail Component', () => {
    let comp: PoidsDetailComponent;
    let fixture: ComponentFixture<PoidsDetailComponent>;
    const route = ({ data: of({ poids: new Poids(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [PoidsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoidsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoidsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poids).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
