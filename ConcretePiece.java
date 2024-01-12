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
    protected int squares = 0;


    //Constructors:
    public ConcretePiece (){
    }

    //Methods:
    //Getters and Setters:
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
    public int getSquares(){
        return this.squares;
    }


    /**
     * adds p as a Position (object) to the moves list.
     * @param p - the position that the piece is on now.
     */
    public void addMove(Position p)
    {
        this.moves.add(p);
        int mSize = moves.size();
        if(mSize > 1) { //Means it is not the first addition of some move.
            Position p2 = moves.get(mSize-2);
            this.squares = this.squares + (Math.abs((p.getX() - p2.getX()) + (p.getY() - p2.getY())));
        }
    }

    /**
     * This function prints the moves of the piece like this:
     * [(5, 5), (5, 7), (3, 7)]   (by println).
     * Note: if the piece didn't move (only one position recorded), it won't print anything.
     */
    public void printMoves(){
        if (moves.size() > 1){
            System.out.println(name+": "+moves.toString());
        }
    }
    public int numOfMoves(){
        return moves.size();
    }

    public void printSquares(){
        if(moves.size() > 1){
            System.out.println(name+": "+this.squares+" squares");
        }
    }

    public static class movesComp implements Comparator<ConcretePiece>{
        @Override
        public  int compare(ConcretePiece p1, ConcretePiece p2){
            return 1;
        }

        public int compare(ConcretePiece p1, ConcretePiece p2, Player winP){
            //Comparing if both from the same player:
            if(p1.getOwner() == p2.getOwner()) {
                //Comparing by number of moves:
                int comp = Integer.compare(p1.numOfMoves(), p2.numOfMoves());
                //Comparing by serial number:
                if(comp == 0){
                    return Integer.compare(p1.getNumber(), p2.getNumber());
                }
                return comp;
            }
            //Comparing by winner:
            if(p1.getOwner() == winP){
                return -1;
            }
            return 1;

        }
    }

    public static class killsComp implements Comparator<ConcretePiece>{
        @Override
        public int compare(ConcretePiece p1, ConcretePiece p2){
            return 1;
        }
        public int compare(ConcretePiece p1, ConcretePiece p2, Player winP){
            //First comp by kills:
            int firstComp = Integer.compare(((Pawn) p1).getKills(),  ((Pawn) p2).getKills());
            if( firstComp == 0){
                //If equals, compare by serial number:
                int secondComp = Integer.compare(p1.getNumber(), p2.getNumber());
                if (secondComp == 0) {
                    if(p1.getOwner() == winP){ return -1; }
                    return 1;
                }
                return secondComp;
            }
            return firstComp*(-1);
        }
    }

    public static class squaresComp implements Comparator<ConcretePiece>{
        @Override
        public int compare(ConcretePiece p1, ConcretePiece p2){
            return 1;
        }
        public int compare(ConcretePiece p1, ConcretePiece p2, Player winP){
            //Comparing by number of squares:
            int firstComp = Integer.compare(p1.getSquares(), p2.getSquares());
            if(firstComp == 0){
                //Comparing by serial number:
                int secondComp = Integer.compare(p1.getNumber(), p2.getNumber());
                if(secondComp == 0){
                    if(p1.getOwner() == winP){ return -1; }
                    return 1;
                }
                return secondComp;
            }
            return firstComp*(-1);
        }
    }



}
