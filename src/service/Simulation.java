
package service;

import java.util.ArrayList;
import java.util.List;

import model.Robot;
import model.Task;
import model.ChargingTile;
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

    // Add a robot to the list of robots
    public void addRobot(Robot robot) {
        if (robot == null) {
            throw new IllegalArgumentException("Robot cannot be null.");
        }

        robots.add(robot);
    }

    public ArrayList<Robot> getRobots() {
        return new ArrayList<>(robots);
    }

    // Advance the simulation by one tick
    public void step() {
        tickCount++;

        // Assign tasks to available robots
        taskManager.assignTasks(robots);

        // Update task progression for each robot
        for (Robot robot : robots) {
            processRobot(robot);
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

        // If a robot does not have a task, process it separately
        if (task == null) {
            processIdleRobot(robot);
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

        // Determine where the robot needs to go
        Coordinate destination = null;

        if ("ASSIGNED".equals(task.getStatus())) {
            destination = task.getPickup();
        } else if ("PICKED_UP".equals(task.getStatus())) {
            destination = task.getDelivery();
        }

        // Move toward the destination if not already there
        if (destination != null && !isAtLocation(robot, destination)) {

            ArrayList<Coordinate> route = movementController.findPath(
                robot.getPosition(),
                destination,
                warehouse,
                robots,
                robot
            );

            if (route == null || route.isEmpty()) {
                System.out.println(
                    "Robot " + robot.getId() +
                    " cannot find a route."
                );
                return;
            }

            robot.setCurrentRoute(route);

            Coordinate nextPosition = route.get(0);

            boolean moved = movementController.moveOneStep(
                robot,
                nextPosition,
                warehouse,
                robots
            );

            if (!moved) {
                System.out.println(
                    "Robot " + robot.getId() +
                    " is blocked and will retry next tick."
                );
                return;
            }
        }

        // Pickup stage
        if ("ASSIGNED".equals(task.getStatus())) {
            if (isAtLocation(robot, task.getPickup())) {
                taskManager.setTaskStatus(
                    task, "PICKED_UP"
                );

                System.out.println(
                    "Robot " + robot.getId() +
                    " picked up delivery for task " + task.getId()
                );
            }

            // Only process one stage per tick
            return;
        }

        // Delivery stage
        if ("PICKED_UP".equals(task.getStatus())) {
            if (isAtLocation(robot, task.getDelivery())) {
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

    // Send idle robots to the nearest available charging station
    private void processIdleRobot(Robot robot) {
        // If the robot is idle but its battery is already full, it doesn't need to recharge
        if (robot.getBatteryLevel() == 100) {
            robot.setStatus("IDLE");
            return;
        }

        // Find all charging stations in the warehouse
        List<Coordinate> chargingStations = warehouse.findTiles(ChargingTile.class);

        Coordinate nearestStation = null;
        ArrayList<Coordinate> shortestRoute = null;

        // Check each charging station to see if already occupied by another robot
        for (Coordinate station : chargingStations) {
            boolean occupied = false;

            for (Robot other : robots) {
                if (other != robot && isAtLocation(other, station)) {
                    occupied = true;
                    break;
                }
            }

            if (occupied) {
                continue;
            }

            // If the robot is already at this station, it can charge
            if (isAtLocation(robot, station)) {
                nearestStation = station;
                shortestRoute = new ArrayList<>();
                break;
            }

            // Otherwise, find a route to this charging station
            ArrayList<Coordinate> route = movementController.findPath(robot.getPosition(), station, warehouse, robots, robot);
            // Keep the shortest available route
            if (route != null && (shortestRoute == null || route.size() < shortestRoute.size())) {
                nearestStation = station;
                shortestRoute = route;
            }
        }

        // If no reachable charging station can be found, remain idle
        if (nearestStation == null) {
            robot.setStatus("IDLE");
            return;
        }

        // If already at a charging station, charge battery 1 point
        if (isAtLocation(robot, nearestStation)) {
            robot.setStatus("CHARGING");
            robot.chargeBattery();
            return;
        }

        // Otherwise, move one step along the route toward the charging station
        robot.setCurrentRoute(shortestRoute);

        Coordinate nextPosition = shortestRoute.get(0);

        boolean moved = movementController.moveOneStep(robot, nextPosition, warehouse, robots);

        if (moved) {
            robot.setStatus("MOVING_TO_CHARGER");
        }
    }

    // Print all robots to the console
    public void displayAllRobots() {
        if (robots.isEmpty()) {
			System.out.println("No robots found.");
			return;
		}

        for (Robot robot : robots) {
            robot.printRobot();
            System.out.println("--------------------");
        }
    }
}
