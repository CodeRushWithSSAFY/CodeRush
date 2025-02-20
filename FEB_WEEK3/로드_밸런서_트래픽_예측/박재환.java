package 로드_밸런서_트래픽_예측;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

// Softeer Lv.3
// [21년 재직자 대회 예선] 로드 밸런서 트래픽 예측
// https://softeer.ai/practice/6263
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        init();
        // 입력 확인 ✅
//        for(int server=1; server <= serversNum; server++) {
//            System.out.print(server + "번 서버 (크기 : " + servers[server].length+") : ");
//            for(int i : servers[server]) {
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }



        박재환 problem = new 박재환();
        long[] distributionTasks = problem.getDistributionTask();

        StringBuilder sb = new StringBuilder();
        for(int server=1; server <= serversNum; server++) {
            sb.append(distributionTasks[server]).append(' ');
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();

    }

    static int serversNum;  //  서버의 개수 ( N )
    static long tasks;   // 요청의 수 ( K ) - 10^18
    static int[][] servers; // 서버의 연결 관계를 저장
    private static void init() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        serversNum = Integer.parseInt(st.nextToken());
        tasks = Long.parseLong(st.nextToken());

        servers = new int[serversNum+1][];  // 인덱스 접근 통일을 위해 +1 크기로 설정함
        for(int server=1; server <= serversNum; server++) {
            st = new StringTokenizer(br.readLine().trim());

            int connSize = Integer.parseInt(st.nextToken());
            int[] connArr = new int[connSize];
            for(int conn=0; conn < connSize; conn++) {
                connArr[conn] = Integer.parseInt(st.nextToken());
            }
            servers[server] = connArr;
        }
    }

    // 위상정렬을 사용하여 상위노드부터, 하위노드로 업무를 분배한다.
    private long[] getDistributionTask() {    // 각 노드별로 분포되어있는 업무를 가져온다.
        // 노드로 들어오는 간선의 수를 구한다.
        calcInEdge();
        // inEdge 확인 ✅`
//        for(int server=1; server<=serversNum; server++) {
//            System.out.print(inEdge[server] + " ");
//        }
        // 위상 정렬 수행
        int[] sortedServer = sortByInEdge();
        // 위상정렬 확인 ✅
//        for(int server : sortedServer) {
//            System.out.print(server+" ");
//        }


        long[] distributionTasks = new long[serversNum+1];  // 분배되어 있는 업무 상태를 저장할 배열
        distributionTasks[1] = tasks;   // 루트 노드로 모든 업무가 요청된다.
        for(int server : sortedServer) {    // 상위노드부터 하위노드로 업무를 분배한다.
            int connSize = servers[server].length; // 현재 노드와 연결되어 있는 자식 노드의 수를 가져온다.

            if(connSize == 0) continue; // 더 이상 업무를 분배할 노드가 없음

            // 업무를 분배할 노드가 있음
            // 각 자식들에게 균등하게 분배한다.
            // 남은 업무는 RR 방식을 사용해 순서대로 부여한다.
            long div = distributionTasks[server] / connSize;    // 균등하게 분배될 업무의 양
            long remain = distributionTasks[server] % connSize; // RR 방식으로 분배될 업무의 양

            // 1. 균등하게 분배한다.
            for(int child : servers[server]) {  // 각 자식노드들에게 업무를 균등하게 분배한다.
                distributionTasks[child] += div;
            }

            // 2. 남은 업무를 RR 방식을 사용하여 분배한다.
            for(int r=0; r<remain; r++) {
                distributionTasks[servers[server][r]]++;
            }
        }

        // 업무 분배 확인 ✅
//        for(int server=1; server<=serversNum; server++) {
//            System.out.print(distributionTasks[server]+" ");
//        }
        return distributionTasks;
    }


    // 1. 노드로 들어오는 간선의 수를 구한다.
    int[] inEdge;
    private void calcInEdge() {
        inEdge = new int[serversNum+1];

        for(int server=1; server <= serversNum; server++) { // 각 서버에 연결되어 있는 자식 노드를 조회한다.
            for(int child : servers[server]) {
                inEdge[child]++;
            }
        }
    }

    // 2. inEdge 를 바탕으로 위상정렬을 수행한다.
    private int[] sortByInEdge() {
        Stack<Integer> st = new Stack<>();

        for(int server=1; server<=serversNum; server++) {   // 모든 서버를 조회하며, inEdge 가 0 인 요소를 찾는다.
            // 들어오는 간선의 개수가 0인 것이 우선순위를 갖는다.
            if(inEdge[server] == 0) {
                st.push(server);
            }
        }

        int idx = 0;
        int[] sortedServer = new int[serversNum];   // 정렬된 서버를 저장하는 배열
        // 우선순위가 높은 것부터 처리한다.
        while(!st.isEmpty()) {
            int nowServer = st.pop();
            // 값이 0인 서버를 우선적으로 배치한다. -> 루트 노드이다.
            sortedServer[idx++] = nowServer;

            for(int child : servers[nowServer]) {   // 현재 서버와 연결된 자식 서버의 inEdge 수를 1 줄여준다
                inEdge[child]--;

                if(inEdge[child] == 0) {    // 들어오는 간선의 개수가 0이 된다면 Stack 에 삽입한다.
                    st.push(child);
                }
            }
        }

        return sortedServer;
    }
}

/*
총 N 개의 서버가 있다.
호드 밸런서와 워커 노드 모두 서버라고 지칭
하나의 서버가 로드 밸런서이면서 워커 노드일 수 없음 -> 한번에 한개만 ㅇㅇ

i 번쩨 서버가 로드 밸런서라면 중복을 포함하여 r[i] 개 서버로 트래픽을 분산한다.
r[i] = 0 -> i 번째는 워커노드로 료청을 직접 처리 / 1 이상이라면 로드밸런서로 RR 방식으로 트래픽 분산
-> 라운드 로빈이면 Queue 혹은 수식이 있는 듯
-> 제한 범위가 너무 커서 수식일듯? - 상수시간으로 접근?

각 로드밸런서에는 x[i] 변수가 있음. x[i] 는 처음에 1
=> 로드밸런서로 요청 1개 들어옴 -> p[i] x[i] 서버로 트래픽 전달 -> x[i] = x[i]%r[i]+1 -> 해당 수식은 그냥 다음 서버 가리키는 듯?
1번 서버는 루트 로드 밸런서로,  모든 요청은 여기로 옴

트래픽 분산 규칙
i -> p[i][j] 간선들로 구성된 그래프에서 사이클 X == 1 번 서버로 많은 요청 -> 모든 서버로 적어도 1개 요청 전달

총 K 개의 요청 -> 각 서버로 들어오는 요청의 개수가 몇 개?

제약조건
2 ≤ N ≤ 100,000
1 ≤ K ≤ 10^18
r1 + r2 + ... + rN ≤ 500,000 이다.
 */

/*
📌 위상 정렬 사용
 */