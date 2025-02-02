import java.io.*;
import java.util.*;

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static List<Integer> process = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            process.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(process);
        System.out.println(process.get(N - 1) + (N - 1));
    }
}
