# OurMarket In-site Supermarket (OIS)
## Constraints
- During a simulation the OIS is responsible to represent the simulation in OurMarket: o A layout for OurMarket;
  - An image representing a Customer, a Manager and a Cashier;
- The OIS is an autonomous running process with its own GUI;
- In a simulation there is one OIS only;

## GUI
The OIS GUI, developed using <b>Java Swing</b> must implement the following requirements:

- An OutsideHall:
  - Where selected Customers wait authorization (given by the Manager) to enter the OurMarket;
  - implementation based on a fifo;
  - Customers with lower id have priority on entering the fifo;
- An EntranceHall:
  - Where Customers coming from the OutsideHall wait authorization (given by the Manager) to go to the CorridorHall;
  - Implementation based on a fifo with 6 positions;
- A CorridorHall:
  - Where Customers coming from the EntranceHall wait before entering a corridor;
  - There is one CorridorHall for each Corridor;
  - Implementation of each CorridorHall is based on a fifo with 3 positions;
  - Customers can move to a corridor if it is empty;
- A Corridor:
  - Where Customers coming from a CorridorHall can move to pick-up items from the shelves;
  - Implementation:
  -  • based on a treadmill where each Customer keeps its order;
  -  • if there are more than one Customer, the movement of each one is carried out alternatively;
  -  • The treadmill comprises 10 steps and each Customer moves one step at a time (cto);
  - At any moment no more than 2 Customers can be on the same corridor;
  - There are 3 Corridors;
- PaymentHall:
  - Where Customers coming from a Corridor wait authorization (given by the Cashier) to pay;
  - Implementation based on a fifo with 2 positions;
  - When the PaymentHall is full and there are customers waiting in the Corridors, priority is given to customers with a lower ID.
- PaymentPoint:
  - One customer in the PaymentHall is called at a time to pay, by the Cashier

## Implementation
### Parameters
Optional Parameter:
- serverPort: Port of the socket server
#### Example
```
java -jar OIS.java 6500
```
