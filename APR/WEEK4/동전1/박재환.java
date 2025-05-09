package WEEK4.동전1;

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
    static int coinCnt, targetPrice;    // 동전의 종류, 목표 금액
    static int[] coins;                 // 각 동전의 가치
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        coinCnt = Integer.parseInt(st.nextToken());
        targetPrice = Integer.parseInt(st.nextToken());

        coins = new int[coinCnt];
        for(int i=0; i<coinCnt; i++) {
            coins[i] = Integer.parseInt(br.readLine().trim());
        }

        getAllScenario();
    }

    // 목표 금액을 만들 수 있는 모든 경우의 수를 구한다.
    static void getAllScenario() {
        int[] dpArr = new int[targetPrice+1];
        dpArr[0] = 1;   // 0 원을 만드는 경우는 1가지

        for(int coin : coins) {
            for(int v=coin; v<targetPrice+1; v++) {
                if(dpArr[v] < dpArr[v] + dpArr[v-coin]) {
                    dpArr[v] += dpArr[v-coin];
                }
            }
        }

        System.out.println(dpArr[targetPrice]);
    }
}

/*
    N 가지 종류의 동전이 있다.
    각 동전을 적당히 사용해 K 원을 만들고 싶다.

    이때 만들 수 있는 동전의 경우수는?
 */
