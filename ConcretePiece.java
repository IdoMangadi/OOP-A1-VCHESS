import java.util.ArrayList;

import static java.util.function.Predicate.isEqual;

/**
 * This class...
 *
 */

public abstract class ConcretePiece implements Piece{
    //Fields:
    protected Player owner;
    protected String type;
    protected String name;
    protected ArrayList<Position> moves = new ArrayList<>();


    //Constructors:
    public ConcretePiece (){
    }

    //Methods:
    public Player getOwner(){
        return this.owner;
    }
    public String getType(){
        return this.type;
    }

    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }



    /**
     * adds p as a Position (object) to the moves list.
     * @param p - the position that the piece is on now.
     */
    public void addMove(Position p){
        this.moves.add(p);
    }

    /**
     * This function prints the moves of the piece like this:
     * [(5, 5), (5, 7), (3, 7)]   (by println).
     * Note: if the piece didn't move (only one position recorded), it won't print anything.
     */
    public void printMoves(){
            System.out.println(name+": "+moves.toString());
    }

}
