import java.io.*;
import java.util.*;
import java.lang.Math.*;

/*
 * 우선 CPTI를 입력 받습니다.
 *  이때 각각의 입력을 문자열로 받은 후 0 1의 byte arr 로 바꿉니다.
 * 각각의 쌍을 만든 후 친밀감을 느끼지 않는 쌍을 xor 연산을 이용해서 구합니다.
 * 전체 만들 수 있는 개수인 (N)(N - 1)에서 친밀감을 느끼지 않는 쌍을 빼서 답을 구합니다.
 */
public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static StringTokenizer st;
    private static int N;
    private static int M;
    private static int[] cpti;

    private static int makePair() {
        int result = 0;
        for (int idxFirst = 0; idxFirst < N - 1; idxFirst++) {
            for (int idxSecond = idxFirst + 1; idxSecond < N; idxSecond++) {
                if (Integer.bitCount(cpti[idxFirst] ^ cpti[idxSecond]) <= 2) result++;
            }
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cpti = new int[N];
        
        for (int rowIdx = 0; rowIdx < N; rowIdx++) {
            String item = br.readLine();
            int cptiItem = 0;
            for (int itemIdx = 0; itemIdx < M; itemIdx++) {
                if (item.charAt(itemIdx) == '1') {
                    cptiItem += Math.pow(2, itemIdx);                
                }
            }
            cpti[rowIdx] = cptiItem;
        }
        
        System.out.println(makePair());
    }
}
