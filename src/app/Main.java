package app;

import model.Robot;
import model.Coordinate;
// import model.Tile;
import model.PickupTile;
import model.DeliveryTile;
import model.WallTile;

import service.Warehouse;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // **TEMPORARY TESTING CODE**
        // Create robot with id 1 and position 1,1
        Coordinate myCoordinate = new Coordinate(1, 1);
        Robot myRobot = new Robot (1, myCoordinate);

        // Print the robot's current info
        myRobot.printRobot();
        System.out.println();

        // Try updating some info
        myCoordinate = new Coordinate(2, 2);    // Update position
        myRobot.setPosition(myCoordinate);
        List<Coordinate> route = new ArrayList<>(List.of(new Coordinate(1, 1), new Coordinate(1, 2),
                                                         new Coordinate(2, 2), new Coordinate(3, 2)));
        myRobot.setCurrentRoute(route); // Update route
        myRobot.setStatus("MOVING");    // Update status
        myRobot.chargeBattery();    // Charge a battery point (should fail cause its full by default)
        myRobot.consumeBattery();   // Consume a battery point
        myRobot.consumeBattery();   // Consume a battery point

        // Print the robot's new info
        myRobot.printRobot();
        // **TEMPORARY TESTING CODE**




        /*                        *** Jason's Temporary Warehouse testing area ***                  */

        // Creating and printing out an 8x12 empty warehouse map. 
        // Note that perimeter walls are always added by default. */
        Warehouse myFirstWarehouse = new Warehouse(8, 12);
        System.out.println("\n*** Jason's stuff ***\nInitializing an empty warehouse: \n");
        myFirstWarehouse.printMap();

        // Modifying a few tiles, then reprinting the warehouse map
        myFirstWarehouse.setTile(new Coordinate(2,2), new PickupTile());
        myFirstWarehouse.setTile(new Coordinate(5,9), new DeliveryTile());
        myFirstWarehouse.setTile(new Coordinate(2,9), new WallTile());
        myFirstWarehouse.setTile(new Coordinate(2,10), new WallTile());

        System.out.println("The same map with some altered tiles: \n");
        myFirstWarehouse.printMap();


        // Checking isTraversable method
        Coordinate position = new Coordinate(0,0);
        System.out.println("// Traversal Check \nThe tile at coordinate (" + position.getRow() + ", " + position.getCol() + "): ");
        if (myFirstWarehouse.isTraversable(position)){
            System.out.println("The tile is traversable!\n");
        } else {
            System.out.println("The tile cannot be traversed. Sorry.\n");
        }


        // Checking isWithinBounds method
        Coordinate position2 = new Coordinate(5,5);
         System.out.println("// Bounds Check \nThe tile at coordinate (" + position2.getRow() + ", " + position2.getCol() + "): ");
        if (myFirstWarehouse.isWithinBounds(position2)) {
            System.out.println("The coordinate is inside the warehouse!\n");
        } else {
            System.out.println("The coordinate is invalid and not part of the warehouse.\n");
        }

        /* ----------------------------------------------------------------------------------------- */
    }
}