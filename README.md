README
(updated 11/23/2026)

# Object-Oriented Programming Team Project | Warehouse Simulation

## Project Overview

A simulation of a small automated warehouse. Automated robots move through a warehouse, collect items from pickup locations, and deliver them to designated drop-off points. The system must demonstrate how robot behavior, warehouse conditions, and work assignments change over time.

## Team Members  

- Marina  
- Audrey  
- Swan  
- Kyle  
- Francisco  
- Jason  

The following is a generalized list of contributions from each student. It was very common for us to error check each other's code, resolve any outstanding issues, implement helper functions for each other, etc. 

| Name | Primary Contributions |
|---|---| 
| Marina | Simulation and Simulation Test class designer, testing and feedback |
| Audrey | Robot and Coordinate class designer, overall project management, testing and feedback |
| Swan | Task and Task Manager designer |
| Kyle | Main menu implementation, documentation |
| Francisco | Movement Controller designer |
| Jason | Warehouse and Tile class designer, documentation |


## Features  

Here is a list of some notable features:

- Ability to sync and tie all warehouse actions to a unit of time.
  - Multiple units of time can also be traversed at once, allowing for expedited shipping.
- Customizable warehouse and tile placement (dev only).
  - Examples include walls, charging stations, floors and delivery zones.
- Ability to reset the warehouse, all tasks, and current robot locations back to its initial settings.
- User option to create, queue, and observe different tasks for each robot, such as delivery pickups.
- A coordinate system to assist in delegating orders to robots, tile placement, pathfinding validation, etc.
- An autonomous navigation system to help robots reach their destinations efficiently.
- Menu options to create new tasks, view current tasks, and view all robots' information.
- Robust exception handling for user input and other potential program errors.

## Class Design  

Some of the more prominent classes used in the program are listed below. 

| Class | Responsibility |
|---|---| 
| Coordinate | Allows an easy way to input, search, and view locations throughout the warehouse grid by using matrix-style coordinate notation. |
| Tile (abstract) | Along with its tile-type subclasses, serves as the building blocks for the warehouse grid. Tiles include walls, floors, pick-up zones, drop-off zones, and charging stations. |
| Robot | Creates a robot object that casually moves about the warehouse collecting and dropping off packages. Holds a unique ID number, position, battery level, task, and pathing route. Its status can also be printed to the console. |
| Task | Creates a new task and assigns it to a robot. Tasks include picking up and dropping off packages at their respective locations. Each task has their own unique ID.  |
| Movement Controller | Responsible for robot pathing throughout the warehouse. Checks if tiles can be traversed (i.e. no running through walls). Tries to find the simplest path possible to a destination. |
| Simulation | Simulates all robots' movement and statuses while maneuvering the warehouse, such as charging, idling, task handling, and determining the next destination. Also in charge of the passage of time and the current "tick" count.  |
| Simulation Test | Works in tandem with Simulation to handle edge cases, display error messages, and check that everything is running smoothly and correctly. |
| Task Manager | In charge of queuing up and handing out tasks to robots, or storing them in a pending state for later use. Also able to print all available and pending tasks to the console. |
| Warehouse | The program's 2D movement grid / floor plan. Can create a new map, modify existing tiles, find all locations of similar tiles, check that certain tiles are traversable, and print the current status of the warehouse map--including robot locations--to the console. |


## Packages

Our package layout closely follows what was recommended by our previous project's guide. 

- App: Includes the `Main.java` file
- Model: Holds all of the `Coordinate.java`, `Tile.java` (and its subclasses), `Task.java`, and `Robot.java` object data. 
- Persistence: No saving or loading was required for this project, although if further expanded upon may prove useful.
- Service: Holds the `Movement.java`, `Simulation.java`, `TaskManager.java`, and `Warehouse.java` files. 

## Building  

This specific project was built entirely in VS Code using that program's recommended Java extensions and add-ons, including a JDK. Note that an in-program pdf viewer is required to observe the project's UML diagram.  


## Running  

The program is run through a console menu user-interface; the following are options the user can select when presented with the main menu. Note that some of these options will prompt for additional information from the user.

| # | Option | Description |
|---|---|---|
| 1 | Advance One Tick | Advances the simulation by 1 unit of time. All robot movement, current battery charge amounts, pending / assigned tasks, etc. are incremented during each time advance. |
| 2 | Advance Multiple Ticks | Same as above, except advances the simulation by a desired unit of time; inputting '2' advances time by two units, for example. 
| 3 | Create New Task | Creates a new task for a delivery robot to perform. The user can give the task an unique number ID as well as the coordinates for the pick-up and drop-off point (i.e. pickup and delivery tiles). |
| 4 | View Tasks | Displays all current tasks. Tasks are classified as pending until an idle robot is available to actively start its delivery protocol. |
| 5 | View Robots | Displays relevant info for each robot-- its ID number, current position, battery level, status, current task, and current destination. |
| 6 | Restart Simulation | Resets all values, robot positions, tasks, etc. back to their original values when first opening the program. |
| 0 | Exit | Terminates the program. |  

