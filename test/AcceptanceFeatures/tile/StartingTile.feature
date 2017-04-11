Feature: First tile placed at start up
  As player1
  I want to see my first tile placed automatically when the game starts
  So that the game can proceed

  Scenario: Startup tile
    Given I am player1
    When The game starts
    Then My tile should be placed on the board