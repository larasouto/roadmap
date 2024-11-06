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

describe('StudentIdCard e2e test', () => {
  const studentIdCardPageUrl = '/student-id-card';
  const studentIdCardPageUrlPattern = new RegExp('/student-id-card(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const studentIdCardSample = { cardNumber: 'substantiateXXX' };

  let studentIdCard;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/student-id-cards+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/student-id-cards').as('postEntityRequest');
    cy.intercept('DELETE', '/api/student-id-cards/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (studentIdCard) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/student-id-cards/${studentIdCard.id}`,
      }).then(() => {
        studentIdCard = undefined;
      });
    }
  });

  it('StudentIdCards menu should load StudentIdCards page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('student-id-card');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StudentIdCard').should('exist');
    cy.url().should('match', studentIdCardPageUrlPattern);
  });

  describe('StudentIdCard page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(studentIdCardPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StudentIdCard page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/student-id-card/new$'));
        cy.getEntityCreateUpdateHeading('StudentIdCard');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', studentIdCardPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/student-id-cards',
          body: studentIdCardSample,
        }).then(({ body }) => {
          studentIdCard = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/student-id-cards+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [studentIdCard],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(studentIdCardPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StudentIdCard page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('studentIdCard');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', studentIdCardPageUrlPattern);
      });

      it('edit button click should load edit StudentIdCard page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StudentIdCard');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', studentIdCardPageUrlPattern);
      });

      it('edit button click should load edit StudentIdCard page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StudentIdCard');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', studentIdCardPageUrlPattern);
      });

      it('last delete button click should delete instance of StudentIdCard', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('studentIdCard').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', studentIdCardPageUrlPattern);

        studentIdCard = undefined;
      });
    });
  });

  describe('new StudentIdCard page', () => {
    beforeEach(() => {
      cy.visit(`${studentIdCardPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StudentIdCard');
    });

    it('should create an instance of StudentIdCard', () => {
      cy.get(`[data-cy="cardNumber"]`).type('honestlyXXXXXXX');
      cy.get(`[data-cy="cardNumber"]`).should('have.value', 'honestlyXXXXXXX');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        studentIdCard = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', studentIdCardPageUrlPattern);
    });
  });
});
