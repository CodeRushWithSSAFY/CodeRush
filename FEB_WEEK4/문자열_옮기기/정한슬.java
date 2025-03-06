import java.io.*;
import java.util.*;

/*
	글자를 바꾸는 방식
    양수 X: 맨 앞자리 문자를 맨 뒤로 X회 보낸다
    음수 X: 맨 뒤자리 문자를 맨 앞으로 X회 보낸다

    숫자를 다 더한 다음 결과 값으로만 글자를 바꾸자

    1. 양수일 때
    앞 | 뒤로 나누고 뒤앞으로  stringbuilder에 넣으면 끝
   	abcde , 3 -> deabc
    0~2 | 3~4
    substring(0, X), substring(X, length() - 1)
    2. 음수일 때
    앞 | 뒤로 나누되
    abcde -3 ->edcab
    X = lengt() + X로 바꾼뒤 뒤에것만 수정
    0~1 | 2~4(순서를 바꿔야함) stringbuilder.reverse()
    substring(0, X) | append(substring(X, length() - 1)).reverse()

*/
public class 정한슬 {
  public static void main(String args[]) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    StringTokenizer st = null;
    int testCaseNum = Integer.parseInt(br.readLine().trim());
    StringBuilder sb = new StringBuilder();
    for(int testCase = 1; testCase <= testCaseNum; testCase++)
    {
      String inputString = br.readLine().trim();
      int changeCount = Integer.parseInt(br.readLine().trim());
      st = new StringTokenizer(br.readLine().trim());
      long totalShift = 0; // int 타입의 범위를 넘는 경우가 있을 수도 있으니 long 타입으로,,
      // -109 이상 109 이하
      for (int changeNum = 0; changeNum < changeCount; changeNum++) {
        totalShift += Long.parseLong(st.nextToken());
      }

      int strLen = inputString.length();
      // 모듈로 연산으로 최적화
      int totalShiftInt = (int) (((totalShift % strLen) + strLen) % strLen);

      if (totalShiftInt == 0) {
        sb.append(inputString);
      }else if (totalShiftInt > 0) {
        String front = inputString.substring(0, totalShiftInt);
        String back =  inputString.substring(totalShiftInt);
        sb.append(back).append(front);
      } else {
        totalShiftInt *= -1;
        String front = inputString.substring(0, totalShiftInt);
        String back =  inputString.substring(totalShiftInt);
        sb.append(back).reverse().append(front);
      }
      sb.append("\n");
    }
    br.close();
    sb.setLength(sb.length() - 1);
    bw.write(sb.toString());
    bw.flush();
    bw.close();
  }
}