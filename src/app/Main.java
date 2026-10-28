package app;

import model.Robot;
import model.Coordinate;

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

    }
}