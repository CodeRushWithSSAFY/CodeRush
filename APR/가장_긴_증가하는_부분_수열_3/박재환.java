package APR.가장_긴_증가하는_부분_수열_3;

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
	static int arrLen;				// 수열의 길이
	static int[] arr;				// 입력 수열
	static List<Integer> lisList;	// LIS 
	static void init() throws IOException {
		arrLen = Integer.parseInt(br.readLine().trim());
		arr = new int[arrLen];
		
		// 수열을 입력 받음
		st = new StringTokenizer(br.readLine().trim());
		// 입력과 동시에 LIS 생성
		lisList = new ArrayList<>();
		for(int idx=0; idx<arrLen; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
			
			// 첫 값이라면 그냥 추가만 하고 패스 
			if(idx == 0) {
				lisList.add(arr[idx]);
				continue;
			}
			
			// 이전의 LIS 가 존재한다면
			// 이전의 LIS 값의 마지막 값보다 큰 값이라면
			// 바로 추가한다.
			if(lisList.get(lisList.size()-1) < arr[idx]) {
				lisList.add(arr[idx]);
				continue;
			}
			
			// 새로운 LIS 수열을 생성해야한다면
			// 현재 원소가 들어갈 위치를 찾는다. -> 이진탐색 ( Lower Bound : 찾고자 하는 수 이상의 값 중 가장 왼쪽 값 )
			int insertIdx = findInsertIdx(arr[idx]);
			// 찾은 위치에 원소를 삽입(교체)한다.
			lisList.set(insertIdx, arr[idx]);
		}
		
		System.out.println(lisList.size());
	}
	
	static int findInsertIdx(int targetNum) {
		int l = 0, r = lisList.size()-1;
		
		while(l < r) {
			int mid = l + (r-l)/2;
			
			if(lisList.get(mid) < targetNum) l = mid+1;
			else r = mid;
		}
		
		return l;
	}
}
