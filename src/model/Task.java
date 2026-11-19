package model;

public class Task {
    private final int id;
    private final Coordinate pickup;
    private final Coordinate delivery;
    private String status;
    private Robot assignedRobot;

    // Create a pending task with no robot assigned
    public Task(int id, Coordinate pickup, Coordinate delivery) {
        if (id < 0) {
            throw new IllegalArgumentException("Task ID cannot be negative.");
        }
        if (pickup == null || delivery == null) {
            throw new IllegalArgumentException(
                "Pickup and delivery locations cannot be null."
            );
        }

        this.id = id;
        this.pickup = pickup;
        this.delivery = delivery;
        this.status = "PENDING";
        this.assignedRobot = null;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Coordinate getPickup() {
        return pickup;
    }

    public Coordinate getDelivery() {
        return delivery;
    }

    public String getStatus() {
        return status;
    }

    public Robot getAssignedRobot() {
        return assignedRobot;
    }

    // Setters
    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Task status cannot be null or empty."
            );
        }

        this.status = status;
    }

    public void setAssignedRobot(Robot assignedRobot) {
        this.assignedRobot = assignedRobot;
    }

    // Print the task's information
    public void printTask() {
        System.out.println("----- Information for Task " + id + " -----");
        System.out.println("ID: " + id);
        System.out.println(
            "Pickup location: " + pickup.getRow() + "," + pickup.getCol()
        );
        System.out.println(
            "Delivery location: " + delivery.getRow() + "," + delivery.getCol()
        );
        System.out.println("Status: " + status);

        if (assignedRobot == null) {
            System.out.println("Assigned robot: None");
        } else {
            System.out.println("Assigned robot: " + assignedRobot.getId());
        }
    }
}