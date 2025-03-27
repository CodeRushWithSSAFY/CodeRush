package MAR.WEEK4.퍼즐_조각_채우기;

import java.util.*;

public class 박재환 {

  public static void main(String[] args) {
    // int[][] game_board = {
    // {1,1,0,0,1,0},
    // {0,0,1,0,1,0},
    // {0,1,1,0,0,1},
    // {1,1,0,1,1,1},
    // {1,0,0,0,1,0},
    // {0,1,1,1,0,0}};
    //
    // int[][] table = {
    // {1,0,0,1,1,0},
    // {1,0,1,0,1,0},
    // {0,1,1,0,1,1},
    // {0,0,1,0,0,0},
    // {1,1,0,1,1,0},
    // {0,1,0,0,0,0}
    // };

    // int[][] game_board = {
    // {1, 1, 1, 0},
    // {1, 0, 0, 0},
    // {1, 0, 1, 1},
    // {1, 1, 1, 1}
    // };
    //
    // int[][] table = {
    // {0, 0, 0, 1},
    // {1, 1, 1, 0},
    // {0, 0, 0, 0},
    // {0, 0, 0, 0}
    // };

    int[][] game_board = {
        { 1, 1, 1, 1 },
        { 1, 0, 0, 1 },
        { 1, 0, 1, 1 },
        { 1, 1, 1, 1 }
    };

    int[][] table = {
        { 0, 0, 0, 0 },
        { 0, 1, 0, 0 },
        { 1, 1, 0, 0 },
        { 0, 0, 0, 0 }
    };

    // int[][] game_board = {
    // {0, 0, 0, 0, 0},
    // {0, 0, 0, 0, 0},
    // {0, 0, 0, 0, 0},
    // {0, 0, 0, 0, 0},
    // {0, 0, 0, 0, 0}
    // };
    //
    // int[][] table = {
    // {1, 1, 1, 1, 0},
    // {1, 0, 0, 1, 0},
    // {1, 1, 1, 0, 0},
    // {0, 0, 0, 0, 1},
    // {0, 0, 0, 0, 0}
    // };
    // int[][] game_board = {
    // {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
    // {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0},
    // {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
    // {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1},
    // {0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
    // {0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
    // {0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0},
    // {0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0},
    // {1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
    // {0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0},
    // {0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1},
    // {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0}
    // };
    //
    // int[][] table = {
    // {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1},
    // {1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1},
    // {1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0},
    // {0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0},
    // {1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0},
    // {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
    // {1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1},
    // {1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1},
    // {0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
    // {1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1},
    // {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
    // {1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1}
    // };
    System.out.println(new 박재환().solution(game_board, table));
  }

  int[] dx = { 0, 1, 0, -1 };
  int[] dy = { 1, 0, -1, 0 };
  // 블록들을 저장할 배열
  List<boolean[][]> tableBlocks;
  List<boolean[][]> gameBoardBlocks;

