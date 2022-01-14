import java.util.Scanner;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Board contains the logic for John Conway's Game of Life.
 * 
 * @author Sharon
 * @version 1.0
 */
public class Board {

    /** Constant value of the number of rows for the grid. */
    final int LAST_ROW;

    /** Constant value of the number of columns for the grid. */
    final int LAST_COL;

    /** Contains Cell objects representing each cell of the grid. */
    private Cell[][] grid;


    private HashSet<Cell> changedCells = new HashSet<Cell>();
    private HashSet<Cell> neighbouringCells = new HashSet<Cell>();

    private Scanner scan; 
    /**
     * Constructor for the Board class.
     * 
     * @param r the number of rows in the grid
     * @param c the number of columns in the grid
     */
    public Board(int r, int c) {
        LAST_ROW = r;
        LAST_COL = c;
        scan = new Scanner(System.in);
        grid = new Cell[LAST_ROW][LAST_COL];
        for (int row = 0; row < LAST_ROW; row++) {
            for (int col = 0; col < LAST_COL; col++) {
                /*
                 * Grids use [row, column]. Cells have(x,y) coordinates. 
                 * Grid[row][column] => Cell(column, row)
                 */
                grid[row][col] = new Cell(col, row);
            }
        }
    }

    /**
     * Initiates the turns and displays prompts for input.
     */
    public void run() {
        int input;
        boolean done = false; 
        do {
            System.out.println("\n\n" + gridToString(grid));
            displayMenu();
            input = scan.nextInt();
            switch (input) {
            case 1: 
                System.out.println("Sorry this option is under construction...");
                break; 
            case 2: 
                nextTurn(); 
                break; 
            case 3: 
                done = true; 
                System.out.println("Goodbye"); 
                break; 
            default: 
                System.out.println("Invalid input.");
                break;
            }
        } while(!done);
        scan.close();
    }
    
    /**
     * Prints out a menu option with available actions.
     */
    private void displayMenu() {
        System.out.println("\n1) Change Cell States");
        System.out.println("2) Next Turn");
        System.out.println("3) Exit");
    }

    /**
     * Activates the cell located at the specified coordinate.
     * @param x value of the x coordinate
     * @param y value of the y coordinate
     */
    public void activateCell(int x, int y) {
        grid[y][x].setState(true);
    }

    /**
     * Goes through the actions required for each turn. Count the number of
     * neighbours for each cell. Update the grid with the cells' current state.
     */
    public void nextTurn() {
        for(Cell cell : changedCells) {
            cell.updateNeighbours(); 
        }
        changedCells.clear(); 
        for(Cell cell : neighbouringCells) {
            if(cell.isStateChanged()) {
                changedCells.add(cell); 
            }
        }
        neighbouringCells.clear(); 
    }

    /**
     * Creates a representation of the grid as a String.
     * @param grid reference to the grid to print
     * @return String representation of the grid's current state
     */
    public String gridToString(Cell[][] grid) {
        String str = " ";
        // Adds column label
        for (int col = 0; col < LAST_COL; col++) {
            str += " " + col + " ";
        }
        for (int row = 0; row < grid.length; row++) {
            // Adds row label
            str += "\n" + row;
            for (int col = 0; col < grid[row].length; col++) {
                str += "[" + grid[row][col] + "]";
            }
        }
        return str;
    }


    /**
     * Shows the current grid with active/inactive cells.
     * @return The current grid state as a String
     */
    public String toString() {
        String str = gridToString(grid);
        return str;
    }

    /**
     * Represents a cell within the grid.
     */
    private class Cell {

        /** The current state of the cell. False is inactive. True is active. */
        private boolean state;

        /** The x coordinate of the cell. */
        private int x;

        /** The y coordinate of the cell. */
        private int y;

        /** The current count of live bordering cells. */
        private int neighbourCount = 0;

        /**
         * Constructor that sets the cell's coordinates.
         * @param x value of the X coordinate
         * @param y value of the Y coordinate
         */
        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Setter method for the state.
         * @param newState value of the new state
         */
        public void setState(boolean newState) {
           state = newState;
           changedCells.add(this); 
        }

        /**
         * Counts the number of neighbouring cells that are active. Neighbours 
         * are the cell vertically, horizontally, and adjacent to the provided 
         * cell.
         */
        private void updateNeighbours() {
            int colStart = (x - 1 < 0) ? x : x - 1;
            int colEnd = (x + 1 < LAST_COL) ? x + 1 : x;
            int rowStart = (y - 1 < 0) ? y : y - 1;
            int rowEnd = (y + 1 < LAST_ROW) ? y + 1 : y;
            for (int r = rowStart; r <= rowEnd; r++) {
                for (int c = colStart; c <= colEnd; c++) {
                    if (!(r == y && c == x)) {
                        if (state) {
                            grid[r][c].addNeighbour();
                        } else {
                            grid[r][c].subtractNeighbour();
                        }
                        neighbouringCells.add(grid[r][c]);
                    }
                }
            }
        }

        /**
         * Evaluates if the cell state needs to be changed.
         * @return true if state has changed or false if unchanged
         */
        public boolean isStateChanged() { 
            if (state) {
                if (neighbourCount < 2 || neighbourCount > 3) {
                    setState(false); 
                    return true;
                }
            } else {
                if (neighbourCount == 3) {
                    setState(true); 
                    return true;
                }
            }
            return false; 
        }

        /**
         * Setter method for incrementing the neighbour count.
         */
        public void addNeighbour() {
            neighbourCount++;
        }

        /**
         * Setter method for decrementing the neighbour count.
         */
        public void subtractNeighbour() {
            if (neighbourCount > 0) {
                neighbourCount--;
            }
        }


        /**
         * Getter method for the state.
         * @return state true/false if alive/dead
         */
        public boolean getState() {
            return state;
        }

        /**
         * Getter method for the X coordinate.
         * @return x coordinate as an int
         */
        public int getX() {
            return x;
        }

        /**
         * Getter method for the Y coordinate.
         * @return y coordinate as an int
         */
        public int getY() {
            return y;
        }

        /**
         * Creates a string representation of the cell's state. 'X' is active. ' ' is
         * inactive.
         * @return string representation of the state
         */
        public String toString() {
            if (state) {
                return "X";
            } else {
                return " ";
            }
        }
    }
}
