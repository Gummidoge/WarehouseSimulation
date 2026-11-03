package model;

/* Walls will act as the perimeter around each warehouse layout, as well as being 
peppered here and there to cause conflict. Tests all robots' pathfinding patience. 
*/

public class WallTile extends Tile {

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public String getDisplayCharacter() {
        return "W";
    }
}