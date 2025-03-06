import java.io.*;
import java.util.*;

/*
    거리 합 구하기

    플로이드 워셜 -> n^3이므로 불가능
    n^2 도 안됨
    노드 간의 경로는 유일함..

    1. 바텀업
        1번을 루트로 하는 트리에 대해서 서브트리의 노드개수와 1번까지의 모든 거리 합을 구한다.
    2. 탑다운
        1번 외의 노드들을 루트로 만들어 모든 거리 합을 구한다.
        부모 노드의 거리합은 이미 부모노드가 루트일 때의 거리합으로 되어있음. 
        자식 노드 거리 합 = 부모 노드 거리 합 + (((부모-자식 거리) * (N - 서브트리 노드개수)) * (부모-자식거리)) - (서브트리 노드개수 * (부모-자식거리))
*/
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    
    static int N;
    static List<int[]>[] tree; // 0 : 연결 노드, 1 : 거리

    // 1번 노드가 루트일 때 기준
    static int[] nodeCntRoot1; // 각 노드에 대한 서브트리 노드 개수
    static long[] nodeDistSumRoot1; // 각 노드까지의 거리 합

    static long[] nodeDistSum; // 각각 노드가 루트일 때 거리 합

    public static void getNodeCntAndDistSum(int rootNode, int parentNode) {
        nodeCntRoot1[rootNode] = 1; // 자기 자신 세기
        
        for (int[] child : tree[rootNode]) {
            int childNode = child[0];
            long dist = child[1];

            // 노드 연결이 양방향이기 때문에 부모 노드도 연결돼있으므로 빼줘야한다.
            if (childNode == parentNode) continue;
            
            // 맨 아래 노드까지 우선 들어간다.
            getNodeCntAndDistSum(childNode, rootNode);

            // 아래에서부터 위로 올라가면서 개수 세고 
            nodeCntRoot1[rootNode] += nodeCntRoot1[childNode];
            // 거리 합 구하기
            nodeDistSumRoot1[rootNode] += nodeDistSumRoot1[childNode] + nodeCntRoot1[childNode] * dist;
        }
    }

    public static void getAllNodeDistSum(int curNode, int parentNode) {
        for (int[] child : tree[curNode]) {
            int childNode = child[0];
            long distToParent = child[1];

            if (childNode == parentNode) continue;

            long plusDist = distToParent * (N - nodeCntRoot1[childNode]);
            // curNode가 루트일 때 curNode와 childNode 사이의 dist가 nodeCnt[childNode] 개수 만큼 더해져 있으므로 빼준다.
            long minusDist = distToParent * nodeCntRoot1[childNode];  
            nodeDistSum[childNode] = nodeDistSum[curNode] + plusDist - minusDist;

            getAllNodeDistSum(childNode, curNode);
        }
    }
    
    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine().trim());
        tree = new ArrayList[N + 1];
        nodeCntRoot1 = new int[N + 1];
        nodeDistSumRoot1 = new long[N + 1];
        nodeDistSum = new long[N + 1];

        for (int i = 0; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            tree[node1].add(new int[]{node2, dist});
            tree[node2].add(new int[]{node1, dist});
        }

        getNodeCntAndDistSum(1, 1); // 루트 노드를 1번으로 하는 nodeCnt, nodeDistSum 구하기
        nodeDistSum[1] = nodeDistSumRoot1[1]; // 1번 루트의 거리합 복사
        getAllNodeDistSum(1, 1); // 1번부터 아래로 가면서 거리합 구하기

        for (int node = 1; node <= N; node++) {
            System.out.println(nodeDistSum[node]);
        }

        
    }
}
