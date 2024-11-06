import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { StudentIdCardService } from '../service/student-id-card.service';
import { IStudentIdCard } from '../student-id-card.model';
import { StudentIdCardFormService } from './student-id-card-form.service';

import { StudentIdCardUpdateComponent } from './student-id-card-update.component';

describe('StudentIdCard Management Update Component', () => {
  let comp: StudentIdCardUpdateComponent;
  let fixture: ComponentFixture<StudentIdCardUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let studentIdCardFormService: StudentIdCardFormService;
  let studentIdCardService: StudentIdCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StudentIdCardUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StudentIdCardUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StudentIdCardUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    studentIdCardFormService = TestBed.inject(StudentIdCardFormService);
    studentIdCardService = TestBed.inject(StudentIdCardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const studentIdCard: IStudentIdCard = { id: 456 };

      activatedRoute.data = of({ studentIdCard });
      comp.ngOnInit();

      expect(comp.studentIdCard).toEqual(studentIdCard);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudentIdCard>>();
      const studentIdCard = { id: 123 };
      jest.spyOn(studentIdCardFormService, 'getStudentIdCard').mockReturnValue(studentIdCard);
      jest.spyOn(studentIdCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ studentIdCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: studentIdCard }));
      saveSubject.complete();

      // THEN
      expect(studentIdCardFormService.getStudentIdCard).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(studentIdCardService.update).toHaveBeenCalledWith(expect.objectContaining(studentIdCard));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudentIdCard>>();
      const studentIdCard = { id: 123 };
      jest.spyOn(studentIdCardFormService, 'getStudentIdCard').mockReturnValue({ id: null });
      jest.spyOn(studentIdCardService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ studentIdCard: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: studentIdCard }));
      saveSubject.complete();

      // THEN
      expect(studentIdCardFormService.getStudentIdCard).toHaveBeenCalled();
      expect(studentIdCardService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudentIdCard>>();
      const studentIdCard = { id: 123 };
      jest.spyOn(studentIdCardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ studentIdCard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(studentIdCardService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
