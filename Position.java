/**
 * This class...
 */

public class Position {
    //Fields:
    private int x;
    private int y;
    private Piece pieceOn;

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
    public Piece getPieceOn(){
        return this.pieceOn;
    }

    public void setPiece(Piece pieceToSet){
        this.pieceOn = pieceToSet;
    }
    public void clearPiece(){
        this.pieceOn = null;
    }

    public boolean isEmpty(){
        if (this.pieceOn == null){
            return true;
        }
        return false;
    }

}
