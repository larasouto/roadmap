import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Enrolment e2e test', () => {
  const enrolmentPageUrl = '/enrolment';
  const enrolmentPageUrlPattern = new RegExp('/enrolment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const enrolmentSample = { createdAt: '2024-11-05T19:56:08.455Z' };

  let enrolment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/enrolments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/enrolments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/enrolments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (enrolment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/enrolments/${enrolment.id}`,
      }).then(() => {
        enrolment = undefined;
      });
    }
  });

  it('Enrolments menu should load Enrolments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('enrolment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Enrolment').should('exist');
    cy.url().should('match', enrolmentPageUrlPattern);
  });

  describe('Enrolment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(enrolmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Enrolment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/enrolment/new$'));
        cy.getEntityCreateUpdateHeading('Enrolment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', enrolmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/enrolments',
          body: enrolmentSample,
        }).then(({ body }) => {
          enrolment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/enrolments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [enrolment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(enrolmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Enrolment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('enrolment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', enrolmentPageUrlPattern);
      });

      it('edit button click should load edit Enrolment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Enrolment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', enrolmentPageUrlPattern);
      });

      it('edit button click should load edit Enrolment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Enrolment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', enrolmentPageUrlPattern);
      });

      it('last delete button click should delete instance of Enrolment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('enrolment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', enrolmentPageUrlPattern);

        enrolment = undefined;
      });
    });
  });

  describe('new Enrolment page', () => {
    beforeEach(() => {
      cy.visit(`${enrolmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Enrolment');
    });

    it('should create an instance of Enrolment', () => {
      cy.get(`[data-cy="createdAt"]`).type('2024-11-05T22:38');
      cy.get(`[data-cy="createdAt"]`).blur();
      cy.get(`[data-cy="createdAt"]`).should('have.value', '2024-11-05T22:38');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        enrolment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', enrolmentPageUrlPattern);
    });
  });
});
