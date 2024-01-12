import java.util.ArrayList;
import java.util.Collections;

/**
 * This class...
 */

public class GameLogic implements PlayableLogic {
    //Fields:
    private ConcretePiece[][] board = new ConcretePiece[11][11];
    private ConcretePlayer player1 = new ConcretePlayer(true);
    private ConcretePlayer player2 = new ConcretePlayer(false);
    private int turnsCounter = 0;
    private ArrayList<ConcretePiece> pieces = new ArrayList<>();

    //Constructors:
    public GameLogic() {
        init();
    }

    private void init(){
        pieces.clear();
        //Init board:

        //Init white pawns and king:
        board[5][3] = new Pawn(player1);
        board[4][4] = new Pawn(player1);
        board[5][4] = new Pawn(player1);
        board[6][4] = new Pawn(player1);
        board[3][5] = new Pawn(player1);
        board[4][5] = new Pawn(player1);
        board[6][5] = new Pawn(player1);
        board[7][5] = new Pawn(player1);
        board[4][6] = new Pawn(player1);
        board[5][6] = new Pawn(player1);
        board[6][6] = new Pawn(player1);
        board[5][7] = new Pawn(player1);
        board[5][5] = new King(player1);

        int counter = 1;
        for(int y=3; y<=7; y++){
            for(int x=3; x<=7; x++){
                if (board[x][y]!=null && board[x][y].getOwner()==player1){
                    board[x][y].setName("D"+counter);
                    board[x][y].addMove(new Position(x,y));
                    pieces.add(board[x][y]);
                    counter++;
                }
            }
        }
        board[5][5].setName("K7");

        //Init black pawns:
        board[3][0] = new Pawn(player2);
        board[4][0] = new Pawn(player2);
        board[5][0] = new Pawn(player2);
        board[6][0] = new Pawn(player2);
        board[7][0] = new Pawn(player2);
        board[5][1] = new Pawn(player2);

        board[10][3] = new Pawn(player2);
        board[10][4] = new Pawn(player2);
        board[10][5] = new Pawn(player2);
        board[10][6] = new Pawn(player2);
        board[10][7] = new Pawn(player2);
        board[9][5] = new Pawn(player2);

        board[3][10] = new Pawn(player2);
        board[4][10] = new Pawn(player2);
        board[5][10] = new Pawn(player2);
        board[6][10] = new Pawn(player2);
        board[7][10] = new Pawn(player2);
        board[5][9] = new Pawn(player2);

        board[0][3] = new Pawn(player2);
        board[0][4] = new Pawn(player2);
        board[0][5] = new Pawn(player2);
        board[0][6] = new Pawn(player2);
        board[0][7] = new Pawn(player2);
        board[1][5] = new Pawn(player2);

        //Updating number and moves:
        counter = 1;
        for(int y=0; y<=10; y++){
            for(int x=0; x<=10; x++){
                if (board[x][y]!=null && board[x][y].getOwner()==player2){
                    board[x][y].setName("A"+counter);
                    board[x][y].addMove(new Position(x,y));
                    pieces.add(board[x][y]);
                    counter++;
                }
            }
        }
    }

    //Methods:
    public boolean move(Position a, Position b){
        if(moveValidCheck(a,b)){
            //Moving the piece:
            board[b.getX()][b.getY()] = board[a.getX()][a.getY()];
            board[a.getX()][a.getY()] = null;

            //Updating moves recording:
            board[b.getX()][b.getY()].addMove(b);

            //Capturing check: (Only for pawn)
            if(!board[b.getX()][b.getY()].getType().equals("U+2654")){
                CapturingCheck(b);
            }

            turnsCounter++;
            return true;
        }

        return false;
    }

