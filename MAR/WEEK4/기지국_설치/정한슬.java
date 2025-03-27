import java.util.*;

/*
  기존에 설치된 기지국은 stations 배열에 존재.
  w는 5g 의 전달 범위이며 stations 배열에 존재하는 기지국도 5g기지국 전달 범위를 갖고,
  새로 설치하려는 기지국도 모두 w만큼의 전달 범위를 갖는다.

  모든 아파트에 전파를 전달하기 위해 증설해야할 기지국 개수의 최소 값

  아파트의 개수: 200_000_000 이하의 자연수
  stations의 크기: 10_000 이하의 자연수
  stations는 오름차순으로 정렬되어있고, 배열에 담긴 수는 N보다 같거나 작은 자연수
  W: 10_000 이하의 자연수

*/
class 정한슬 {
  static int minInstallationCount = 0;
  // static List<int[]> wifiInstallRange;
  static int left, right, frontPosition, backPosition;
  static int rangeValue;

  public int solution(int n, int[] stations, int w) {
    // wifiInstallRange = new ArrayList<>();
    rangeValue = 2*w + 1;
    left = 1;
    right = 1;
    for (int station : stations) {
      frontPosition = station - w;
      backPosition = station + w;
      if (frontPosition <= 0) frontPosition = 1;
      if (backPosition > n + 1) backPosition = n;
      if (frontPosition == 1) {
        left = backPosition + 1;
        continue;
      }
      // wifiInstallRange.add(new int[]{left, right});
      right = frontPosition == 1 ? 1 : frontPosition - 1;
      // System.out.println("left: " + left + " right: " + right );

      int range = right - left + 1;
      while (range > 0) {
        minInstallationCount++;
        // System.out.println("1. ++");
        range -= rangeValue;
      }
      left = backPosition + 1;

    }


    if (left >= right) {
      right = n;
      // System.out.println("left >= right");
      int range = right - left + 1;
      while (range > 0) {
        minInstallationCount++;
        // System.out.println("2. ++");
        range -= rangeValue;
      }
    }
    // System.out.println(right);
    // System.out.println(left);
    return minInstallationCount;
  }
}