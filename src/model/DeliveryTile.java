package model;

/* Designated tile for a robot's delivery dropoff location. Traversable. */

public class DeliveryTile extends Tile {

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override 
    public String getDisplayCharacter() {
        return "D";
    }

}