    private boolean moveValidCheck(Position a, Position b){
        boolean bCorner = false;

        //Checking if a is empty:
        if(board[a.getX()][a.getY()]==null){
            return false;
        }
        //Checking if b is empty:
        if(board[b.getX()][b.getY()]!=null){
            return false;
        }

        //Checking if b is  a corner stone:
        if ((b.getX()==0 && (b.getY()==0 || b.getY()==10)) || (b.getX()==10 && (b.getY()==0 || b.getY()==10)) ){
            bCorner = true;
        }

        //If b is a corner stone but the piece is pawn:
        if (bCorner && !board[a.getX()][a.getY()].getType().equals("U+2654")){
            return false;
        }

        //Checking if the path is clear:
        //Vertical move:
        if (a.getX()==b.getX()){
            if(b.getY()>a.getY()){
                //check from a to b by +
                for(int i=a.getY()+1; i<=b.getY(); i++) {
                    if (board[a.getX()][i] != null) return false;
                }
            }
            else{
                //check from a to b by -
                for(int i=a.getY()-1; i>=b.getY(); i--) {
                    if (board[a.getX()][i] != null) return false;
                }
            }
            return true;
        }

        //Horizontal move:
        if (a.getY()==b.getY()){
            if(b.getX()>a.getX()){
                //check from a to b by +
                for(int i=a.getX()+1; i<=b.getX(); i++) {
                    if (board[i][a.getY()] != null) return false;
                }
            }
            else{
                //check from a to b by -
                for(int i=a.getX()-1; i>=b.getX(); i--) {
                    if (board[i][a.getY()] != null) return false;
                }
            }
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
            if(board[p.getX()][p.getY()-1] != null) {  //Checking that there is a piece there.
                if (board[p.getX()][p.getY()-1].getOwner() != board[p.getX()][p.getY()].getOwner()) {  //Owner check.
                    if (!board[p.getX()][p.getY()-1].getType().equals("U+2654")) { //King-eating-try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX(), p.getY()-2)) {
                            board[p.getX()][p.getY()-1] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()][p.getY()-2] != null &&
                                board[p.getX()][p.getY()-2].getOwner() == board[p.getX()][p.getY()].getOwner() &&
                                !board[p.getX()][p.getY()-2].getType().equals("U+2654")){
                            board[p.getX()][p.getY()-1] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                    }
                }
            }
        }

