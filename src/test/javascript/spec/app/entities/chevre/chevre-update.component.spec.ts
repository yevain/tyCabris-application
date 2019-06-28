/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ChevreUpdateComponent } from 'app/entities/chevre/chevre-update.component';
import { ChevreService } from 'app/entities/chevre/chevre.service';
import { Chevre } from 'app/shared/model/chevre.model';

describe('Component Tests', () => {
  describe('Chevre Management Update Component', () => {
    let comp: ChevreUpdateComponent;
    let fixture: ComponentFixture<ChevreUpdateComponent>;
    let service: ChevreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ChevreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChevreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChevreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChevreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chevre(123);
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
        const entity = new Chevre();
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
