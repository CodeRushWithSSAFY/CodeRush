package WEEK3.우수_마을;

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
	static int nodes;	// 마을의 개수
	static int[] peoples;	// 각 마을의 인원
	static boolean[][] conn;				// 각 마을이 연결되어 있는지 
	static void init() throws IOException {
		nodes = Integer.parseInt(br.readLine().trim());
		// 각 마을의 인원을 기준으로 내림차순 정렬
//		peoples = new PriorityQueue<>((a, b) -> b[1] - a[1]);
		peoples = new int[nodes];

		st = new StringTokenizer(br.readLine().trim());
		for(int idx=0; idx<nodes; idx++) {
//			peoples.offer(new int[] {idx, Integer.parseInt(st.nextToken())});
			peoples[idx] = Integer.parseInt(st.nextToken());
		}

		// 도로의 개수는 N-1
		conn = new boolean[nodes][nodes];
		for(int edge=0; edge < nodes-1; edge++) {
			st = new StringTokenizer(br.readLine().trim());
			int nodeA = Integer.parseInt(st.nextToken())-1;
			int nodeB = Integer.parseInt(st.nextToken())-1;
			
			conn[nodeA][nodeB] = true;
			conn[nodeB][nodeA] = true;
		}
		
		// 우수마을을 선정한다.
		getBestTown();
	}
	
	/*
	 * 1. 각 마을의 인원을 기준으로 내림차순한 PQ 를 사용한다. ❌
	 * 		1-1. 현재 마을 인접한 마을 중, 우수 마을이 없다면, 선택한다.
	 * 2. 트리 DP
	 * 		2-1. dp[a][0] = c : a 노드를 정검으로 하면서 a 가 우수 마을이 아닐 때, 우수 마을의 주민수의 최대값
	 * 		2-2. dp[a][1] = c : a 노드를 정점으로 하면서 a 가 우수 마을일 때, 우수 마을의 주민 수의 최대값
	 *
	 */
	static boolean[] checked;	// 처리된 마을을 기록
	static int[][] dpArr; 		// 트리 DP
	static void getBestTown() {
		checked = new boolean[nodes];
		dpArr = new int[nodes][2];	// 각 정점에서 [미포함, 포함]

		doSimulation(0);

		System.out.println(Math.max(dpArr[0][0], dpArr[0][1]));
	}

	static void doSimulation(int townIdx) {
		checked[townIdx] = true;		// 체크 처리
		dpArr[townIdx][0] = 0;			// 해당 노드가 우수 마을이 아닐 경우, 인원이 포함되지 않음
		dpArr[townIdx][1] = peoples[townIdx];			// 해당 노드가 우수 마을인 경우, 인원이 포함됨

		// 해당 마을과 연결되어 있는 마을들을 확인
		for(int idx=0; idx<nodes; idx++) {
			// 연결되어 있지 않거나, 이미 체크된 경우는 패스
			if(!conn[townIdx][idx] || checked[idx]) continue;

			doSimulation(idx);
			dpArr[townIdx][0] += Math.max(dpArr[idx][0], dpArr[idx][1]);	// 인접 마을이 우수마을이거나, 아니거나
			dpArr[townIdx][1] += dpArr[idx][0];		// 해당 마을이 우수마을이면, 인접한 마을은 우수마을이 될 수 없음
		}
	}

//	static boolean[] picked;	// 우수마을로 선정된 마을을 기록한다.
//	static void getBestTown() {
//		int maxPeople = 0;
//		picked = new boolean[nodes];
//
//		while(!peoples.isEmpty()) {
//			int[] nowTown = peoples.poll();
//			int townNo = nowTown[0];
//			int people = nowTown[1];
//
//			// 현 마을의 인접한 마을에 우수 마을이 없나 확인한다.
//			if(!checkNearTown(townNo)) continue;
//
//			// 우수마을 선정이 가능하다.
//			picked[townNo] = true;
//			maxPeople += people;
//		}
//
//		System.out.println(maxPeople);
//	}
//
//	static boolean checkNearTown(int townNo) {
//		for(int near=0; near<nodes; near++) {
//			if(near == townNo) continue;	// 자기 자신은 패스
//
//			// 인접한 마을이고, 우수마을로 선정되어있다면
//			if(conn[townNo][near] && picked[near]) return false;
//		}
//
//		return true;
//	}
}

/* 
 * N 개의 마을이 있고, 트리 구조로 이루어져 있다. 
 * 마을과 마을 사이를 잇는 N-1 개의 길이 있다. 
 * 양방향이다. 
 * 
 * 조건
 * 1. 우수 마을로 선정된 마을 주민 수의 총 합을 최대로 한다. 
 * 2. 우수 마을은 인접해있을 수 없다. 
 * 3. 우수 마을이 아닌 마을과 우수 마을은 적어도 한 개 인접해있어야한다.
 */
