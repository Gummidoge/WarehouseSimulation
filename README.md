# WarehouseSimulation
A simulation of a small automated warehouse. Robots move through a warehouse, collect items from pickup locations, and deliver them to designated drop-off points. The system must demonstrate how robot behavior, warehouse conditions, and work assignments change over time.

## Preliminary Ideas / Brainstorming
Nothing here is final and is open to revisions; suggestions welcome!  

- A simulation class to manage the general operations of each "tick"
  -Essentially, our program wants to run on "ticks", and a variety of things must be attempted in each tick. The user should be able to observe the results after each tick. Further into development, we may add a way to advance more than one tick at a time.
  - Members inlude the warehouse object, task manager object, movement controller object, and a list of the robots.
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

- Updated 10/26/2026