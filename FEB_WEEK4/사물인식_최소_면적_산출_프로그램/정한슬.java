import java.io.*;
import java.util.*;

/*
    N: 점의 개수 -> dotCount
    K: 색깔의 개수 -> colorCount

    1부터 20개까지의 색깔이 존재.
    주어진 점들의 색깔들을 최소한 각각 1개씩은 포함하는 직사각형 중 넓이가 가장 작은 것을 출력
    직사각형의 가로나 세로가 선분 혹은 점으로 나타나는 경우도 존재하며 이때는 넓이가 0이다.

    플랫한 시야가 전혀 떠오르지 않아서.. 인터넷을 참고하여 dfs 접근이라는 것을 알게되어 풀이
*/
public class Main {
  static BufferedReader br;
  static BufferedWriter bw;
  static StringTokenizer st;

  static int dotCount;
  static int colorCount;
  static List<int[]>[] dotPositionByColor;
  static int x;
  static int y;
  static int color;
  static int minX = Integer.MAX_VALUE;
  static int minY = Integer.MAX_VALUE;
  static int maxX = Integer.MIN_VALUE;
  static int maxY = Integer.MIN_VALUE;
  static int minArea = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException {
    bw = new BufferedWriter(new OutputStreamWriter(System.out));

    initInput();
    for (int[] startPosition : dotPositionByColor[0]) {// 처음 색깔 먼저 넣어 dfs를 돌자
      dfs(1, startPosition[0], startPosition[1], startPosition[0], startPosition[1]);
    }

    bw.write(String.valueOf(minArea));
    bw.flush();
    bw.close();
  }

  private static void dfs(int depth, int curMinX, int curMinY, int curMaxX, int curMaxY) {
    if (depth == colorCount) { // 모든 색깔을 다 봤다면 끝.
      int curArea = (curMaxX - curMinX) * (curMaxY - curMinY);
      minArea = Math.min(minArea, curArea);
      return;
    }

    for (int[] nextColorPosition : dotPositionByColor[depth]) { // 바로 다른 색깔 중에서도 minX, minY, maxX, maxY값을 찾아서 갱신하자.
      // 그렇게 갱신하게 된다면, 이전 색깔 1개는 포함하는 과정이 될것
      minX = Math.min(curMinX, nextColorPosition[0]);
      minY = Math.min(curMinY, nextColorPosition[1]);
      maxX = Math.max(curMaxX, nextColorPosition[0]);
      maxY = Math.max(curMaxY, nextColorPosition[1]);

      int tmpArea = (maxX - minX) * (maxY - minY);
      if (minArea <= tmpArea) continue; // 만일 현재 색깔까지 갱신한 넓이가 최소값보다 같거나 크다면 이 면적은 포기하고 넘어간다.
      dfs(depth + 1, minX, minY, maxX, maxY);
    }

  }

  private static void initInput() throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine().trim());

    dotCount = Integer.parseInt(st.nextToken());
    colorCount = Integer.parseInt(st.nextToken());

    dotPositionByColor = new ArrayList[colorCount];
    for (int color = 0; color < colorCount; color++) {
      dotPositionByColor[color] = new ArrayList<>();
    }
    for (int count = 0; count < dotCount; count++) {
      st = new StringTokenizer(br.readLine().trim());
      x = Integer.parseInt(st.nextToken());
      y = Integer.parseInt(st.nextToken());
      color = Integer.parseInt(st.nextToken());
      dotPositionByColor[color - 1].add(new int[]{x, y});
    }
    br.close();
  }
}