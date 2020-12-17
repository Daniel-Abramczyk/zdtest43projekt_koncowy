Feature: basic dev to functionalities
  Scenario: Select first podcast and play it
    Given DevTo main page is open
    When User click on podcasts
    And User select first podcasts
    Then User can see its title
    And User can play it
  Scenario: Open First Video page
    Given DevTo main page is open
    When User click on videos
    And User select first video
    Then User see video title
  Scenario: Searching for right phrase
    Given DevTo main page is open
    When User search "testing"
    Then Top result should contain the work "testing"