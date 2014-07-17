#gameframework

##Introduction
This is a game framework for creating grid-based games, such as Shokoban, Sudoku, Bejeweled, etc.
It is written in Java.

##Overview
The main design pattern that is used in this game framework is the **Model View Controller (MVC)** pattern.
- The model is represented by the *Grid* abstract class that contains a two-dimensional array (resembling a table or a grid) that stores instances of a class that implements the *Data* interface.
- The controller is represented by the *Mechanics* abstract class. The class connects all the elements of the framework together:
	- It has a method to attach the *UserInterface* to itself.
	- It has a method to attach the *Grid* to itself. (Note: The UserInterface should always be attached before the Grid. The game will only run after the *Mechanics* has successfully validated the *Grid*.
	- The game logic resides in the *Mechanics* class. It contains the method signatures that control the player view and check if the player has won the game. -The game developer that uses the game framework can implement the two methods accordingly.
- The view is represented by the *UserInterface* interface. The game programmers that implement this game framework should implement the *UserInterface* and defines the user interface for their users themselves. The crucial method signatures that resides inside the *UserInterface* interface are:
  - run(Mechanics)
  - updateUI(String[][])
- More on the implementation of the UserInterface interface will be discussed later.
- The UML diagram for the framework are as follows:
![alt tag](https://github.com/afifsohaili/gameframework/uml.png)

##Design rationale
- The main design pattern that is used for the game framework is the observer pattern or **Model-View-Controller (MVC)** pattern. MVC pattern will allow game developers to have a clear separation of the codes for the game's logic, data, and user interface. It will help organize the codes better.
- **Strategy pattern** are used in the game framework as well. *Data* and *UserInterface* are both interfaces that require concrete implementations by the game developers, and the game developers are given the freedom to implement the method signatures inside these two interfaces according to their own needs and likings. The *Mechanics* class will use these signatures to run the game, update the user interface and display errors.
- Template method pattern are also used in the framework.
- *Mechanics* is an abstract class that contains several method signatures that game developers need to implement.
	- generatePlayerView(String[][])
	- input(String... input)
- Apart from the abstract methods, *Mechanics* will handle the initialization and performs minimal data validation on the model (*Grid*) before the game runs. The class is also the one responsible in getting all the defined options in the options file, and it is the one that moderate the communications between the *UserInterface* and the *Grid*.
- *Grid* is an abstract class that stores the game data. The implementation of the updateGrid(String... update) method is required to allow *Mechanics* class to interact with the *Grid* and allow the *UserInterface* to update itself accordingly.

##Using the framework

**I. Implement the *Data* interface**
This is a framework meant to create grid-based games, so each of your tiles will contain some kind of data. e.g. each tiles in a Sudoku game will contain a number, and each tiles in a Bejeweled game will contain a color. 
Consider carefully what your grid will store, and have a class that implements the *Data* interface.
```Java
// e.g. GameData for Sudoku
public class SudokuData implements Data<Integer> { 
	...
}
```

**II. Implement the *Grid* class**
Now that you have your *Data* ready, you should think about how your data will be stored in the Grid. The *Grid* abstract class contains a two dimensional array of type *T* (*T* is a class that implements the Data interface). Define a class that extends the *Grid* abstract class.
```Java
public class GameGrid extends Grid<GameData> {
    @Override
    protected void init() {
         ...
    }

    @Override
    public GameData getElementAtPosition(int row, int column) {
        ... 
    } 

    @Override
    public boolean updateGrid(String... update) {
        ... 
    } 
}
```

For now, let's skip the implementation of the method *updateGrid(String... update)* for a while and focus on the first two methods:
- *init()*: This is the method where you should initialize the two-dimensional array mentioned it earlier. The declaration of the array is T[][] data, and the framework cannot initialize the array for you since Java does not allow instantiating instances of generic classes. Then, you should perform suitable algorithms to generate your grid. e.g. a Sudoku game needs to make sure in every row, column, and 3x3 boxes of the Grid contains numbers from 1 to 9 exactly once, and a Bejeweled game needs to make sure that there is no 3 blocks of the same color stack together side-by-side either horizontally or vertically. Anyhow, you should think of an algorithm to generate your grid.
- *getElementAtPosition(int,int)*: This method should return the data that resides at row *row* and column *column*. This should be fairly simple to be implemented.

III. Extend the Mechanics class
Now that the game data is available, you can start implementing the game logic. The game logic resides in the Mechanics class, and there are a couple of abstract methods in it that you need to implement. Thus, start by defining a class that extends the abstract class Mechanics. You will need to implement two methods:
- *checkWinCondition(Data[][])*: This method should check if the game's data satisfy the winning condition of the game. If the player has won, boolean value true should be returned. Otherwise, it should return false. You should implement your desired winning condition for the game.
- *input(String...)*: This method should accept and process user input passed by the *UserInterface*. The method should pass user input to *Grid.updateGrid(String...)* so that necessary changes to the model can be made according to the given user input.
Now that the game can accept and process user input, you can implement the abstract method *updateGrid(String...)*. The method should update the *Grid* according to the user input.

IV. Create the user interface.
Now you have your game's data and logic, you should program a user interface for your players. The *UserInterface* interface provides method signatures that the framework's *Mechanics* will use to run the game, update the user interface, accepts input, and display errors if one is encountered. You are free to use *System.out.print()* or the Swing framework to generate the user interface.