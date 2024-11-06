import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { IEnrolment } from '../enrolment.model';
import { EnrolmentService } from '../service/enrolment.service';
import { EnrolmentFormService } from './enrolment-form.service';

import { EnrolmentUpdateComponent } from './enrolment-update.component';

describe('Enrolment Management Update Component', () => {
  let comp: EnrolmentUpdateComponent;
  let fixture: ComponentFixture<EnrolmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enrolmentFormService: EnrolmentFormService;
  let enrolmentService: EnrolmentService;
  let courseService: CourseService;
  let studentService: StudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnrolmentUpdateComponent],
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
      .overrideTemplate(EnrolmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnrolmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enrolmentFormService = TestBed.inject(EnrolmentFormService);
    enrolmentService = TestBed.inject(EnrolmentService);
    courseService = TestBed.inject(CourseService);
    studentService = TestBed.inject(StudentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Course query and add missing value', () => {
      const enrolment: IEnrolment = { id: 456 };
      const course: ICourse = { id: 4984 };
      enrolment.course = course;

      const courseCollection: ICourse[] = [{ id: 18596 }];
      jest.spyOn(courseService, 'query').mockReturnValue(of(new HttpResponse({ body: courseCollection })));
      const additionalCourses = [course];
      const expectedCollection: ICourse[] = [...additionalCourses, ...courseCollection];
      jest.spyOn(courseService, 'addCourseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enrolment });
      comp.ngOnInit();

      expect(courseService.query).toHaveBeenCalled();
      expect(courseService.addCourseToCollectionIfMissing).toHaveBeenCalledWith(
        courseCollection,
        ...additionalCourses.map(expect.objectContaining),
      );
      expect(comp.coursesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Student query and add missing value', () => {
      const enrolment: IEnrolment = { id: 456 };
      const student: IStudent = { id: 27947 };
      enrolment.student = student;

      const studentCollection: IStudent[] = [{ id: 16476 }];
      jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
      const additionalStudents = [student];
      const expectedCollection: IStudent[] = [...additionalStudents, ...studentCollection];
      jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enrolment });
      comp.ngOnInit();

      expect(studentService.query).toHaveBeenCalled();
      expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(
        studentCollection,
        ...additionalStudents.map(expect.objectContaining),
      );
      expect(comp.studentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enrolment: IEnrolment = { id: 456 };
      const course: ICourse = { id: 31816 };
      enrolment.course = course;
      const student: IStudent = { id: 27040 };
      enrolment.student = student;

      activatedRoute.data = of({ enrolment });
      comp.ngOnInit();

      expect(comp.coursesSharedCollection).toContain(course);
      expect(comp.studentsSharedCollection).toContain(student);
      expect(comp.enrolment).toEqual(enrolment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnrolment>>();
      const enrolment = { id: 123 };
      jest.spyOn(enrolmentFormService, 'getEnrolment').mockReturnValue(enrolment);
      jest.spyOn(enrolmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enrolment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enrolment }));
      saveSubject.complete();

      // THEN
      expect(enrolmentFormService.getEnrolment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enrolmentService.update).toHaveBeenCalledWith(expect.objectContaining(enrolment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnrolment>>();
      const enrolment = { id: 123 };
      jest.spyOn(enrolmentFormService, 'getEnrolment').mockReturnValue({ id: null });
      jest.spyOn(enrolmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enrolment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enrolment }));
      saveSubject.complete();

      // THEN
      expect(enrolmentFormService.getEnrolment).toHaveBeenCalled();
      expect(enrolmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnrolment>>();
      const enrolment = { id: 123 };
      jest.spyOn(enrolmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enrolment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enrolmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCourse', () => {
      it('Should forward to courseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(courseService, 'compareCourse');
        comp.compareCourse(entity, entity2);
        expect(courseService.compareCourse).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStudent', () => {
      it('Should forward to studentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(studentService, 'compareStudent');
        comp.compareStudent(entity, entity2);
        expect(studentService.compareStudent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
