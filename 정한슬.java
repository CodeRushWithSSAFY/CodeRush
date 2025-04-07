import java.util.*;
import java.io.*;

public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int testCase;
	static int relationCount;
	static Map<String, Integer> nameIdx;
	static String friend1Name;
	static String friend2Name;
	static StringBuilder sb;
	static int[] parents;
	static int[] ranks;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		testCase = Integer.parseInt(br.readLine().trim());
		
		sb = new StringBuilder();
		for (int test = 0; test < testCase; test++) {
			initInputForTest();
		}
		
		br.close();
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	private static int find(int element) {
		if (parents[element] == element) {
			return element;
		}
		
		return parents[element] = find(parents[element]);
	}
	
	private static int union(int element1, int element2) {
		int parent1 = find(element1);
		int parent2 = find(element2);
		
		if (parent1 == parent2) {
			return Math.max(ranks[parent1], ranks[parent2]);
		}
		
		if (ranks[parent1] > ranks[parent2]) {
			ranks[parent1] += ranks[parent2];
			parents[parent2] = parent1;
			return ranks[parent1];
		}
		
		ranks[parent2] += ranks[parent1];
		parents[parent1] = parent2;
		return ranks[parent2];
	}
	
	private static void make(int relationCount) {
		ranks = new int[relationCount];
		parents = new int[relationCount];
		
		for (int idx = 0; idx < relationCount; idx++) {
			ranks[idx] = 1;
			parents[idx] = idx;
		}
	}
	
	private static void initInputForTest() throws IOException {
		relationCount = Integer.parseInt(br.readLine().trim());
		
		make(relationCount * 2); // max값은 2배일테니까
		nameIdx = new HashMap<>();
		int friendIdx = 0;
		for (int relationIdx = 0; relationIdx < relationCount; relationIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			friend1Name = st.nextToken();
			friend2Name = st.nextToken();
			
			nameIdx.putIfAbsent(friend1Name, friendIdx++);
			nameIdx.putIfAbsent(friend2Name, friendIdx++);
			
			int friend1Idx = nameIdx.get(friend1Name);
			int friend2Idx = nameIdx.get(friend2Name);
			
			sb.append(union(friend1Idx, friend2Idx)).append("\n");
		}
	}
}
