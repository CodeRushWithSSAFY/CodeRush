import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N;
    private static long K;
    private static int[][] graph;
    private static int[] nodeIn;
    private static int[] sortedServer;
    private static long[] answer;

    public static void topology() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(1);
        int index = 0;
        sortedServer = new int[N];
        while(!q.isEmpty()) {
            int current = q.poll();
            sortedServer[index] = current;
            index++;
            for (int i = 0; i < graph[current].length; i++) {
                int target = graph[current][i];
                nodeIn[target] -= 1;
                if (nodeIn[target] == 0) q.add(target);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Long.parseLong(st.nextToken());
        graph = new int[N + 1][];
        nodeIn = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            graph[i] = new int[r];
            for (int j = 0; j < r; j++) {
                int target = Integer.parseInt(st.nextToken());
                graph[i][j] = target;
                nodeIn[target]++;
            }
        }
        topology();
        answer = new long[N + 1];
        answer[1] = K;
        for (int i = 0; i < N; i++) {
            int current = sortedServer[i];
            if (graph[current].length == 0) continue;
            long distribute = answer[current] / (long) graph[current].length;
            long moduler = answer[current] % graph[current].length;
            for (int j = 0; j < graph[current].length; j++) {
                int target = graph[current][j];
                answer[target] += distribute;
            }
            for (int j = 0; j < moduler; j++) {
                int target = graph[current][j];
                answer[target] += 1;
            }
        }
        for (int i = 1; i <= N; i++) {
            System.out.print(answer[i] + " ");            
        }
    }
}
