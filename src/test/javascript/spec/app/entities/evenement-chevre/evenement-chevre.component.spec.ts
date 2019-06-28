/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { EvenementChevreComponent } from 'app/entities/evenement-chevre/evenement-chevre.component';
import { EvenementChevreService } from 'app/entities/evenement-chevre/evenement-chevre.service';
import { EvenementChevre } from 'app/shared/model/evenement-chevre.model';

describe('Component Tests', () => {
  describe('EvenementChevre Management Component', () => {
    let comp: EvenementChevreComponent;
    let fixture: ComponentFixture<EvenementChevreComponent>;
    let service: EvenementChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [EvenementChevreComponent],
        providers: []
      })
        .overrideTemplate(EvenementChevreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvenementChevreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EvenementChevreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EvenementChevre(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.evenementChevres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
