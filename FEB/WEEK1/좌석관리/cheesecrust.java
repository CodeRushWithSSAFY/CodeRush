import java.io.*;
import java.util.*;

/*
 * 먼저 사람의 배열을 만듭니다 10000
 * 명령이 들어오면 밥을 먹었는지 확인합니다.
 * 먹지 않았다면 안전한 자리에 앉힙니다. (초기의 안전한 자리는 (0,0))
 * 앉힌 후에 배열을 순회하여 가장 안전한 자리를 구합니다.(모든 맵 순회)
 * 이를 좌표로 저장
 * 위 처럼 명령을 모두 수행합니다.
 * 사원의 상태는 0: 안옴, 1: 먹는중, 2: 다 먹음이다.
 */
class Worker {
    public int y;
    public int x;
    public boolean ate;

    Worker(int y, int x) {
        this.y = y;
        this.x = x;
        this.ate = false;
    }
}

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer st;
    private static int[][] map;
    private static Map<Integer, Worker> dic = new HashMap<>();
    private static int[] dy = {-1, 1, 0, 0};
    private static int[] dx = {0, 0, -1, 1};
    private static int N, M, Q;
    
    public static void guardCheck(int y, int x, int check) throws Exception {
        for (int i = 0; i < 4; i++) {
            if (y + dy[i] < 1 || N < y + dy[i] || x + dx[i] < 1 || M < x + dx[i]) continue;
            map[y + dy[i]][x + dx[i]] += check;        
        }
    }

    public static void checkSeat(int id) throws Exception {
        int y = 1;
        int x = 1;
        int safe = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (map[i][j] != 0) continue;
                int seatSafe = Integer.MAX_VALUE;
                for (Worker worker : dic.values()) {
                    if (worker.ate == true) continue;
                    int tmp = (int) Math.pow(worker.y - i, 2) + (int) Math.pow(worker.x - j, 2);
                    seatSafe = Math.min(tmp, seatSafe);
                }
                if (seatSafe > safe) {
                    y = i;
                    x = j;
                    safe = seatSafe;
                }
            }
        }
        if (map[y][x] != 0) {
            bw.write("There are no more seats.\n");
                return;
        }
        map[y][x] = id;
        guardCheck(y, x, 1);
        dic.put(id, new Worker(y, x));
        bw.write(id + " gets the seat (" + y + ", " + x +").\n");
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        map = new int[N + 1][M + 1];
        
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            int id = Integer.parseInt(st.nextToken());
            
            if (cmd.equals("In")) {
                if (dic.get(id) == null) {
                    checkSeat(id);
                } else if (dic.get(id).ate == false) {
                    bw.write(id + " already seated.\n");                    
                } else if (dic.get(id).ate == true) {
                    bw.write(id + " already ate lunch.\n");
                }
            } else if (cmd.equals("Out")) {
                if (dic.get(id) == null) {
                    bw.write(id + " didn't eat lunch.\n");
                } else if (dic.get(id).ate == false) {
                    Worker worker = dic.get(id);
                    worker.ate = true;
                    guardCheck(worker.y, worker.x, -1);
                    map[worker.y][worker.x] = 0;
                    bw.write(id + " leaves from the seat (" + worker.y + ", " + worker.x + ").\n");
                } else if (dic.get(id).ate == true) {
                    bw.write(id + " already left seat.\n");
                }
            }
        }
        bw.flush();
        bw.close();
    }
}

