import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    union-find
 */
public class Main {
    static int elementCnt;
    static int[] parent, rank;

    public static void make() {
        parent = new int[elementCnt];
        rank = new int[elementCnt];

        for (int idx = 0; idx < elementCnt; idx++) {
            parent[idx] = idx;
            rank[idx] = 0;
        }
    }

    public static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return false;

        if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
            return true;
        }

        if (rank[rootA] == rank[rootB])
            rank[rootB]++;
        parent[rootA] = rootB;
        return true;
    }

    public static int find(int element) {
        if (element == parent[element]) return element;

        return parent[element] = find(parent[element]); // 경로 압축
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine().trim());
        elementCnt = Integer.parseInt(st.nextToken()) + 1;
        int commandCnt =  Integer.parseInt(st.nextToken());

        make();
        for (int cnt = 0; cnt < commandCnt; cnt++) {
            st = new StringTokenizer(br.readLine().trim());

            int command = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (command == 0) { // union
                union(a, b);
            } else if (command == 1) { // find
                if (find(a) == find(b)) System.out.println("YES");
                else System.out.println("NO");
            }
        }
    }
}
