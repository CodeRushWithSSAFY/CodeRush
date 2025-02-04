import java.io.*;
import java.util.*;
import java.lang.*;

/*
 * 노드 클래스를 선언한다.
 * - 왼쪽 직원에게 올라온 업무와 오른쪽 직원에게 올라온 업무를 담는 큐 각각 할당.
 * - 말단 직원의 경우 표시를 한다.
 * - 직원 클래스에는 업무를 올리는 함수를 만든다. 큐에서 맞는 업무를 골라 제거하고 return 한다.
 * - 업무를 올리는 건 말단인 경우 비어있지 않은 곳에서 올린디. 모두 비었있을 경우 null
 * - 업무를 받는건 누가 올렸는지에 따라 판단한다.
 * 직원 배열을 만든 후 배열의 뒤에서 부터 일을 올린다.
 * 트리 구조를 배열로 만들었기 때문에
 * 배열의 (인덱스 - 1) 의 몫이 부모의 위치
 * 일은 부모부터 아래로 내려가면서 처리한다.
 */
class Employee {
    private boolean isLeaf;
    public ArrayDeque<Integer> leftQ;
    public ArrayDeque<Integer> rightQ;

    Employee(boolean isLeaf) {
        this.isLeaf = isLeaf;
        leftQ = new ArrayDeque<>();
        rightQ = new ArrayDeque<>();
    }

    Integer getWork(int date) {
        if (isLeaf) {
            if (leftQ.isEmpty() && !rightQ.isEmpty()) return rightQ.poll();
            else if (rightQ.isEmpty() && !leftQ.isEmpty()) return leftQ.poll();
            else {
                return null;
            }
        } else {
            if (date % 2 == 0) {
                if (rightQ.isEmpty()) return null;
                return rightQ.poll();
            } else {
                if (leftQ.isEmpty()) return null;
                return leftQ.poll();
            }
        }
    }

    void addWork(int who, int work) {
        if (who % 2 == 0) {
            leftQ.add(work);
        } else {
            rightQ.add(work);
        }
    }
}

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static Employee[] employeeArr;

    public static void main(String[] args) throws Exception {
        int H, K, R;
        st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        int pivot = (int) Math.pow(2, H);
        employeeArr = new Employee[pivot * 2];
        for (int i = 1; i < pivot * 2; i++) {
            if (i < pivot) {
                employeeArr[i] = new Employee(false);
            } else {
                employeeArr[i] = new Employee(true);
            }
        }

        for (int i = 0; i < pivot; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < K; j++) {
                if (i % 2 == 0) {
                    employeeArr[pivot + i].leftQ.add(Integer.parseInt(st.nextToken()));
                } else {
                    employeeArr[pivot + i].rightQ.add(Integer.parseInt(st.nextToken()));
                }
            }
        }

        int answer = 0;
        // 뒤에서 부터 일을 올린다.
        for (int date = 1; date <= R; date++) {
            for (int idx = 1; idx < pivot * 2; idx++) {
                Integer work = employeeArr[idx].getWork(date);
                if (work == null) continue;
                if (idx == 1) {
                    answer += work;
                    continue;
                }
                employeeArr[idx / 2].addWork(idx, work);
            }
        }

        System.out.println(answer);
    }
}
