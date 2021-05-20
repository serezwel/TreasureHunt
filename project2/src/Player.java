import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

import java.text.DecimalFormat;

/**
 * Player class specifying the player and its characteristics in the game
 */

public class Player implements Pointable{

    /**
     * image source file
     */
    public static final String FILENAME = "res/images/player.png";
    /**
     * step size of the player each tick
     */
    public static final double STEP_SIZE = 10;
    // energy level threshold
    private static final int LOWENERGY = 3;
    // zero vector
    private static final Vector2 ZERO_VECTOR = new Vector2(0,0);
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();
    private final double SHOOTING_DISTANCE = 150;
    private static DecimalFormat df = new DecimalFormat("0.00");

    // image and type
    private final Image image;
    // render position
    private Point pos;
    // direction
    private double directionX;
    private double directionY;

    // healthbar parameters
    private int energy;

    /**
     * Player constructor specifying its image, initial coordinates and energy level in the game.
     * @param x initial x coordinate
     * @param y initial y coordinate
     * @param energy initial energy level
     */
    public Player(double x, double y, int energy) {
        this.image = new Image(FILENAME);
        this.pos = new Point(x,y);
        this.energy = energy;
    }

    /**
     * gets the current coordinates of the player object in the game
     * @return pos the coordinates of the player.
     */
    public Point getPos(){
        return this.pos;
    }

    /**
     * gets the current energy level of the player in the game
     * @return energy the energy level of the player
     */
    public int getEnergy(){
        return this.energy;
    }

    /**
     * sets the direction at which the player will go in the game
     * @param dest the destination coordinate for the player to go to
     */
    public void pointTo(Point dest){
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    /**
     * normalizeD to normalize the direction of the player into 1 unit.
     */
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    /**
     * Performs state update for the player and the bullet.
     * @param tomb the game itself.
     */
    public void update(ShadowTreasure tomb) {
        //Check if player has met treasure or player has no energy left and sandwich to kill all zombies
        if (tomb.getTreasure().meets(this)) {
            tomb.setEndOfGame(true);
        }
        if (tomb.getNearestSandwich() == null && tomb.getNearestZombie() != null && this.energy < LOWENERGY
        && !tomb.getBullet().isVisible()) {
            tomb.setEndOfGame(true);
        }
        // set direction
        if (this.energy < LOWENERGY && tomb.getNearestSandwich() != null) {
            // direction to sandwich
            pointTo(tomb.getNearestSandwich().getPos());
            if (tomb.getNearestSandwich().meets(this)) {
                eatSandwich();
                tomb.getNearestSandwich().setVisible(false);
            }
        } else if (tomb.getNearestZombie() != null) {
            //aim for nearest zombie
            pointTo(tomb.getNearestZombie().getPos());
            if (pos.distanceTo(tomb.getNearestZombie().getPos()) < SHOOTING_DISTANCE) {
                //shoot the zombie
                if (!tomb.getNearestZombie().getIsShot()) {
                    tomb.getNearestZombie().setShot(true);
                    shootZombie();
                }
                tomb.getBullet().setVisible(true);
                tomb.getBullet().pointTo(tomb.getNearestZombie().getPos());
            }
        } else {
            //go to treasure
            pointTo(tomb.getTreasure().getPos());
        }
        // move one step for bullet and player
        this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);
        //bullet follows player while invisible, goes to zombie while visible
        if (!tomb.getBullet().isVisible()) { tomb.getBullet().setPos(this.pos); }
        else {
            tomb.writetoFile(tomb.getBullet().getPos());
            tomb.getBullet().setPos(new Point(tomb.getBullet().getPos().x+
                    tomb.getBullet().BULLET_STEP_SIZE*tomb.getBullet().getDirectionX(),
                    tomb.getBullet().getPos().y+
                            tomb.getBullet().BULLET_STEP_SIZE*tomb.getBullet().getDirectionY()));
        }
        //check if bullet has already met the zombie
        if (tomb.getNearestZombie() != null && tomb.getNearestZombie().meets(tomb.getBullet())) {
            tomb.writetoFile(tomb.getBullet().getPos());
            tomb.getNearestZombie().setVisible(false);
            tomb.getBullet().setVisible(false);
        }
    }

    /**
     * renders the player object into the game and display its energy level
     */
    public void render() {
        image.drawFromTopLeft(pos.x, pos.y);
        // also show energy level
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }

    /**
     * method to add energy level when the player meets the sandwich
     */
    public void eatSandwich(){
        energy += 5;
    }

    /**
     * method to subtract energy level when the player shoots the zombie.
     */
    public void shootZombie(){
        energy -= 3;
    }
}
