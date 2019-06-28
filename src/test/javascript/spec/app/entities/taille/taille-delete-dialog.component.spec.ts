/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { TailleDeleteDialogComponent } from 'app/entities/taille/taille-delete-dialog.component';
import { TailleService } from 'app/entities/taille/taille.service';

describe('Component Tests', () => {
  describe('Taille Management Delete Component', () => {
    let comp: TailleDeleteDialogComponent;
    let fixture: ComponentFixture<TailleDeleteDialogComponent>;
    let service: TailleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [TailleDeleteDialogComponent]
      })
        .overrideTemplate(TailleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TailleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TailleService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
