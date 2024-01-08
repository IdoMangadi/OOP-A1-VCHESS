/**
 * This class...
 */

public class ConcretePlayer  implements Player{

    //Fields:
    private boolean playerType;
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
