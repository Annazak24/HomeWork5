Feature: User API

  Scenario: Get user list
    Given stub server is running
    When I request user list
    Then response status should be 200
