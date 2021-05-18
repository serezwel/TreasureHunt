import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public class Player implements Pointable{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    // energy level threshold
    private static final int LOWENERGY = 3;
    // zero vector
    private static final Vector2 ZERO_VECTOR = new Vector2(0,0);
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

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

    public void update(ShadowTreasureComplete tomb){
        // Check if the player meets the Zombie and if so reduce energy by 3 and
        // terminate. Otherwise if the player meets the Sandwich increase the energy
        // an set the Sandwich to invisible
        if (tomb.getNearestZombie().meets(this)) {
            reachZombie();
            tomb.setEndOfGame(true);
        } else if (tomb.getNearestSandwich().meets(this)) {
            eatSandwich();
            tomb.getNearestSandwich().setVisible(false);
        }

        // set direction
        if (this.energy >= LOWENERGY){
            // direction to zombie
            pointTo(tomb.getTreasure().getPos());
        } else{
            // direction to sandwich
            pointTo(tomb.getNearestSandwich().getPos());
        }
        // move one step
        this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);
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
    public void reachZombie(){
        energy -= 3;
    }
}
