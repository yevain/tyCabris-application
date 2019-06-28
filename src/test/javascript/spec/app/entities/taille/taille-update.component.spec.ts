/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { TailleUpdateComponent } from 'app/entities/taille/taille-update.component';
import { TailleService } from 'app/entities/taille/taille.service';
import { Taille } from 'app/shared/model/taille.model';

describe('Component Tests', () => {
  describe('Taille Management Update Component', () => {
    let comp: TailleUpdateComponent;
    let fixture: ComponentFixture<TailleUpdateComponent>;
    let service: TailleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [TailleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TailleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TailleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TailleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Taille(123);
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
        const entity = new Taille();
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
