import bagel.util.Point;

public class Record {
    private Point pos;
    private int energy;

    public Record(Point p, int e){
        this.pos = p;
        this.energy = e;
    }

    public double getPosX() {
        return pos.x;
    }

    public double getPosY() {
        return pos.y;
    }

    public int getEnergy(){
        return energy;
    }
}