  public int solution(int[][] game_board, int[][] table) {
    tableBlocks = new ArrayList<>();
    gameBoardBlocks = new ArrayList<>();

    findBlock(table, tableBlocks, 1);
    findBlock(game_board, gameBoardBlocks, 0);

    // System.out.println("보드");
    // for(boolean[][] arr : gameBoardBlocks) {
    // for(boolean[] arr2 : arr) {
    // for(boolean b : arr2) {
    // System.out.print(b ? 1:0);
    // }
    // System.out.println();
    // }
    // System.out.println();
    // }
    //
    // System.out.println("테이블");
    // for(boolean[][] arr : tableBlocks) {
    // for(boolean[] arr2 : arr) {
    // for(boolean b : arr2) {
    // System.out.print(b ? 1:0);
    // }
    // System.out.println();
    // }
    // System.out.println();
    // }
    //
    // System.out.println("비트 변환");

    // 각 도형을 비트로 변환하여 저장
    List<String>[] gameBoardBlocksBit = new ArrayList[gameBoardBlocks.size()];
    for (int idx = 0; idx < gameBoardBlocks.size(); idx++) {
      gameBoardBlocksBit[idx] = new ArrayList<>();
    }

    List<String>[] tableBlocksBit = new ArrayList[tableBlocks.size()];
    for (int idx = 0; idx < tableBlocks.size(); idx++) {
      tableBlocksBit[idx] = new ArrayList<>();
    }

    makeBit(gameBoardBlocks, gameBoardBlocksBit, false);
    makeBit(tableBlocks, tableBlocksBit, true);

    // System.out.println("보드 ( 회전 X )");
    // for(int idx=0; idx<gameBoardBlocksBit.length; idx++) {
    // for(String s : gameBoardBlocksBit[idx]) {
    // System.out.println(s);
    // }
    // System.out.println();
    // }
    //
    // System.out.println("테이블 ( 회전 O )");
    // for(int idx=0; idx<tableBlocksBit.length; idx++) {
    // for(String s : tableBlocksBit[idx]) {
    // System.out.println(s);
    // }
    // System.out.println();
    // }

    // 생성된 비트 조합으로 일치하는지 확인
    boolean[] gameBoardUsed = new boolean[gameBoardBlocks.size()];
    boolean[] tableUsed = new boolean[tableBlocks.size()];

    int totalCnt = 0;
    for (int tableIdx = 0; tableIdx < tableUsed.length; tableIdx++) {
      for (String tableBit : tableBlocksBit[tableIdx]) {
        // 해당 블록을 사용했다면
        if (tableUsed[tableIdx])
          break;
        for (int gameBoardIdx = 0; gameBoardIdx < gameBoardUsed.length; gameBoardIdx++) {
          if (gameBoardUsed[gameBoardIdx])
            continue;

          if (gameBoardBlocksBit[gameBoardIdx].get(0).equals(tableBit)) {
            // 일치한다면
            gameBoardUsed[gameBoardIdx] = true;
            tableUsed[tableIdx] = true;

            // 채워진 칸의 개수를 구한다.
            for (char c : tableBit.toCharArray()) {
              if (c == '1')
                totalCnt++;
            }
            break;
          }
        }
      }
    }

    return totalCnt;
  }

  /*
   * 도형을 비트로 만든다. ( = 문자열 )
   * rotate 값이 true 일 경우 90 도씩 총 3번 즉 4개의 케이스를 만들어 저장한다.
   */
  static void makeBit(List<boolean[][]> blocks, List<String>[] blockBit, boolean rotate) {
    // game_board, table 공통
    for (int idx = 0; idx < blocks.size(); idx++) {
      boolean[][] block = blocks.get(idx);
      StringBuilder sb = new StringBuilder();
      // 정방향일 때, 저장
      // 좌 -> 우
      for (boolean[] arr : block) {
        for (boolean b : arr) {
          sb.append(b ? 1 : 0);
        }
        sb.append(',');
      }
      // 탐색 가짓수 줄이기
      if (blockBit[idx].contains(sb.toString()))
        continue;

      blockBit[idx].add(sb.toString());
    }

    if (!rotate)
      return;

    // rotate 가 참일때, 즉 table 블록들만

    // 각 90 도로 회전 시킨 비트 형태를 기록한다.
    for (int idx = 0; idx < blocks.size(); idx++) {
      boolean[][] block = blocks.get(idx);
      StringBuilder sb = new StringBuilder();
      // 좌 -> 우, 위 방향으로
      for (int y = 0; y < block[0].length; y++) {
        for (int x = block.length - 1; x > -1; x--) {
          sb.append(block[x][y] ? 1 : 0);
        }
        sb.append(',');
      }
      // 탐색 가짓수 줄이기
      if (blockBit[idx].contains(sb.toString()))
        continue;

      blockBit[idx].add(sb.toString());
    }

    for (int idx = 0; idx < blocks.size(); idx++) {
      boolean[][] block = blocks.get(idx);
      StringBuilder sb = new StringBuilder();
      // 우 -> 좌, 아래서 위
      for (int x = block.length - 1; x > -1; x--) {
        for (int y = block[0].length - 1; y > -1; y--) {
          sb.append(block[x][y] ? 1 : 0);
        }
        sb.append(',');
      }
      // 탐색 가짓수 줄이기
      if (blockBit[idx].contains(sb.toString()))
        continue;

      blockBit[idx].add(sb.toString());
    }

    for (int idx = 0; idx < blocks.size(); idx++) {
      boolean[][] block = blocks.get(idx);
      StringBuilder sb = new StringBuilder();
      // 상 하, 오른쪽에서 왼쪽
      for (int y = block[0].length - 1; y > -1; y--) {
        for (int x = 0; x < block.length; x++) {
          sb.append(block[x][y] ? 1 : 0);
        }
        sb.append(',');
      }
      // 탐색 가짓수 줄이기
      if (blockBit[idx].contains(sb.toString()))
        continue;

      blockBit[idx].add(sb.toString());
    }
  }

