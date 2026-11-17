package app;

import model.Robot;
import model.Coordinate;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Initialize an int to hold the user's menu selections
        int menuSelect = 1;

        // Begin a loop to return to menu after each selection (until the selection is 0)
        do {
            // Print menu title
            System.out.println("Robotic Warehouse Management System\n");

            // Print menu
            System.out.print("1. Start/Restart Simulation\n" + "2. Create New Task\n" + "3. Advance Time\n" + "4. View Robots\n"
                            + "5. View Tasks\n" + "0. Exit\n\n");

            // User input check (using helper function readInt() at the bottom of main)
            menuSelect = readInt(input, "Enter selection: ", 0, 5);

            switch (menuSelect) {
                case 1: {
                    Warehouse myFirstWarehouse = new Warehouse(8, 12);
                    System.out.println("\n Initializing an empty warehouse: \n");
                    myFirstWarehouse.printMap();
                    break;
                }

                case 2: {
                    // needs Coordinate start Coordinate end
                    int newTaskId = readInt(input, "Enter Task ID: ", 1000, 9999);

                    if (taskSystem.findTaskById(newTaskId) != null) {
                        System.out.println("Task ID already exists.\n");
                        break;   
                    } 
                    else 
                    {   
                        int newXAxis = readInt(input, "Enter starting x-axis : ", 0, 8);
                        int newYAxis = readInt(input, "Enter starting y-axis : ", 0, 12);

                        Coordinate myStartCoordinate = new Coordinate(newXAxis, newYAxis);

                        newXAxis = readInt(input, "Enter ending x-axis : ", 0, 8);
                        newYAxis = readInt(input, "Enter ending y-axis : ", 0, 12);

                        Coordinate myEndCoordinate = new Coordinate(newXAxis, newYAxis);

                        Task newTask = new Task(newTaskId, myStartCoordinate, myEndCoordinate);

                        TaskManager.addTask(newTask);
                    }
                    
                     
                }

                case 3: {
                    simulation.step();
                }

                case 4: {
                    // maybe this works
                    myRobot.printRobot();
                    System.out.println();
                }

                case 5: {
                    // same for this one
                    currentTask.printTask();
                    System.out.println();
                }

        // When the user enters 0, the menu loop is exited.
        } while (menuSelect != 0);

        System.out.println();   // Print newline for spacing


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
        private static int readInt(
        Scanner input, String prompt, int min, int max) {

        while (true) 
            {
            System.out.print(prompt);

            if (!input.hasNextInt()) 
                {
                    System.out.println("Please enter a valid integer value.");
                    input.next(); 
                    continue;
                }

            int value = input.nextInt();

            if (value >= min && value <= max) 
                {
                    input.nextLine(); 
                    return value;
                }

            System.out.println("Enter a number from " + min + " to " + max + ".");
            }
        }        
    }
}
