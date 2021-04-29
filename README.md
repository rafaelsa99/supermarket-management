# Supermarket Management
Practical assignment of the Software Architectures course of the Masters in Informatics Engineering of the University of Aveiro.

## Introduction
The project is focused on an architecture where concurrency (processes and threads) is key aspect.

## Description
The project is about a simulation of the management process in a small supermarket (OurMarket) during the COVID-19 pandemic.

There are two main entities: the OurMarket Control Center (OCC) and the OurMarket in-site supermarket (OIS). The OCC supervises the activities inside the OIS and the OIS is where the product purchase process takes place.

The OCC and the OIS are implemented as two different processes and the communication between them is through sockets.

## General Requirements and Constraints
- Two processes:
  - the OurMarket Control Center (OCC): where configuration, control and supervision takes place;
  - the OurMarket In-site Supermarket (OIS): supermarket infrastructure;
- Technologies:
  - programming language: <b>Java</b>
  - The communication between OCC and OIS is based on sockets and at the side of the socket servers it must support multiple-clients simultaneously;
  - Cannot use Java classes/libraries that implement any kind of queues.
- A simulation exists while the application is deployed completely and running its two main entities;
- A shopping simulation exists while there is at least one customer willing to shop or still shopping;
- Customer: entity that goes in OurMarket to go shopping;
- Manager: entity that partially supervises the movement of customers;
- Cashier: entity that accepts the payment;