  void findBlock(int[][] arr, List<boolean[][]> blockList, int targetSpace) {
    boolean[][] checked = new boolean[arr.length][arr.length];

    for (int x = 0; x < arr.length; x++) {
      for (int y = 0; y < arr.length; y++) {
        // 블록이 아님, 이미 확인함
        if (arr[x][y] != targetSpace || checked[x][y])
          continue;
        // System.out.println(x+","+y);
        // 블록이다.
        // 블록의 영역을 구함
        int minX, maxX, minY, maxY; // 정사각형 영역을 구할거임
        // 값 초기화
        minX = maxX = x;
        minY = maxY = y;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { x, y });
        checked[x][y] = true;

        Queue<int[]> spaceLoc = new LinkedList<>();
        // 인접 영역을 모두 탐색한다.
        while (!q.isEmpty()) {
          int[] curPoints = q.poll();
          int curX = curPoints[0];
          int curY = curPoints[1];

          spaceLoc.offer(curPoints);

          for (int dir = 0; dir < 4; dir++) {
            int nx = curX + dx[dir];
            int ny = curY + dy[dir];

            // 범위 체크
            if (nx < 0 || ny < 0 || nx >= arr.length || ny >= arr.length)
              continue;
            // 방문 여부, 블록 여부
            if (checked[nx][ny] || arr[nx][ny] != targetSpace)
              continue;

            // 블록이다
            checked[nx][ny] = true;
            q.offer(new int[] { nx, ny });

            // 영역 계산
            minX = Math.min(minX, nx);
            maxX = Math.max(maxX, nx);
            minY = Math.min(minY, ny);
            maxY = Math.max(maxY, ny);
          }
        }

        int diffX = Math.abs(maxX - minX);
        int diffY = Math.abs(maxY - minY);
        // 영역의 크기만큼 도형을 저장
        boolean[][] block = new boolean[diffX + 1][diffY + 1];

        // for(int x1=minX; x1<=maxX; x1++) {
        // for(int y1=minY; y1<=maxY; y1++) {
        // if(arr[x1][y1] == targetSpace) {
        // block[x1-minX][y1-minY] = true;
        // }
        // }
        // }

        while (!spaceLoc.isEmpty()) {
          int[] curPoints = spaceLoc.poll();
          int curX = curPoints[0];
          int curY = curPoints[1];

          block[curX - minX][curY - minY] = true;
        }

        blockList.add(block);
      }
    }
  }
}
/*
 * 보드는 정사각형 격자
 * 
 * 1. 조각은 한 번에 하나씩 채워 넣는다.
 * 2. 조각을 회전시킬 수 있다.
 * 3. 조각을 뒤집을 수는 없다.
 * 4. 새로 채워넣은 퍼즐조각과 인접한 칸이 비어있으면 안된다. -> 꽉 차야한다.
 * 
 * 격자의 최대 크기는 50 x 50
 * 
 * 1. 퍼즐 조각 구분하기
 * 상하좌우로 인접한 구역은 하나의 퍼즐이다. -> 이것도 정사각형으로 추출됨
 * 우째 저장할겨?
 * 
 * 
 * 예제 테케
 * 블록 : 5 개
 * 4^5 * 격자?
 */