        //Right eaten:
        if(isValidCoor(p.getX()+1, p.getY())){  //Position validation check.
            if(board[p.getX()+1][p.getY()] != null) {  //Checking that there is a piece there.
                if (board[p.getX()+1][p.getY()].getOwner() != board[p.getX()][p.getY()].getOwner()) {  //Owner check.
                    if (!board[p.getX()+1][p.getY()].getType().equals("U+2654")) { //King-eating-try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX()+2, p.getY())) {
                            board[p.getX()+1][p.getY()] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()+2][p.getY()] != null &&
                                board[p.getX()+2][p.getY()].getOwner() == board[p.getX()][p.getY()].getOwner() &&
                                !board[p.getX()+2][p.getY()].getType().equals("U+2654")){
                            board[p.getX()+1][p.getY()] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                    }
                }
            }
        }

        //Down eaten:
        if(isValidCoor(p.getX(), p.getY()+1)){  //Position validation check.
            if(board[p.getX()][p.getY()+1] != null) {  //Checking that there is a piece there.
                if (board[p.getX()][p.getY()+1].getOwner() != board[p.getX()][p.getY()].getOwner()) {  //Owner check.
                    if (!board[p.getX()][p.getY()+1].getType().equals("U+2654")) { //King-eating-try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX(), p.getY()+2)) {
                            board[p.getX()][p.getY()+1] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()][p.getY()+2] != null &&
                                board[p.getX()][p.getY()+2].getOwner() == board[p.getX()][p.getY()].getOwner() &&
                                !board[p.getX()][p.getY()+2].getType().equals("U+2654")){
                            board[p.getX()][p.getY()+1] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                    }
                }
            }
        }

        //Left eaten:
        if(isValidCoor(p.getX()-1, p.getY())){  //Position validation check.
            if(board[p.getX()-1][p.getY()] != null) {  //Checking that there is a piece there.
                if (board[p.getX()-1][p.getY()].getOwner() != board[p.getX()][p.getY()].getOwner()) {  //Owner check.
                    if (!board[p.getX()-1][p.getY()].getType().equals("U+2654")) { //King-eating-try checking.
                        //Edge eating:
                        if (!isValidCoor(p.getX()-2, p.getY())) {
                            board[p.getX()+1][p.getY()] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                        //Two sides capture eating:
                        else if(board[p.getX()-2][p.getY()] != null &&
                                board[p.getX()-2][p.getY()].getOwner() == board[p.getX()][p.getY()].getOwner() &&
                                !board[p.getX()-2][p.getY()].getType().equals("U+2654")){
                            board[p.getX()-1][p.getY()] = null;
                            ((Pawn) board[p.getX()][p.getY()]).addKill();
                        }
                    }
                }
            }
        }
    }

    public Piece getPieceAtPosition(Position p){
        return board[p.getX()][p.getY()];
    }

    public Player getFirstPlayer(){
        return player1;
    }
    public Player getSecondPlayer(){
        return player2;
    }

    public boolean isGameFinished(){

        //Finding the KING:
        Position kingP = findKing();
        int kingX=kingP.getX(), kingY=kingP.getY();

        //Checking if the KING is in a corner:
        if( (kingX==0)&&(kingY==0) || (kingX==10)&&(kingY==0) || (kingX==10)&&(kingY==10) || (kingX==0)&&(kingY==10)){
            this.player1.addWin(); //Adding a win to player1 (white).
            winningScenario(player1);
            return true;
        }

        //Checking if the KING is captured:
        boolean kingCap = true;
        //Assuming it's captured unless one side is empty or white pawn:
        //Remainder: the sign for white pawn is: "U+2659".
        //Up:
        if(isValidCoor(kingX,kingY-1) && (board[kingX][kingY-1] == null || board[kingX][kingY-1].getType().equals("U+2659"))){ kingCap = false; }
        //Right:
        if(isValidCoor(kingX+1,kingY) && (board[kingX+1][kingY] == null || board[kingX+1][kingY].getType().equals("U+2659"))){ kingCap = false; }
        //Down:
        if(isValidCoor(kingX,kingY+1) && (board[kingX][kingY+1] == null || board[kingX][kingY+1].getType().equals("U+2659"))){ kingCap = false; }
        //Left:
        if(isValidCoor(kingX-1,kingY) && (board[kingX-1][kingY] == null || board[kingX-1][kingY].getType().equals("U+2659"))){ kingCap = false; }
        //King captured:
        if(kingCap){
            this.player2.addWin(); //Adding a win to player2 (black).
            winningScenario(player2);
        }
        return kingCap;
    }


    /**
     * This function activated when player win the game.
     * @param winP - the winner.
     */
    private void winningScenario(Player winP){
        //Sorting pieces ArrayList by: number of movements.
        ConcretePiece.movesComp comp1 = new ConcretePiece.movesComp();
        pieces.sort((p1,p2) -> comp1.compare(p1, p2, winP));
        //Printing:
        for(ConcretePiece p : pieces){ p.printMoves(); }

        System.out.println("***************************************************************************");

        //Sorting pieces ArrayList by: kills.
        //Remove the king to the side:
        Position kingP = findKing();
        ConcretePiece tmpKing = board[kingP.getX()][kingP.getY()];
        pieces.remove(tmpKing);
        ConcretePiece.killsComp comp2 = new ConcretePiece.killsComp();
        pieces.sort((p1,p2) -> comp2.compare(p1, p2, winP));
        //Printing:
        for(ConcretePiece p : pieces){
                ((Pawn) p).printKills();
        }
        //Adding the king back:
        pieces.add(tmpKing);

        System.out.println("***************************************************************************");

        //Sorting pieces ArrayList by: squares
        ConcretePiece.killsComp comp3 = new ConcretePiece.killsComp();
        pieces.sort((p1,p2) -> comp3.compare(p1, p2, winP));
        for(ConcretePiece p : pieces){ p.printSquares(); }

        System.out.println("***************************************************************************");


    }


    /**
     * Helper function to find the king on the board.
     * @return the Position it founded.
     */
    private Position findKing(){
        for(int y=0; y<=10; y++){
            for(int x=0; x<=10; x++){
                if(board[x][y] !=null && board[x][y].getType().equals("U+2654")){
                    return new Position(x,y);
                }
            }
        }
        return null;
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

    public void reset(){
        //Reset board:
        for(int i=0; i<=10; i++){
            for(int j=0; j<=10; j++){
                board[i][j] = null;
            }
        }
        init();
    }

    public void undoLastMove(){
        //TODO: complete.
    }

    public int getBoardSize(){
        return 11;
    }





}
