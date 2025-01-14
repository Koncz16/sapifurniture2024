Feature: Check if the find all endpoints work for ShoeCabinet
  As a furniture tool user, I want to be able to see all the shoe cabinets

  Scenario: One element
    Given that we have the following shoe cabinets:
      | width  | height | depth | material   | shelves_count |
      | 10     | 10     | 10    | Wood       | 4             |
    When I invoke the shoe cabinet all endpoint
    Then I should get the height "10" for the position "0"
    
  Scenario: Two elements
    Given that we have the following shoe cabinets:
      | width  | height | depth | material   | shelves_count |
      | 10     | 10     | 10    | Wood       | 4             |
      | 15     | 12     | 15    | Metal      | 5             |
    When I invoke the shoe cabinet all endpoint
    Then I should get the height "12" for the position "1"
