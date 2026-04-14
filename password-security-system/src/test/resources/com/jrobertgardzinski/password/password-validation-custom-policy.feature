@allure.label.epic:Use-case @allure.label.feature:Password-Validation
Feature: Password Validation - Custom Policy

  Scenario: Valid password is accepted
    Given a custom password policy with minimum length 8 and special chars "$%"
    When the user provides password "Pass%1ab"
    Then the password is accepted

  Scenario: Password too short is rejected
    Given a custom password policy with minimum length 8 and special chars "$%"
    When the user provides password "Pa%1"
    Then the password is rejected with an error containing "MIN_LENGTH_NOT_MET"

  Scenario: Password with default special chars is rejected
    Given a custom password policy with minimum length 8 and special chars "$%"
    When the user provides password "Passw0rd#"
    Then the password is rejected with an error containing "SPECIAL_CHAR_REQUIRED"
