import java.io.*;
import java.util.*;

public class 이수빈 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int busCount = Integer.parseInt(br.readLine().trim());
        int[] buses = new int[busCount];

        st = new StringTokenizer(br.readLine().trim());
        for (int index = 0; index < busCount; index++) {
            buses[index] = Integer.parseInt(st.nextToken());
        }

        long result = 0;
        int jCount = 0;

        // i, j, k -- ak < ai < aj
        // ak < ai 인 경우를 세준다
        for (int i = 0; i < busCount; i++) {
            jCount = 0;
            for (int j = i + 1; j < busCount; j++) {
                if (buses[i] < buses[j]) {
                    jCount++;
                } else {
                    result += jCount;
                }
            }
        }

        System.out.println(result);

    }
}
