package APR.전화번호_목록;

import java.util.*;
import java.io.*;

public class 박재환 {
	static BufferedReader br;
	static StringBuilder sb;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine().trim());
		while(TC-- > 0) {
			init();
		}
		br.close();
		System.out.println(sb);
	}
	
	static int telListCnt;		// 전화번호 개수 
	static String[] telList;		// 전화번호부
	static void init() throws IOException {
		telListCnt = Integer.parseInt(br.readLine().trim());
		telList = new String[telListCnt];
		
		for(int i=0; i<telListCnt; i++) {
			telList[i] = br.readLine().trim();
		}
		
		// 한 번호가 다른 번호의 접두어인 경우가 없어야한다. 
		Arrays.sort(telList); // 사전순 정렬
		
		sb.append(isOk() ? "YES" : "NO").append('\n');
	}
	
	/*
	 * 전화번호 최대 10000 개 XXXXXX
	 * 완탐시 1억 -> 딱 1초
	 * 
	 * 사전 순 정렬로, 바로 뒤 원소와만 비교
	 */
	static boolean isOk() {
	    for (int i = 0; i < telListCnt - 1; i++) {
	        if (telList[i + 1].startsWith(telList[i])) return false;
	    }
	    return true;
	}
}
