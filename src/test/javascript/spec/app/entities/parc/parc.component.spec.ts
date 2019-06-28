/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcComponent } from 'app/entities/parc/parc.component';
import { ParcService } from 'app/entities/parc/parc.service';
import { Parc } from 'app/shared/model/parc.model';

describe('Component Tests', () => {
  describe('Parc Management Component', () => {
    let comp: ParcComponent;
    let fixture: ComponentFixture<ParcComponent>;
    let service: ParcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcComponent],
        providers: []
      })
        .overrideTemplate(ParcComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Parc(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parcs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
