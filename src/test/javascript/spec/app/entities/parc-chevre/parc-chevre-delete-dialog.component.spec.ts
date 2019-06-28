/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { ParcChevreDeleteDialogComponent } from 'app/entities/parc-chevre/parc-chevre-delete-dialog.component';
import { ParcChevreService } from 'app/entities/parc-chevre/parc-chevre.service';

describe('Component Tests', () => {
  describe('ParcChevre Management Delete Component', () => {
    let comp: ParcChevreDeleteDialogComponent;
    let fixture: ComponentFixture<ParcChevreDeleteDialogComponent>;
    let service: ParcChevreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [ParcChevreDeleteDialogComponent]
      })
        .overrideTemplate(ParcChevreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParcChevreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParcChevreService);
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
