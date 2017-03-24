Feature: Placing tiles on the first layer
  As a player
  I want to place tiles on the first layer
  So that I can expand the island

  Scenario: Placing the first tile
    Given I have a tile
    And No other tiles have been placed
    When I place the tile
    Then The tile should be placed on the board

  Scenario: Successfully placing another tile on the first layer
    Given a non-empty board
    When I place the tile on  the first layer
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