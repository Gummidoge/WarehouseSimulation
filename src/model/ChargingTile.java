package model;

/* Designated tile for charging up a robot's energy. Traversable. */

public class ChargingTile extends Tile {

    @Override 
    public boolean isTraversable() {
        return true;
    }

    @Override 
    public String getDisplayCharacter() {
        return "C";
    }
}