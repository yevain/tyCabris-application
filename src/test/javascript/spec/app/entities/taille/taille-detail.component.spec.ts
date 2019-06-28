/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { TailleDetailComponent } from 'app/entities/taille/taille-detail.component';
import { Taille } from 'app/shared/model/taille.model';

describe('Component Tests', () => {
  describe('Taille Management Detail Component', () => {
    let comp: TailleDetailComponent;
    let fixture: ComponentFixture<TailleDetailComponent>;
    const route = ({ data: of({ taille: new Taille(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [TailleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TailleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TailleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taille).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
