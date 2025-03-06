import java.io.*;
import java.util.*;

/*

    i, j, k
    3중 포문 하면 1250억번 연산,,
    2번만에 풀 수 있다..?
    n^2 + n n(n + 1);
    두개를 선택하면, 된다.
    i < j < k
    ai < aj
    ai > ak
    ak < ai < aj

*/
public class 정한슬 {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    int N = Integer.parseInt(br.readLine().trim());
    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int[] busArr = new int[N];
    for (int i = 0; i < N; i++) {
      busArr[i] = Integer.parseInt(st.nextToken());
    }

    br.close();
    long answer = 0L;
    for (int i = 0; i < N - 2; i++) {
      int cnt = 0;
      for (int j = i + 1; j < N; j++) {
        if (busArr[i] < busArr[j]) {
          cnt++;
        } else  {
          answer += cnt;
        }
      }
    }
    bw.write(String.valueOf(answer));
    bw.flush();
    bw.close();
  }
}