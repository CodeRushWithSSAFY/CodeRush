import java.io.*;
import java.util.*;

public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine().trim());
        int[][] board = new int[n + 1][n + 1];
        
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 1; j <= n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] uninstall = new int[n + 1][n + 1];
        int[][] install = new int[n + 1][n + 1];
        /*
            install
            현재 위치에 스프링클러를 설치하는 경우가 더 큰지, 이전에 설치했던 경우가 더 큰지 비교

            uninstall
            스프링클러 신경 안쓰고 그냥 누적합
        */

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // 이전에 설치됐을 때
                int installTop = install[i - 1][j] + board[i][j];
                int installLeft = install[i][j - 1] + board[i][j];
                int installMax = Math.max(installTop, installLeft);
                // 현재 위치에 설치할 때
                int uninstallTop = uninstall[i - 1][j] + (board[i][j] * 2);
                int uninstallLeft = uninstall[i][j - 1] + (board[i][j] * 2);
                int uninstallMax = Math.max(uninstallTop, uninstallLeft);
                
                install[i][j] = Math.max(installMax, uninstallMax);
                uninstall[i][j] = Math.max(uninstall[i - 1][j] + board[i][j], uninstall[i][j - 1] + board[i][j]);
            }
        }

        System.out.println(install[n][n]);
        
    }
}
