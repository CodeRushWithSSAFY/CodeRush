import java.io.*;
import java.util.*;

public class 정한슬 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int H = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int R = Integer.parseInt(st.nextToken());

    int leafCount = (int) Math.pow(2, H); // 리프노드 개수
    int totalNodeCount = (int) Math.pow(2, H + 1) - 1; // 총 node 개수
    int leafNodeStartIndex = totalNodeCount - leafCount;
    Queue<Integer> [][] nodeTrees = new Queue[totalNodeCount][2]; // 각 노드 별로 오른쪽/왼쪽 자식 있음.
    for (int node = 0; node < totalNodeCount; node++) {
      for (int child = 0; child < 2; child++) {
        nodeTrees[node][child] = new ArrayDeque<>();
      }
    }

    // 처음에는 말단 직원에만 일이 존재함.
    for (int leafIndex = leafNodeStartIndex; leafIndex < totalNodeCount; leafIndex++) {
      st = new StringTokenizer(br.readLine().trim());
      for (int count = 0; count < K; count++) {
        nodeTrees[leafIndex][0].add(Integer.parseInt(st.nextToken())); // 말단 직원에게 순서대로 업무 넣기
      }
    }
    br.close();

    int day = 1; // 현재 시작 날짜
    int totalCalWork = 0;
    //        0
    //   1         2
    // 3   4    5     6
    //7 8 9 10 11 12 13 14
    while (day <= R) {
      // 조직도의 최고 노드에게 일이 주어졌을 경우
      if (day % 2 == 1 && !nodeTrees[0][0].isEmpty()) { // 홀수 날: 왼쪽 업무 먼저
        // 0입장에서는 홀수 날짜에는 1이 준 업무 먼저 처리한다.
        totalCalWork += nodeTrees[0][0].poll();
      } else if (day % 2 == 0 && !nodeTrees[0][1].isEmpty()) {
        totalCalWork += nodeTrees[0][1].poll();
      }
      // 조직도의 1번부터 6까지의 일 처리
      for (int node = 1; node < leafNodeStartIndex; node++) {
        int parentNode = (node - 1) / 2;
        if (day % 2 == 1 && !nodeTrees[node][0].isEmpty()) { // 홀수 날: 왼쪽 업무 전달
          int task = nodeTrees[node][0].poll();
          int childFlag = node % 2 == 1 ? 0 : 1; // 현재 node가 홀수이면 부모 노드의 0번째 노드(왼쪽) 짝수면 반대
          nodeTrees[parentNode][childFlag].add(task);
        } else if (day % 2 == 0 && !nodeTrees[node][1].isEmpty()) { // 짝수 날: 오른쪽 업무 전달
          int task = nodeTrees[node][1].poll();
          int childFlag = node % 2 == 1 ? 0 : 1;
          nodeTrees[parentNode][childFlag].add(task);
        }
      }
      // 말단 노드는 업무가 존재하면 바로 위에 올린다.
      for (int node = leafNodeStartIndex; node < totalNodeCount; node++) {
        int parentNode = (node - 1) / 2;
        if (!nodeTrees[node][0].isEmpty()) {
          int task = nodeTrees[node][0].poll();
          int childFlag = node % 2 == 1 ? 0 : 1; // 현재 node가 홀수이면 부모 노드의 0번째 노드(왼쪽) 짝수면 반대
          nodeTrees[parentNode][childFlag].add(task);
        }
      }
      day++;
    }
    bw.write(String.valueOf(totalCalWork));
    bw.flush();
    bw.close();
  }
}
/*
    H, K, R: 조직도의 높이, 말단에 대기하는 업무 개수, 업무가 진행되는 날짜 수
    말단 직원에 대해 대기하는 업무 순서대로
    제일 왼쪽의 말단 직원부터 순서대로

    -> 완료된 업무들의 번호 합 출력

    1. 올라온 순서대로 하나 처리
    2. 홀수 번째 날짜(1, 3, 5,..)에는 왼쪽 부하 직원이 올린 업무 처리
    3. 짝수 번째 날짜(2, 4, 6,..)에는 오른쪽 부하 직원이 올린 업무 처리
*/