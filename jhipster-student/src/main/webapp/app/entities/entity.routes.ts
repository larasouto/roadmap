import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'student',
    data: { pageTitle: 'Students' },
    loadChildren: () => import('./student/student.routes'),
  },
  {
    path: 'student-id-card',
    data: { pageTitle: 'StudentIdCards' },
    loadChildren: () => import('./student-id-card/student-id-card.routes'),
  },
  {
    path: 'book',
    data: { pageTitle: 'Books' },
    loadChildren: () => import('./book/book.routes'),
  },
  {
    path: 'course',
    data: { pageTitle: 'Courses' },
    loadChildren: () => import('./course/course.routes'),
  },
  {
    path: 'enrolment',
    data: { pageTitle: 'Enrolments' },
    loadChildren: () => import('./enrolment/enrolment.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
