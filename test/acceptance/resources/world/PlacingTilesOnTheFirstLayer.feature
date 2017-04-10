Feature: Placing tiles on the first layer
  As a player
  I want to place tiles on the first layer
  So that I can expand the island

  Scenario: First tile is already placed
    Given A new board
    When I check that the fist tile exists
    Then Then the special tile should exist with the volcano at the center

  Scenario: Placing the special first tile again
    Given The special first tile has been placed
    When I place another special first tile
    Then that tile is not placed on the board

  Scenario: Successfully placing another tile on the first layer
    Given a non-empty board
    When I place the tile on the first layer
    And it is adjacent to an existing tile
    And it is not overlapping another tile
    Then the tile should be placed on the board adjacent to an existing tile

  Scenario: Illegally placing another tile on the first layer via no adjacency
    Given a board with at least one tile
    When I attempt to place the tile on the first layer
    And it is not adjacent to an existing tile
    Then it should not be placed on the board

  Scenario: Illegally placing another tile on the first layer via overlapping an existing tile
    Given a board with at least one tile on it
    When I attempt to place my tile on the first layer
    And it overlaps an existing tile
    Then it should not be placed on the board in that location