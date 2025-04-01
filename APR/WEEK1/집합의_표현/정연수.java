import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int[] parent;
	
	static int findParent(int node) {
		if (node == parent[node]) return node;
		return parent[node] = findParent(parent[node]);
	}
	
	static void union(int left, int right) {
		int leftRoot = findParent(left);
		int rightRoot = findParent(right);
		
		if (leftRoot == rightRoot) return;
		parent[leftRoot] = rightRoot;
	}
	
	static void init() {
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int nodeSize = Integer.parseInt(st.nextToken());
		int edgeSize = Integer.parseInt(st.nextToken());
		parent = new int[nodeSize + 1];
		init();
		
		for (int idx = 0; idx < edgeSize; idx++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			int left = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());

			if (cmd == 0) {
				union(left, right);
			} else {
				if (findParent(left) == findParent(right)) System.out.println("YES");
				else System.out.println("NO");
			}
		}
	}
}

