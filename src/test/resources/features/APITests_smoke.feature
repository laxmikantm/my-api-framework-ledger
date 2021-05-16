Feature: Users API Test
  Description: Here we test the High level API health for the site

  @api_test
  Scenario Outline: Smoke test - POST basic user
    Given I use "eligibility-check" API end point
    When I POST a user user "<name>" "<address>" "<email>"
    Then I get success response 200 code
    And response contains correct structure for the user end point response
    And response contains "<eligibleCards>"

    Examples:
    |name   |address          |email                    |eligibleCards|
    |Boris  |10 Downing Street|b@invalidmail.co.uk      |C1           |
    |Angela |1 Rathbone Place |angela@invalidmail.co.uk |C1, C2       |
    |Theresa|22 Singapore road|theresa@invalidmail.co.uk|C2           |