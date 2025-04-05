import java.util.*;
import java.io.*;

/*
 * n + 1 개의 집합 개수가 있을 때 합집합 연산과 두 원소가 같은 집합에 포함되어있느지를 확인하는 연산을 수행하자.
 * 각 원소 1개있는 것들을 집합들로 구분해놓고, 해당 집합들을 합치고(union) 해당 집합의 부모를 찾는(find) 유니온파인드로 풀이
 * 
 * command가 0일때는 union 하고 1일때는 그 부모가 같은지(같은 집합인지) true/false 반환
 * */
public class 정한슬 {
	static final String YES = "YES";
	static final String NO = "NO";
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static BufferedWriter bw;
	
	static int vertexCount;
	static int edgeInfoCount;
	static int[] parents;
	static int[] ranks;
	
	static int command;
	static int vertex1;
	static int vertex2;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		sb = new StringBuilder();
		checkSame();
		
		br.close();
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static void checkSame() throws IOException {
		make();
		
		for (int edgeInfo = 0; edgeInfo < edgeInfoCount; edgeInfo++) {
			st = new StringTokenizer(br.readLine().trim());
			command = Integer.parseInt(st.nextToken());
			vertex1 = Integer.parseInt(st.nextToken());
			vertex2 = Integer.parseInt(st.nextToken());
			
			if (command == 0) {
				union(vertex1, vertex2);
			} else if (command == 1) {
				if (find(vertex1) == find(vertex2)) {
					sb.append(YES).append("\n");
				} else {
					sb.append(NO).append("\n");
				}
			}
		}
	}
	
	private static boolean union(int element1, int element2) {
		int element1Parent = find(element1);
		int element2Parent = find(element2);
		
		if (element1Parent == element2Parent) {
			return false;
		}
		
		if (ranks[element1Parent] > ranks[element2Parent]) {
			parents[element2Parent] = element1Parent;
			return true;
		}
		
		if (ranks[element1Parent] == ranks[element2Parent]) {
			ranks[element2Parent]++;
		}
		parents[element1Parent] = element2Parent;
		return true;
	}
	
	private static int find(int element) {
		int elementParent = parents[element];
		
		if (elementParent == element) return element;
		
		return parents[element] = find(elementParent);
	}
	
	private static void make() {
		parents = new int[vertexCount + 1];
		ranks = new int[vertexCount + 1];
		
		for (int idx = 0; idx <= vertexCount; idx++) {
			parents[idx] = idx;
			ranks[idx] = 0;
		}
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		vertexCount = Integer.parseInt(st.nextToken());
		edgeInfoCount = Integer.parseInt(st.nextToken());
	}
}
