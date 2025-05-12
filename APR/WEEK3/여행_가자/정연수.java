import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] parent;
    
    static int find(int node) {
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
    
    static void union(int a, int b) {
        parent[find(a)] = find(b);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // N과 M이 각각 다른 줄에 있을 수 있으므로 개별적으로 읽음
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        
        parent = new int[201];
        
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }
        
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int road = Integer.parseInt(st.nextToken());
                if (road == 1) {
                    union(i, j);
                }
            }
        }
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        int firstCity = Integer.parseInt(st.nextToken());
        int rootGroup = find(firstCity);
        
        for (int i = 1; i < M; i++) {
            int nextCity = Integer.parseInt(st.nextToken());
            if (rootGroup != find(nextCity)) {
                System.out.println("NO");
                return;
            }
        }
        
        System.out.println("YES");
    }
}
