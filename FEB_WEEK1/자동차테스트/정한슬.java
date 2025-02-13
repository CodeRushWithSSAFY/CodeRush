import java.io.*;
import java.util.*;

/*
    입력
    n: 자동차 갯수 -> carCnt
    q: 질의의 갯수 -> queryCnt;
    q개 갯수대로 원하는 중앙값 mi가 나열됨

    출력
    질의에 맞게 mi가 나올 수 있는 가짓수를 출력

*/
public class 정한슬 {
  static int[] carFuelEfficiencies;
  static int[] wantedMiddleValues;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    StringTokenizer st = new StringTokenizer(br.readLine().trim());
    int carCnt = Integer.parseInt(st.nextToken());
    int queryCnt = Integer.parseInt(st.nextToken());

    carFuelEfficiencies = new int[carCnt];
    wantedMiddleValues = new int[queryCnt];

    st = new StringTokenizer(br.readLine().trim());
    for (int car = 0; car < carCnt; car++) {
      carFuelEfficiencies[car] = Integer.parseInt(st.nextToken());
    }

    for (int query = 0; query < queryCnt; query++) {
      st = new StringTokenizer(br.readLine().trim());
      wantedMiddleValues[query] =  Integer.parseInt(st.nextToken());
    }
    br.close();

    Arrays.sort(carFuelEfficiencies); // 오름차순으로 정렬

    bw.flush();
    StringBuilder sb = new StringBuilder();
    for (int wantedMiddelValue : wantedMiddleValues) {
      sb.append(checkCombiCnt(wantedMiddelValue)).append("\n");
    }
    sb.setLength(sb.length() - 1);
    bw.write(sb.toString());
    bw.close();
  }
  private static int checkCombiCnt(int wantedMiddelValue) {
    int valueIdx = Arrays.binarySearch(carFuelEfficiencies, wantedMiddelValue);
    if (valueIdx < 0)  return 0; // wantedMiddelValue가 없으면 만들 수 없음
    if (valueIdx == 0 || valueIdx == carFuelEfficiencies.length - 1) return 0; //wantedMiddelValue보다 작은거나 큰게 없으면 만들 수 없음.

    int lowCount = valueIdx;
    int highCount = carFuelEfficiencies.length - 1 - valueIdx;

    return lowCount * highCount;
  }
}

