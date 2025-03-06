import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N;
    private static long B;
    private static int[] computerArr;

    public static boolean check(long mid) {
        long tmp = 0;
        for (int i = 0; i < N; i++) {
            if (mid > computerArr[i]) {
                tmp += (mid - computerArr[i]) * (mid - computerArr[i]);
                if (tmp > B) {
                    return false;
                }
            } else {
                break;
            }
        }
        return true;
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        B = Long.parseLong(st.nextToken());
        computerArr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            computerArr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(computerArr);
        long min = computerArr[0];
        long max = computerArr[N - 1] + (long) Math.sqrt(B);
        long answer = 0;
        while(min <= max) {
            long mid = (min + max) / 2;
            if (check(mid) == true) {
                answer = mid;
                min = mid + 1;
            } else {
                max = mid - 1;
            }
        }
        System.out.println(answer);
    }
}

