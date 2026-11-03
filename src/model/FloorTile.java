package model;

/* Nothing too special to say about the floor tile. Allows our robot friends 
to utilize movement points to their fullest potential. 
*/

public class FloorTile extends Tile {

    @Override
    public boolean isTraversable() {
        return true;
    }

    // For now, floor tiles will display a dot.
    // After testing, we'll utilize a single space instead to improve grid readability.
    @Override
    public String getDisplayCharacter() {
        return ".";
    }

}