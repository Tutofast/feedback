Feature: Register Feedback

  Scenario Outline: As a student i want to see an error if i register a duplicated feedback.
    Given I can register a Feedback
    And I register a repeated feedbackId
    Then I should see a error message <error>
    Examples:
    | error |
    | "El numero de feedback no esta disponible"|

  Scenario Outline: As a student i want to see an error if i register an incorrect number of stars.
    Given I can register a Feedback
    And I register a number of stars
    Then I should see a error message <error>
    Examples:
    | error |
    | "Error en el numero de estrellas registradas" |