package model;

public class Coordinate {
    private final int row;
    private final int col;

    // Constructor
    public Coordinate(int row, int col) {
        // Validate parameters
        if (row < 0) {  // Add in later: OR row is larger than warehouse width
            throw new IllegalArgumentException("Invalid row number.");
        }
        if (col < 0) {  // Add in later: OR col is larger than warehouse height
            throw new IllegalArgumentException("Invalid column number.");
        }

        // Initialize row and col to parameters
        this.row = row;
        this.col = col;
    }


    // Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}