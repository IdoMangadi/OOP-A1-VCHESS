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
    private ArrayList<ConcretePiece> shrinkSteppedOn = new ArrayList<>();

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
     * Adding the piece p to the stepped on history list.
     * @param p - the stepping piece.
     */
    public void addStepped(ConcretePiece p){
            this.steppedOn.add(p);
            if(!this.shrinkSteppedOn.contains(p)){
                this.shrinkSteppedOn.add(p);
            }
    }

    public void removeLast(){
        if(!this.steppedOn.isEmpty()){
            ConcretePiece p = this.steppedOn.remove(this.steppedOn.size()-1); //Removing the last piece that stepped on the position.
            if(this.steppedOn.isEmpty() || !this.steppedOn.contains(p)){
                this.shrinkSteppedOn.remove(p); //Removing that piece from the list of the pieces that stepped on in this hall game.
            }
        }
    }

    /**
     * This method returns the number of pieces that stepped on it.
     * @return the number of pieces that stepped on it.
     */
    public int getNumOfPiecesStepped(){
        return this.shrinkSteppedOn.size();
    }

    /**
     * Printing only positions that at least two pieces stepped on.
     */
    public void printStepped(){
        int numOfStepped = this.shrinkSteppedOn.size();
        if (numOfStepped > 1) {
            System.out.println(this.toString() + this.shrinkSteppedOn.size() + " pieces");
        }
    }

    //Comparator:
    public static class steppedOnComp implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2)
        {
            int firstComp = Integer.compare(p1.getNumOfPiecesStepped(), p2.getNumOfPiecesStepped());
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
