package APR.사이클_게임;

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
	static int nodes, edges;	// 점의 수, 진행된 차례의 수
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		nodes = Integer.parseInt(st.nextToken());
		edges = Integer.parseInt(st.nextToken());
		
		/*
		 * 사이클을 판단한다. 
		 * 부모 노드로 돌아가는 경우가 있는 경우 
		 * Union - Find ? 
		 */
		make();
		
		for(int i=1; i<edges+1; i++) {
			st = new StringTokenizer(br.readLine().trim());
			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());
			
			// 사이클 발생시, 현 순서 출력 후 종료
			if(!union(nodeA, nodeB)) {
				System.out.println(i);
				return;
			}
		}
		// 사이클을 만들지 못함
		System.out.println(0);
	}
	
	// Union - Find 구현
	static int[] parents;
	static void make() {
		parents = new int[nodes];
		for(int i=0; i<nodes; i++) {
			parents[i] = i;
		}
	}
	
	static int find(int node) {
		if(node == parents[node]) return node;
		
		return parents[node] = find(parents[node]);
	}
	
	static boolean union(int nodeA, int nodeB) {
		int rootA = find(nodeA);
		int rootB = find(nodeB);
		
		// 사이클이 발생 
		if(rootA == rootB) return false;
		
		parents[rootB] = rootA;
		return true;
	}
}

/* 
 * 선 플레이어가 홀수 번째
 * 후 플레이어가 짝수 번째
 * 
 *  0 ~ n-1 번호를 갖는 점이 평면상에 주어진다. 
 *  어느 세 점도 일직선 위에 놓이지 않는다. 
 *  
 *  플레이어는 두 점을 선택해 이를 연결하는 선분을 긋는다. 
 *  선분은 교차가 가능하다. 
 *  처음으로 사이클을 완성하는 순간 게임이 종료된다. 
 *  
 *  사이클이 완성되었는지 판단하고, 완성되었다면 몇 번째 차례에서 처음으로 완성되었는지 출력하라
 */