import java.util.*;

class Solution {
    
    int[][] staticBoard;
    int[][] visit;
    int boardSize;
    int[] dy = {0, 0, -1, 1};
    int[] dx = {-1, 1, 0, 0};
    
    public void dfs(int row, int col, int count, int curDir) {
        if (row == boardSize - 1 && col == boardSize - 1) {
            return;
        }
        for (int dir = 0; dir < 4; dir++) {
            int nxtRow = row + dy[dir];
            int nxtCol = col + dx[dir];
            int nxtCount = count + 100;
            if (nxtRow < 0 || boardSize <= nxtRow || nxtCol < 0 || boardSize <= nxtCol) continue;
            if (staticBoard[nxtRow][nxtCol] == 1) continue;
            if (count != 0 && curDir != dir) nxtCount += 500;
            
            if (visit[nxtRow][nxtCol] <= nxtCount - 500) continue;
            
            if (nxtRow == boardSize - 1 && nxtCol == boardSize - 1) {
                visit[nxtRow][nxtCol] = Math.min(visit[nxtRow][nxtCol], nxtCount);  
            } else {
                visit[nxtRow][nxtCol] = nxtCount;                
            }
            dfs(nxtRow, nxtCol, nxtCount, dir);
        }
    }

    public void init() {        
        visit = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                visit[row][col] = Integer.MAX_VALUE;
            }
        }
    }
    
    public int solution(int[][] board) {
        int answer = 0;
        staticBoard = board;
        boardSize = board.length;
        init();
        visit[0][0] = 0;
        dfs(0, 0, 0, 0); 
        return visit[boardSize - 1][boardSize - 1];
    }
}
