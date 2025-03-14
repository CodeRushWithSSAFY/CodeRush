import java.io.*;
import java.util.*;

class Solution {
    int[] totalOil;
    int[][] oilLand;
    int[][] visit;
    int[] dy = {-1, 1, 0, 0};
    int[] dx = {0, 0, -1, 1};
    
    void bfs(int row, int col) {
        int count = 1;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        Set<Integer> s = new HashSet<>();
        q.add(new int[]{row, col});
        s.add(col);
        visit[row][col] = 1;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nxtRow = cur[0] + dy[i];
                int nxtCol = cur[1] + dx[i];
                if (nxtRow < 0 || visit.length <= nxtRow || nxtCol < 0 || visit[0].length <= nxtCol) continue;
                if (visit[nxtRow][nxtCol] == 1 || oilLand[nxtRow][nxtCol] == 0) continue;
                visit[nxtRow][nxtCol] = 1;
                count++;
                s.add(nxtCol);
                q.add(new int[]{nxtRow, nxtCol});
            }
        }
        for (int idx : s) {
            totalOil[idx] += count;
        }
    }
    
    public int solution(int[][] land) {
        int answer = 0;
        totalOil = new int[land[0].length];
        visit = new int[land.length][land[0].length];
        oilLand = land;
        
        for (int row = 0; row < land.length; row++) {
            for (int col = 0; col < land[0].length; col++) {
                if (land[row][col] == 0 || visit[row][col] == 1) continue;
                bfs(row, col);
            }
        }
        
        for (int idx = 0; idx < totalOil.length; idx++) {
            answer = Math.max(answer, totalOil[idx]);
        }
        return answer;
    }
}
