Feature: Placing a tile on a higher layer

Scenario: Successfully placing a tile on a higher layer
  Given a board with at least two tiles
  When I place the tile on the layer higher than those
    And it does not completely overlap a tile
    And the upper tile volcano covers a lower volcano
    And there is no air gap below the tile
  Then The tile should be placed on the board on the higher layer