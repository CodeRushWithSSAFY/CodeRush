package MAR.WEEK4.자물쇠와_열쇠;

import java.util.*;

public class 박재환 {
  public static void main(String[] args) {
    int[][] key = {
        { 0, 0, 0 },
        { 1, 0, 0 },
        { 0, 1, 1 }
    };

    int[][] lock = {
        { 1, 1, 1 },
        { 1, 1, 0 },
        { 1, 0, 1 }
    };

    System.out.println(new 박재환().solution(key, lock));
  }

  int lockSize, keySize;

  public boolean solution(int[][] key, int[][] lock) {
    lockSize = lock.length;
    keySize = key.length;

    boolean answer = false;
    copyLock(key, lock);
    // 2. 열쇠를 돌려서, 각 자리에 모두 넣어본다.
    // 열쇠는 적어도 한 줄은 자물쇠에 속해있어야한다.
    // 비트마스킹?
    // 열쇠는 총 4가지의 경우를 갖는다.
    for (int rotate = 0; rotate < 4; rotate++) {
      // System.out.println("key");
      // for(int[] arr : key) {
      // System.out.println(Arrays.toString(arr));
      // }
      // System.out.println();

      if (copyLock(key, lock)) {
        answer = true;
        break;
      }
      key = rotateKey(key);
    }

    return answer;
  }

  // 1. 열쇠를 돌린다. ( 배열 회전 )
  int[][] rotateKey(int[][] key) {
    int[][] rotateKey = new int[keySize][keySize];

    // 가로 -> 세로
    int rowIdx = keySize - 1;
    for (int x = 0; x < keySize; x++) {
      for (int y = 0; y < keySize; y++) {
        rotateKey[y][rowIdx] = key[x][y];
      }
      rowIdx--;
    }

    return rotateKey;
  }

  // 2. 회전 시킨 상태의 열쇠가 맞는 위치가 있는지 확인한다.
  boolean copyLock(int[][] key, int[][] lock) {
    int extraSize = keySize - 1;
    // 자물쇠를 여분의 공간이 있는 위치에 복사한다.
    int[][] copyLock = new int[lockSize + extraSize * 2][lockSize + extraSize * 2];
    for (int x = extraSize; x < lockSize + extraSize; x++) {
      for (int y = extraSize; y < lockSize + extraSize; y++) {
        int copyValue = lock[x - extraSize][y - extraSize];
        copyLock[x][y] = copyValue;
      }
    }

    // 키를 이동시키면 딱 들어가는지 확인한다.
    for (int x = 0; x < copyLock.length - extraSize; x++) {
      for (int y = 0; y < copyLock.length - extraSize; y++) {
        if (locateKey(x, y, extraSize, key, copyLock))
          return true;
      }
    }

    return false;
  }

  boolean locateKey(int x, int y, int extraSize, int[][] key, int[][] lock) {
    // 해당 위치에 열쇠를 일치시켰을 때, 자물쇠의 상태를 기록한다.
    for (int keyX = 0; keyX < keySize; keyX++) {
      for (int keyY = 0; keyY < keySize; keyY++) {
        lock[keyX + x][keyY + y] += key[keyX][keyY];
      }
    }

    // 딱 맞는지 확인한다.
    if (fullFill(extraSize, lock))
      return true;

    // 다시 원상복구한다.
    for (int keyX = 0; keyX < keySize; keyX++) {
      for (int keyY = 0; keyY < keySize; keyY++) {
        lock[keyX + x][keyY + y] -= key[keyX][keyY];
      }
    }

    return false;
  }

  boolean fullFill(int extraSize, int[][] lock) {
    for (int x = extraSize; x < extraSize + lockSize; x++) {
      for (int y = extraSize; y < extraSize + lockSize; y++) {
        if (lock[x][y] != 1)
          return false;
      }
    }
    return true;
  }
}

/*
 * 자물쇠는 N x N 크기의 정사각형 격자이다.
 * 
 * 열쇠는 M x M 크기의 정사각형 격자이다.
 * 열쇠는 회전과 이동이 가능하다.
 * 
 * 자물쇠 영역을 벗어난 열쇠는 자물쇠를 여는데 영향을 주기 않는다.
 * 
 * 일단 자물쇠를 돌리고, 완전탐색으로 찾는다?
 * 나올 수 있는 자물쇠 경우 4가지
 */