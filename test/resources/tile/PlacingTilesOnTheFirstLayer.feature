Feature: Placing tiles on the first layer

Scenario: Placing the first tile
  Given I have a tile
  And No other tiles have been placed
  When I place the tile
  Then The tile should be placed on the board

Scenario: Placing another tile on the first layer
  Given a non-empty board
  When I place the tile
  And it is not overlapping another tile
  And it is adjacent to an existing tile
  Then the

