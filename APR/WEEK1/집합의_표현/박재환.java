package WEEK1.집합의_표현;

import java.util.*;
import java.io.*;

public class 박재환 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		init();
		
		br.close();
		
		System.out.println(sb);
	}
	
	static int num, commands;
	static int[] parents;	// 각 집합의 대표 노드를 가리킨다. 
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		num = Integer.parseInt(st.nextToken());
		commands = Integer.parseInt(st.nextToken());
		
		make();
		while(commands-- > 0) {
			st = new StringTokenizer(br.readLine().trim());
			
			int action = Integer.parseInt(st.nextToken());
			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());
			
			switch(action) {
				case 0 : {	// 합집합
					union(nodeA, nodeB);
					break;
				}
				case 1 : {	// 같은 집합인지 체크
					sb.append(find(nodeA) == find(nodeB) ? "YES" : "NO").append('\n');
				}
			}
			
		}
		
	}

	static void make() {
		parents = new int[num+1];
		for(int i=0; i<num+1; i++) {
			parents[i] = i;
		}
	}
	
	static int find(int node) {
		if(parents[node] == node) return node;
		
		return parents[node] = find(parents[node]);
	}
	
	static boolean union(int nodeA, int nodeB) {
		int rootA = find(nodeA);
		int rootB = find(nodeB);
		
		if(rootA == rootB) return false;
		
		parents[rootB] = rootA;
		return true;
	}
}

/* 
 * 0 ~ n 개의 집합이 있다. 
 * 합집합 연산과, 두 원소가 같은 집합에 있는지 확인하는 연산을 수행하려 한다.
 * -> Union Find 
 */