/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ChevreComponent } from 'app/entities/chevre/chevre.component';
import { ChevreService } from 'app/entities/chevre/chevre.service';
import { Chevre } from 'app/shared/model/chevre.model';

describe('Component Tests', () => {
  describe('Chevre Management Component', () => {
    let comp: ChevreComponent;
    let fixture: ComponentFixture<ChevreComponent>;
    let service: ChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ChevreComponent],
        providers: []
      })
        .overrideTemplate(ChevreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChevreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChevreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Chevre(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.chevres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
