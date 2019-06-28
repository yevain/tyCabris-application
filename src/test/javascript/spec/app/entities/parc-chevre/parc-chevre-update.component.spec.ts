/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcChevreUpdateComponent } from 'app/entities/parc-chevre/parc-chevre-update.component';
import { ParcChevreService } from 'app/entities/parc-chevre/parc-chevre.service';
import { ParcChevre } from 'app/shared/model/parc-chevre.model';

describe('Component Tests', () => {
  describe('ParcChevre Management Update Component', () => {
    let comp: ParcChevreUpdateComponent;
    let fixture: ComponentFixture<ParcChevreUpdateComponent>;
    let service: ParcChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcChevreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParcChevreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcChevreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcChevreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParcChevre(123);
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
        const entity = new ParcChevre();
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
