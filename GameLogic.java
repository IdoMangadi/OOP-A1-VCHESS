/**
 * This class...
 */

public class GameLogic implements PlayableLogic {
    //Fields:
    private Position[][] board = new Position[11][11];
    private ConcretePlayer player1 = new ConcretePlayer(true);
    private ConcretePlayer player2 = new ConcretePlayer(false);
    private int turnsCounter = 0;


    //Constructors:
    public GameLogic() {
        init();
    }

    private void init(){

        //Init board:
        for(int i=0; i<=10; i++){
            for(int j=0; j<=10; j++){
                board[i][j] = new Position(i,j);
            }
        }

        //Init white pawns and king:
        board[5][3].setPiece(new Pawn(player1));
        board[4][4].setPiece(new Pawn(player1));
        board[5][4].setPiece(new Pawn(player1));
        board[6][4].setPiece(new Pawn(player1));
        board[3][5].setPiece(new Pawn(player1));
        board[4][5].setPiece(new Pawn(player1));
        board[6][5].setPiece(new Pawn(player1));
        board[7][5].setPiece(new Pawn(player1));
        board[4][6].setPiece(new Pawn(player1));
        board[5][6].setPiece(new Pawn(player1));
        board[6][6].setPiece(new Pawn(player1));
        board[5][7].setPiece(new Pawn(player1));
        board[5][5].setPiece(new King(player1));

        //Init black pawns:
        board[3][0].setPiece(new Pawn(player2));
        board[4][0].setPiece(new Pawn(player2));
        board[5][0].setPiece(new Pawn(player2));
        board[6][0].setPiece(new Pawn(player2));
        board[7][0].setPiece(new Pawn(player2));
        board[5][1].setPiece(new Pawn(player2));

        board[10][3].setPiece(new Pawn(player2));
        board[10][4].setPiece(new Pawn(player2));
        board[10][5].setPiece(new Pawn(player2));
        board[10][6].setPiece(new Pawn(player2));
        board[10][7].setPiece(new Pawn(player2));
        board[9][5].setPiece(new Pawn(player2));

        board[3][10].setPiece(new Pawn(player2));
        board[4][10].setPiece(new Pawn(player2));
        board[5][10].setPiece(new Pawn(player2));
        board[6][10].setPiece(new Pawn(player2));
        board[7][10].setPiece(new Pawn(player2));
        board[5][9].setPiece(new Pawn(player2));

        board[0][3].setPiece(new Pawn(player2));
        board[0][4].setPiece(new Pawn(player2));
        board[0][5].setPiece(new Pawn(player2));
        board[0][6].setPiece(new Pawn(player2));
        board[0][7].setPiece(new Pawn(player2));
        board[1][5].setPiece(new Pawn(player2));
    }

    //Methods:
    public boolean move(Position gottenA, Position gottenB){
        //Creating new pointers to the real positions on the board according to the given coordinate:
        Position a = board[gottenA.getX()][gottenA.getY()]; //Will point to the real src position
        Position b = board[gottenB.getX()][gottenB.getY()]; //Will point to the real dst position

        boolean bCorner = false;

        //Checking if b is empty:
        if(!b.isEmpty()){
            return false;
        }

        //Checking if b is  a corner stone:
        if ((b.getX()==0 && (b.getY()==0 || b.getY()==10)) || (b.getX()==10 && (b.getY()==0 || b.getY()==10)) ){
            bCorner = true;
        }

        //If b is a corner stone but the piece is pawn:
        if(bCorner && (a.getPieceOn()!=null && !a.getPieceOn().getType().equals("U+2654"))){
            return false;
        }

        //Checking if the path is clear:
        //Vertical move:
        if (a.getX()==b.getX()){
            if(b.getY()>a.getY()){
                //check from a to b by +
                for(int i=a.getY()+1; i<=b.getY(); i++) {
                    if (!board[a.getX()][i].isEmpty()) return false;
                }
            }
            else{
                //check from a to b by -
                for(int i=a.getY()-1; i>=b.getY(); i--) {
                    if (!board[a.getX()][i].isEmpty()) return false;
                }
            }
            //Moving the piece:
            b.setPiece(a.getPieceOn());
            a.clearPiece();
            //Capturing check: (Only for pawn)
            if(!b.getPieceOn().getType().equals("U+2654")){
                CapturingCheck(b);
            }
            turnsCounter++;
            return true;
        }

        //Horizontal move:
        if (a.getY()==b.getY()){
            if(b.getX()>a.getX()){
                //check from a to b by +
                for(int i=a.getX()+1; i<=b.getX(); i++) {
                    if (!board[i][a.getY()].isEmpty()) return false;
                }
            }
            else{
                //check from a to b by -
                for(int i=a.getX()-1; i>=b.getX(); i--) {
                    if (!board[i][a.getY()].isEmpty()) return false;
                }
            }
            b.setPiece(a.getPieceOn());
            a.clearPiece();
            //Capturing check: (Only for pawn)
            if(!b.getPieceOn().getType().equals("U+2654")){
                CapturingCheck(b);
            }
            turnsCounter++;
            return true;
        }

        return false;
    }

