import java.io.*;
import java.util.*;

/*
    N: 미생물들의 수 -> microCount
    a1 a2 a3 ... 이 있을 때 a1이 a2보다 작으면 a2에 흡수된다.
    흡수되는 과정은 인접한 미생물에서만 가능하고 차례대로 진행된다.
    작거나 같을 때 흡수됨!

    (1, 4) (2, 1) (3, 3) (4, 2) (5, 5)일때,
    1일차
    (1, 4) 랑 (2, 1) 인접한 두개를 비교했을 때 idx: 1이 크다. 흡수 -> (1, 5)
    (1, 5) (3, 3) (4, 2) 1은 이미 흡수했으니까 pass 하고 (3, 3) (4, 2) 비교했을 때 idx:3이 크다. 흡수 -> (3, 5)
    (3, 5) (5, 5) 3도 흡수 했으니 pass 하고 idx 5 입장에서 3과 같기 때문에 흡수 가능하다. 흡수 -> (5, 10)

    2일차
    (1, 5) (5, 10)
    idx 1은 idx 5보다 작다. 흡수 X
    idx 5는 idx 1보다 크므로 흡수 가능. 흡수 -> (5, 15)

    그럼 이러한 로직을 어떻게 풀이 할것인가?
    배열로 풀자니 흡수된 미생물들을 0으로 둔다 쳐도 여러번 탐색해야하는데 최적화를 해야하지 않을까?
    linked list로 흡수된 애들을 삭제시켜서 <앞 , >뒤로만 확인하는게 맞지 않나?
    -> remove 메서드가 O(n)의 복잡도를 갖고 있기 때문에 시간초과 났다.

    그럼 remove하기보다 다음날 것에 넣어두고 다음날 꺼를 매일매일 흡수과정을 진행해보자.

    <문제점> 흡수 한 미생물을 한번더 흡수하지 못하도록 어떻게 할 것인가?
    흡수 작용이 끝난 미생물을 다음날 것에 넣는다 해도 오늘날의 미생물쪽에서 해당 미생물을 흡수할 수 있을정도로 크다면 흡수할 수 있게 해야한다.
    왜? 하루에 자신보다 작거나 같은것들을 모두 흡수하기 때문에..
*/
public class 정한슬 {
  // 미생물을 나타내는 클래스
  static class Microbe {
    int idx;   // 미생물의 초기 위치 인덱스
    long size; // 미생물의 크기

    public Microbe(int idx, long size) {
      this.idx = idx;
      this.size = size;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int N = Integer.parseInt(br.readLine()); // 미생물의 개수

    Deque<Microbe> curDeque = new LinkedList<>(); // 현재 일차에서 처리할 미생물
    Deque<Microbe> nextDeque = new LinkedList<>(); // 다음 일차로 넘어갈 미생물

    // 초기 미생물 정보 입력 받기
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++) {
      curDeque.offerLast(new Microbe(i + 1, Long.parseLong(st.nextToken())));
    }

    // 큐에 한 개의 미생물만 남을 때까지 반복
    while (curDeque.size() > 1) {
      while (!curDeque.isEmpty()) {
        Microbe curMicrobe = curDeque.pollFirst(); // 현재 처리할 미생물
        long curSize = curMicrobe.size;

        // 다음 일차 미생물 큐에 있는 미생물이 현재 미생물보다 작은 경우 합치기
        if (!nextDeque.isEmpty() && nextDeque.peekLast().size <= curMicrobe.size) {
          Microbe lastMicrobe = nextDeque.pollLast();
          curSize += lastMicrobe.size;
        }

        // 현재 일차 미생물 큐에 있는 다음 미생물이 현재 미생물보다 작은 경우 합치기
        if (!curDeque.isEmpty() && curDeque.peekFirst().size <= curMicrobe.size) {
          Microbe firstMicrobe = curDeque.pollFirst();
          curSize += firstMicrobe.size;
        }

        // 합쳐진 결과를 다음 일차 큐에 추가
        nextDeque.addLast(new Microbe(curMicrobe.idx, curSize));
      }

      // 다음 일차 준비
      curDeque = nextDeque;
      nextDeque = new LinkedList<>();
    }

    // 최종 남은 미생물 정보 출력
    Microbe answer = curDeque.pollFirst();
    System.out.println(answer.size); // 남은 미생물의 크기
    System.out.println(answer.idx);  // 남은 미생물의 초기 인덱스
  }
}