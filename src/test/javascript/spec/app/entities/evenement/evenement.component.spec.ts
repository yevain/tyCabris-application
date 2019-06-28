/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { EvenementComponent } from 'app/entities/evenement/evenement.component';
import { EvenementService } from 'app/entities/evenement/evenement.service';
import { Evenement } from 'app/shared/model/evenement.model';

describe('Component Tests', () => {
  describe('Evenement Management Component', () => {
    let comp: EvenementComponent;
    let fixture: ComponentFixture<EvenementComponent>;
    let service: EvenementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [EvenementComponent],
        providers: []
      })
        .overrideTemplate(EvenementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvenementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EvenementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Evenement(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.evenements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
