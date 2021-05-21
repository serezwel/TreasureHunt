import bagel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import bagel.util.Point;

/**
 * An example Bagel game.
 * The code in this class used the sample solution of project 1 provided in the LMS.
 */
public class ShadowTreasure extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");
    /**
    * Public Attribute CLOSENESS is used to determine the distance at which the treasure and sandwich can "meet" player.
     */
    public static final double CLOSENESS = 50;
    /**
     * Public Attribute BULLET_CLOSENESS determines the distance at which the bullet kills the zombie.
     */
    public static final double BULLET_CLOSENESS = 25;

    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");

    // tick cycle and var
    private final int TICK_CYCLE = 10;
    private int tick;

    // list of characters
    private Player player;
    private ArrayList<Sandwich> sandwich = new ArrayList<Sandwich>();
    private ArrayList<Zombie> zombie = new ArrayList<Zombie>();
    private Treasure treasure;
    private Bullet bullet;
    //comparator for sorting distance
    private DistanceComparator comparator = new DistanceComparator();
    private FileWriter csvWriter = new FileWriter("res/IO/output.csv");
    // end of game indicator
    private boolean endOfGame;

    /**
     * Public Constructor to determine the initial state of the game.
     * @throws IOException
     */
    public ShadowTreasure() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
    }

    /**
     * Method getTreasure returns the treasure object in the Shadow Treasure game.
     * @return treasure the treasure in the game
     */
    public Treasure getTreasure() {
        return treasure;
    }

    /**
     * Method getPlayer returns the player object in the Shadow Treasure game.
     * @return player the player in the game
     */
    public Player getPlayer() { return player; }

    /**
     * Method getBullet returns the bullet object in the game.
     * @return bullet the bullet in the game
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Method getNearestZombie returns the zombie closest and visible to the player by returning the first visible
     * zombie in the zombie ArrayList.
     * @return zombie.get(i) the nearest visible zombie to the player.
     * @return null there are no visible zombies left in the game.
     */
    public Zombie getNearestZombie() {
        for (int i=0;i<zombie.size();i++) {
            if (zombie.get(i).isVisible()) {
                return zombie.get(i);
            }
        }
        return null;
    }

    /**
     * method writetoFile writes the coordinate of the bullet when it appears in the game to output.csv file
     * @param point the coordinates of the bullet to be written to the csv.
     */
    public void writetoFile(Point point) {
        try {
            csvWriter.append(df.format(bullet.getPos().x));
            csvWriter.append(", ");
            csvWriter.append(df.format(bullet.getPos().y));
            csvWriter.append("\n");
        } catch (IOException e) {
            System.out.println("Failed to write bullet position");
        }
    }
    /**
     * Method getNearestSandwcih returns the sandwich closest and visible to the player by returning the first visible
     * sandwich in the sandwich ArrayList.
     * @return sandwich.get(i) the nearest visible sandwich to the player.
     * @return null if there is no visible sandwich left in the game.
     */
    public Sandwich getNearestSandwich() {
        for (int i=0;i<sandwich.size();i++) {
            if (sandwich.get(i).isVisible()) {
                return sandwich.get(i);
            }
        }
        return null;
    }

    /**
     * public method setEndOfGame sets to true if the game already ends.
     * i.e. When the player meets the treasure or player can't kill all zombies in the game
     * @param endOfGame true if the game already ended.
     */
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                type = type.replaceAll("[^a-zA-Z0-9]", ""); // remove special characters
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                switch (type) {
                    case "Player":
                        this.player = new Player(x, y, Integer.parseInt(parts[3]));
                        this.bullet = new Bullet(x, y);
                        break;
                    case "Zombie":
                        zombie.add(new Zombie(x, y));
                        break;
                    case "Sandwich":
                        sandwich.add(new Sandwich(x, y));
                        break;
                    case "Treasure":
                        this.treasure = new Treasure(x, y);
                        break;
                    default:
                        throw new BagelError("Unknown type: " + type);
                }
            }
            comparator.setPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update.
     * The game is updated every tick, which is 10 frames in the game.
     */
    @Override
    public void update(Input input) {
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)){
            //check if player successfully meets the treasure
            if (treasure.meets(player)) {
                try {
                    csvWriter.flush();
                    csvWriter.close();
                } catch (IOException e) {
                    System.out.println("Failed to print output CSV");
                }
                System.out.println(player.getEnergy() + ", success!");
            } else {
                System.out.println(player.getEnergy());
                try {
                    csvWriter.flush();
                    csvWriter.close();
                } catch (IOException e) {
                    System.out.println("Failed to print output CSV");
                }
            }
            Window.close();
        } else {
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player status
                Collections.sort(zombie, comparator);
                Collections.sort(sandwich, comparator);
                player.update(this);
                tick = 1;
            }
            tick++;
            for (int i = 0;i<sandwich.size();i++) {
                if (sandwich.get(i).isVisible()) {
                    sandwich.get(i).draw();
                }
            }
            for (int i = 0;i<zombie.size();i++) {
                if (zombie.get(i).isVisible()) {
                    zombie.get(i).draw();
                }
            }
            if (bullet.isVisible()){
                bullet.draw();
            }
            treasure.draw();
            player.render();
        }
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
