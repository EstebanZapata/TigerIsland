Feature: Placing a tile on a higher layer

  Scenario: Successfully placing a tile on a higher layer
    Given a board with at least two tiles
    When I place the tile on the layer higher than those
      And it does not completely overlap a tile
      And the upper tile volcano covers a lower volcano
      And there is no air gap below the tile
    Then The tile should be placed on the board on the higher layer

  Scenario: Illegally placing a tile on a higher layer due to complete overlap
    Given a board with at least two tiles on it
    When I attempt to place the tile on the layer higher than those
      And it completely overlaps a tile
    Then the tile should not be placed on the board

  Scenario: Illegally placing a tile on a higher layer due to not covering volcano
    Given a board with two or more tiles
    When I attempt to place the tile on the layer higher than those two
      And the upper volcano does not cover the lower volcano
    Then the tile should not be placed

  Scenario: Illegally placing a tile on a higher layer due to gap between layers
    Given a board with two tiles or more
    When I attempt to place the tile on the layer higher than the existing tiles
      And there is a gap between tiles
    Then the tile should not be placed there