import static java.util.function.Predicate.isEqual;

/**
 * This class...
 *
 */

public abstract class ConcretePiece implements Piece{
    //Fields:
    private final Player owner;
    private final String type;


    //Constructors:
    public ConcretePiece (Player ownerV, String typeV){
        this.owner = ownerV;
        //If it's the white player:
        if (this.owner.isPlayerOne()){
            if (typeV.equals("P")){
                this.type = "U+2659"; //White pawn UNICODE.
            }
            else{
                this.type = "U+2654"; //White king UNICODE.
            }
        }
        //If it's the black player:
        else{
            this.type = "U+265F"; //Black pawn UNICODE.
        }

    }

    //Methods:
    public Player getOwner(){
        return owner;
    }
    public String getType(){
        return type;
    }

}
