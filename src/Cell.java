/**
 * Represents a cell within the grid. 
 * @author Sharon
 * @version 1.0
 */
public class Cell {
    /** The current state of the cell. False is inactive. True is active. */
    private boolean state; 
    
    /** The x coordinate of the cell.*/
    private int x; 
    
    /** The y coordinate of the cell. */
    private int y; 
    
    /** 
     * Constructor that sets the cell's coordinates. 
     * @param x value of the X coordinate
     * @param y value of the Y coordinate
     */
    public Cell(int x, int y) {
        this.x = x; 
        this.y = y;
    }
    
    /** 
     * Setter method for the state.
     * @param state value of the new state
     */
    public void setState(boolean state) {
        this.state = state; 
    }
    
    /**
     * Getter method for the state.
     * @return state as a boolean
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
     * Creates a string representation of the cell's state. 'X' is active. 
     * ' ' is inactive.
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
