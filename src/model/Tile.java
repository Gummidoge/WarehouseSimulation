package model;

/* The generic tile class. */

public abstract class Tile {

    // Checks if tile can be moved onto
    public abstract boolean isTraversable();

    // Returns a string character representing the tile (W for Wall, P for pickup, etc)
    public abstract String getDisplayCharacter();
}