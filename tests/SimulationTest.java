
import java.util.ArrayList;

import model.Coordinate;
import model.Robot;
import model.Task;

import service.Warehouse;
import service.TaskManager;
import service.MovementController;
import service.Simulation;

public class SimulationTest {

    private static void check(boolean condition,
                              String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();
        TaskManager manager = new TaskManager();
        MovementController movement =
            new MovementController();

        ArrayList<Robot> robots = new ArrayList<>();

        Robot robot = new Robot(
            1, new Coordinate(0, 0)
        );
        robots.add(robot);

        Task task = new Task(
            1,
            new Coordinate(1, 1),
            new Coordinate(2, 2)
        );
        manager.addTask(task);

        Simulation simulation = new Simulation(
            warehouse, manager, movement, robots
        );

        // TEST 1: Initial tick and task state
        check(simulation.getTickCount() == 0,
              "Tick should start at zero");

        check("PENDING".equals(task.getStatus()),
              "Task should start pending");

        System.out.println("PASS: Initial state");

        // TEST 2: Tick advancement and assignment
        simulation.step();

        check(simulation.getTickCount() == 1,
              "Tick should be one");

        check("ASSIGNED".equals(task.getStatus()),
              "Task should be assigned");

        check(robot.getCurrentTask() == task,
              "Robot should have the assigned task");

        System.out.println("PASS: Tick and assignment");

        // TEST 3: Pickup
        robot.setPosition(new Coordinate(1, 1));
        simulation.step();

        check("PICKED_UP".equals(task.getStatus()),
              "Task should be picked up");

        System.out.println("PASS: Pickup");

        // TEST 4: Delivery
        robot.setPosition(new Coordinate(2, 2));
        simulation.step();

        check("COMPLETED".equals(task.getStatus()),
              "Task should be completed");

        System.out.println("PASS: Delivery");

        // TEST 5: Robot becomes available
        check(robot.getCurrentTask() == null,
              "Robot should have no task");

        check("IDLE".equals(robot.getStatus()),
              "Robot should be idle");

        check(simulation.getTickCount() == 3,
              "Tick should be three");

        System.out.println("PASS: Robot availability");

        // TEST 6: Battery depletion
        Robot lowBatteryRobot = new Robot(
            2, new Coordinate(0, 0)
        );

        Task batteryTask = new Task(
            2,
            new Coordinate(1, 1),
            new Coordinate(2, 2)
        );

        // Assign while the robot has battery
        lowBatteryRobot.assignTask(batteryTask);
        lowBatteryRobot.setStatus("BUSY");
        batteryTask.setStatus("ASSIGNED");

        // Drain the battery
        for (int i = 0; i < 100; i++) {
            lowBatteryRobot.consumeBattery();
        }

        ArrayList<Robot> batteryRobots =
            new ArrayList<>();
        batteryRobots.add(lowBatteryRobot);

        Simulation batterySimulation = new Simulation(
            warehouse,
            new TaskManager(),
            movement,
            batteryRobots
        );

        batterySimulation.step();

        check(lowBatteryRobot.getBatteryLevel() == 0,
              "Battery should be zero");

        check("STOPPED".equals(lowBatteryRobot.getStatus()),
              "Robot should be stopped");

        check("ASSIGNED".equals(batteryTask.getStatus()),
              "Depleted robot should not progress task");

        System.out.println("PASS: Battery depletion");

        
        // TEST 7: Multiple robots and tasks
        TaskManager multiManager = new TaskManager();
        ArrayList<Robot> multiRobots = new ArrayList<>();

        Robot robotA = new Robot(
            10, new Coordinate(0, 0)
        );
        Robot robotB = new Robot(
            11, new Coordinate(3, 3)
        );

        multiRobots.add(robotA);
        multiRobots.add(robotB);

        Task taskA = new Task(
            10,
            new Coordinate(1, 1),
            new Coordinate(2, 2)
        );
        Task taskB = new Task(
            11,
            new Coordinate(4, 4),
            new Coordinate(5, 5)
        );

        multiManager.addTask(taskA);
        multiManager.addTask(taskB);

        Simulation multiSimulation = new Simulation(
            warehouse, multiManager, movement, multiRobots
        );

        multiSimulation.step();

        check("ASSIGNED".equals(taskA.getStatus()),
              "Task A should be assigned");
        check("ASSIGNED".equals(taskB.getStatus()),
              "Task B should be assigned");

        check(robotA.getCurrentTask() == taskA,
              "Robot A should have task A");
        check(robotB.getCurrentTask() == taskB,
              "Robot B should have task B");

        System.out.println("PASS: Multiple task assignment");

        // TEST 8: Independent pickup and delivery
        robotA.setPosition(new Coordinate(1, 1));
        multiSimulation.step();

        check("PICKED_UP".equals(taskA.getStatus()),
              "Task A should be picked up");
        check("ASSIGNED".equals(taskB.getStatus()),
              "Task B should still be assigned");

        robotA.setPosition(new Coordinate(2, 2));
        robotB.setPosition(new Coordinate(4, 4));
        multiSimulation.step();

        check("COMPLETED".equals(taskA.getStatus()),
              "Task A should be completed");
        check("PICKED_UP".equals(taskB.getStatus()),
              "Task B should be picked up");

        robotB.setPosition(new Coordinate(5, 5));
        multiSimulation.step();

        check("COMPLETED".equals(taskB.getStatus()),
              "Task B should be completed");

        check("IDLE".equals(robotA.getStatus())
              && "IDLE".equals(robotB.getStatus()),
              "Both robots should be idle");

        System.out.println("PASS: Independent task progression");

        // TEST 9: No battery means no new assignment
        TaskManager emptyBatteryManager = new TaskManager();
        ArrayList<Robot> emptyBatteryRobots =
            new ArrayList<>();

        Robot emptyRobot = new Robot(
            20, new Coordinate(0, 0)
        );

        for (int i = 0; i < 100; i++) {
            emptyRobot.consumeBattery();
        }

        emptyBatteryRobots.add(emptyRobot);

        Task pendingTask = new Task(
            20,
            new Coordinate(1, 1),
            new Coordinate(2, 2)
        );

        emptyBatteryManager.addTask(pendingTask);

        Simulation emptyBatterySimulation = new Simulation(
            warehouse,
            emptyBatteryManager,
            movement,
            emptyBatteryRobots
        );

        emptyBatterySimulation.step();

        check("PENDING".equals(pendingTask.getStatus()),
              "Task should remain pending");

        check(emptyRobot.getCurrentTask() == null,
              "Empty battery robot should not receive a task");

        System.out.println("PASS: No assignment with empty battery");

        System.out.println(
            "\nAll 9 simulation tests passed!"
        );

    }
}
