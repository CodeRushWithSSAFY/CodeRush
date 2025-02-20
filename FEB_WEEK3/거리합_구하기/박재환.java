import java.util.*;
import java.io.*;

// Softeer Lv.3
// [21년 재직자 대회 본선] 거리 합 구하기
// https://softeer.ai/practice/6258
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int node;
    static ArrayList<int[]>[] tree;    // 각 노드의 연결을 기록 [연결 노드, 길이]
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        init();
        // 입력 확인
//        for(int idx=1; idx<node+1; idx++) {
//            System.out.print(idx+" : ");
//            for(int[] conn : tree[idx]) {
//                System.out.print(conn[0]+" ");
//            }
//            System.out.println();
//        }

        박재환 problem = new 박재환();
        StringBuilder sb = new StringBuilder();
        long[] result = problem.allDistSum();
        for(int n=1; n<node+1; n++) {
            sb.append(result[n]).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void init() throws IOException {
        node = Integer.parseInt(br.readLine().trim());
        tree = new ArrayList[node+1];
        for(int idx=1; idx<node+1; idx++) {
            tree[idx] = new ArrayList<>();
        }

        // 간선 정보
        for(int edge=0; edge<node-1; edge++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            tree[nodeA].add(new int[] {nodeB, dist});
            tree[nodeB].add(new int[] {nodeA, dist});
        }

        br.close();
    }

    long[] subTree;
    long[] distSum;
    private long[] allDistSum() {
        subTree = new long[node+1]; // 서브트리의 크기를 저장할 배열
        distSum = new long[node+1]; // 서브트리의 크기를 저장할 배열
        dfs(1,1);
        dfs2(1,1);

        // 서브트리 크기 확인
//        for(long l : subTree) {
//            System.out.println(l);
//        }

        // 거리합 확인
//        for(long l : distSum) {
//            System.out.println(l);
//        }

        return distSum;
    }

    /*
    바텀업 방식으로
    1. 서브트리의 크기를 구한다.
    2. 각 노듸의 distSum 을 구한다 -> 📌 루트 노드를 기준으로!

    바텀업 방식으로 서브트리의 크기와 루트 노드 기준 distSum 의 크기를 구한다.
     */
    private void dfs(int curNode, int parentNode) {
        subTree[curNode] = 1;   // 자기 자신을 포함한 서브트리 크기 초기화

        for(int[] nodeConn : tree[curNode]) {   // curNode 와 연결되어 있는 Node 들을 순회한다.
            int childNode = nodeConn[0];
            int dist = nodeConn[1];

            if(childNode != parentNode) {   // 사이클 방지
                dfs(childNode, curNode);  // 자신의 자식의 서브트리와, 루트노드로의 distSum 을 계산한다.
                // 현재 노드까지의 거리합, 자식 노드까지의 거리합 -> 자식 서브트리 크기 * 자식 노드에서 현 노드까지 거리
                distSum[curNode] += distSum[childNode] + subTree[childNode]*dist;
                subTree[curNode] += subTree[childNode];
            }
        }
    }


    /*
    루트노드 기준 거리합이 아닌, 다른 노드들이 루트일 때의 거리합을 구한다.

    ⚠️ 탑 다운 방식을 사용한다.
     */
    private void dfs2(int curNode, int parentNode) {
        for(int[] nodeConn : tree[curNode]) {   // curNode 와 연결되어 있는 Node 들을 순회한다.
            int childNode = nodeConn[0];
            int dist = nodeConn[1];

            if(childNode != parentNode) {   // 사이클 방지
                // 자식 노드 = 부모노드 + (N-subtree)*dist - subtree*dist
                // => 부모노드 + (N-2*subtree) * dist
                distSum[childNode] = distSum[curNode] + dist * (node-2*subTree[childNode]);
                dfs2(childNode, curNode);  // 자신의 자식의 서브트리를 계산한다.
            }
        }
    }
}


/*
⭐ 서브트리와, 루트노드까지의 거리합을 기준으로 각 노드들의 거리합을 구할 수 있다.
 */