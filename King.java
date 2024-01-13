/**
 * This class extends the ConcretePiece class.
 * Represent an actual king (white) on the board.
 */

public class King extends ConcretePiece{

    //Fields:

    //Constructors:
    public King(Player ownerV){
        super.owner = ownerV;
        super.type = "\u2654";
    }
    //Methods:

}
