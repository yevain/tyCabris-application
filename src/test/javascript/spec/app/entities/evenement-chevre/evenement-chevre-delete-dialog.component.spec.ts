/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TyCabrisApplicationTestModule } from '../../../test.module';
import { EvenementChevreDeleteDialogComponent } from 'app/entities/evenement-chevre/evenement-chevre-delete-dialog.component';
import { EvenementChevreService } from 'app/entities/evenement-chevre/evenement-chevre.service';

describe('Component Tests', () => {
  describe('EvenementChevre Management Delete Component', () => {
    let comp: EvenementChevreDeleteDialogComponent;
    let fixture: ComponentFixture<EvenementChevreDeleteDialogComponent>;
    let service: EvenementChevreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TyCabrisApplicationTestModule],
        declarations: [EvenementChevreDeleteDialogComponent]
      })
        .overrideTemplate(EvenementChevreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvenementChevreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EvenementChevreService);
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
