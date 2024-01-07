/**
 * This class...
 */

public class ConcretePlayer  implements Player{

    //Fields:
    private final boolean playerType;
    private int winsNum;

    //Constructors:
    public ConcretePlayer (boolean playerType){
        this.playerType =  playerType;
    }

    //Methods:
    public boolean isPlayerOne(){
        return this.playerType;
    }
    public int getWins(){
        return winsNum;
    }
}
