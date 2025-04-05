import java.util.*;

/*
    주문서의 순서 규칙
    0. 알파벳 소문자 11 글자 이하로 구성. -> 아니었음.. bans 배열에 있는 단어만.. 11글자..
    n이 1_000_000일때
    1. 글자 수가 적은 주문 부터
    2. 글자 수가 같다면, 사전 순서대로

    주문서에서 지운 bans 배열의 주문들이 있을때 n번째의 주문을 출력

    a ~ z : 26자
    a : 1
    b : 2
    c : 3
    ..
    z : 26

    aa : 27
    ab : 28
    ac : 29
    ...
    az : 27 + 25 = 52

    ba : 53
    bb : 54
    ...
    bz : 53 + 25 = 78

    ca : 79
    ...

    <해결 방법>
    1. ban된 단어의 주문서 번호를 찾는다.
    2 - 1. 그게 실제 찾아야하는 단어의 n 번보다 같거나 작으면 삭제되어야하는 단어 count의 + 1 한다.
    2 - 2. n보다 크면 pass 한다.
    3. ban된 단어들을 다 확인 했으면 찾아야하는 단어의 n번에서 삭제된 count의 개수를 더한 번호의 word를 출력한다.
*/
class 정한슬 {
  static final int ALPHABET_COUNT = 26;
  static final int A_ASCII = 'a';
  static int deletedWordCount;
  static long findNumber;
  static long curWordNumber;

  public String solution(long n, String[] bans) {
    findNumber = n;

    // 주문서의 순서 규칙과 같이 sort
    Arrays.sort(bans, (s1, s2) -> {
      if (s1.length() == s2.length()) {
        return s1.compareTo(s2);
      }
      return s1.length() - s2.length();
    });

    for (String banWord : bans) {
      curWordNumber = getWordIndex(banWord);
      if (curWordNumber - deletedWordCount > findNumber) break; // 정렬했으니까 이 이후는 다 큰 주문 값일 것. break 진행
      deletedWordCount++;
    }

    findNumber += deletedWordCount;

    return getWordFromIndex(findNumber);
  }

  private static String getWordFromIndex(long index) {
    StringBuilder sb = new StringBuilder();
    while(index > 0) {
      if (index % ALPHABET_COUNT == 0) {
        sb.append((char) (A_ASCII + (ALPHABET_COUNT - 1)));
        index--;
      } else {
        sb.append((char) (A_ASCII + (index % ALPHABET_COUNT - 1)));
      }
      index /= ALPHABET_COUNT;
    }

    return sb.reverse().toString();
  }

  private static long getWordIndex(String banWord) {
    long curWordIndex = 0;
    for (int idx = 0; idx < banWord.length(); idx++) {
      int curAscii = banWord.charAt(idx);
      int diff = curAscii - A_ASCII + 1;
      int powValue = banWord.length() - (1 + idx);
      curWordIndex += (long) Math.pow(ALPHABET_COUNT, powValue) * diff;
    }
    return curWordIndex;
  }
}