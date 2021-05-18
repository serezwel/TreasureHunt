import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;


/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    // for rounding double number; use this to print the location of the player
    private static DecimalFormat df = new DecimalFormat("0.00");
    
    public ShadowTreasure() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        // Add code to initialize other attributes as needed
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        // Code here to read from the file and set up the environment
    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        // Logic to update the game, as per specification must go here
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
