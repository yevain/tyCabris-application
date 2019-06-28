/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcChevreComponent } from 'app/entities/parc-chevre/parc-chevre.component';
import { ParcChevreService } from 'app/entities/parc-chevre/parc-chevre.service';
import { ParcChevre } from 'app/shared/model/parc-chevre.model';

describe('Component Tests', () => {
  describe('ParcChevre Management Component', () => {
    let comp: ParcChevreComponent;
    let fixture: ComponentFixture<ParcChevreComponent>;
    let service: ParcChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcChevreComponent],
        providers: []
      })
        .overrideTemplate(ParcChevreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcChevreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcChevreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ParcChevre(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parcChevres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
