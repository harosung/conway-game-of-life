import java.util.Scanner;

/**
 * Class to run an instance of the Board class which is programmed for John 
 * Conway's Game of Life. 
 * @author Sharon
 * @version 1.0
 */
public class Main {

    /**
     * Drives the program.
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Board game = new Board(10,10); 
        //Preset for Glider pattern 
        int[][] glider = {{9, 7}, {8, 7}, {7, 7}, {7, 8}, {8, 9}};
        for (int i = 0; i < glider.length; i++) {
            game.activateCell(glider[i][0], glider[i][1]); 
        }
        System.out.println(game.toString());
        System.out.println("Press enter to continue. Type 0 to exit.");
        String input = scan.nextLine(); 
        do {
            game.nextTurn();
            System.out.println("Press enter to continue. Type 0 to exit.");
            input = scan.nextLine(); 
        } while (!input.equals(String.valueOf(0))); 
        scan.close(); 
    }

}
