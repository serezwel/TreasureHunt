import bagel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import bagel.util.Point;

/**
 * An example Bagel game.
 */
public class ShadowTreasureComplete extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");
    public static final double CLOSENESS = 50;
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

    public ShadowTreasureComplete() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
        System.out.println(player.getPos().x + "," + player.getPos().y + "," + player.getEnergy());
    }
    public Treasure getTreasure() {
        return treasure;
    }

    public Player getPlayer() { return player; }

    public Bullet getBullet() {
        return bullet;
    }

    public Zombie getNearestZombie() {
        for (int i=0;i<zombie.size();i++) {
            if (zombie.get(i).isVisible()) {
                return zombie.get(i);
            }
        }
        return null;
    }
    
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

    public Sandwich getNearestSandwich() {
        for (int i=0;i<sandwich.size();i++) {
            if (sandwich.get(i).isVisible()) {
                return sandwich.get(i);
            }
        }
        return null;
    }

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
     */
    @Override
    public void update(Input input) {
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)){
            //check if player successfully meets the treasure
            if (treasure.meets(player)) {
                System.out.println(player.getEnergy() + ", success!");
                try {
                    csvWriter.flush();
                    csvWriter.close();
                } catch (IOException e) {
                    System.out.println("Failed to print output CSV");
                }
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
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player status
                Collections.sort(zombie, comparator);
                Collections.sort(sandwich, comparator);
                player.update(this);
                tick = 1;
                System.out.println(df.format(player.getPos().x) + "," + df.format(player.getPos().y) + "," + player.getEnergy());
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
            treasure.draw();
            player.render();
            if (bullet.isVisible()){
                bullet.draw();

            }
        }
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasureComplete game = new ShadowTreasureComplete();
        game.run();
    }
}
