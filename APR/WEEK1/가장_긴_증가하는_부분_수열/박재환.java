package WEEK1.가장_긴_증가하는_부분_수열;

import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		init();
		br.close();
	}
	
	static int len;
	static int[] arr;
	static void init() throws IOException {
		len = Integer.parseInt(br.readLine().trim());
		
		arr = new int[len];
		st = new StringTokenizer(br.readLine().trim());
		for(int i=0; i<len; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		useDp();
	}
	
	static void useDp() {
		int[] dp = new int[len];
		
		for(int i=0; i<len; i++) {
			dp[i] = 1;	// 자기 자신
			/*
			 * i 번째 이전의 원소를 확인한다. 
			 */
			for(int j=0; j<i; j++) {
				if(arr[i] > arr[j]) {
					dp[i] = Math.max(dp[i], dp[j]+1);	// 이전 원소에서 끝나는 증가 수열의 길이 
				}
			}
		}
		
		System.out.println(Arrays.stream(dp).max().orElse(-1));
	}
}

/* 
 * 수열 A 가 주어진다. 
 * 가징 긴 증가하는 부분 수열을 구하여라 
 * LIS
 * -> 오름차순으로 증가하는 가장 긴 부분 수열 ( 서로 연속할 필요는 없다 )
 * 
 * 1. DP
 * 
 * 2. Binary Search
 */