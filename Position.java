/**
 * This class...
 */

public class Position {
    //Fields:
    private int x;
    private int y;

    //Constructors:
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    //Methods:
    public String getCoor(){
        return this.x + "," + this.y ;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

}
