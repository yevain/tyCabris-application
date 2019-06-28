/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { PoidsComponent } from 'app/entities/poids/poids.component';
import { PoidsService } from 'app/entities/poids/poids.service';
import { Poids } from 'app/shared/model/poids.model';

describe('Component Tests', () => {
  describe('Poids Management Component', () => {
    let comp: PoidsComponent;
    let fixture: ComponentFixture<PoidsComponent>;
    let service: PoidsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [PoidsComponent],
        providers: []
      })
        .overrideTemplate(PoidsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoidsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoidsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Poids(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.poids[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
