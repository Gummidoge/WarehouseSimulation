package service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import model.Coordinate;
import model.Robot;

public class MovementController {

    public ArrayList<Coordinate> findPath(Coordinate start, Coordinate end, Warehouse warehouse, ArrayList<Robot> robots, Robot robot) {
        if (warehouse == null || start == null || end == null) {
            return null;
        }
        if (!warehouse.isWithinBounds(start) || !warehouse.isWithinBounds(end) || !warehouse.isTraversable(start) || !warehouse.isTraversable(end)) {
            return null;
        }

        boolean[][] visited = new boolean[warehouse.getRows()][warehouse.getCols()];
        Coordinate[][] previous = new Coordinate[warehouse.getRows()][warehouse.getCols()];
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start.getRow()][start.getCol()] = true;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        while (!queue.isEmpty()) {
            Coordinate current = queue.remove();
            if (current.getRow() == end.getRow() && current.getCol() == end.getCol()) {
                ArrayList<Coordinate> route = new ArrayList<>();
                while (current.getRow() != start.getRow() || current.getCol() != start.getCol()) {
                    route.add(current);
                    current = previous[current.getRow()][current.getCol()];
                }
                Collections.reverse(route);
                return route;
            }

            for (int[] direction : directions) {
                int row = current.getRow() + direction[0];
                int col = current.getCol() + direction[1];

                // Check integers first: Coordinate rejects negative values.
                if (row < 0 || row >= warehouse.getRows() || col < 0 || col >= warehouse.getCols() || visited[row][col]) {
                    continue;
                }
                Coordinate next = new Coordinate(row, col);
                if (warehouse.isTraversable(next)) {
                    visited[row][col] = true;
                    previous[row][col] = current;
                    queue.add(next);
                }
            }
        }
        return null;
    }


    public boolean isValidMove(Coordinate nextPosition, Warehouse warehouse,
            ArrayList<Robot> robots, Robot robot) {
        if (nextPosition == null || warehouse == null || robots == null || robot == null) {
            return false;
        }
        if (!warehouse.isWithinBounds(nextPosition) || !warehouse.isTraversable(nextPosition)) {
            return false;
        }
        for (Robot other : robots) {
            if (other == null || other == robot) {
                continue;
            }
            Coordinate position = other.getPosition();

            // An unknown position makes occupancy unsafe to assume
            if (position == null) {
                return false;
            }
            if (position.getRow() == nextPosition.getRow()
                    && position.getCol() == nextPosition.getCol()) {
                return false;
            }
        }
        return true;
    }


    public boolean moveOneStep(Robot robot, Coordinate nextPosition, Warehouse warehouse, ArrayList<Robot> robots) {

        if (robot == null || nextPosition == null || robot.getPosition() == null || robot.getBatteryLevel() <= 0) {
            return false;
        }
        Coordinate current = robot.getPosition();
        if (Math.abs((long) nextPosition.getRow() - current.getRow()) + Math.abs((long) nextPosition.getCol() - current.getCol()) != 1) {
            return false;
        }
        // Prevent the supplied destination from disagreeing with the route.
        if (robot.getCurrentRoute() == null || robot.getCurrentRoute().isEmpty()) {
            return false;
        }
        Coordinate first = robot.getCurrentRoute().get(0);
        if (first == null || first.getRow() != nextPosition.getRow() || first.getCol() != nextPosition.getCol()) {
            return false;
        }
        if (warehouse == null || !warehouse.isWithinBounds(current) || !warehouse.isTraversable(current) || !isValidMove(nextPosition, warehouse, robots, robot)) {
            return false;
        }

        robot.setPosition(nextPosition);
        robot.consumeBattery();
        robot.getCurrentRoute().remove(0);
        return true;
    }
}

