
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
    
    /** Contains Cell objects representing each cell of the grid.*/
    private Cell[][] grid; 
    
    /** Helper array for copying the current grid state.*/
    private Cell[][] tempGrid; 
    
    /**
     * Constructor for the Board class.
     * @param r the number of rows in the grid
     * @param c the number of columns in the grid
     */
    public Board(int r, int c) {
        ROWS = r; 
        COLS = c; 
        grid = new Cell[ROWS][COLS]; 
        for (int row  = 0; row < ROWS; row++) {
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
     * Activates the cell located at the specified coordinate.
     * @param x value of the x coordinate
     * @param y value of the y coordinate
     */
    public void activateCell(int x, int y) {
        grid[y][x].setState(true); 
    }
    
    /**
     * Goes through the actions required for each turn. 
     * Count the number of neighbours for each cell.
     * Update the grid with the cells' current state.
     */
    public void nextTurn() {
        tempGrid = cloneGrid();
        // Evaluate the new state of each cell 
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.println("GRID [" + r + "][" + c + "]");
                //System.out.println("Grid " + r + "," + c + "is " + getNextState(grid[r][c])); 
                grid[r][c].setState(getNextState(tempGrid[r][c]));
                
            }
        }
        System.out.println("==============GRID==============");
        System.out.println(gridToString(grid));
    }
    
    /**
     * Evaluates the new state of the provided cell.
     * @param sq cell to evaluate
     * @return state of the new cell as a boolean 
     */
    public boolean getNextState(Cell sq) {
        int count = countNeighbours(sq); 
        boolean state = sq.getState(); 
        if (state) {
            /* If cell active and has less than 2 or more than 3 neighbours
             * then make cell dormant
            */
            if (count < 2 || count > 3) {
                state = false; 
            }
        } else {
            /* If cell dormant and has 3 neighbours then activate cell
            */
            if (count == 3) {
                state = true; 
            }
        }
      System.out.println("Cell state: " + state);
        return state; 
    }
    
    /**
     * Counts the number of neighbouring cells that are active. Neighbours are 
     * the cell vertically, horizontally, and adjacent to the provided cell. 
     * @param sq the cell to evaluate for neighbours
     * @return the number of neighbours as an int
     */
    private int countNeighbours(Cell sq) {
        int x = sq.getX(); 
        int y = sq.getY(); 
        int count = 0; 
        for (int r = y - 1; r < y + 2; r++) {
            // Check for row edge
            if (!(r < 0 || r > ROWS - 1)) {
                for (int c = x - 1; c < x + 2; c++) {
                    // Check for column edge
                    if (!(c < 0 || c > COLS - 1)) {
                        // Skip if cell is the provided cell
                        if (!((r == y) && (c == x))){
                            System.out.print("Checking [" + r + "][" + c + "]...");
                            if (tempGrid[r][c].getState()) {
                                System.out.println("true");
                                count++;
                            } else {
                                System.out.println("false");
                            }

                        }
                }
              }
          }
      }
      return count; 
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
     * Helper method that makes a copy of the current grid state.
     * @return current grid state in a 2D array
     */
    private Cell[][] cloneGrid() {
        Cell[][] temp = new Cell[ROWS][COLS]; 
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell sq = new Cell(c, r); 
                temp[r][c] = sq;
                temp[r][c].setState(grid[r][c].getState()); 
            }
        }
        return temp; 
    }
    
    /**
     * Shows the current grid with active/inactive cells.
     * @return The current grid state as a String
     */
    public String toString() {
        String str = gridToString(grid); 
        return str; 
    }
    
    
}
