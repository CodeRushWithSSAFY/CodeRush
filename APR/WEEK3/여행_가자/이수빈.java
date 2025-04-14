import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int cityCnt, planCnt;
    static List<Integer>[] graph;
    static Set<Integer> plan;

    static int[] parent, rank;

    public static void make() {
        parent = new int[cityCnt + 1];
        rank = new int[cityCnt + 1];
        for (int idx = 0; idx <= cityCnt; idx++) {
            parent[idx] = idx;
            rank[idx] = 1;
        }
    }

    public static int find(int element) {
        if (parent[element] == element) return element;

        return parent[element] = find(parent[element]);
    }

    public static boolean union(int e1, int e2) {
        int e1Root = find(e1);
        int e2Root = find(e2);

        if (e1Root == e2Root) return false;

        if (rank[e1Root] < rank[e2Root]) {
            parent[e1Root] = e2Root;
            return true;
        }

        parent[e2Root] = e1Root;

        if (rank[e1Root] == rank[e2Root]) {
            rank[e1Root]++;
        }
        return true;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        cityCnt = Integer.parseInt(br.readLine().trim());
        planCnt = Integer.parseInt(br.readLine().trim());

        make();
        for (int from = 1; from <= cityCnt; from++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int to = 1; to <= cityCnt; to++) {
                if (Integer.parseInt(st.nextToken()) == 1) {
                    union(from, to);
                }
            }
        }

        st = new StringTokenizer(br.readLine().trim());
        int root = find(Integer.parseInt(st.nextToken()));
        boolean flag = true;
        for (int idx = 1; idx < planCnt; idx++) {
            int city = Integer.parseInt(st.nextToken());

            if (find(city) != root) {
                flag = false;
                break;
            }
        }

        if (flag) System.out.println("YES");
        else System.out.println("NO");
    }
}
