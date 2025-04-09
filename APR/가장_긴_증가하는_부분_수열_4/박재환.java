package APR.가장_긴_증가하는_부분_수열_4;

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
	static int arrLen;
	static int[] arr;
	static void init() throws IOException {
		arrLen = Integer.parseInt(br.readLine().trim());
		arr = new int[arrLen];
		
		st = new StringTokenizer(br.readLine().trim());
		for(int idx=0; idx<arrLen; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}
		
		makeLIS();
	}
	
	/*
	 * LIS 의 길이를 만든다.
	 * 		이때, 각 원소에서 LIS 의 길이를 저장한다. 
	 */
	static void makeLIS() {
		// LIS 수열의 최대 길이를 기록할 리스트
		List<Integer> lisList = new ArrayList<Integer>();
		// 각 원소의 위치에서 LIS 의 최대 길이를 기록 
		int[] lisArr = new int[arrLen];
		
		// 초기 설정
		lisList.add(arr[0]);
		lisArr[0] = 1;
		
		for(int idx=1; idx<arrLen; idx++) {
			// 1. 현재 원소가 LIS 의 끝에 들어갈 수 있는 경우
			if(lisList.get(lisList.size()-1) < arr[idx]) {
				// LIS 끝에 현재 원소를 삽입하고, 현 위치에서의 길이를 기록한다.
				lisList.add(arr[idx]);
				lisArr[idx] = lisList.size();
			} else {	// 2. 새로운 LIS 수열을 생성해야함 
				// 2-1. Binary Search 를 통해 현재 원소가 삽입될 위치를 찾음 
				//			현재 원소보다 큰 가장 왼쪽의 원소 
				int insertIdx = findInsertIdx(lisList, arr[idx]);
				
				// 2-1. 원소를 해당 위치에 삽입하고, 길이를 갱신
				//			이때 길이는 새로운 LIS 수열이므로, 삽입 위치 + 1  로 갱신
				lisList.set(insertIdx, arr[idx]);
				lisArr[idx] = insertIdx+1;
			}
		}
		
		// LIS 의 길이 입력
		sb.append(lisList.size()).append('\n');
		
		// 생성된 LIS 를 기준으로 원소를 역추적 
		traceItem(lisArr, lisList.size());
	}
	
	static void traceItem(int[] lisArr, int size) {
		// 역순으로 찾아지므로 Stack 사용 
		Stack<Integer> st = new Stack<>();
		for(int idx=arrLen-1; idx > -1; idx--) {
			if(lisArr[idx] == size) {
				st.push(arr[idx]);
				size--;
			}
		}
		
		while(!st.isEmpty()) {
			sb.append(st.pop()).append(' ');
		}
	}
	
	// BinarySearch -> 현재 원소가 들어갈 위치를 찾음 ( Lower Bound : 현재 원소 이하의 가장 첫 번째 위치 )
	static int findInsertIdx(List<Integer> list, int num) {
		// 초기 탐색 범위를 설정 
		int l = 0, r = list.size()-1;
		
		while(l < r) {
			// 탐색 범위의 중간 위치를 찾음 
			int mid = l + (r-l)/2;
			
			// 찾으려는 수가 오른쪽에 있다면 
			if(list.get(mid) < num) {
				l = mid+1;
			} else {	// 찾으려는 수가 왼쪽에 있다면
				r = mid;
			}
		}
		return l;
	}
}
