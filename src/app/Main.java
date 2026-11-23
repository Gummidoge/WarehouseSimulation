package app;

import model.Robot;
import model.Task;
import model.ChargingTile;
import model.Coordinate;
import model.PickupTile;
import model.DeliveryTile;
import model.WallTile;

import service.Simulation;
import service.MovementController;
import service.TaskManager;
import service.Warehouse;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Initialize an 11x12 warehouse, has a perimeter of walls by default
        Warehouse warehouse = new Warehouse(11, 12);

        // Set tiles in the warehouse here as desired. Create delivery, pickup, etc. locations.
        // This section may possibly be changed to load from a file if we add persistence.
        warehouse.setTile(new Coordinate(1,4), new PickupTile());
        warehouse.setTile(new Coordinate(1,6), new PickupTile());
        warehouse.setTile(new Coordinate(1,8), new PickupTile());
        warehouse.setTile(new Coordinate(1, 10), new PickupTile());
        warehouse.setTile(new Coordinate(4,8), new PickupTile());

        warehouse.setTile(new Coordinate(9,1), new DeliveryTile());
        warehouse.setTile(new Coordinate(9,4), new DeliveryTile());
        warehouse.setTile(new Coordinate(9,7), new DeliveryTile());
        warehouse.setTile(new Coordinate(9,10), new DeliveryTile());

        warehouse.setTile(new Coordinate(4,1), new ChargingTile());
        warehouse.setTile(new Coordinate(4,10), new ChargingTile());

        warehouse.setTile(new Coordinate(1,3), new WallTile());
        warehouse.setTile(new Coordinate(2,3), new WallTile());
        warehouse.setTile(new Coordinate(5,1), new WallTile());
        warehouse.setTile(new Coordinate(5,2), new WallTile());
        warehouse.setTile(new Coordinate(5,3), new WallTile());
        warehouse.setTile(new Coordinate(5,4), new WallTile());
        warehouse.setTile(new Coordinate(5,7), new WallTile());
        warehouse.setTile(new Coordinate(5,8), new WallTile());
        warehouse.setTile(new Coordinate(5,9), new WallTile());
        warehouse.setTile(new Coordinate(5, 10), new WallTile());
        warehouse.setTile(new Coordinate(8,3), new WallTile());
        warehouse.setTile(new Coordinate(8,8), new WallTile());
        warehouse.setTile(new Coordinate(9,3), new WallTile());
        warehouse.setTile(new Coordinate(9,8), new WallTile());

        // Initialize the rest of the management objects
        TaskManager taskManager = new TaskManager();
        MovementController movementController = new MovementController();

        // Initialize simulation using the given objects
        Simulation simulation = new Simulation(warehouse, taskManager, movementController, new ArrayList<>());

        // Initialize three robots and declare their starting locations,
        // maybe can use this to reset scenario later on when implemented
        Robot robotOne = new Robot (1, new Coordinate(1, 1));
        Robot robotTwo = new Robot (2, new Coordinate(2, 1));
        Robot robotThree = new Robot (3, new Coordinate(3, 1));
        // Add the three robots to the robot list in simulation
        simulation.addRobot(robotOne);
        simulation.addRobot(robotTwo);
        simulation.addRobot(robotThree);

        // Initialize an int to hold the user's menu selections
        int menuSelect = 1;

        // Begin a loop to return to menu after each selection (until the selection is 0)
        do {
            // Print simulation title
            System.out.println("\nRobot Warehouse Simulation");
            // Print current tick count
            System.out.println("--- Simulation Tick: " + simulation.getTickCount() + " ---\n");

            // Print the warehouse grid
            warehouse.printMap(simulation.getRobots());

            // Print menu
            System.out.println("--- User Menu ---\n");
            System.out.print("1. Advance 1 Tick\n" + "2. Advance Multiple Ticks\n" + "3. Create New Task\n"
                           + "4. View Tasks\n" + "5. View Robots\n" + "6. Restart Simulation\n" + "0. Exit\n\n");

            // User input check (using helper function readInt() at the bottom of main)
            menuSelect = readInt(input, "Enter selection: ", 0, 6);

            switch (menuSelect) {
                // 1. Advance 1 Tick
                case 1: {
                    System.out.println("\nAdvancing 1 tick.");
                    simulation.step();
                    break;
                }
                // 2. Advance Multiple Ticks
                case 2: {
                    System.out.println("\nAdvance Multiple Ticks Selected.");
                    System.out.println("How many ticks would you like to advance the simulation?");
                    int ticksToAdvance = readInt(input, "# of ticks: ", 1, 999);
                    // Loop that many times, do a step each time
                    for (int i = 0; i < ticksToAdvance; i++) {
                        simulation.step();  // Perform all relevant function calls for a tick passing
                    }
                    System.out.println("Advanced " + ticksToAdvance + " tick(s).");
                    break;
                }

                // 3. Create a New Task
                case 3: {
                    System.out.println("\nCreate New Task Selected.\n");

                    // Prompt user for task ID, must be a 4 digit int
                    int newTaskId = readInt(input, "Enter task ID: ", 1000, 9999);

                    // Check if the entered ID already exists
                    if (taskManager.findTaskById(newTaskId) != null) {
                        System.out.println("Task ID already exists.");
                        break;  // If so, immediately exit back to menu
                    }
                    
                    // Search the warehouse for which tiles are pickup locations, then list them out
                    List<Coordinate> pickupLocations = warehouse.findTiles(PickupTile.class);
                    System.out.println("\nAvailable pickup locations:");
                    for (Coordinate c : pickupLocations) { System.out.println(c.getRow() + "," + c.getCol()); }

                    // Initialize pickup coordinate object
                    Coordinate pickupCoordinate;
                    // Begin a loop asking for a valid pickup location
                    while (true) {
                        // Prompt the user to choose one of the listed locations
                        System.out.println("Please select one of the available locations for pickup.");
                        int pickupRow = readInt(input, "Enter row: ", 0, warehouse.getRows() - 1);
                        int pickupCol = readInt(input, "Enter column: ", 0, warehouse.getCols() - 1);

                        // Assign the given coordinate to the pickup location
                        pickupCoordinate = new Coordinate(pickupRow, pickupCol);

                        // Search pickupLocations for the given coordinate
                        if (pickupLocations.contains(pickupCoordinate)) {
                            // If found, assign the location
                            System.out.println("Pickup location assigned.");
                            break;  // Exit the loop prompting for pickup location
                        }
                        else {
                            // Otherwise, inform user and continue loop.
                            System.out.println("Invalid location entered.");
                        }
                    }

                    // Search the warehouse for which tiles are delivery locations, then list them out
                    List<Coordinate> deliveryLocations = warehouse.findTiles(DeliveryTile.class);
                    System.out.println("\nAvailable delivery locations:");
                    for (Coordinate c : deliveryLocations) { System.out.println(c.getRow() + "," + c.getCol()); }

                    // Initialize delivery coordinate object
                    Coordinate deliveryCoordinate;
                    // Begin a loop asking for a valid delivery location
                    while (true) {
                        // Prompt the user to choose one of the listed locations
                        System.out.println("Please select one of the available locations for delivery.");
                        int deliveryRow = readInt(input, "Enter row: ", 0, warehouse.getRows() - 1);
                        int deliveryCol = readInt(input, "Enter column: ", 0, warehouse.getCols() - 1);

                        // Assign the given coordinate to the delivery location
                        deliveryCoordinate = new Coordinate(deliveryRow, deliveryCol);

                        // Search deliveryLocations for the given coordinate
                        if (deliveryLocations.contains(deliveryCoordinate)) {
                            // If found, assign the location
                            System.out.println("Delivery location assigned.");
                            break;  // Exit the loop prompting for delivery location
                        }
                        else {
                            // Otherwise, inform user and continue loop.
                            System.out.println("Invalid location entered.");
                        }
                    }

                    // Initialize the task and add it to the task list. The task defaults as having
                    // no robot assigned to it, but calling step will handle the assignemnt.
                    Task newTask = new Task(newTaskId, pickupCoordinate, deliveryCoordinate);
                    taskManager.addTask(newTask);
                    System.out.println("\nTask created successfully.");
                    break;
                }

                // 4. View Tasks
                case 4: {
                    System.out.println("Displaying all current tasks.\n");
                    taskManager.displayAllTasks();
                    break;
                }

                // 5. View Robots
                case 5: {
                    System.out.println("Displaying all current robots.\n");
                    simulation.displayAllRobots();
                    break;
                }

                // 6. Reset Simulation
                case 6: {
                    System.out.println("\nRestarting simulation...");
                    simulation.restart();
                    break;
                }
            }

        // When the user enters 0, the menu loop is exited.
        } while (menuSelect != 0);

        System.out.println();   // Print newline for spacing
    }
    


    // MAIN HELPER METHODS

    // readInt - verifies and returns an int input. Also allows a custom prompt, min range, and max range
    private static int readInt(
    Scanner input, String prompt, int min, int max) {
        while (true) {
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
