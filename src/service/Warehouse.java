package service;

import model.Coordinate;
import model.Tile;
// import model.ChargingTile;
// import model.DeliveryTile;
import model.FloorTile;
// import model.PickupTile;
import model.WallTile;


public class Warehouse {
    private final int rows;
    private final int cols;
    private final Tile[][] map;

    /* Constructor - creates an empty warehouse (with perimeter walls and floors) of a desired width and length. Must be at
       least 6x6 in size. We need to give the robots some room to stretch their legs / treads / rotary blades, after all. */
    public Warehouse(int rows, int cols){
        if (rows <= 5 || cols <= 5) {
            throw new IllegalArgumentException("Warehouse is too small. 6x6 is the minimum required size.");
        }
        this.rows = rows;
        this.cols = cols;
        this.map = new Tile[rows][cols];

        // Initializing an empty warehouse with perimeter walls.
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                
                // Checks whether a tile is on a border. If so, it becomes a wall tile.
                // It looks complicated, but it's not too bad.
                boolean isPerimeter = ( row == 0 || row == rows - 1 || col == 0 || col == cols - 1 );

                if (isPerimeter) {
                    map[row][col] = new WallTile();
                } else {
                    map[row][col] = new FloorTile();
                }
            }
        }
    } // end constructor


    // Getter - returns the specific tile at the requested coordinate
    public Tile getTile(Coordinate coordinate){
        int row = coordinate.getRow();
        int col = coordinate.getCol();

        // Exception check
        if (row >= rows || col >= cols) {
            throw new IndexOutOfBoundsException("Invalid coordinate - it must be inside the warehouse.");
        }

        return map[row][col];
    }


    // Getter - row size
    public int getRows() {
        return rows;
    }


    // Getter - column size
    public int getCols() {
        return cols;
    }


    /* Setter - modifies a tile at the chosen coordinate into a new tile type. The bread and butter
    method for adding charging stations, delivery dropoff points, pickup zones, even more walls, etc. 
    Recommended for testing purposes as well. */
    public void setTile(Coordinate coordinate, Tile tile) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();

        // Exception checks
        if (row >= rows || col >= cols) {
            throw new IndexOutOfBoundsException("Invalid coordinate - it must be inside the warehouse.");
        }
        if (tile == null) {
            throw new IllegalArgumentException("Tile cannot be null.");
        }
        boolean isPerimeter = ( row == 0 || row == rows - 1 || col == 0 || col == cols - 1 );
        if (isPerimeter) {
            throw new IllegalArgumentException("Perimeter walls cannot be replaced.");
        }

        // Converts the old tile into the desired version. 
        map[row][col] = tile;
    }


    // Print - displays the current warehouse setup to the console
    public void printMap() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(map[row][col].getDisplayCharacter() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}