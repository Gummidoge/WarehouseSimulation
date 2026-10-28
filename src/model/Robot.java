package model;

import java.util.List;
import java.util.ArrayList;

public class Robot {
    private int id;
    private Coordinate position;
    private int batteryLevel;
    private Task currentTask;
    private List<Coordinate> currentRoute;
    private String status;


    // Constructor
    public Robot(int id, Coordinate position) {
        // Validate parameters
        if (id < 0) {
            throw new IllegalArgumentException("Robot ID cannot be negative.");
        }
        if (position == null) {
            throw new IllegalArgumentException("Robot position cannot be null.");
        }

        // Initialize id and position to given parameters
        this.id = id;
        this.position = position;
        
        // Initialize everything else to a default state
        this.batteryLevel = 100;
        this.currentTask = null;
        this.currentRoute = new ArrayList<>();
        this.status = "IDLE";
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public List<Coordinate> getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(List<Coordinate> newRoute) {
        this.currentRoute = new ArrayList<>(newRoute);
    }

    public String getStatus() {
        return status;
    }

    // Note: possibly change status setting to using an enum at some point
    public void setStatus(String status) {
        this.status = status;
    }


    // Other various methods for robot operations

    // Consume one point of battery
    public void consumeBattery() {
        // If battery is not empty, decrement it
        if (batteryLevel > 0) {
            batteryLevel--;
        }
        else {
            System.out.println("Robot " + id + " consumption failed: Battery already empty.\n");
        }
    }

    // Charge one point of battery
    public void chargeBattery() {
        // If battery is not full, increment it
        if (batteryLevel < 100) {
            batteryLevel++;
        }
        else {
            System.out.println("Robot " + id + " charging failed: Battery already at full.\n");
        }
    }

    // Assign a task
    public boolean assignTask(Task newTask) {
        // If the robot doesnt already have an assigned task,
        // and the new task is not null, assign the new one
        if (this.currentTask == null && newTask != null) {
            this.currentTask = newTask;
            return true;
        }
        else {
            return false;
        }
    }

    // Clear the robot's current task (thus clear route as well)
    public void clearCurrentTask() {
        this.currentTask = null;
        this.currentRoute.clear();
    }

    // Print all of a robot's information
    public void printRobot() {
        System.out.println("-----Information for Robot " + id + "-----\n");

        System.out.println("ID: " + id);
        System.out.println("Current Position: " + position.getRow() + "," + position.getCol());
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Status: " + status);

        // If no current task, print none
        if (currentTask == null) {
            System.out.println("Current task: None");
        }
        // Else, print the id of the current assigned task
        else {
            System.out.println("WIP - print the robot's assigned task id");
            // System.out.println("Current task: " + currentTask.getId());
        }

        // If no current route, display no destination
        if (currentRoute.isEmpty()) {
            System.out.println("Current destination: None");
        }
        // Else, display the last element in the list as the destination
        else {
        Coordinate destination = currentRoute.get(currentRoute.size() - 1);

        System.out.println("Current destination: "
            + destination.getRow() + ","
            + destination.getCol());
        }
    }
}