# Low Level Design - Snake And Ladder

### Introduction
This repository contains the low level design (LLD) implementation of a snake and ladder game

![Image](src/main/resources/static/images/snake_and_ladder.png)

### Requirements & Setup

Requirements to run this program are :

- Java 17
- Maven 

### Running the application locally
- Clone this repository
- Run the application

### Requirement 
[Link](https://workat.tech/machine-coding/editorial/how-to-design-snake-and-ladder-machine-coding-ehskk9c40x2w)
1. Here we create a board of size 10 and dice of side 6. 
2. Each player puts their counter on the board at starting position at 1 and takes turns to roll the dice. 
3. Move your counter forward the number of spaces shown on the dice. 
4. If your counter lands at the bottom of a ladder, you can move up to the top of the ladder. If your counter lands on the head of a snake, you must slide down to the bottom of the snake. 
5. Each player will get a fair chance to roll the dice. 
6. On the dice result of 6, the user gets one more chance to roll the dice again. However, the same user can throw the dice a maximum of 3 times.
Note: if the result of the dice is 6,6,6 the user can not throw the dice again as the maximum attempts are over and the next user will get to throw the dice. 
7. When the user rolls dice and it leads to an invalid move, the player should remain in the same position.
Ex: when the user is in position 99 and rolling of dice yields any number more than one the user remains in the same position. 
8. Print the ranks of users who finished first, second, and so on…

### Class Diagram
![Class Diagram](src/main/resources/static/images/class_diagram.png)

### Output

    Enter number of snakes
    2
    Enter snake start and end
    77 6
    Enter snake start and end
    56 15
    Enter number of ladder
    2
    Enter ladder start and end
    11 93
    Enter ladder start and end
    44 64
    Enter number of player
    2
    Enter name of Player 1
    Ruchita
    Enter name of Player 2
    Pragati
    Player : Ruchita rolled a position : 1 to new position : 1
    Player : Pragati rolled a position : 1 to new position : 1
    Player : Ruchita rolled a position : 2 to new position : 3
    Player : Pragati rolled a position : 1 to new position : 2
    Player : Ruchita rolled a position : 2 to new position : 5
    Player : Pragati rolled a position : 4 to new position : 6
    Player : Ruchita rolled a position : 6 to new position : 93
    Player : Pragati rolled a position : 2 to new position : 8
    Player : Ruchita rolled a position : 4 to new position : 97
    Player : Pragati rolled a position : 3 to new position : 93
    Player : Ruchita rolled a position : 5 to new position : 97
    Player : Pragati rolled a position : 3 to new position : 96
    Player : Ruchita rolled a position : 1 to new position : 98
    Player : Pragati rolled a position : 5 to new position : 96
    Player : Ruchita rolled a position : 1 to new position : 99
    Player : Pragati rolled a position : 4 to new position : 100
    Player: Pragati has won the game






