import java.io.*;
import java.util.*;

/*
    업무 처리

*/
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int LEFT = 0;
    static final int RIGHT = 1;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine().trim());

        int height = Integer.parseInt(st.nextToken());
        int task = Integer.parseInt(st.nextToken());
        int days = Integer.parseInt(st.nextToken());

        int staffCountWithoutLeaf = (int)Math.pow(2, height) - 1;
        int leafStaffCount = (int)Math.pow(2, height);

        int startParentOfLeafStaffIndex = height == 1 ? 0 : (int)Math.pow(2, height - 1) - 1;
        int startParentOfLeafStaffCount = (int)Math.pow(2, height - 1);

        Queue<Integer>[][] staffs = new LinkedList[staffCountWithoutLeaf][2];
        Queue<Integer>[] leafStaffs = new LinkedList[leafStaffCount];

        // staffs 배열 초기화
        for (int staffIndex = 0; staffIndex < staffCountWithoutLeaf; staffIndex++) {
            staffs[staffIndex][LEFT] = new LinkedList<>();
            staffs[staffIndex][RIGHT] = new LinkedList<>();
        }

        // leafStaffs 배열 초기화
        // 말단 직원 업무 넣기
        for (int staffIndex = 0; staffIndex < leafStaffCount; staffIndex++) {
            st = new StringTokenizer(br.readLine().trim());
            leafStaffs[staffIndex] = new LinkedList<>();
            for (int taskIndex = 0; taskIndex < task; taskIndex++) {
                leafStaffs[staffIndex].add(Integer.parseInt(st.nextToken()));
            }
        }

        int result = 0;
        for (int day = 1; day <= days; day++) {

            // 홀수
            if (day % 2 == 1) {
                for (int index = 0; index < staffCountWithoutLeaf; index++) {
                    // 루트 노드일 때
                    if (index == 0) {
                        if (!staffs[index][LEFT].isEmpty()) result += staffs[index][LEFT].poll();
                    } else {
                        // 왼쪽 자식 큐를 봤을 때 값이 있으면 올린다.
                        int parentIndex = (index - 1) / 2;

                        // 체크하는 노드가 홀수이면 부모의 왼쪽 큐, 짝수이면 오른쪽 큐
                        if (!staffs[index][LEFT].isEmpty()) {
                            if (index % 2 == 1) {
                                staffs[parentIndex][LEFT].add(staffs[index][LEFT].poll());
                            } else {
                                staffs[parentIndex][RIGHT].add(staffs[index][LEFT].poll());
                            }
                        }
                    }

                }
            }
            // 짝수
            if (day % 2 == 0) {
                for (int index = 0; index < staffCountWithoutLeaf; index++) {
                    // 루트 노드일 때
                    if (index == 0) {
                        if (!staffs[index][RIGHT].isEmpty()) result += staffs[index][RIGHT].poll();
                    } else {
                        // 왼쪽 자식 큐를 봤을 때 값이 있으면 올린다.
                        int parentIndex = (index - 1) / 2;

                        // 체크하는 노드가 홀수이면 부모의 왼쪽 큐, 짝수이면 오른쪽 큐
                        if (!staffs[index][RIGHT].isEmpty()) {
                            if (index % 2 == 1) {
                                staffs[parentIndex][LEFT].add(staffs[index][RIGHT].poll());
                            } else {
                                staffs[parentIndex][RIGHT].add(staffs[index][RIGHT].poll());
                            }
                        }
                    }

                }
            }

            // 말단 직원들 task 올리기
            int parentIndex = startParentOfLeafStaffIndex;
            for (int staffIndex = 0; staffIndex < leafStaffCount; staffIndex++) {
                // 왼쪽
                if (!leafStaffs[staffIndex].isEmpty()) {
                    staffs[parentIndex][LEFT].add(leafStaffs[staffIndex].poll());
                }

                // 오른쪽
                staffIndex++;
                if (!leafStaffs[staffIndex].isEmpty()) {
                    staffs[parentIndex][RIGHT].add(leafStaffs[staffIndex].poll());
                }
                parentIndex++;
            }
        }
        System.out.println(result);
    }
}
