# WarehouseSimulation
A simulation of a small automated warehouse. Robots move through a warehouse, collect items from pickup locations, and deliver them to designated drop-off points. The system must demonstrate how robot behavior, warehouse conditions, and work assignments change over time.

## Preliminary Ideas / Brainstorming
Nothing here is final and is open to revisions; suggestions welcome!  

- The warehouse layout can be modeled as a class that holds a 2D array of Tile objects, like a checkerboard or matrix.  
  - The data field might include variables that describe the important aspects of the board, like board size, max number of (RNG) walls, or (RNG) robot start locations
  - We definitely want to print the board out and show the results after each move

- Each "square" or "tile" can be an instantiation of a Tile class.
  - We might use an enumeration to describe the tile type. For instance: OPEN_SPACE, WALL, CHARGE_STATION, etc.
 
- A delivery robot class
  - Data members might include total fuel capacity, fuel remaining, total moves made, and/or whether the robot is holding a delivery
  - Needs to say "Ouch!" when running into a wall. Doubly so if bumped into another robot.
  - Member methods may include bool hasDelivery, bool isCharged.
 
- A general way to define and model time
  - We could model time as 1 action = 1 unit of time, 10 actions = 10 units of time, etc.
  - Keep track with increment variables after each action
 

 


