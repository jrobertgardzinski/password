@allure.label.epic:Password @allure.label.feature:Password-Validation
Feature: Password Validation - Default Policy

  Background:
    Given the default password policy is active:
      | minLength         | 12       |
      | specialChars      | !@#$%^&* |
      | requiresUppercase | true     |
      | requiresLowercase | true     |
      | requiresDigit     | true     |

  Scenario: Valid password is accepted
    When the user provides password "Str0ng#Pass!"
    Then the password is accepted

  Scenario: Password too short is rejected
    When the user provides password "Sh0rt#"
    Then the password is rejected with an error containing "MIN_LENGTH_NOT_MET"

  Scenario: Password without special char is rejected
    When the user provides password "Str0ngPass1ng"
    Then the password is rejected with an error containing "SPECIAL_CHAR_REQUIRED"
