import java.util.ArrayList;
import java.util.Comparator;

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

    public int getNumber(){
        String res = name.substring(1);
        return (Integer.parseInt(res));
    }



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
        if (moves.size()>1){
            System.out.println(name+": "+moves.toString());
        }
    }
    public int numOfMoves(){
        return moves.size();
    }

    public static class movesComp implements Comparator<ConcretePiece>{
        @Override
        public int compare(ConcretePiece p1, ConcretePiece p2){
            int comp = Integer.compare(p1.numOfMoves(), p2.numOfMoves());
            return comp;
        }
    }

    public static class killComp implements Comparator<ConcretePiece>{
        @Override
        public int compare(ConcretePiece p1, ConcretePiece p2){
            //First comp by kills:
            int firstComp = Integer.compare(((Pawn) p1).getKills(),  ((Pawn) p2).getKills());
            if( firstComp == 0){
                //If equals, compare by serial number:
                int secondComp = Integer.compare();

            }
            return firstComp*-1;
        }
    }



}
