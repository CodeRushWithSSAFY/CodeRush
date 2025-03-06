package 출퇴근길;

import java.util.*;
import java.io.*;

// Softeer Lv.3
// [HSAT 6회 정기 코딩 인증평가 기출] 출퇴근길
// https://softeer.ai/practice/6248
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        init();

        // 출퇴근길에 중복되는 노드를 탐색한다.
        int duplicateNodes = getDuplicateNodes();

        bw.write(String.valueOf(duplicateNodes));
        bw.flush();
        bw.close();
    }

    /*
     * route 는 정방향 그래프로 출발지에서 방문 가능한 노드를 선택할 때 사용한다.
     * reverseRoute 는 역방향 그래프로, 중간 노드에서 도착지까지 방문이 가능한지 검증하기 위해 사용한다.
     */
    static List<Integer>[] route;    // 각 노드에서 이동할 수 있는 연결 관계를 저장한다.
    static List<Integer>[] reverseRoute;    // 각 노드에서 이동할 수 있는 연결 관계를 역방향으로 저장한다.
    static int nodeNums, edgeNums;    // 노드의 수, 간선의 개수
    static int home, office;    // 집, 회사
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        nodeNums = Integer.parseInt(st.nextToken());
        edgeNums= Integer.parseInt(st.nextToken());

        route = new ArrayList[nodeNums+1];
        reverseRoute = new ArrayList[nodeNums+1];
        for(int node=0; node<=nodeNums; node++) {
            route[node] = new ArrayList<>();
            reverseRoute[node] = new ArrayList<>();
        }

        // 연결관계 입력
        for(int edge=0; edge<edgeNums; edge++) {
            st = new StringTokenizer(br.readLine().trim());

            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            // 정방향 그래프 연결
            route[nodeA].add(nodeB);
            // 역방향 그래프 연결
            reverseRoute[nodeB].add(nodeA);
        }

        // 집, 회사 입력
        st = new StringTokenizer(br.readLine().trim());
        home = Integer.parseInt(st.nextToken());
        office = Integer.parseInt(st.nextToken());

        br.close();
    }

    // 중복 노드의 개수를 탐색한다.
    /*
     * 이동가능한 모든 노드를 탐색한다.
     * 정방향 그래프, 역방향 그래프에 대하여 2번씩 탐색한다.
     * 1. 정방향 그래프 (출발지 -> 목적지)
     *         이동중에 방문 가능한 모든 노드를 탐색한다.
     * 2. 역방향 그래프 (목적지 -> 출발지)
     *         이동중에 방문 가능한 모든 노드를 탐색한다.
     * => 이는 출발지에서 이동 가능한 중간 노드들, 도착지까지 이동가능한 중간 노드들을 탐색한다.
     */
    private static int getDuplicateNodes() {
        // 각 탐색에서 방문가능한 노드를 기록하기 위해여 boolean 배열을 생성한다.

        // 집에서 회사까지 방문 가능한 노드들
        boolean[] homeToOffice = new boolean[nodeNums+1];
        homeToOffice[office] = true;
        findAllRoute(home, route, homeToOffice);

        // 회사까지 방문 가능한 중간 노드들
        boolean[] midToOffice = new boolean[nodeNums+1];
        findAllRoute(office, reverseRoute, midToOffice);

        // 회사에서 집까지 방문 가능한 노드들
        boolean[] officeToHome= new boolean[nodeNums+1];
        officeToHome[home] = true;
        findAllRoute(office, route, officeToHome);

        // 집까지 방문 가능한 중간 노드들
        boolean[] midToHome= new boolean[nodeNums+1];
        findAllRoute(home, reverseRoute, midToHome);

        return checkDuplicateNodes(homeToOffice, midToOffice, officeToHome, midToHome);
    }

    /*
     * (현 위치, 목적지, 탐색에 사용할 그래프, 방문처리 배열)
     * 각 탐색마다 사용할 그래프(정방향, 역방향)이 다르고, 방문처리 배열이 달라야하기 때문에, 파라미터로 받았습니다
     */
    private static void findAllRoute(int curNode, List<Integer>[] searchRoute, boolean[] visitedNode) {
        /*
        ⚠️ 해당 기저조건을 사용하지 않는 이유
        문제에서 요구하는 조건인 출근길과 퇴근길 모두 거쳐갈 수 있는 경로를 구한다.

        풀이법에서 사용한
        1. 정방향 그래프 (출발지 -> 목적지)
                이동중에 방문 가능한 모든 노드를 탐색한다.
        2. 역방향 그래프 (목적지 -> 출발지)
                이동중에 방문 가능한 모든 노드를 탐색한다.
        방법을 사용하기 위해서는 목적지에 도착 후 종료하게 되면, 정확한 탐색을 할 수 없다.

        예를 들어 집 -> 회사 의 경로를 구할 때 회사에 도착 후 함수를 종료시켜버린다면
        집에서 회사까지의 모든 노드를 구하는 것은 맞지만, 회사에서 이동 가능한 노드는 제외되게 된다.
        이는 마지막에서 모든 노드의 방문처리를 확인할 때 정확하지 않는 결과를 초래한다.
         */
//        if(curNode == destination) {    // 목적지에 도착한 경우
//            return;    // 종료
//        }

        if(visitedNode[curNode]) {    // 이미 방문한 노드라면
            // 더 이상 탐색할 필요가 없다
            return;
        }

        // 현재 노드를 방문처리한다.
        visitedNode[curNode] = true;

        // 현재 노드와 연결되어 있는 노드들을 탐색한다.
        for(int connNode : searchRoute[curNode]) {
            findAllRoute(connNode, searchRoute, visitedNode);
        }

        // 해제처리는 하지 않는다 -> 양방향 연결이 아니기때문에!
    }

    // 모든 탐색에 사용된 노드의 개수를 반환한다.
    // 이때 집과, 회사는 제외한다.
    private static int checkDuplicateNodes(boolean[] homeToOffice, boolean[] midToOffice, boolean[] officeToHome, boolean[] midToHome) {
        int duplicateNodes = 0;

        for(int node=1; node<=nodeNums; node++) {
            if(node == home || node == office) continue;    // 집과 회사는 제외한다.

            // 모든 탐색에 사용된 노드라면
            if(homeToOffice[node] && midToOffice[node] && officeToHome[node] && midToHome[node]) duplicateNodes++;
        }

        return duplicateNodes;
    }
}
/*
출퇴근길을 단방향 그래프이다.

집 S, 회사 T

목적지의 정점을 방문하고 나면, 더이상 움직이지 않음
-> 출근길에 T 는 마지막에 한 번 등장
-> 퇴근길에 S 또한 마지막에 한 번 등장

출퇴근길에 모두 방문하는 노드의 개수는 ?

풀이 방법
1. DFS를 사용한다.
    a. 출발지에서 목적지까지의 경로를 구한다.
        - 목적지까지의 경로를 기록한다.
        - 목적지까지 도착하는 경우만 기록한다? -> 뒤로 돌아가지 못하는경우가 있으니까?
    b. 위와 동일
    c. 이후 모두 사용되는 노드를 찾는다.

    => 문제점
        1. 목적지가 아닌 지점으로는 돌아와서 탐색이 가능 -> 중복처리가 애매함
            -> 예외처리를 해야하나?

문제를 풀어서 생각하면
출발지 -> 중간 노드 -> 도착지
도착지 -> 중간 노드 -> 출발지
이걸 구해야한다.

즉 출발지에서 -> 중간 노드를 갈 수 있는지 + 중간 노드에서 도착지를 갈 수 있는지

 */
