package APR.여행_가자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static StringTokenizer st;
    static int nodes, destinations;
    static int[] parents;   // 각 집합의 루트 노드를 가리킨다.
    static void init() throws IOException {
        nodes = Integer.parseInt(br.readLine().trim());
        destinations = Integer.parseInt(br.readLine().trim());
        // 집합 생성
        make();
        // 두 집합 합치기
        for(int x=1; x<nodes+1; x++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int y =1; y<nodes+1; y++) {
                // 연결되어 있다면
                if(st.nextToken().equals("1")) {
                    union(x,y);
                }
            }
        }

        Set<Integer> set = new HashSet<>();
        st = new StringTokenizer(br.readLine().trim());
        while(destinations-- > 0) {
            set.add(find(Integer.parseInt(st.nextToken())));
        }

        System.out.println(set.size() == 1 ? "YES" : "NO");
    }

    static void make() {
        parents = new int[nodes+1];

        for(int i=0; i<nodes+1; i++) {
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
    도시가 이어져 있을 수도 없을 수도 있다.
    다른 도시를 경유해 이동할 수 있다.

    두 도시의 연결 여부를 확인한다.

 */