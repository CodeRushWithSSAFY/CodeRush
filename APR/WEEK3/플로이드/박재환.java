package WEEK3.플로이드;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        init();

        br.close();
        System.out.println(sb);
    }

    static StringTokenizer st;
    static int cites, buses;
    static int[][] connInfo;
    static final int INF = 987654321;  // 최대 거리 (100000 의 거리를 갖지만, 최소거리가 해당거리일 수 있음)
    static void init() throws IOException {
        cites = Integer.parseInt(br.readLine().trim());
        buses = Integer.parseInt(br.readLine().trim());

        connInfo = new int[cites][cites];
        // 초기화
        // 자기자신 0
        // 연결 X 무한 처리
        for (int x = 0; x < cites; x++) {
            for (int y = 0; y < cites; y++) {
                if(x==y) connInfo[x][y] = 0;
                else connInfo[x][y] = INF;
            }
        }

        while(buses-- > 0) {
            st = new StringTokenizer(br.readLine().trim());
            int cityA = Integer.parseInt(st.nextToken())-1;
            int cityB = Integer.parseInt(st.nextToken())-1;
            int cost = Integer.parseInt(st.nextToken());

            connInfo[cityA][cityB] = Math.min(connInfo[cityA][cityB], cost);
        }

        getAllMinDist();
        printRoutes();
    }

    /*
        각 시작 정점에서 모든 정점으로의 거리를 구한다.
     */
    static void getAllMinDist() {
        // 최단 거리 업데이트
        // i 를 경유해감
        for(int i=0; i<cites; i++) {
            // 노드 x에서 y로 가는 경우
            for (int x=0; x<cites; x++) {
                for(int y=0; y<cites; y++) {
                    connInfo[x][y] = Math.min(connInfo[x][y], connInfo[x][i] + connInfo[i][y]);
                }
            }
        }
    }

    static void printRoutes() {
        for (int x = 0; x < cites; x++) {
            for (int y = 0; y < cites; y++) {
                sb.append(connInfo[x][y] == INF ? 0 : connInfo[x][y]).append(' ');
            }
            sb.append('\n');
        }
    }
}

/*
    모든 도시의 쌍에 대해 A -> B 로 가는 비용의 최소값을 구하라
 */
