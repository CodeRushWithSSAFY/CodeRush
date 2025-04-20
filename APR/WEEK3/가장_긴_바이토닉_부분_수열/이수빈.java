import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int size = Integer.parseInt(br.readLine().trim());

        int[] array = new int[size];
        int[] frontDp = new int[size];
        int[] backDp = new int[size];
        st = new StringTokenizer(br.readLine().trim());
        for (int idx = 0; idx < size; idx++) {
            array[idx] = Integer.parseInt(st.nextToken());
        }

        for (int idx = 0; idx < size; idx++) {
            frontDp[idx] = 1;
            for (int prevIdx = 0; prevIdx < idx; prevIdx++) {
                if (array[idx] > array[prevIdx]) {
                    frontDp[idx] = Math.max(frontDp[idx], frontDp[prevIdx] + 1);
                }
            }
        }

        for (int idx = size - 1; idx >= 0; idx--) {
            backDp[idx] = 1;
            for (int prevIdx = size - 1; prevIdx > idx; prevIdx--) {
                if (array[idx] > array[prevIdx]) {
                    backDp[idx] = Math.max(backDp[idx], backDp[prevIdx] + 1);
                }
            }
        }

        int result = 1;
        for (int idx = 0; idx < size; idx++) {
            result = Math.max(result, frontDp[idx] + backDp[idx] - 1);
        }
        System.out.println(result);
    }
}
