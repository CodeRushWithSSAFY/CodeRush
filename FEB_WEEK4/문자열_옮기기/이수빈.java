import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 이수빈 {
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        int testCaseCnt = Integer.parseInt(br.readLine().trim());
        sb = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseCnt; testCase++) {
            String str = br.readLine().trim();
            int strLength = str.length();

            int cmdCnt = Integer.parseInt(br.readLine().trim());

            st = new StringTokenizer(br.readLine().trim());

            long startIdx = 0; // 시작 위치 인덱스 (long으로 해야함!!)
            for (int cmd = 0; cmd < cmdCnt; cmd++) {
                long num = Long.parseLong(st.nextToken());
                startIdx += num;
            }

            while (startIdx < 0) { // 음수일 때 양수가 되도록 더해줌
                startIdx += strLength;
            }
            startIdx = startIdx % strLength; // 매우 큰 수일 때 아래 반복문에서 int 범위를 넘어가기 때문에 해줘야함. 이거 안해서 틀림

            for (int idx = (int)startIdx; idx < (int)startIdx + strLength; idx++) {
                sb.append(str.charAt(idx % strLength));
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }
}