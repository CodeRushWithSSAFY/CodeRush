import java.util.*;
/*
    초기의 map은 int[][] 로 받는다.
    `-'A'` 를 수행하며 A는 0 이런식으로 초기화 합니다.
    밖과 맞닿아 있는 공간은 -1, 
    맞닿아 있지 않은 공간은 -2
    1. 지게차 or 크래인으로 빼낸다.
        지게차는 밖과 맞닿아 있는 경우에만 뺄 수 있다.
        빼낸 공간은 -2로 모두 초기화 합니다.
    2. 밖과 맞닿아 있는지 칠한다.
        모두 돌면서 테두리인지 확인
        테두리 이면서 내가 -2면 -1로 바꾼다. && -2 면서 사방에 -1있으면 -1로 바꾼다.
            여기서 부터 bfs로 쭉 가면서 다 칠한다.
            이때 주변이 블럭일때만 안 돔 -1이면 쭉 지나가고 -2이면 -1로 바꾸어준다.
        따라서 비었는데 밖이랑 안 닿으면 -2
 */
class Solution {
    private static int[][] map;
    private static int mapRow;
    private static int mapCol;
    private static int[] dy = {0, 0, -1, 1};
    private static int[] dx = {-1, 1, 0, 0};
    private static int answer = 0;
    
    private static void crain(int target) {
        for (int row = 1; row <= mapRow; row++) {
            for (int col = 1; col <= mapCol; col++) {
                if (map[row][col] == target) map[row][col] = -2;
            }
        }
    }

    private static boolean check(int row, int col) {
        for (int dir = 0; dir < 4; dir++) {
            if (map[row + dy[dir]][col + dx[dir]] == -1) return true;
        }
        return false;
    }
    
    private static void car(int target) {
       for (int row = 1; row <= mapRow; row++) {
            for (int col = 1; col <= mapCol; col++) {
                // target이 주변이 공기와 맞닿아 있는가?                
                if (map[row][col] == target && check(row, col)) {
                    // 일단 빼고 마지막에 공기와 닿는지 체크
                    map[row][col] = -2;                    
                }
            }
        }
    }
    
    private static void fillAir() {
        int[][] visit = new int[mapRow + 2][mapCol + 2];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        visit[0][0] = 1;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for (int idx = 0; idx < 4; idx++) {
                int nxtRow = cur[0] + dy[idx];
                int nxtCol = cur[1] + dx[idx];
                if (nxtRow < 0 || mapRow + 2 <= nxtRow || nxtCol < 0 || mapCol + 2 <= nxtCol) continue;
                // 이미 방문.
                if (visit[nxtRow][nxtCol] == 1) continue;
                // 물건이 있습니다.
                if (map[nxtRow][nxtCol] != -2 && map[nxtRow][nxtCol] != -1) continue;
                map[nxtRow][nxtCol] = -1;
                visit[nxtRow][nxtCol] = 1;
                q.add(new int[]{nxtRow, nxtCol});
            }
        }
    }
    
    private static void init() {
        for (int row = 0; row <= mapRow + 1; row++) {
            for (int col = 0; col <= mapCol + 1; col++) {
                map[row][col] = -1;
            }   
        }
    }
    
    private static void count() {
        for (int row = 1; row <= mapRow; row++) {
            for (int col = 1; col <= mapCol; col++) {
                if (map[row][col] != -1 && map[row][col] != -2) answer++;
            }
        }
    }
    
    public int solution(String[] storage, String[] requests) {

        mapRow = storage.length;
        mapCol = storage[0].length();
        map = new int[mapRow + 2][mapCol + 2];
        
        init();
        for (int row = 1; row <= storage.length; row++) {
            for (int col = 1; col <= storage[0].length(); col++) {
                map[row][col] = storage[row - 1].charAt(col - 1) - 'A';
            }
        }
   
        for (int idx = 0; idx < requests.length; idx++) {
            // 지게차
            if (requests[idx].length() == 1) {
                car(requests[idx].charAt(0) - 'A');                
            }
            // 크레인
            else {
                crain(requests[idx].charAt(0) - 'A');
            }
            fillAir();
        }
        
        count();
        return answer;
    }
}