    /**
     * Private use function activated after moving.
     * It's checks if a capturing has done (only by pawn).
     * Remainder: king type is : "U+2654".
     * @param p - the position in which the pawn
     */
    private void CapturingCheck(Position p){

        //Up eaten:
        if(isValidCoor(p.getX(), p.getY()-1)){  //Position validation check.
            if(board[p.getX()][p.getY()-1].getPieceOn()!= null) {  //Checking that there is a piece there.
                if (board[p.getX()][p.getY() - 1].getPieceOn().getOwner() != p.getPieceOn().getOwner()) {  //Owner check.
                    if (!board[p.getX()][p.getY() - 1].getPieceOn().getType().equals("U+2654")) { //King eating try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX(), p.getY() - 2)) {
                            board[p.getX()][p.getY()-1].clearPiece();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()][p.getY()-2].getPieceOn()!= null &&
                                board[p.getX()][p.getY()-2].getPieceOn().getOwner() == p.getPieceOn().getOwner() &&
                                !board[p.getX()][p.getY()-2].getPieceOn().getType().equals("U+2654")){
                            board[p.getX()][p.getY()-1].clearPiece();
                        }
                    }
                }
            }
        }

        //Right eaten:
        if(isValidCoor(p.getX()+1, p.getY())){  //Position validation check.
            if(board[p.getX()+1][p.getY()].getPieceOn()!= null) {  //Checking that there is a piece there.
                if (board[p.getX()+1][p.getY()].getPieceOn().getOwner() != p.getPieceOn().getOwner()) {  //Owner check.
                    if (!board[p.getX()+1][p.getY()].getPieceOn().getType().equals("U+2654")) { //King eating try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX()+2, p.getY())) {
                            board[p.getX()+1][p.getY()].clearPiece();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()+2][p.getY()].getPieceOn()!= null &&
                                board[p.getX()+2][p.getY()].getPieceOn().getOwner() == p.getPieceOn().getOwner() &&
                                !board[p.getX()+2][p.getY()].getPieceOn().getType().equals("U+2654")){
                            board[p.getX()+1][p.getY()].clearPiece();
                        }
                    }
                }
            }
        }

        //Down eaten:
        if(isValidCoor(p.getX(), p.getY()+1)){  //Position validation check.
            if(board[p.getX()][p.getY()+1].getPieceOn()!= null) {  //Checking that there is a piece there.
                if (board[p.getX()][p.getY()+1].getPieceOn().getOwner() != p.getPieceOn().getOwner()) {  //Owner check.
                    if (!board[p.getX()][p.getY()+1].getPieceOn().getType().equals("U+2654")) { //King eating try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX(), p.getY()+2)) {
                            board[p.getX()][p.getY()+1].clearPiece();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()][p.getY()+2].getPieceOn()!= null &&
                                board[p.getX()][p.getY()+2].getPieceOn().getOwner() == p.getPieceOn().getOwner() &&
                                !board[p.getX()][p.getY()+2].getPieceOn().getType().equals("U+2654")){
                            board[p.getX()][p.getY()+1].clearPiece();
                        }
                    }
                }
            }
        }

        //Left eaten:
        if(isValidCoor(p.getX()-1, p.getY())){  //Position validation check.
            if(board[p.getX()-1][p.getY()].getPieceOn()!= null) {  //Checking that there is a piece there.
                if (board[p.getX()-1][p.getY()].getPieceOn().getOwner() != p.getPieceOn().getOwner()) {  //Owner check.
                    if (!board[p.getX()-1][p.getY()].getPieceOn().getType().equals("U+2654")) { //King eating try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX()-2, p.getY())) {
                            board[p.getX()-1][p.getY()].clearPiece();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()-2][p.getY()].getPieceOn()!= null &&
                                board[p.getX()-2][p.getY()].getPieceOn().getOwner() == p.getPieceOn().getOwner() &&
                                !board[p.getX()-2][p.getY()].getPieceOn().getType().equals("U+2654")){
                            board[p.getX()-1][p.getY()].clearPiece();
                        }
                    }
                }
            }
        }


    }

    public Piece getPieceAtPosition(Position gottenPosition){
        return board[gottenPosition.getX()][gottenPosition.getY()].getPieceOn();
    }

    public Player getFirstPlayer(){
        return player1;
    }
    public Player getSecondPlayer(){
        return player2;
    }

    public boolean isGameFinished(){

        //Finding the KING:
        int kingX=5, kingY=5;
        for(int i=0; i<=10; i++){
            for(int j=0; j<=10; j++){
                if(board[i][j].getPieceOn()!=null && board[i][j].getPieceOn().getType().equals("U+2654")){
                    kingX = i;
                    kingY = j;
                }
            }
        }

        //Checking if the KING is in a corner:
        if( (kingX==0)&&(kingY==0) || (kingX==10)&&(kingY==0) || (kingX==10)&&(kingY==10) || (kingX==0)&&(kingY==10)){
            this.player1.addWin(); //Adding a win to player1 (white).
            return true;
        }

        //First try, more complicated:
//        if((board[0][0].getPieceOn()!=null && board[0][0].getPieceOn().getType().equals("U+2654")) ||
//                (board[10][0].getPieceOn()!=null && board[10][0].getPieceOn().getType().equals("U+2654")) ||
//                (board[10][10].getPieceOn()!=null && board[10][10].getPieceOn().getType().equals("U+2654")) ||
//                (board[0][10].getPieceOn()!=null && board[0][10].getPieceOn().getType().equals("U+2654"))){
//            return true;
//        }

        //Checking if the KING is captured:
        boolean kingCap = true;

        //Assuming its captured unless one side is empty or white pawn:
        //Up:
        if(isValidCoor(kingX,kingY-1) && (board[kingX][kingY-1].isEmpty() || board[kingX][kingY-1].getPieceOn().getType().equals("U+2659"))){
            kingCap = false;
        }
        //Right:
        if(isValidCoor(kingX+1,kingY) && (board[kingX+1][kingY].isEmpty() || board[kingX+1][kingY].getPieceOn().getType().equals("U+2659"))){
            kingCap = false;
        }
        //Down:
        if(isValidCoor(kingX,kingY+1) && (board[kingX][kingY+1].isEmpty() || board[kingX][kingY+1].getPieceOn().getType().equals("U+2659"))){
            kingCap = false;
        }
        //Left:
        if(isValidCoor(kingX-1,kingY) && (board[kingX-1][kingY].isEmpty() || board[kingX-1][kingY].getPieceOn().getType().equals("U+2659"))){
            kingCap = false;
        }

        if(kingCap){
            this.player2.addWin(); //Adding a win to player2 (black).
        }
        return kingCap;
    }

    /**
     * private function helping validate boundaries exceeding.
     * @param x - x coordinate
     * @param y - y coordinate
     * @return true if the index is valid and false if index out of boundaries.
     */
    private boolean isValidCoor(int x, int y){
        if(x<0 || y<0 || x>10 || y>10) return false;
        return true;
    }

    /**
     * Attacker is player 2 (BLACK)
     * Defender is player 1 (WHITE)
     * Player 2 Acts first.
     * @return true if it is the attacker turn.
     */
    public boolean isSecondPlayerTurn(){
        return turnsCounter%2==0;
    }

    //TODO: add the rest of the functions here !

    public void reset(){
        init(); //Reset board.


    }




    public int getBoardSize(){
        return 11;
    }





}
