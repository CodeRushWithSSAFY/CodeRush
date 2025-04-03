package APR.평범한_배낭;

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
    static int itemCnt, limitW;	// 물품의 수, 버틸 수 있는 무게
    static int [][] items;		// 각 물품의 [무게, 가치]
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        itemCnt = Integer.parseInt(st.nextToken());
        limitW = Integer.parseInt(st.nextToken());

        items = new int[itemCnt][2];
        for(int item=0; item<itemCnt; item++) {
            st = new StringTokenizer(br.readLine().trim());
            items[item][0] = Integer.parseInt(st.nextToken());
            items[item][1] = Integer.parseInt(st.nextToken());
        }

        getMaxItems();
    }

    static void getMaxItems() {
        int[] bag = new int[limitW+1];	// 각 배낭의 무게마다 넣을 수 있는 최대 가치 ( 1-base )


        for(int[] item : items) {
            int itemW = item[0];
            int itemV = item[1];

            for(int b=limitW; b > itemW-1; b--) {
                bag[b] = Math.max(bag[b], bag[b-itemW] + itemV);
            }
        }

        System.out.println(bag[limitW]);
    }
}
