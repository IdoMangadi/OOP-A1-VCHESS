/**
 * This class...
 */

public class ConcretePlayer  implements Player{

    //Fields:
    private boolean playerType; //true = player 1 = white
    private int winsNum;

    //Constructors:
    public ConcretePlayer (boolean playerType){
        this.playerType =  playerType;
    }

    //Methods:
    public boolean isPlayerOne(){  //Means the white
        return this.playerType;
    }
    public int getWins(){
        return winsNum;
    }
    public void addWin(){
        this.winsNum++;
    }
}
