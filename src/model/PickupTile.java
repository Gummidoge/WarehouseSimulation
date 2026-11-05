package model;

/* A tile containing ready-to-deliver packages. Traversable, provided 
the robo-workers are careful with their steps.
*/

public class PickupTile extends Tile {

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override
    public String getDisplayCharacter() {
        return "P";
    }

}