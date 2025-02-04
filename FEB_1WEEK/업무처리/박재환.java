package FEB_1WEEK_2;


import java.io.*;
import java.util.*;

public class 박재환 {
    static Queue<Integer>[][] tasks;
    static int node = 0;
    static int leaf = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 조직도의 높이
        int h = Integer.parseInt(st.nextToken());
        // 말단에 대기중인 업무의 개수
        int k = Integer.parseInt(st.nextToken());
        // 업무가 진행되는 날짜의 수
        int r = Integer.parseInt(st.nextToken());

        // 전체 사원 수 ( 전체 노드 수 )
        node = (int) Math.pow(2, h + 1) - 1;
        // 말단 사원 수 ( 리프 노드 수 )
        leaf = (int) Math.pow(2, h);

        tasks = new Queue[node][2];
        // 배열 초기화
        for (int i = 0; i < node; i++) {
            for (int j = 0; j < 2; j++) {
                tasks[i][j] = new LinkedList<>();
            }
        }

        // 말단 사원 업무 리스트 입력
        for (int i = node - leaf; i < node; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < k; j++) {
                tasks[i][0].add(Integer.parseInt(st.nextToken()));
            }
        }

        박재환 problem = new 박재환();
        System.out.println(problem.solution(r));
    }

    long answer = 0;

    public long solution(int r) {
        int day = 1;

        while (day <= r) {
            // 홀수 왼쪽, 짝수 오른쪽
            int leftRight = day % 2 == 0 ? 1 : 0;

            // 부서장 일처리
            if (!tasks[0][leftRight].isEmpty()) {
                answer += tasks[0][leftRight].poll();
            }

            // 중간 직원들의 일처리
            for (int i = 1; i < node - leaf; i++) {
                int manager = (i - 1) / 2;

                // 해야하는 업무가 남아있다면
                if (!tasks[i][leftRight].isEmpty()) {
                    // 짝수날이라면 오른쪽 업무, 홀수 날이라면 왼쪽 업무
                    int cur = tasks[i][leftRight].poll();

                    // 왼쪽 자식이면 왼쪽에
                    if (i % 2 == 1) {
                        tasks[manager][0].offer(cur);
                    } else {
                        tasks[manager][1].offer(cur);
                    }
                }
            }

            // 하루에 말단사원이 하나씩 일을 처리한다.
            for (int i = node - leaf; i < node; i++) {
                int manager = (i - 1) / 2;

                // 해야하는 업무가 남아있다면
                if (!tasks[i][0].isEmpty()) {
                    int cur = tasks[i][0].poll();

                    // 왼쪽 자식이면 왼쪽에
                    if (i % 2 == 1) {
                        tasks[manager][0].offer(cur);
                    } else {
                        tasks[manager][1].offer(cur);
                    }
                }
            }
            day++;
        }
        return answer;
    }
}