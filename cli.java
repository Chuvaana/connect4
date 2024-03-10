/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 *
 * @author jgjpro
 */
public class Cli{
    //input scanner to get user input
    Scanner input = new Scanner(System.in); 
    //variables used in grid
    private int xsize = 7;
    private int ysize = 6;
    private int currentPlayer = 1;
    //game variables to communicate with top program
    private boolean hasWon = false;
    private boolean hasDraw = false;
    private boolean quit = false;
    private boolean newGame = false;
    //making of grid and logic
    Grid my_grid = new Grid();
    logic my_logic = new logic(my_grid); //create game logic

    public Cli() {
    }

    public void runtime() {
        System.out.println("Player " + currentPlayer + "'s turn");
        display();
        
        int x = getUserInput();
        if (x != -1) {
            int y = my_grid.find_y(x);
            if (y != -1) {
                if (my_logic.set_and_check(x, y, currentPlayer)) {
                    hasWon = true;
                } else if (my_logic.draw_game()) {
                    hasDraw = true;
                } else {
                    currentPlayer = my_grid.changeplayer(currentPlayer, 2);
                }
            } else {
                System.out.println("Column filled.");
            }
        }
    }
    
    private int getUserInput() {
        int x = 0;
        try {
            System.out.print("Enter column number: ");
            x = input.nextInt();
            if (x <= 0 || x > my_grid.get_xsize()) {
                throw new InputMismatchException();
            }
            return x;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and " + my_grid.get_xsize());
            input.nextLine();
            return -1;
        }
    }

    public void showWin() {//prints to terminal when a player wins

        display();//print out the grid
        System.out.println("\nwinner is player " + currentPlayer
                + "\nPlay again?\n"
                + "press 0 for new game\n"
                + "press a letter to quit");
        int choice = -1;
        try {//checks for user input == int == 0
            choice = input.nextInt();
        } catch (Exception exc) {
            System.out.println("Quitting");
            quit = true;
        }
        if (choice == 0) {
            newGame = true;
        }
    }

    public void showDraw() {
        display();//print out the grid
        System.out.println(
                "\ndraw game"
                + "\nPlay again?\n"
                + "press 0 for new game\n"
                + "press a letter to quit");
        int choice = -1;
        try {//checks for user input == int == 0
            choice = input.nextInt();
        } catch (Exception exc) {
            System.out.println("Quitting");
            quit = true;
        }
        if (choice == 0) {
            newGame = true;
        }
    }

    public void display() {//prints out the matrix board
        for (int i = -1; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                if (i < 0) {
                    System.out.print(" " + (j + 1) + " ");
                } else {
                    printCell(j, i);
                }
            }
            System.out.println();
        }
    }

    private void printCell(int j, int i) {
        System.out.print("[");
        if (my_grid.matrix_equals(j, i, 0)) {
            System.out.print(" ]");
        } else {
            int[][] temp_matrix = my_grid.get_matrix();
            System.out.print(temp_matrix[j][i] + "]");
        }
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasDraw() {
        return hasDraw;
    }

    public boolean getQuit() {
        return quit;
    }

    public boolean getNewGame() {
        return newGame;
    }
}