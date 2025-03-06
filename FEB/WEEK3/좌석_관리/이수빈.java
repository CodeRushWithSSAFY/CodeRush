import java.io.*;
import java.util.*;

/*
    좌석 관리

    새로 들어온 사람에게 안전도가 가장 높은 좌석을 배정
    안전도가 높은 좌석이 여러개라면 X 가장 낮게, Y 가장 낮게, 다 비어있다면 1,1
*/
public class 이수빈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, Q;
    static int[] visited = new int[10001]; // 0 : 아직 방문 안함, 1 : 현재 밥먹는중, 2 : 밥먹고떠남
    static int[][] seats; // -1: 불가능, 0 : 빈자리, idx : 직원
    static int[][] safety;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};


    public static boolean isEmpty() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (seats[i][j] != 0) return false;
            }
        }
        return true;
    }

    public static void changeSeat(int row, int col, int idx, int isOut) {
        seats[row][col] = idx;
        for (int i = 0; i < 4; i++) {
            int nextRow = row + dx[i];
            int nextCol = col + dy[i];
            if (nextRow < 1 || nextCol < 1 || nextRow > N || nextCol > M) continue;
            if (isOut == 1) seats[nextRow][nextCol] = 0;
            else seats[nextRow][nextCol] = -1;
        }
    }

    public static void initSafety() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                safety[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    
    public static void updateSafety() {
        initSafety();
    
        if (isEmpty()) {
            return; // 이미 위에서 0으로 초기화했으므로 바로 리턴
        }
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (seats[i][j] == 0) {
                    for (int row = 1; row <= N; row++) {
                        for (int col = 1; col <= M; col++) {
                            if (seats[row][col] > 0) {
                                int newSafety = (i - row) * (i - row) + (j - col) * (j - col);
                                safety[i][j] = Math.min(newSafety, safety[i][j]);
                            }
                        }
                    }
                }
            }
        }

    }

    public static int[] getMaxSafety() {
        int[] pos = new int[2];
        int maxSafety = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (seats[i][j] == 0) {
                    boolean flag = true;
                    // 상하좌우에 아무것도 없어야함
                    for (int k = 0; k < 4; k++) {
                        int nextRow = i + dx[k];
                        int nextCol = j + dy[k];

                        if (nextRow < 1 || nextCol < 1 || nextRow > N || nextCol > M) continue;
                        if (seats[nextRow][nextCol] > 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag && safety[i][j] > maxSafety) {
                        maxSafety = safety[i][j];
                        pos[0] = i;
                        pos[1] = j;
                    }
                }
            }
        }
        return pos;
    }

    public static int[] getSeatPos(int idx) {
        int[] pos = new int[]{0, 0};
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (seats[i][j] == idx) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }
    
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        seats = new int[N + 1][M + 1];
        safety = new int[N + 1][M + 1];
        initSafety();

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine().trim());
            String command = st.nextToken();
            int staffIdx = Integer.parseInt(st.nextToken());

            if (command.equals("In")) {
                if (visited[staffIdx] == 0) {
                    // 안전도 가장 높고, 빈 위치, 상하좌우에 아무것도 없는 위치 구하기, 없으면 (0,0) 반환
                    int[] pos = getMaxSafety();
                    
                    // 자리가 가득 찼을 때
                    if (pos[0] == 0 && pos[1] == 0) {
                        System.out.println("There are no more seats.");
                        
                    // 성공적으로 좌석에 앉았을 때
                    } else {
                        // seats 업데이트
                        changeSeat(pos[0], pos[1], staffIdx, 0);
                        // 안전도 업데이트
                        updateSafety();
                        // 사원 상태 업데이트
                        visited[staffIdx] = 1;
                        
                        System.out.println(staffIdx + " gets the seat (" + pos[0] + ", " + pos[1] + ").");
                    }
                } 
                // 현재 밥 먹고 있을 때
                else if (visited[staffIdx] == 1) System.out.println(staffIdx + " already seated.");
                // 이미 먹고 떠났을 때
                else if (visited[staffIdx] == 2) System.out.println(staffIdx + " already ate lunch.");
            }
            
            if (command.equals("Out")) {
                if (visited[staffIdx] == 1) {
                    int[] pos = getSeatPos(staffIdx);
                    // seats 업데이트
                    changeSeat(pos[0], pos[1], 0, 1);
                    // 안전도 업데이트
                    updateSafety();
                    visited[staffIdx] = 2;
                    System.out.println(staffIdx + " leaves from the seat (" + pos[0] + ", " + pos[1] + ").");
                    
                }
                else if (visited[staffIdx] == 0) System.out.println(staffIdx + " didn't eat lunch.");
                else if (visited[staffIdx] == 2) System.out.println(staffIdx + " already left seat.");
            }

        }
    }
}
