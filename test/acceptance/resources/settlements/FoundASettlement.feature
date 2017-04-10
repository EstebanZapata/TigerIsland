Feature: Found a settlement
  Scenario: Found the first settlement
    Given a tile is on the board
    When I want to found a settlement
    Then the settlement is founded
