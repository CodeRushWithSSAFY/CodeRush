package WEEK2.서버_증설_횟수;

import java.util.PriorityQueue;

public class 박재환 {
    public static void main(String[] args) {
        int[] players = {0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5};
        int m = 3;
        int k = 5;
        System.out.println(new Solution().solution(players, m, k));
    }

    static class Solution {
        PriorityQueue<Integer> allocatedServer;     // 증설된 서버를 관리한다. (서버가 유지되는 시간을 기록한다)
        int servers;                                // 증설된 서버의 개수
        public int solution(int[] players, int m, int k) {  // 시간대별 사용자, 서버가 감당할 수 있는 사용자, 서버 유지 시간
            servers = 0;
            allocatedServer = new PriorityQueue<>();    // 오름차순 정렬 되어 있다.

            // 시간대별로 최대 사용자의 수를 확인한다.
            for(int time=0; time<players.length; time++) {
                int player = players[time];     // 현시간 사용자

                // 1. 현재 증설된 서버로 버틸 수 있는지 확인한다.

                // 추가적인 증설이 필요한다.
                if(player/m > allocatedServer.size()) {
//                    System.out.println(time);
                    // 추가로 필요한 서버의 대수
                    int needs = player/m  - allocatedServer.size();
                    // 현재 생성되는 서버의 유지 시간
                    int holdingTime = time + k - 1;
//                    System.out.println(holdingTime);
                    for(int server=0; server < needs; server++) {
                        allocatedServer.offer(holdingTime);
                    }
                }
//                System.out.println(allocatedServer);

                // 증설이 필요 없는 경우

                // 더 이상 유지되지 않는 서버를 제거한다.
                retrieveServer(time);
            }

            return servers + allocatedServer.size();
        }

        // 증설된 서버 중, 더 이상 유지되지 않는 서버를 회수한다.
        void retrieveServer(int now) {
            // 증설된 서버가 있고, 종료되어야하는 서버라면
            while(!allocatedServer.isEmpty() && allocatedServer.peek() == now) {
                // 제거 후, 개수를 세어준다.
                allocatedServer.poll();
                servers++;
            }
        }
    }
}
/*
 * m명 늘어날 때마다 서버 1대가 추가로 필요하다.
 * 어느 시간대의 사용자가 m명 미만이라면, 서버 증설이 필요하지 않다.
 *
 * 어느 시간대의 사용자가 n*m명 이상, (n+1)*m 명 미만이라면 최소 n 대의 증설된 서버가 필요하다.
 *
 * 한 번 증설된 서버는 k 시간 동안 운영하고 그 이후에는 반납한다.
 *
 * 입력 -> 0~23시 까지의 시간대별 이용자, 서버 한 대가 감당할 수 있는 사용자, 서버 한 대가 운영 가능한 시간
 *
 * 시간대별 서버의 상태를 기록한다.
 *
 * 기록해야할 서버의 정보 -> 할당된 시간
 *
 * 첫 증설을 m 이 넘어가는 시점
 */

/*
    매 시간 1000 명의 사용자가 있음
    서버의 유지 시간은 1시간
*/