## Notable Design Decisions and Feedback

Some miscellaneous notes: 
- The very first thing that sprang to mind when our team brainstormed this project was to create a 2D grid / array to represent the warehouse floor. Most of our other class design decisions were based heavily with this in mind, allowing for a more manageable workflow.
- Brainstorming big projects like this in general is immensely helpful, and to keep everyone on the same page.
- Representing Tile as an abstract class was a good idea and helped with code readability and conciseness.
- Once again, we decided to follow most of the guidelines illustrated in our first class project instruction packet (package layout, workflow responsibilities, etc.), and it again worked out nicely. Organizing the initial package layout in particular is quite handy (i.e. app, model, service).
- The team is becoming more accustomed to using Git and GitHub effectively.

## Known Limitations  

- It's possible that 2 robots could get hung up on each other when each one passes nearby. We have yet to institute a fix, but we are aware of the problem!
- Other than that, there aren't any other issues that we can think of.

Thanks for reading!  


# Bonus: Original Brainstorm Notes

## Preliminary Ideas / Brainstorming
Nothing here is final and is open to revisions; suggestions welcome!  

- A simulation class to manage the general operations of each "tick"
  -Essentially, our program wants to run on "ticks", and a variety of things must be attempted in each tick. The user should be able to observe the results after each tick. Further into development, we may add a way to advance more than one tick at a time.
  - Members include the warehouse object, task manager object, movement controller object, and a list of the robots.
  - For each "tick", operations such as these are performed:
    -Check if any tasks are currently pending
    -If any tasks are pending, attempt to assign them to an available robot
    -For robots that have tasks, attempt to proceed with their current task
    -If a robot just reached a delivery, update their route
    -If a robot just finished a task or cannot complete their task, update their status
  - And possibly more, as program development progresses. Note: Simulation should NOT handle the implementation for these operations, only call them.
  - Keep track with increment variables after each action.
  - Possibly implement a method to reset the scenario at some point.

- The warehouse layout can be modeled as a class that holds a 2D array of Tile objects, like a checkerboard or matrix.  
  - The members include the 2D array of tile objects, and the board size (rows and cols). Further into development we may add max number of (RNG) obstacles, or (RNG) robot start locations.
  - Member methods may include:
    - get rows/cols
    - is within bounds (returns if a given coordinate is outside of the grid)
    - is traversable
    - get tile (return what kind of tile is at a given coordinate)

- The behavior of each robot can be defined in a class called MovementController. This class can contain methods to assist robots with traversing the grid. No declared members.
- Methods may include:
  - find path (do path finding to check if the robot's destination is within reach for it, record the route if so. Assign the route to the robot)
  - move a step (attempt to move the robot one step based on its current route, perform checks inside)
  - is valid move (checks if the next step in the route is traversable)

- The management of tasks can be defined in a class called TaskManager, including a member containing an array of tasks currently recorded in the simulation.
- Users are able to add tasks, and the status of tasks may change over time, so this class must handle these operations.
- Methods may include:
  - add a task to the list
  - attempt to assign a task to a robot
  - get all tasks
  - get pending tasks
  - update the status of a task
  - find a task by a given id

- Each "square" or "tile" can be an instantiation of a Tile class.
  - The Tile class can be an abstract class, and there can be different kinds of tiles on the warehouse grid.
  - For example: Wall, Floor, Pickup, Delivery, Charging Station. Note: Robot is not a Tile class.
  - Member methods may include:
    - is traversable
    - get display character (what character that tile is being shown as on the board)
 
- A delivery robot class
  - Data members might include id, position, battery power, current assigned task, and current status
  - Needs to say "Ouch!" when running into a wall. Doubly so if bumped into another robot. (We can add this implementation into MovementController :p)
  - Member methods may include:
    -set/get position
    -set/get current task
    -get status

- A coordinate class to assist with defining positions of things
  - This is really just a helper class of sorts that contains an int for the row and an int for the column number, to help specify where something is on the grid.
  - Methods may include:
    - get row
    - get col

- Eventually, persistence may be implemented to assist with testing, but it is not necessary for the assignment by any means. The file manager class was added to the uml for now, but it is low priority until we get a running program to actually test.

- Also we should add the readInt helper function Jason wrote at the bottom of Main here as well, to help with the user adding tasks, potentially advancing multiple ticks, or other features we may add
