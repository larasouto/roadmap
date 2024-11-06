import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IStudentIdCard } from 'app/entities/student-id-card/student-id-card.model';
import { StudentIdCardService } from 'app/entities/student-id-card/service/student-id-card.service';
import { StudentService } from '../service/student.service';
import { IStudent } from '../student.model';
import { StudentFormService } from './student-form.service';

import { StudentUpdateComponent } from './student-update.component';

describe('Student Management Update Component', () => {
  let comp: StudentUpdateComponent;
  let fixture: ComponentFixture<StudentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let studentFormService: StudentFormService;
  let studentService: StudentService;
  let studentIdCardService: StudentIdCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StudentUpdateComponent],
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
      .overrideTemplate(StudentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StudentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    studentFormService = TestBed.inject(StudentFormService);
    studentService = TestBed.inject(StudentService);
    studentIdCardService = TestBed.inject(StudentIdCardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call studentIdCard query and add missing value', () => {
      const student: IStudent = { id: 456 };
      const studentIdCard: IStudentIdCard = { id: 14931 };
      student.studentIdCard = studentIdCard;

      const studentIdCardCollection: IStudentIdCard[] = [{ id: 21525 }];
      jest.spyOn(studentIdCardService, 'query').mockReturnValue(of(new HttpResponse({ body: studentIdCardCollection })));
      const expectedCollection: IStudentIdCard[] = [studentIdCard, ...studentIdCardCollection];
      jest.spyOn(studentIdCardService, 'addStudentIdCardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ student });
      comp.ngOnInit();

      expect(studentIdCardService.query).toHaveBeenCalled();
      expect(studentIdCardService.addStudentIdCardToCollectionIfMissing).toHaveBeenCalledWith(studentIdCardCollection, studentIdCard);
      expect(comp.studentIdCardsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const student: IStudent = { id: 456 };
      const studentIdCard: IStudentIdCard = { id: 16830 };
      student.studentIdCard = studentIdCard;

      activatedRoute.data = of({ student });
      comp.ngOnInit();

      expect(comp.studentIdCardsCollection).toContain(studentIdCard);
      expect(comp.student).toEqual(student);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudent>>();
      const student = { id: 123 };
      jest.spyOn(studentFormService, 'getStudent').mockReturnValue(student);
      jest.spyOn(studentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: student }));
      saveSubject.complete();

      // THEN
      expect(studentFormService.getStudent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(studentService.update).toHaveBeenCalledWith(expect.objectContaining(student));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudent>>();
      const student = { id: 123 };
      jest.spyOn(studentFormService, 'getStudent').mockReturnValue({ id: null });
      jest.spyOn(studentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: student }));
      saveSubject.complete();

      // THEN
      expect(studentFormService.getStudent).toHaveBeenCalled();
      expect(studentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStudent>>();
      const student = { id: 123 };
      jest.spyOn(studentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ student });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(studentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStudentIdCard', () => {
      it('Should forward to studentIdCardService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(studentIdCardService, 'compareStudentIdCard');
        comp.compareStudentIdCard(entity, entity2);
        expect(studentIdCardService.compareStudentIdCard).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
