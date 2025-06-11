package MAY.WEEK2.고층_건물;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static StringTokenizer st;
    static int buildingCnt;           // 빌딩 수
    static int[] buildings;          // 빌딩 높이
    static double[][] inclines;         // 기울기
    static void init() throws IOException {
        buildingCnt = Integer.parseInt(br.readLine().trim());
        buildings = new int[buildingCnt];
        inclines = new double[buildingCnt][buildingCnt];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<buildingCnt; i++) {
            buildings[i] = Integer.parseInt(st.nextToken());
        }
        maxBuildingCnt();
    }

    /*
        빌딩에서 볼 수 있는 다른 빌딩의 최대 개수를 구한다.

        - 현 위치의 빌딩을 기준으로 왼쪽 / 오른쪽 구역을 나누어 탐색한다.
        - 기울기의 개념 사용

        => 현재까지 기울기보다 큰 기울기가 생기면, 보이는 건물이 하나 더 증가한다.
     */
    static void maxBuildingCnt() {
        int max = Integer.MIN_VALUE;
        for(int building=0; building<buildingCnt; building++) {
            int cnt = 0;
            /*
                왼쪽으로 이동할 수록 x 좌표 감소 -> 양수
                높이 차에 따라 부호가 갈림
                기울기가 작아질수록 새로운 건물 볼 수 있음
             */
            double leftIncline = Double.POSITIVE_INFINITY;
            /*
                오른쪽으로 이동할 수록 x 좌표 증가 -> 음수
                높이 차에 따라 부호가 갈림
                기울기가 커질수록 새로운 건물 볼 수 있음
             */
            double rightIncline = Double.NEGATIVE_INFINITY;

            // 1. 왼쪽
            for(int left=building-1; left>-1; left--) {
                // 기울기
                double incline = (double)(buildings[building]-buildings[left])/(building-left);
                // 기울기가 작아질수록
                if(incline < leftIncline) {
                    cnt++;
                    leftIncline = incline;
                }
            }

            // 2. 오른쪽
            for(int right=building+1; right<buildingCnt; right++) {
                // 기울기
                double incline = (double)(buildings[right]-buildings[building])/(right-building);
                // 기울기가 커질수록
                if(incline > rightIncline) {
                    cnt++;
                    rightIncline = incline;
                }
            }

            max = Math.max(max, cnt);
        }

        System.out.println(max);
    }
}

/*
    N 개의 빌딩이 있다.
    고층 빌딩 A 에서 다른 고츨 빌딩 B 가 볼 수 있는 빌딩이 되려면
    두 지붕을 잇는 선분이 A 와 B 를 제외한 다른 고틍 빌딩을 지나거나 접하지 않아야한다.
    => 꾸준히 증가 / 감소 하는 형태
    => LIS ❌

    문재 그대로 생각하기
    1. 각 지점을 기준으로 한다.
        1-1. 왼쪽과 오른쪽을 구분한다.
            1-1-1. 왼쪽 부분 -> LIS(?) 를 구한다. -> 감소하면 바로 종료
            1-1-2. 오른쪽 부분 -> LIS(?) 를 구한다. -> 감소하면 바로 종료
            => 그냥 증가 수열인듯?
            => 증가도 아닌거 같음
            => 감소하다가 증가하더라도, 범위로 구분

            => 좌 우 범위를 기준으로, 특정 수보다 작아지는 부분에서 탐색 종료
            단순하게 값으로 보면 안될듯
 */
