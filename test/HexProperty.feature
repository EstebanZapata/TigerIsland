Feature: Hex
  Scenario: Checking Hex Terrain type
    Given Hex Terrain type is volcano
    When I check the Terrain type
    Then I see type is volcano