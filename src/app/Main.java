package app;

import model.Robot;
import model.Coordinate;
import model.Tile;
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

        // Creating and printing out a 10x10 empty warehouse map. 
        // Note that perimeter walls are always added by default. */
        Warehouse myFirstWarehouse = new Warehouse(10, 10);
        System.out.println("\n*** Jason's stuff ***\nAn empty sample warehouse display: \n");
        myFirstWarehouse.printMap();

        // Modifying a few tiles, then reprinting the warehouse map
        myFirstWarehouse.setTile(new Coordinate(2,2), new PickupTile());
        myFirstWarehouse.setTile(new Coordinate(7,5), new DeliveryTile());
        myFirstWarehouse.setTile(new Coordinate(2,7), new WallTile());
        System.out.println("The same map with some altered tiles: \n");
        myFirstWarehouse.printMap();

        // Checking getTile method (Wall tile example)
        Coordinate position = new Coordinate(2,7);
        System.out.println("The tile at coordinate (" + position.getRow() + ", " + position.getCol() + "): ");
        Tile myTile = myFirstWarehouse.getTile(position);
        if (myTile.isTraversable()){
            System.out.println("You can walk here.\n");
        } else {
            System.out.println("You will bonk your robot head here.\n");
        }

        /* ----------------------------------------------------------------------------------------- */
    }
}