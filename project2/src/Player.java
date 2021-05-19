import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

import java.io.IOException;
import java.text.DecimalFormat;

public class Player implements Pointable{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    public static final double BULLET_STEP_SIZE = 25;
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

    public Player(double x, double y, int energy) {
        this.image = new Image(FILENAME);
        this.pos = new Point(x,y);
        //this.directionX = 0;
        //this.directionY = 0;
        this.energy = energy;
    }

    public Point getPos(){
        return this.pos;
    }

    public int getEnergy(){
        return this.energy;
    }

    // point to a destination
    public void pointTo(Point dest){
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    // normalize direction
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    public void update(ShadowTreasureComplete tomb) {
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
            pointTo(tomb.getTreasure().getPos());
        }
        // move one step for bullet and player
        this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);
        //bullet follows player while invisible, goes to zombie while visible
        if (!tomb.getBullet().isVisible()) { tomb.getBullet().setPos(this.pos); }
        else {
            tomb.writetoFile(tomb.getBullet().getPos());
            tomb.getBullet().setPos(new Point(tomb.getBullet().getPos().x+BULLET_STEP_SIZE*tomb.getBullet().getDirectionX(),
                    tomb.getBullet().getPos().y+BULLET_STEP_SIZE*tomb.getBullet().getDirectionY()));
        }
        if (tomb.getNearestZombie() != null && tomb.getNearestZombie().meets(tomb.getBullet())) {
            tomb.writetoFile(tomb.getBullet().getPos());
            tomb.getNearestZombie().setVisible(false);
            tomb.getBullet().setVisible(false);
        }
    }

    // render
    public void render() {
        image.drawFromTopLeft(pos.x, pos.y);
        // also show energy level
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }

    public void eatSandwich(){
        energy += 5;
    }
    public void shootZombie(){
        energy -= 3;
    }
}
