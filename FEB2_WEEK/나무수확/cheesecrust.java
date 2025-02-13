import java.io.*;
import java.util.*;

/*
    각 자리마다 스프링 쿨러를 설치 하였는지에 대한 dp
    설치한 경우를 가정한 배열과 설치하지 않았을때의 최댓값을 구한다.
    각 지점에서 최선의 경로가 다음 지점에서의 최선의 경로가 아닐 수 있기 때문에 나누어서 계산해야합니다.
 */
public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N;
    private static int[][] map;
    private static int[][] install;
    private static int[][] uninstall;
    private static int[] dy = {1, 0};
    private static int[] dx = {0, 1};

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        map = new int[N + 1][N + 1];
        install = new int[N + 1][N + 1];
        uninstall = new int[N + 1][N + 1];
        
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                install[i][j] = Math.max(Math.max(uninstall[i - 1][j], uninstall[i][j - 1]) + map[i][j] * 2, Math.max(install[i - 1][j], install[i][j - 1]) + map[i][j]);
                uninstall[i][j] = Math.max(uninstall[i - 1][j], uninstall[i][j - 1]) + map[i][j];
            }
        }
        System.out.println(install[N][N]);
    }
}
