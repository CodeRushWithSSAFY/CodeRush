package WEEK3.지게차와_크레인;

import java.util.LinkedList;
import java.util.Queue;

public class 박재환 {
    public static void main(String[] args) {
//        String[] storage = {"AZWQY", "CAABX", "BBDDA", "ACACA"};
        String[] storage = {"HAH", "HBH", "HHH", "HAH", "HBH"};
//        String[] requests = {"A", "BB", "A"};
        String[] requests = {"C", "B", "B", "B", "B", "H"};

        System.out.println(new Solution().solution(storage, requests));
    }

    static class Solution {
        char[][] storageCopy;   // 외부 영역을 나타내는 배열
        int allItems;           // 모든 화물의 수를 나타낸다.
        int height, width;      // 주어지는 격자의 크기
        public int solution(String[] storage, String[] requests) {
            height = storage.length;
            width = storage[0].length();
            // 2차원 배열 생성
            copy(storage);

            // char 배열의 초기 값은 0
//            System.out.println(storageCopy[0][0]==0);

            // 2차원 배열 확인
//            for (char[] arr : storageCopy) {
//                System.out.println(Arrays.toString(arr));
//            }
//            System.out.println();

            // 명령어 수행
            doRequest(requests);

            // 작업 이후 2차원 배열 확인
//            for (char[] arr : storageCopy) {
//                System.out.println(Arrays.toString(arr));
//            }

            return allItems;
        }

        /*
            명령어를 순서대로 수행한다.
            알파벳 한개 -> 지게차
            알파벳 두개 -> 크레인
        */
        void doRequest(String[] requests) {
            for (String request : requests) {
                char c = request.charAt(0); // 요청된 화물

                if (request.length() == 1) { // 지게차 요청
                    forkLift(c);
                } else {    // 크레인 요청
                    crane(c);
                }
            }
        }

        /*
            지게차로 물건을 빼낸다.
            외부와 연결되어 있는 위치의 물건들만 빼낼 수 있다.

            BFS 를 사용하여 0,0 위치부터 탐색하여 방문 가능한 모든 위치를 탐색한다.
            -> 자연스럽게 외부에서 이어지는 공간만 탐색이 가능하다.
         */
        void forkLift(char request) {
            // 접근이 가능한 구역은 상하좌우만 탐색한다.
            int[] dx = {0,1,0,-1};
            int[] dy = {1,0,-1,0};

            Queue<int[]> route = new LinkedList<>();    // 방문할 위치들을 기록해 놓을 큐
            boolean[][] visited = new boolean[height+2][width+2];   // 중복 탐지를 위한 배열

            // 탐색 시작 위치 설정
            route.offer(new int[] {0,0});
            visited[0][0] = true;

            while(!route.isEmpty()) {
                int[] curPoint = route.poll();
                int x = curPoint[0];
                int y = curPoint[1];

                for(int dir=0; dir<4; dir++) {
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];

                    // 범위를 벗어나는 경우
                    if(nx < 0 || ny < 0 || nx >=height+2 || ny >= width+2) continue;
                    // 이미 방문한 구역인 경우
                    if(visited[nx][ny]) continue;

                    // 방문 가능한 구역인 경우

                    // 1. 빈 공간인 경우 이동
                    if(storageCopy[nx][ny] == 0) {
                        visited[nx][ny] = true;
                        route.offer(new int[] {nx, ny});
                    } else if(storageCopy[nx][ny] == request) { // 2. 찾고자하는 물건의 경우
                        // 연달아 있는 물건에 대해서는 처리하지 않는다.
                        visited[nx][ny] = true;
                        storageCopy[nx][ny] = 0;
                        allItems--;
                    }
                }
            }
        }

        /*
            크레인으로 물건들을 빼낸다.
            크레인은 원하는 물건을 위치에 상관없이 뺄 수 있다.
         */
        void crane(char request) {
            // 외부 영역을 제외한 영역 탐색
            for (int x = 1; x < storageCopy.length - 1; x++) {
                for (int y = 1; y < storageCopy[x].length - 1; y++) {
                    // 찾고자하는 화물이 아니라면
                    if (storageCopy[x][y] != request) continue;

                    // 찾고자 하는 화물이라면 외부 영역으로 처리
                    storageCopy[x][y] = 0;
                    // 전체 개수에서 줄여줌
                    allItems--;
                }
            }
        }

        /*
            전달 받은 storage 배열을 2차원 배열 형태로 생성한다.
        */
        void copy(String[] storage) {
            allItems = height * width;

            storageCopy = new char[height + 2][width + 2];   // 외부 영역을 나타내기 위해 배열을 한번 더 감싼다.

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    storageCopy[x + 1][y + 1] = storage[x].charAt(y);
                }
            }

        }
    }
}

/*
    N x M 개의 컨테이너가 있다.
    특정 종류 컨테이너의 출고 요청이 들어올 때마다
    창고에서 접근이 가능한 해당 종류의 컨테이너를 모두 꺼낸다.

    접근이 가능하다 -> 4면중 1면이 창고 외부와 연결되어있다.
    (탐색은 상하좌우로 실시한다.)

    창고 외부와 연결되지 않은 컨테이너도 꺼낸다. (왜?)
    -> 요청한 종류의 모든 컨테이너를 꺼낸다.

    알파벳 하나로 들어오는 요청은 지게차 사용
    알파벳 두 개로 들어오는 경우는 크레인 사용 (모든 컨테이너 꺼냄)
*/
