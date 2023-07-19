package mnkgame;

import java.util.Random;

/**
 * Software player only a bit smarter than random.
 * <p>
 * It can detect a single-move win or loss. In all the other cases behaves
 * randomly.
 * </p>
 */
public class Player2 implements MNKPlayer {
    MNKCell firstMove;
    public MNKCellState me;
    private MNKCellState adv;
    private MNKBoard B;
    private MNKGameState myWin;
    private MNKGameState yourWin;
    private int TIMEOUT;
    private int bestMoveRow;
    private int bestMoveCol;
    private int bestVal;
    private int moveVal;
    private int posCentraleI;
	private int posCentraleJ;
    private long count;
    MNKCellState [][] board;
    private Random rand;
   // private MNKCellState[][] Bo;


     public Player2() {
    }

    @Override
    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
        //myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		//yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
        rand    = new Random(System.currentTimeMillis());
        B = new MNKBoard(M, N, K);
        board = new MNKCellState[M][N];
        for(int i=0; i<B.M; i++){
            for(int j=0; j<B.N; j++){
                board[i][j] = MNKCellState.FREE;
            }
        }
        bestMoveCol = -1;
        bestMoveRow = -1;
        bestVal = -1000;
        TIMEOUT = timeout_in_secs;
    } 

    public String playerName() {
        return "Looser";
    }

    public Boolean isMovesLeft(MNKCellState board[][]){
        for (int i = 0; i < B.M; i++)
            for (int j = 0; j < B.N; j++)
                if (board[i][j] == MNKCellState.FREE)
                    return true;
        return false;
    }

    public int evaluate(MNKCellState board[][]) {
        
        // check orizzontale
        int player = 0;
        int opponent = 0;
        /*
        for (int row = 0; row < B.M; row++){
            player = 0;
            opponent = 0;
            for(int col = 0; col < B.N; col++){      
                if(board[row][col] == me){
                    player++;
                }
                else player = 0;
                if(player == B.K){
                   return +10; 
                } 
            }
            for(int col = 0; col < B.N; col++){          
                if(board[row][col] == adv){
                    opponent++;
                }
                else opponent = 0;
                if(opponent == B.K){
                    return -10;
                } 
            }
        }*/

        //TMP CHECK HORIZONTAL
        for (int row = 0; row < 3; row++){
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2])
            {
                if (board[row][0] == me)
                    return +10;
                else if (board[row][0] == adv)
                    return -10;
            }
        }

    
        // check verticale
        player = 0;
        opponent = 0;
        /*
        for (int col = 0; col < B.M; col++){
            player = 0;
            opponent = 0;
            for(int row = 0; row < B.N; row++){      
                if(board[row][col] == me){
                    player++;
                }
                else player = 0;
                if(player == B.K){
                   return +10; 
                } 
            }
            for(int row = 0; row < B.N; row++){          
                if(board[row][col] == adv){
                    opponent++;
                }
                else opponent = 0;
                if(opponent == B.K){
                    return -10;
                } 
            }
        }*/

        //TMP CHECK VERTICAL
        for (int col = 0; col < 3; col++){
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col])
            {
                if (board[0][col] == me)
                    return +10;
                else if (board[0][col] == adv)
                    return -10;
            }
        }
    


        // check diagonale \
        player=0;
        int playerInv = 0;
        opponent=0;
        int opponentInv = 0;
        int y = 0;
        int x = 0;
        
        
        
        
        
        
        
        
        
        
        
        
        
        /*
        for (int row = 0; row < B.M; row++){
            for (int col = 0; col < B.N; col++){
                y = 0;
                player = 0;
                while(y+col < B.N && y+row < B.M){
                    if (board[y+row][y+col] == me) player++;
                    else player = 0; 
                    y++;
                    if(player == B.K) return +10;
                } 
            } 
            for (int col = 0; col < B.N; col++){
                y = 0;
                opponent = 0;
                while(y+col < B.N && y+row < B.M){
                    if (board[y+row][y+col] == adv) opponent++;
                    else opponent = 0;
                    y++;
                    if(opponent == B.K) return -10;
                }   
            } 
        }

        // check diagonale /
        for (int row = 0; row < B.M; row++){
            for (int col = 0; col < B.N; col++){
                y = B.N;
                x = 0;
                playerInv = 0;
                while(x+row < B.M && y-col-1 > 0){
                    if (board[x+row][y-col-1] == me) playerInv++;
                    else playerInv = 0;    
                    y--;
                    x++;
                    if(playerInv == B.K) return +10;
                }
            }
            for (int col = 0; col < B.N; col++){
                y = B.N;
                x = 0;
                opponentInv = 0;
                while(x+row < B.M && y-col-1 > 0){
                    if (board[x+row][y-col-1] == adv) opponentInv++;
                    else opponentInv = 0;
                    y--;
                    x++;
                    if(opponentInv == B.K) return -10;
                }   
            } 
        }*/

        /*TMP CHECK DIAG*/
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
        {
            if (board[0][0] == me)
                return +10;
            else if (board[0][0] == adv)
                return -10;
        }
     
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0])
        {
            if (board[0][2] == me)
                return +10;
            else if (board[0][2] == adv)
                return -10;
        }


        //System.out.println("player: " + player);
        //System.out.println("opponent: " + opponent);
        // Else if none of them have won then return 0
        return 0;
	}


    // se primo turno
    public int minimax(MNKCellState [][] board, int alpha, int beta, int index, int depth, Boolean isMax, long start) {

        MNKCell c;
        count++;

        /*if (B.gameState != MNKGameState.OPEN) {
            return 0;
        }*/

        int score = evaluate(board);

        //System.out.println(score);
        //System.out.println("depth: "+depth);
       
        //maximizer has won
        if (score == 10) return score - depth;

        //minimizer has won
        else if (score == -10) return score + depth;


        if (isMovesLeft(board) == false) return 0;
        
        if (isMax == true) {
            int best = -1000;
            
            
            for (int i = 0; i < B.M; i++) {
                for (int j = 0; j < B.N; j++) {
                    if (board[i][j] == MNKCellState.FREE) {
                        board[i][j] = me;

                        best = Math.max(best, minimax(board, alpha, beta, index*2+i, depth + 1, false, start));
                        alpha= Math.max(alpha, best);

                        /*
                        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                            break;
                        }*/
                        
                        //System.out.print("alpha: " + alpha + " beta: " + beta);
                        
                        if (beta <= alpha) {
                            //System.out.print("alphabeta");
                            break;
                        }
                        //B.unmarkCell();
                        board[i][j] = MNKCellState.FREE;
                    }
                }
            }
            
            //System.out.println("arrivo primo return best");
            return best;
        }
        else {
            int best = 1000;
            
            
            for (int i = 0; i < B.M; i++) {
                for (int j = 0; j < B.N; j++) {
                    if (board[i][j] == MNKCellState.FREE) {
                        //B.markCell(i, j);
                        board[i][j] = adv;
                        // B[i][j] = me;

                        best = Math.min(best, minimax(board, alpha, beta, index*2+i, depth + 1, true, start));
                        beta= Math.min(beta, best);

                        /*
                        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                            break;
                        }*/
                        
                        if (beta <= alpha) {
                            //System.out.print("alphabeta");
                            break;
                        }

                        //System.out.print("beta: " + beta);
                        //B.unmarkCell();
                        board[i][j] = MNKCellState.FREE;                 
                    }
                }
            }
            
            //System.out.println("arrivo secondo return best");
            return best;
        }
    }

    @Override
    public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
        System.out.println("mossa");

        long start = System.currentTimeMillis();

        MNKCell bestCell;
        bestVal = -1000;
        bestMoveRow = -1;
        bestMoveCol = -1;
        // inizializzo i giocatori
        // se sono il primo gioxcatore prendo P1 else P2
        if (MC.length == 0) {
            myWin   = MNKGameState.WINP1;
            yourWin = MNKGameState.WINP2;
            me = MNKCellState.P1;
            adv = MNKCellState.P2;
        }
        if (MC.length == 1) {
            myWin   = MNKGameState.WINP2;
            yourWin = MNKGameState.WINP1;
            me = MNKCellState.P2;
            adv = MNKCellState.P1;
        }

        // If there is just one possible move, return immediately
        if (FC.length == 1)
            return FC[0];

        //Single move win
        /*
        for(MNKCell d : FC){
            //System.out.println(myWin);
            //System.out.println(B.markCell(FC[i].i,FC[i].j));
            //B.unmarkCell();
            if(B.markCell(d.i,d.j) == myWin){
                System.out.println("vittoria");
                return d;
            }
            else{
                B.unmarkCell();
            }
        }

        //Is ADV Winning?
        for(int i = 0; i < FC.length; i++){
            if(i != FC.length){
                MNKCell d = FC[i];
                if(B.markCell(d.i,d.j) == yourWin) {
                    System.out.println("ok");
					B.unmarkCell();        // undo adversary move
					B.unmarkCell();	       // undo my move	 
					B.markCell(d.i,d.j);   // select his winning position
					return d;							 // return his winning position
				} else {
					B.unmarkCell();	       // undo adversary move to try a new one
				}	
            }
           
        }*/

        if(MC.length > 0) {
            MNKCell c = MC[MC.length-1];
            B.markCell(c.i,c.j);
            board[c.i][c.j] = adv;


            
            for(int i = 0; i < FC.length; i++){

                bestCell = FC[i];
                board[bestCell.i][bestCell.j] = me;


                moveVal = minimax(board, -1000, 1000, 0, 0, true, start);

                //System.out.print(count + "\n");
                System.out.print("pos: " + bestCell.i + " " + bestCell.j + " moveval: " + moveVal + "\n");

                board[bestCell.i][bestCell.j] = MNKCellState.FREE;

                if(moveVal > bestVal){
                    bestMoveRow = bestCell.i;
                    bestMoveCol = bestCell.j;
                    bestVal = moveVal;
                }

                //timeout
                /*
                if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                    System.out.println("start: " + ((System.currentTimeMillis()-start)/1000.0) + ", timeout: " + (TIMEOUT*(99.0/100.0)));

                    for(int k = 0; k < FC.length; k++){
                        MNKCell d = FC[k];
                        if(B.markCell(d.i,d.j) == yourWin) {
                            B.unmarkCell();        
                            B.unmarkCell();	        
                            B.markCell(d.i,d.j);
                            return d;
                        } 
                        else {
                            B.unmarkCell();
                        }
                    }

                    B.markCell(bestMoveRow,bestMoveCol);
                    board[bestMoveRow][bestMoveCol] = me;
                    System.out.println("best row: " + bestMoveRow + ", best col: " + bestMoveCol);
                    return new MNKCell(bestMoveRow, bestMoveCol);
                }*/
            }
            System.out.println("riga scelta: " + bestMoveRow + ", colonna scelta: " + bestMoveCol);


            //System.out.print("best move col: " + bestMoveRow);
            //System.out.print("best move row: " + bestMoveCol);
            
            B.markCell(bestMoveRow,bestMoveCol);
            board[bestMoveRow][bestMoveCol] = me;
            return new MNKCell(bestMoveRow, bestMoveCol);
        }
        else{
            // prima mossa posizionata al centro della board
			if(B.M%2 == 0) posCentraleI = B.M/2;
			else posCentraleI = ((B.M+1)/2)-1;
			if(B.N%2 == 0) posCentraleJ = B.N/2;
			else posCentraleJ = ((B.N+1)/2)-1;
			firstMove = new MNKCell(posCentraleI, posCentraleJ);
			B.markCell(firstMove.i,firstMove.j);
            board[firstMove.i][firstMove.j] = me;
			return firstMove;
        }
    }
}


