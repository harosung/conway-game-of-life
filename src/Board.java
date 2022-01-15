import java.util.Scanner;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Board contains the logic for John Conway's Game of Life.
 * @author Sharon
 * @version 1.0
 */
public class Board {

    /** Constant value of the number of rows for the grid. */
    final int ROWS;

    /** Constant value of the number of columns for the grid. */
    final int COLS;

    /** Contains Cell objects representing each cell of the grid. */
    private Cell[][] grid;

    /** Contains cells that had their states changed. */
    private HashSet<Cell> changedCells = new HashSet<Cell>();
    
    /** Contains neighbours of a cell that had its state changed. */
    private HashSet<Cell> cellsToEval = new HashSet<Cell>();

    /** Scanner for receiving user input. */
    private Scanner scan; 
    /**
     * Constructor for the Board class.
     * @param r the number of rows in the grid
     * @param c the number of columns in the grid
     */
    public Board(int r, int c) {
        ROWS = r;
        COLS = c;
        scan = new Scanner(System.in);
        grid = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
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
                inputCoordinates();
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
        System.out.println("\n1) Select a cell"); 
        System.out.println("2) Next Turn");
        System.out.println("3) Exit");
    }

    private void inputCoordinates() {
        int row; 
        int col; 
        System.out.println("Specify cell coordinate:\nx = ?");
        col = validateCoordinate("x", scan.nextInt()); 
        System.out.println("y = ?");
        row = validateCoordinate("y", scan.nextInt()); 
        String state = grid[row][col].changeState()? "activated" : "deactivated"; 
        System.out.println("Cell[" + row + "][" + col + "] " + state);
    }
    
    private int validateCoordinate(String axis, int pos) {
        int boundary = axis.equals("x")? COLS : ROWS; 
        boolean valid = false; 
        while(!valid)
            if(pos >= 0 && pos < boundary) {
                return pos; 
            } else {
                System.out.println("Invalid input. Outside range of " + axis 
                        + " axis."); 
                System.out.println(axis + " = ?"); 
                pos = scan.nextInt(); 
            }
        return 0; 
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
        for(Cell cell : cellsToEval) {
            if(cell.isStateChanged()) {
                changedCells.add(cell); 
            }
        }
        cellsToEval.clear(); 
    }

    /**
     * Creates a representation of the grid as a String.
     * @param grid reference to the grid to print
     * @return String representation of the grid's current state
     */
    public String gridToString(Cell[][] grid) {
        String str = " ";
        // Adds column label
        for (int col = 0; col < COLS; col++) {
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

        public boolean changeState() {
            setState(!state); 
            return state; 
        }
        
        /**
         * Setter method for the state.
         * @param newState value of the new state
         */
        public void setState(boolean newState) {
           state = newState;
           changedCells.add(this); 
           cellsToEval.add(this); 
        }

        /**
         * Counts the number of neighbouring cells that are active. Neighbours 
         * are the cell vertically, horizontally, and adjacent to the provided 
         * cell.
         */
        private void updateNeighbours() {
            int colStart = (x - 1 < 0) ? x : x - 1;
            int colEnd = (x + 1 < COLS) ? x + 1 : x;
            int rowStart = (y - 1 < 0) ? y : y - 1;
            int rowEnd = (y + 1 < ROWS) ? y + 1 : y;
            for (int r = rowStart; r <= rowEnd; r++) {
                for (int c = colStart; c <= colEnd; c++) {
                    if (!(r == y && c == x)) {
                        if (state) {
                            grid[r][c].addNeighbour();
                        } else {
                            grid[r][c].subtractNeighbour();
                        }
                        cellsToEval.add(grid[r][c]);
                    }
                }
            }
        }

        /**
         * Evaluates if the cell state needs to be changed.
         * @return true if state has changed or false if unchanged
         */
        public boolean isStateChanged() { 
            System.out.println("Grid[" + getY() + "][" + getX() + "]" + 
        "\nNeighbours: " + neighbourCount + 
        "\nCurrently - " + state);
            if (state) {
                if (neighbourCount < 2 || neighbourCount > 3) {
                    setState(false); 
                    System.out.println("CHANGED TO " + state); 
                    return true;
                }
            } else {
                if (neighbourCount == 3) {
                    setState(true); 
                    System.out.println("CHANGED TO " + state); 
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
            return state? "X" : " "; 
        }
    }
}
