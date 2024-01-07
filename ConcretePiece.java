import static java.util.function.Predicate.isEqual;

/**
 * This class...
 *
 */

public abstract class ConcretePiece implements Piece{
    //Fields:
    protected Player owner;
    protected String type;


    //Constructors:
    public ConcretePiece (){
    }

    //Methods:
    public Player getOwner(){
        return owner;
    }
    public String getType(){
        return type;
    }

}
