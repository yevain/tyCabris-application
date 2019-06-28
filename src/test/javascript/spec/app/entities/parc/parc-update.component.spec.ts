/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcUpdateComponent } from 'app/entities/parc/parc-update.component';
import { ParcService } from 'app/entities/parc/parc.service';
import { Parc } from 'app/shared/model/parc.model';

describe('Component Tests', () => {
  describe('Parc Management Update Component', () => {
    let comp: ParcUpdateComponent;
    let fixture: ComponentFixture<ParcUpdateComponent>;
    let service: ParcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParcUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParcUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Parc(123);
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
        const entity = new Parc();
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
