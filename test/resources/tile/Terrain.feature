Feature: Terrain

Scenario: Volcano terrain is not habitable
Given I have a tile
When I check if a volcano is habitable
Then it is not habitable