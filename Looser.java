package mnkgame;

import java.util.Random;


public class Looser implements MNKPlayer {
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
    private int maxDepth;
    MNKCellState [][] board;
    private Random rand;
    static int min = -1000; static int max = 1000;


    public Looser() {
    }

    @Override
    public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
        myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
        rand    = new Random(System.currentTimeMillis());

        //new board of cellstate
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

        //dynamic defined max depth
        maxDepth = (int) (50 * Math.exp(-0.11 * (B.M*B.N)));
        if (maxDepth < 0){
            maxDepth = 1;
        }
    } 

    public String playerName() {
        return "Lo0z3R";
    }

    //return 0 if draw
    public Boolean isMovesLeft(MNKCellState board[][]){
        for (int i = 0; i < B.M; i++)
            for (int j = 0; j < B.N; j++)
                if (board[i][j] == MNKCellState.FREE)
                    return true;
        return false;
    }

    //evaluate function --> +10 win, -10 loss
    public int evaluate(MNKCellState board[][]) {
        
        // Horizontal check
        int player = 0;
        int opponent = 0;

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
        }

        // Vertical check
        player = 0;
        opponent = 0;
        for (int col = 0; col < B.N; col++){
            player = 0;
            opponent = 0;
            for(int row = 0; row < B.M; row++){      
                if(board[row][col] == me){
                    player++;
                }
                else player = 0;
                if(player == B.K){
                   return +10; 
                } 
            }
            for(int row = 0; row < B.M; row++){          
                if(board[row][col] == adv){
                    opponent++;
                }
                else opponent = 0;
                if(opponent == B.K){
                    return -10;
                } 
            }
        }
    
        // Diagonal Check
        player=0;
        int playerInv = 0;
        opponent=0;
        int opponentInv = 0;
        int y = 0;
        int x = 0;
        
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

        // Anti Diagonal Check
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
        }
        return 0;
	}


    //Minimax
    public int minimax(MNKCellState [][] board, int depth, Boolean isMax, int alpha, int beta, long start) {

        MNKCell c;

        //eval function
        int score = evaluate(board);
       
        //maximizer has won
        if (score == 10) return score - depth;

        //minimizer has won
        else if (score == -10) return score + depth;

        //max depth
        if(depth > maxDepth){
            return score;
        }

        if (isMovesLeft(board) == false) return 0;
        
        if (isMax == true) {
            int best = -1000;
            
            for (int i = 0; i < B.M; i++) {
                for (int j = 0; j < B.N; j++) {
                    if (board[i][j] == MNKCellState.FREE) {
                        board[i][j] = me;

                        best = Math.max(best, minimax(board, depth + 1, !isMax, alpha, beta, start));
                        alpha = Math.max(alpha, best);
                        board[i][j] = MNKCellState.FREE;

                        //timeout
                        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                            break;
                        }

                        //alpha beta pruning
                        if (beta <= alpha) {
                            break;
                        }

                    }
                }
            }
            return best;
        }
        else {
            int best = 1000;
            
            for (int i = 0; i < B.M; i++) {
                for (int j = 0; j < B.N; j++) {
                    if (board[i][j] == MNKCellState.FREE) {
                        board[i][j] = adv;

                        best = Math.min(best, minimax(board, depth + 1, !isMax, alpha, beta, start));
                        beta = Math.min(beta, best);
                        board[i][j] = MNKCellState.FREE;

                        //timeout
                        if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {
                            break;
                        }

                        //alpha beta pruning
                        if (beta <= alpha) {
                            break;
                        }
                 
                    }
                }
            }
            return best;
        }
    }

    @Override
    public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {

        long start = System.currentTimeMillis();

        MNKCell bestCell;
        bestVal = -1000;
        bestMoveRow = -1;
        bestMoveCol = -1;

        //init player
        if (MC.length % 2 == 0) {
            me = MNKCellState.P1;
            adv = MNKCellState.P2;
        }
        if (MC.length % 2 == 1) {
            me = MNKCellState.P2;
            adv = MNKCellState.P1;
        }

        //Recover last move from adv (in B e board)
        if(MC.length > 0){
            board[MC[MC.length-1].i][MC[MC.length-1].j] = adv;
            MNKCell c = MC[MC.length-1];
            B.markCell(c.i,c.j);
        }
        for(int i = 0; i < MC.length; i++){
            if (board[MC[i].i][MC[i].j] == MNKCellState.FREE){
                board[MC[i].i][MC[i].j] = me;
            }
        }

        // If there is just one possible move, return immediately
        if (FC.length == 1)
            return FC[0];

		// Single move win
		for(MNKCell d : FC) {
            if(B.markCell(d.i,d.j) == myWin) {
				return d;
			} else {
				B.unmarkCell();
			}
		}
	
        //Single move loss
		for(MNKCell d : FC) {
			if(B.markCell(d.i,d.j) == yourWin) {
				return d;
			} 
               
            else {
				B.unmarkCell();
			}	
		}

        //Select best move
        if((MC.length <= 3 && MC.length != 0) && (B.M >= 4 || B.N >= 4)){
            for(int i = 0; i < FC.length; i++){
                if(FC[i].i - MC[MC.length-1].i <= Math.abs(1) && FC[i].j - MC[MC.length-1].j <= Math.abs(1)){
                    bestCell = FC[i];
                    board[bestCell.i][bestCell.j] = me;
                    moveVal = minimax(board, 0, false, min, max, start);
                    board[bestCell.i][bestCell.j] = MNKCellState.FREE;

                    if(moveVal > bestVal){
                        bestMoveRow = bestCell.i;
                        bestMoveCol = bestCell.j;
                        bestVal = moveVal;
                    }

                    //timeout
                    if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {

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
                        return new MNKCell(bestMoveRow, bestMoveCol);
                    }
                }
            }
                    
            B.markCell(bestMoveRow,bestMoveCol);
            board[bestMoveRow][bestMoveCol] = me;
            return new MNKCell(bestMoveRow, bestMoveCol);
        }

        //select best move
        else if(MC.length > 0) {
            for(int i = 0; i < FC.length; i++){
                    bestCell = FC[i];
                    board[bestCell.i][bestCell.j] = me;
                    moveVal = minimax(board, 0, false, min, max, start);
                    board[bestCell.i][bestCell.j] = MNKCellState.FREE;

                    if(moveVal > bestVal){
                        bestMoveRow = bestCell.i;
                        bestMoveCol = bestCell.j;
                        bestVal = moveVal;
                    }

                    //timeout
                    if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(99.0/100.0)) {

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
                        return new MNKCell(bestMoveRow, bestMoveCol);
                    }
            }
            //System.out.println("riga scelta: " + bestMoveRow + ", colonna scelta: " + bestMoveCol);
                    
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