import java.io.*;
import java.util.*;

class Location {
    public int y;
    public int x;

    Location(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public String toString() {
        return y + " " + x;
    }
}

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N, M;
    private static char[][] map;
    private static int[][] visit;
    private static int ghostNum;
    private static ArrayDeque<Location> personQ = new ArrayDeque<>();
    private static Location exit;
    private static int[] dy = {0, 0, -1, 1};
    private static int[] dx = {1, -1, 0, 0};
    
    public static int bfs() {
        int cnt = 0;
        while (!personQ.isEmpty()) {
            int testCnt = personQ.size();
            for (int t = 0; t < testCnt; t++) {
                Location person = personQ.poll();
                visit[person.y][person.x] = 1;
                if (map[person.y][person.x] == 'D') {
                    return cnt;
                }
                for (int i = 0; i < 4; i++) {
                    int nxtY = person.y + dy[i];
                    int nxtX = person.x + dx[i];
                    if (nxtY < 0 || N <= nxtY || nxtX < 0 || M <= nxtX || map[nxtY][nxtX] == '#' || visit[nxtY][nxtX] == 1) continue;
                    visit[nxtY][nxtX] = 1;
                    personQ.add(new Location(nxtY, nxtX));
                }                
            }
            cnt += 1;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        visit = new int[N][M];

        List<Location> ghosts = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String line = st.nextToken();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'G') {
                    ghostNum += 1;
                    ghosts.add(new Location(i, j));
                }
                if (map[i][j] == 'N') {
                    personQ.add(new Location(i, j));
                }
                if (map[i][j] == 'D') {
                    exit = new Location(i, j);
                }
            }
        }

        int distance = bfs();
        if (distance == -1) {
            System.out.println("No");
            return;
        }
        for (int i = 0; i < ghosts.size(); i++) {
            Location ghost = ghosts.get(i);
            if (Math.abs(ghost.y - exit.y) + Math.abs(ghost.x - exit.x) <= distance) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }
}
