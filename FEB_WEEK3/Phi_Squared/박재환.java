package Phi_Squared;

import java.util.*;
import java.io.*;

// Softeer Lv.3
// [한양대 HCPC 2023] Phi Squared
// https://softeer.ai/practice/7697
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        init();

        eatPhi();

        StringBuilder sb = new StringBuilder();
        sb.append(phi.peek()[1]).append('\n').append(phi.peek()[0]);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static int phiNum;    // 미생물의 수
    static ArrayDeque<long[]> phi;    // 미생물을 저장할 Deque [미생물 번호, 크기]
    private static void init() throws IOException {
        phi = new ArrayDeque<>();

        phiNum = Integer.parseInt(br.readLine().trim());

        // 미생물을 입력받아 Deque 에 저장한다.
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for(int idx=1; idx<=phiNum; idx++) {
            // 미생물의 크기를 입력 받는다.
            int phiSize = Integer.parseInt(st.nextToken());

            phi.offerLast(new long[] {idx, phiSize});
        }
        br.close();
    }

    // 미생물들을 합친다.
    private static void eatPhi() {
        while (phi.size() > 1) {    // 하나의 미생물이 남을때 까지
            // 처리한 배열을 저장할 Deque
            ArrayDeque<long[]> afterDayPhi = new ArrayDeque<>();

            while (!phi.isEmpty()) {    // 하루동안 모든 미생물들이 합쳐진다.
                // 현재 대기중인 미생물 중 가장 첫번째 미생물을 꺼낸다.
                long[] curPhi = phi.pollFirst();
                long phiIdx = curPhi[0];
                long phiSize = curPhi[1];

                // 이전 미생물이 있다면 확인
                if (!afterDayPhi.isEmpty()) {
                    long[] otherPhi = afterDayPhi.peekLast();
                    if (otherPhi[1] <= curPhi[1]) {    // 현재 미생물의 크기가 더 크다면 -> 잡아먹을 수 있다
                        afterDayPhi.pollLast();
                        phiSize += otherPhi[1];
                    }
                }

                // 다음 미생물을 확인한다.
                if (!phi.isEmpty()) {
                    long[] otherPhi = phi.peekFirst();
                    if (otherPhi[1] <= curPhi[1]) {    // 현재 미생물의 크기가 더 크다면 -> 잡아먹을 수 있다
                        phi.pollFirst();
                        phiSize += otherPhi[1];
                    }
                }

                // 합쳐진 미생물을 afterDayPhi에 추가
                afterDayPhi.offerLast(new long[] {phiIdx, phiSize});
            }

            // 처리한 후, afterDayPhi에 남은 미생물들을 phi에 업데이트
            phi = afterDayPhi;
        }
    }
}

/*
 * 조건 : 미생물이 한마리만 남을때까지
 * 1. 하루에 한 번, 줄의 맨 앞에 있는 미생물 부터, 각 미생물은 차례대로 인접한(+1,-1 ?) 미생물 중 자신보다 크기가 작거나, 같은 것을 흡수한다 -> 크기도 커진다 (합쳐진 것의 합)
 * 2. 흡수당한 미생물은 사라진다.
 *         ex) 3 2 1 - 하루 지난 뒤 > 5 1
 * 3. 흡수하는 미생물은 하루에 흡수할 모든 미생물을 한 번에 흡수한다.
 *
 *
 * ex)
 *  (1, 4) (2, 1) (3, 3) (4, 2) (5, 5)
 *  (1, 5) (5, 10)
 *  (5, 15)
 *
 *  미생물을 차례로 처리해야한다. -> 큐?
 *  큐(대기중인 미생물)하고 스택(차례가 지난 미생물)을 사용?
 *  -> 큐에 미생물이 없다? -> 하루가 끝
 *  스택에 있는 미생물을 그대로 큐에 복사?
 *  => ArrayDeque 쓰면 pop 연산으로 하나하나 빼주지 않아도 될듯한데
 */

