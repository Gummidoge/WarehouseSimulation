package model;

public class Coordinate {
    private final int row;
    private final int col;

    // Constructor
    public Coordinate(int row, int col) {
        // Validate parameters
        if (row < 0) {  // Ensure row is not a negative value
            throw new IllegalArgumentException("Invalid row number.");
        }
        if (col < 0) {  // Ensure col is not a negative value
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

    // Compare two Coordinate objects by their row and column values
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordinate)) return false;

        Coordinate other = (Coordinate) obj;

        return row == other.row && col == other.col;
    }
}