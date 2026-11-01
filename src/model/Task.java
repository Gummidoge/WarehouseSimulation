package model;

public class Task {
    private final int id;
    private final Coordinate start;
    private final Coordinate end;
    private String status;
    private Robot assignedRobot;

    // Create a pending task with no robot assigned
    public Task(int id, Coordinate start, Coordinate end) {
        if (id < 0) {
            throw new IllegalArgumentException("Task ID cannot be negative.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException(
                "Pickup and delivery locations cannot be null."
            );
        }

        this.id = id;
        this.start = start;
        this.end = end;
        this.status = "PENDING";
        this.assignedRobot = null;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
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
        System.out.println("-----Information for Task " + id + "-----");
        System.out.println("ID: " + id);
        System.out.println(
            "Pickup location: " + start.getRow() + "," + start.getCol()
        );
        System.out.println(
            "Delivery location: " + end.getRow() + "," + end.getCol()
        );
        System.out.println("Status: " + status);

        if (assignedRobot == null) {
            System.out.println("Assigned robot: None");
        } else {
            System.out.println("Assigned robot: " + assignedRobot.getId());
        }
    }
}