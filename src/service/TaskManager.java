package service;

import java.util.ArrayList;
import model.Task;
import model.Robot;

public class TaskManager {
    private final ArrayList<Task> tasks;

    // Constructor
    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    // Add a task, rejecting null tasks and duplicate IDs
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        if (findTaskById(task.getId()) != null) {
            throw new IllegalArgumentException("Task ID already exists.");
        }

        tasks.add(task);
    }

    // Assign pending tasks to available robots
    public void assignTasks(ArrayList<Robot> robots) {
        if (robots == null) {
            throw new IllegalArgumentException("Robot list cannot be null.");
        }

        for (Task task : getPendingTasks()) {
            for (Robot robot : robots) {
                if (robot == null) {
                    continue;
                }

                if (robot.getCurrentTask() == null
                        && "IDLE".equals(robot.getStatus())
                        && robot.getBatteryLevel() > 0) {

                    if (robot.assignTask(task)) {
                        task.setAssignedRobot(robot);
                        task.setStatus("ASSIGNED");
                        robot.setStatus("BUSY");
                        break;
                    }
                }
            }
        }
    }

    // Return a copy of the task list
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    // Return tasks that are waiting for assignment
    public ArrayList<Task> getPendingTasks() {
        ArrayList<Task> pendingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if ("PENDING".equals(task.getStatus())
                    && task.getAssignedRobot() == null) {
                pendingTasks.add(task);
            }
        }

        return pendingTasks;
    }

    // Change the task's status if it is in our list
    public void setTaskStatus(Task task, String status) {
        if (task == null || !tasks.contains(task)) {
            throw new IllegalArgumentException(
                "Task is not in this task manager."
            );
        }

        task.setStatus(status);
    }

    // Find a task by ID; return null if not found
    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }
}