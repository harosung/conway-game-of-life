

/**
 * Class to run an instance of the Board class which is programmed for John 
 * Conway's Game of Life. 
 * @author Sharon
 * @version 1.1
 */
public class Main {

    /**
     * Drives the program.
     * @param args unused
     */
    public static void main(String[] args) {
        Board game = new Board(10,10); 
        //Preset for Glider pattern 
        int[][] glider = {{9, 7}, {8, 7}, {7, 7}, {7, 8}, {8, 9}};
        for (int i = 0; i < glider.length; i++) {
            game.activateCell(glider[i][0], glider[i][1]); 
        }
        game.run(); 

    }

}
