/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ChevreDeleteDialogComponent } from 'app/entities/chevre/chevre-delete-dialog.component';
import { ChevreService } from 'app/entities/chevre/chevre.service';

describe('Component Tests', () => {
  describe('Chevre Management Delete Component', () => {
    let comp: ChevreDeleteDialogComponent;
    let fixture: ComponentFixture<ChevreDeleteDialogComponent>;
    let service: ChevreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ChevreDeleteDialogComponent]
      })
        .overrideTemplate(ChevreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChevreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChevreService);
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
