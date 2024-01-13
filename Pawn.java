/**
 * This class extends the ConcretePiece class.
 * Represent an actual pawn (black/ white) on the board.
 */

public class Pawn extends ConcretePiece{

    //Fields:
    private int kills = 0;

    //Constructors:
    public Pawn(Player ownerV){
        super.owner = ownerV;
        //Handling white player (From now on "Player 1"):
        if (ownerV.isPlayerOne()){
            super.type = "\u2659";
        }
        //Handling black player (From now on "Player 0"):
        else{
            super.type = "\u265F";
        }
    }

    //Methods:
    public void addKill(int k){
        this.kills = this.kills + k;
    }
    public int getKills(){
        return kills;
    }
    public void printKills(){
        if (this.kills > 0) {
            System.out.println(name+": "+this.kills+" kills");
        }
    }
}
