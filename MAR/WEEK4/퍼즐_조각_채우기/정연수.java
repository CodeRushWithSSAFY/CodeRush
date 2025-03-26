import java.util.*;

class Solution {
    
    List<int[][]> pieceList;
    List<int[][]> blankList;
    int boardSize;
    int[] dy = {0, 0, -1, 1};
    int[] dx = {-1, 1, 0, 0};
    
    // 조각을 90도 시계 방향으로 회전
    int[][] rotateMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] result = new int[m][n];
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                result[j][n-1-i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    // 특정 위치에서 floodfill 알고리즘으로 연결된 영역 찾기
    private List<int[]> floodfill(int[][] board, int startRow, int startCol, int targetValue) {
        List<int[]> coords = new ArrayList<>();
        int n = board.length;
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        coords.add(new int[]{startRow, startCol});
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            for (int i = 0; i < 4; i++) {
                int nr = r + dy[i];
                int nc = c + dx[i];
                
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if (visited[nr][nc]) continue;
                if (board[nr][nc] != targetValue) continue;
                
                queue.add(new int[]{nr, nc});
                visited[nr][nc] = true;
                coords.add(new int[]{nr, nc});
            }
        }
        
        return coords;
    }
    
    // 조각 추출
    private int[][] extractPiece(List<int[]> coords) {
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int maxCol = Integer.MIN_VALUE;
        
        for (int[] coord : coords) {
            minRow = Math.min(minRow, coord[0]);
            minCol = Math.min(minCol, coord[1]);
            maxRow = Math.max(maxRow, coord[0]);
            maxCol = Math.max(maxCol, coord[1]);
        }
        
        int height = maxRow - minRow + 1;
        int width = maxCol - minCol + 1;
        int[][] piece = new int[height][width];
        
        // 기본값 0으로 초기화
        for (int i = 0; i < height; i++) {
            Arrays.fill(piece[i], 0);
        }
        
        // 조각 채우기 (1로)
        for (int[] coord : coords) {
            piece[coord[0] - minRow][coord[1] - minCol] = 1;
        }
        
        return piece;
    }
    
    // 조각의 모든 1 세기
    private int countOnes(int[][] piece) {
        int count = 0;
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 1) count++;
            }
        }
        return count;
    }
    
    // 조각과 빈칸이 맞는지 확인
    private boolean isMatch(int[][] piece, int[][] blank) {
        if (piece.length != blank.length || piece[0].length != blank[0].length) return false;
        
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                // 조각이 1이면 빈칸도 1이어야 함 (빈칸은 0이 빈 곳)
                if (piece[i][j] != blank[i][j]) return false;
            }
        }
        
        return true;
    }
    
    public int solution(int[][] game_board, int[][] table) {
        boardSize = game_board.length;
        pieceList = new ArrayList<>();
        blankList = new ArrayList<>();
        
        // 게임 보드에서 빈칸(0) 찾기
        boolean[][] gameBoardVisited = new boolean[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoardVisited[i][j] || game_board[i][j] != 0) continue;
                
                List<int[]> coords = floodfill(game_board, i, j, 0);
                for (int[] coord : coords) {
                    gameBoardVisited[coord[0]][coord[1]] = true;
                }
                
                int[][] blank = extractPiece(coords);
                // 빈칸은 1로 표시하여 조각과 비교할 때 맞출 수 있게 함
                for (int r = 0; r < blank.length; r++) {
                    for (int c = 0; c < blank[0].length; c++) {
                        blank[r][c] = (blank[r][c] == 0) ? 0 : 1;
                    }
                }
                blankList.add(blank);
            }
        }
        
        // 테이블에서 조각(1) 찾기
        boolean[][] tableVisited = new boolean[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tableVisited[i][j] || table[i][j] != 1) continue;
                
                List<int[]> coords = floodfill(table, i, j, 1);
                for (int[] coord : coords) {
                    tableVisited[coord[0]][coord[1]] = true;
                }
                
                int[][] piece = extractPiece(coords);
                pieceList.add(piece);
            }
        }
        
        int answer = 0;
        boolean[] usedPieces = new boolean[pieceList.size()];
        
        for (int[][] blank : blankList) {
            for (int i = 0; i < pieceList.size(); i++) {
                if (usedPieces[i]) continue;
                
                int[][] piece = pieceList.get(i);
                boolean matched = false;
                
                // 회전 없이 확인
                if (isMatch(piece, blank)) {
                    matched = true;
                } else {
                    // 90도씩 회전하며 확인
                    for (int rot = 0; rot < 3; rot++) {
                        piece = rotateMatrix(piece);
                        if (isMatch(piece, blank)) {
                            matched = true;
                            break;
                        }
                    }
                }
                
                if (matched) {
                    answer += countOnes(piece);
                    usedPieces[i] = true;
                    break;
                }
            }
        }
        
        return answer;
    }
}
