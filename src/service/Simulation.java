
package service;

import java.util.ArrayList;
import model.Robot;

public class Simulation {

    // Components needed to run the simulation
    private Warehouse warehouse;
    private TaskManager taskManager;
    private MovementController movementController;

    // Robots participating in the simulation
    private ArrayList<Robot> robots;

    // Number of simulation steps completed
    private int tickCount;

    // Constructor
    public Simulation(Warehouse warehouse,
                      TaskManager taskManager,
                      MovementController movementController,
                      ArrayList<Robot> robots) {

        // Validate the required components
        if (warehouse == null ||
            taskManager == null ||
            movementController == null ||
            robots == null) {

            throw new IllegalArgumentException(
                "Simulation components cannot be null."
            );
        }

        // Initialize simulation components
        this.warehouse = warehouse;
        this.taskManager = taskManager;
        this.movementController = movementController;
        this.robots = new ArrayList<>(robots);

        // Start simulation at tick zero
        this.tickCount = 0;
    }

    // Return the current simulation tick
    public int getTickCount() {
        return tickCount;
    }

    // Return the robots in the simulation
    public ArrayList<Robot> getRobots() {
        return new ArrayList<>(robots);
    }

    // Advance the simulation by one tick
    public void step() {

        // Increase the simulation tick counter
        tickCount++;

        System.out.println(
            "Simulation Tick: " + tickCount
        );

        // Assign pending tasks to available robots
        taskManager.assignTasks(robots);

        // Display robot information
        for (Robot robot : robots) {
            robot.printRobot();
        }
    }
}
