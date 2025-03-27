package WEEK4.경주로_건설;

import java.util.Arrays;

public class 박재환 {
    public static void main(String[] args) {
        int[][] board =  {
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println(new 박재환().solution(board));
    }

    int minCost;
    int[][][] dp;   // [x][y][각 방향별 최소값]
    public int solution(int[][] board) {
        minCost = Integer.MAX_VALUE;
        dp = new int[board.length][board.length][4];
        // 초기화
        for(int x=0; x<board.length; x++) {
            for(int y=0; y<board.length; y++) {
                Arrays.fill(dp[x][y], Integer.MAX_VALUE);
            }
        }

        getMinCost(0,0,-1,0, board);
        return minCost;
    }

    // 1. DFS 를 이용한다.
    // [x,y, 이전진행방향, 총 비용, board]
    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};
    void getMinCost(int x, int y, int preDir, int cost, int[][] board) {
        if(x == board.length-1 && y == board.length-1) {    // 목적지 도착
            minCost = Math.min(cost, minCost);
            return;
        }

        // 경우 줄이기
        // 중간 비용이 최종 비용보다 커지는 경우 바로 종료
        if(cost >= minCost) return;

        // 더 탐색이 가능한 경우
        // 수직 관계
        // 1,3
        // 0,2
        for(int dir=0; dir<4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            // 범위를 벗어나는 경우
            if(nx < 0 || ny < 0 || nx >= board.length || ny >= board.length) continue;
            // 이동이 불가한 경우
            if(board[nx][ny] == 1) continue;

            // 이동이 가능하다.
            int buildCost = 100;

            // 수직인 관계를 찾는다.
            if((preDir == 1 || preDir == 3) && (dir==0 || dir==2)) {
                buildCost += 500;
            } else if((preDir == 0 || preDir == 2) && (dir==1 || dir==3)) {
                buildCost += 500;
            }

            // 최소 비용 확인
            if(dp[nx][ny][dir] > buildCost+cost) {  // 현재 방법으로 도로를 까는것이 더 싸다면
                dp[nx][ny][dir] = buildCost+cost;
                getMinCost(nx, ny, dir, cost+buildCost, board);
            }
        }
    }
}
/*
    N x N 크기의 격자이다.
    각 칸은 0 또는 1로 채워져있다.

    0 : 칸이 비어 있음
    1 : 칸이 벽으로 채워져 있음

    (0,0) -> (N-1, N-1)

    상하좌우로 이동한다.

    인접한 두 칸을 직선, 서로 직각으로 만나는 구역을 코너

    직선 100 원, 코너 500 원

    걍주로를 건설하는 최소 비용을 구해라

    최대한 직선으로 이동하는 구간이 많은것이 좋다.
    어찌됐든 우측 아래로 이동하는 것이 목표!

    1. DFS
    최대가 25 x 25 크지는 않다고 생각됨

    2. DP
    이전 방향을 어떻게 기록하지?

    3. BFS
    빨리 도착한다고해서 해당 경로가 최소 비용이라는 보장이 없음
    */
