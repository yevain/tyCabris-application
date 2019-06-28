/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { PoidsUpdateComponent } from 'app/entities/poids/poids-update.component';
import { PoidsService } from 'app/entities/poids/poids.service';
import { Poids } from 'app/shared/model/poids.model';

describe('Component Tests', () => {
  describe('Poids Management Update Component', () => {
    let comp: PoidsUpdateComponent;
    let fixture: ComponentFixture<PoidsUpdateComponent>;
    let service: PoidsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [PoidsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoidsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoidsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoidsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poids(123);
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
        const entity = new Poids();
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
