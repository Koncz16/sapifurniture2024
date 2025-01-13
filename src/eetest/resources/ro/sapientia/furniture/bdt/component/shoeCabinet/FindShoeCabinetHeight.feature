Feature: Check if the find all endpoint works for ShoeCabinet
  As a furniture tool user
  I want to be able to see all the shoe cabinets

  Scenario: One element
    Given that we have the following shoe cabinets:
      | height | width | material | shelvesCount |
      | 150    | 80    | Wood     | 5            |
    When I invoke the shoe cabinet all endpoint
    Then I should get the height "150" for the position "0"
    And I should get the shelvesCount "5" for the position "0"

  Scenario: Two elements in the table
    Given that we have the following shoe cabinets:
      | height | width | material | shelvesCount |
      | 150    | 80    | Wood     | 5            |
      | 120    | 70    | Metal    | 4            |
    When I invoke the shoe cabinet all endpoint
    Then I should get the height "150" for the position "0"
    And I should get the shelvesCount "5" for the position "0"
    Then I should get the height "120" for the position "1"
    And I should get the shelvesCount "4" for the position "1"
