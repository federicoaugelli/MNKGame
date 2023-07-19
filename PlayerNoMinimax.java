package mnkgame;

import java.util.Random;

/**
 * Software player only a bit smarter than random.
 * <p>
 * It can detect a single-move win or loss. In all the other cases behaves
 * randomly.
 * </p>
 */
public class PlayerNoMinimax implements MNKPlayer {
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
    static int min = -1000; static int max = 1000;
   // private MNKCellState[][] Bo;


     public PlayerNoMinimax() {
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

    
        // check verticale
        player = 0;
        opponent = 0;
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
        }
    


        // check diagonale \
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
        }


        //System.out.println("player: " + player);
        //System.out.println("opponent: " + opponent);
        // Else if none of them have won then return 0
        return 0;
	}



    @Override
    public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
        System.out.println("mossa");
        long start = System.currentTimeMillis();

        // inizializzo i giocatori
        // se sono il primo gioxcatore prendo P1 else P2
        if (MC.length % 2 == 0) {
            myWin   = MNKGameState.WINP1;
            yourWin = MNKGameState.WINP2;
            me = MNKCellState.P1;
            adv = MNKCellState.P2;
        }
        if (MC.length % 2 == 1) {
            myWin   = MNKGameState.WINP2;
            yourWin = MNKGameState.WINP1;
            me = MNKCellState.P2;
            adv = MNKCellState.P1;
        }

        // If there is just one possible move, return immediately
        if (FC.length == 1)
            return FC[0];


        //for = marca celle della board con p1 e p2 MC

        //single move win
        if("single move win"){

        }

        //single move loss
        else if("single move loss"){

        }

        //alg
        else{
            for(int i = 0; i < FC.length; i++){

                bestCell = FC[i];
                board[bestCell.i][bestCell.j] = me;

                //marca celle orizzontali a
                for(int col = 0; col < B.N; col++){
                    if(board[i][col] == MNKCellState.FREE && board[i][col] != adv){
                        board[i][col] = me;
                    }
                }

                //conta numero vittorie
                int count = count + evaluate(board);

                //Unmark cells
                


                //unmark
                board[bestCell.i][bestCell.j] = MNKCellState.FREE;
            }
        }
    }
}


