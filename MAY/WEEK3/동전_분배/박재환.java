package MAY.WEEK3.동전_분배;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		// 3 번의 입력이 주어진다.
		for(int i=0; i<3; i++) {
    		init();
		}
		br.close();
	}
	
	static StringTokenizer st;
	static int coinCnt;         // 동전의 분류 
	static int[][] coins;       // 동전 [분류, 개수]
	static int totalPrice;      // 동전으로 만들 수 있는 최대 가치
	static void init() throws IOException {
	    totalPrice = 0;
	    
	    coinCnt = Integer.parseInt(br.readLine().trim());
	    coins = new int[coinCnt][];
	    
	    for(int i=0; i<coinCnt; i++) {
	        st = new StringTokenizer(br.readLine().trim());
	        int coin = Integer.parseInt(st.nextToken());
	        int cnt = Integer.parseInt(st.nextToken());
	        totalPrice += (coin * cnt); 
	        coins[i] = new int[] {coin, cnt};
	    }
	    
	    canDivide();
	}
	
	/*
	    전체 금액의 절반을 만들 수 있다 
	    -> 동전을 똑같이 반으로 만들 수 있다.
	    
	    [i][j]
	    => i 번째 종류의 동전을 사용해서, j 금액을 만들 수 있다?
	*/
	static void canDivide() {
	    // 1. 총 금액이 홀수라면 애초에 똑같이 나눌 수 없다.
	    if(totalPrice % 2 == 1) {
	        System.out.println(0);
	        return;
	    }
	    
	    // 2. 총 금액을 똑같이 나눌 수 있다. 
	    totalPrice /= 2;
	    int[][] dp = new int[coinCnt+1][totalPrice+1];
	    dp[0][0] = 1;   // 초기 설정. -> 동전을 하나도 사용하지 않고, 0 원을 만들 수 있있다
	    
	    for(int i=1; i<coinCnt+1; i++) {
	        for(int j=0; j<totalPrice+1; j++) {
                // 2-1. i-1 번째 동전만을 사용해서 현재 금액을 만들 수 있다면
	            if(dp[i-1][j] == 1) {    
	                // 2-2. i 번째 동전을 사용하여 만들 수 있는 금액을 체크한다.
                    for(int k=0; k<coins[i-1][1]+1; k++) {
                        int makePrice = coins[i-1][0]*k+j;
                        if(totalPrice >= makePrice) {
                            dp[i][makePrice] = 1;
                        }
                    }                
	            }
	        }
	    }
	    
	     System.out.println(dp[coinCnt][totalPrice] == 1 ? 1 : 0);
	}
}
