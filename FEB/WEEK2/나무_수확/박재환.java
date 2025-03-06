import java.io.*;
import java.util.*;

public class 박재환 {

    static BufferedReader br;
    static BufferedWriter bw;
    static int mapSize;
    //    static int[][] map;
    static long[][][] map;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        박재환 problem = new 박재환();
        problem.init(); // input
        bw.write(String.valueOf(problem.findMax()));
        bw.flush();
        bw.close();
    }

    public void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        mapSize = Integer.parseInt(br.readLine().trim());
//        map = new int[mapSize][mapSize];
        map = new long[mapSize+1][mapSize+1][2]; // [x][y][0] : 해당 위치까지의 최대 누적 값, [x][y][1] : 스프링 쿨러 설치 좌표 값

        for(int x=1; x<=mapSize; x++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            for(int y=1; y<=mapSize; y++){
//                map[x][y] = Integer.parseInt(st.nextToken());
                map[x][y][0] = Integer.parseInt(st.nextToken());
                map[x][y][1] = map[x][y][0];
            }
        }
        br.close();
    }

    //    int[] dx = {0,1};
//    int[] dy = {1,0};
    public long findMax() {
        // ⚠️ 부분합을 이용한 풀이
        getRangeSum();

        return map[mapSize][mapSize][0] +
                Math.max(
                        map[mapSize-1][mapSize][0] + Math.max(map[mapSize-1][mapSize][1], map[mapSize][mapSize][1]),
                        map[mapSize][mapSize-1][0] + Math.max(map[mapSize][mapSize-1][1], map[mapSize][mapSize][1])
                );
    }

    public void getRangeSum() {
        for(int x=1; x<=mapSize; x++){
            for(int y=1; y<=mapSize; y++) {
                if(x == mapSize && y == mapSize) { // 마지막 칸 제외 탐색 완료
                    return;
                }

                if(map[x-1][y][0] > map[x][y-1][0]) {   // 위에서 내려오는 값이, 좌측에서 오는 값보다 크다면
                    map[x][y][0] += map[x-1][y][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], map[x-1][y][1]);  // 최대값을 갱신한다.
                } else if(map[x-1][y][0] < map[x][y-1][0]){ // 위에서 내려오는 값이, 좌측에서 오는 값보다 작다면
                    map[x][y][0] += map[x][y-1][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], map[x][y-1][1]);  // 최대값을 갱신한다.
                } else {
                    map[x][y][0] += map[x][y-1][0]; // 값을 더해준다.
                    map[x][y][1] = Math.max(map[x][y][1], Math.max(map[x][y-1][1], map[x-1][y][1]));  // 최대값을 갱신한다.
                }
            }
        }
    }
}
