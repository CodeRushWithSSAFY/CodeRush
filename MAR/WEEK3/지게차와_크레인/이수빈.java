import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

class Solution {
    char[][] board;
    boolean[][] visited;
    int rowSize, colSize;
    char EMPTY = '0';
    List<int[]> isSelected;
    
    int[] dr = {0, 0, -1, 1};
    int[] dc = {1, -1, 0, 0};
    
    public void bfs(int row, int col, char alphabet) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{row, col});
        visited[row][col] = true;
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = cur[0] + dr[dir];
                int nextCol = cur[1] + dc[dir];
                
                if (nextRow < 0 || nextCol < 0 || nextRow >= rowSize + 2 || nextCol >= colSize + 2) continue;
                
                if (!visited[nextRow][nextCol]) {
                    if (board[nextRow][nextCol] == alphabet) {
                        isSelected.add(new int[]{nextRow, nextCol});
                        visited[nextRow][nextCol] = true;
                    }
                    
                    if (board[nextRow][nextCol] == EMPTY) {
                        q.add(new int[]{nextRow, nextCol});
                        visited[nextRow][nextCol] = true;
                    }
                        
                }
            }
        }
        
    }
    
    public int solution(String[] storage, String[] requests) {
        
        rowSize = storage.length;
        colSize = storage[0].length();
        
        int answer = rowSize * colSize;
        
        board = new char[rowSize + 2][colSize + 2];
        
        for (int row = 0; row < rowSize + 2; row++) {
            for (int col = 0; col < colSize + 2; col++) {
                if (row == 0 || row == rowSize + 1 || col == 0 || col == colSize + 1)
                    board[row][col] = EMPTY;
            }
        }
        
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row + 1][col + 1] = storage[row].charAt(col);
            }
        }
        
       
        
        for (int idx = 0; idx < requests.length; idx++) {
            char alphabet = requests[idx].charAt(0);
            isSelected = new ArrayList<>();
            visited = new boolean[rowSize + 2][colSize + 2];
            
            if (requests[idx].length() == 1) {
                bfs(0, 0, alphabet);
                
                for (int i = 0; i < isSelected.size(); i++) {
                    int row = isSelected.get(i)[0];
                    int col = isSelected.get(i)[1];
                    board[row][col] = EMPTY;
                    answer--;
                }
                
            } else {
                for (int row = 1; row < rowSize + 1; row++) {
                    for (int col = 1; col < colSize + 1; col++) {
                        if (board[row][col] == alphabet) {
                            board[row][col] = EMPTY;
                            answer--;
                        }
                    }
                }
            }
        }
        
        
        return answer;
    }
}
