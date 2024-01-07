/**
 * This class...
 */

public class Pawn extends ConcretePiece{

    //Fields:
    private int piecesCaptured = 0;

    //Constructors:
    public Pawn(Player ownerV){
        super.owner = ownerV;
        //Handling white player (From now on "Player 1"):
        if (ownerV.isPlayerOne()){
            super.type = "U+2659";
        }
        //Handling black player (From now on "Player 0"):
        else{
            super.type = "U+265F";
        }
    }

    //Methods:

}
