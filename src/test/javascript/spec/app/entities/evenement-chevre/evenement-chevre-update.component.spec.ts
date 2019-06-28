/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { EvenementChevreUpdateComponent } from 'app/entities/evenement-chevre/evenement-chevre-update.component';
import { EvenementChevreService } from 'app/entities/evenement-chevre/evenement-chevre.service';
import { EvenementChevre } from 'app/shared/model/evenement-chevre.model';

describe('Component Tests', () => {
  describe('EvenementChevre Management Update Component', () => {
    let comp: EvenementChevreUpdateComponent;
    let fixture: ComponentFixture<EvenementChevreUpdateComponent>;
    let service: EvenementChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [EvenementChevreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EvenementChevreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvenementChevreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EvenementChevreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EvenementChevre(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EvenementChevre();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
