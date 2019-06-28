/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { TailleComponent } from 'app/entities/taille/taille.component';
import { TailleService } from 'app/entities/taille/taille.service';
import { Taille } from 'app/shared/model/taille.model';

describe('Component Tests', () => {
  describe('Taille Management Component', () => {
    let comp: TailleComponent;
    let fixture: ComponentFixture<TailleComponent>;
    let service: TailleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [TailleComponent],
        providers: []
      })
        .overrideTemplate(TailleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TailleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TailleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Taille(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tailles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
