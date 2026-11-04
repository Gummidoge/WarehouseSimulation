
package service;

import java.util.ArrayList;
import model.Robot;
import model.Task;
import model.Coordinate;

public class Simulation {

    private final Warehouse warehouse;
    private final TaskManager taskManager;
    private final MovementController movementController;
    private final ArrayList<Robot> robots;
    private int tickCount;

    public Simulation(Warehouse warehouse,
                      TaskManager taskManager,
                      MovementController movementController,
                      ArrayList<Robot> robots) {

        if (warehouse == null || taskManager == null ||
            movementController == null || robots == null) {
            throw new IllegalArgumentException(
                "Simulation components cannot be null."
            );
        }

        if (robots.contains(null)) {
            throw new IllegalArgumentException(
                "Robot list cannot contain null."
            );
        }

        this.warehouse = warehouse;
        this.taskManager = taskManager;
        this.movementController = movementController;
        this.robots = new ArrayList<>(robots);
        this.tickCount = 0;
    }

    public int getTickCount() {
        return tickCount;
    }

    public ArrayList<Robot> getRobots() {
        return new ArrayList<>(robots);
    }

    // Advance the simulation by one tick
    public void step() {
        tickCount++;

        System.out.println(
            "\n--- Simulation Tick: " + tickCount + " ---"
        );

        // Assign tasks to available robots
        taskManager.assignTasks(robots);

        // Update task progression for each robot
        for (Robot robot : robots) {
            processRobot(robot);
            robot.printRobot();
        }
    }

    // Compare the robot's position with a destination
    private boolean isAtLocation(Robot robot,
                                 Coordinate destination) {
        if (robot.getPosition() == null ||
            destination == null) {
            return false;
        }

        return robot.getPosition().getRow()
                    == destination.getRow()
            && robot.getPosition().getCol()
                    == destination.getCol();
    }

    // Process pickup and delivery
    private void processRobot(Robot robot) {
        Task task = robot.getCurrentTask();

        if (task == null) {
            return;
        }

        // A depleted robot cannot continue its task
        if (robot.getBatteryLevel() <= 0) {
            robot.setStatus("STOPPED");
            System.out.println(
                "Robot " + robot.getId() +
                " stopped: battery depleted."
            );
            return;
        }

        // Pickup stage
        if ("ASSIGNED".equals(task.getStatus())) {
            if (isAtLocation(robot, task.getStart())) {
                taskManager.setTaskStatus(
                    task, "PICKED_UP"
                );

                System.out.println(
                    "Robot " + robot.getId() +
                    " picked up task " + task.getId()
                );
            }

            // Only process one stage per tick
            return;
        }

        // Delivery stage
        if ("PICKED_UP".equals(task.getStatus())) {
            if (isAtLocation(robot, task.getEnd())) {
                taskManager.setTaskStatus(
                    task, "COMPLETED"
                );

                System.out.println(
                    "Robot " + robot.getId() +
                    " completed task " + task.getId()
                );

                robot.clearCurrentTask();
                robot.setStatus("IDLE");
            }
        }
    }
}
