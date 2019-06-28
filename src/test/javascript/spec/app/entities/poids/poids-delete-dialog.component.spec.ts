/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { PoidsDeleteDialogComponent } from 'app/entities/poids/poids-delete-dialog.component';
import { PoidsService } from 'app/entities/poids/poids.service';

describe('Component Tests', () => {
  describe('Poids Management Delete Component', () => {
    let comp: PoidsDeleteDialogComponent;
    let fixture: ComponentFixture<PoidsDeleteDialogComponent>;
    let service: PoidsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [PoidsDeleteDialogComponent]
      })
        .overrideTemplate(PoidsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoidsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoidsService);
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
