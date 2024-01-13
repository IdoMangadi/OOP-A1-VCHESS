import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represent a (x,y) type position.
 * Holds a comparator of stepped on pieces.
 */

public class Position {
    //Fields:
    private int x;
    private int y;
    private ArrayList<ConcretePiece> steppedOn = new ArrayList<>();

    //Constructors:
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    //Methods:
    public String getCoor(){
        return this.x + "," + this.y ;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    @Override
    public String toString(){
        return "("+this.x+", "+this.y+")";
    }

    /**
     * Note: this method adding one time every piece hou stepped on it.
     * Even if a piece stepped several tine on the position, it will be adding one time.
     * @param p - the stepping piece.
     */
    public void addStepped(ConcretePiece p){
        if (!this.steppedOn.contains(p)) {
            this.steppedOn.add(p);
        }
    }

    public int getNumOfStepped(){
        return this.steppedOn.size();
    }

    /**
     * Printing only positions that at least two pieces stepped on.
     */
    public void printStepped(){
        int numOfStepped = this.steppedOn.size();
        if (numOfStepped > 1) {
            System.out.println(this.toString() + this.steppedOn.size() + " pieces");
        }
    }

    //Comparator:
    public static class steppedOnComp implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2)
        {
            int firstComp = Integer.compare(p1.getNumOfStepped(), p2.getNumOfStepped());
            if (firstComp == 0){
                int secondComp = Integer.compare(p1.getX(), p2.getX());
                if (secondComp == 0){
                    int thirdComp = Integer.compare(p1.getY(), p2.getY());
                    return thirdComp;
                }
                return secondComp;

            }
            return firstComp *(-1);
        }
    }

}
