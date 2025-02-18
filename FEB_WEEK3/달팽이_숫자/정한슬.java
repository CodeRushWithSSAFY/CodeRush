import java.util.Scanner;
import java.io.FileInputStream;

class Solution
{
  static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 반복하는 순서대로 둠

  public static void main(String args[]) throws Exception
  {
    Scanner sc = new Scanner(System.in);
    int T = sc.nextInt();

    for(int test_case = 1; test_case <= T; test_case++)
    {
      int boardSize = sc.nextInt();
      int[][] board = makeBoard(boardSize);
      printAnswer(board, test_case);
    }

    sc.close();
  }

  private static int[][] makeBoard(int boardSize) {
    int[][] board = new int[boardSize][boardSize];
    int num = 1;
    int curR  = 0;
    int curC = 0;
    int direction = 0;


    //  숫자는 boardSize * boardSize 를 초과하지 못한다.
    while (num <= boardSize * boardSize) {
      board[curR][curC] = num++; // 숫자 배치

      int nextR = curR + directions[direction][0];
      int nextC = curC + directions[direction][1];

      if (!isInBoard(nextR, nextC, boardSize) || board[nextR][nextC] != 0) {
        direction = (direction + 1) % 4;
        nextR = curR + directions[direction][0];
        nextC = curC + directions[direction][1];
      }

      curR = nextR;
      curC = nextC;
    }
    return board;
  }

  private static boolean isInBoard(int row, int col, int boardSize) {
    return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
  }

  private static void printAnswer(int[][] board, int test_case) {
    // 해당 주석으로 하면 swea에서 제출했을 때 fail이 됩니다.
    //StringBuilder sb = new StringBuilder();
    //sb.append("#").append(test_case).append("\n");
    //for (int row = 0; row < board.length; row++) {
    //  for (int col = 0; col < board[0].length; col++) {
    //     sb.append(board[row][col]).append(" ");
    //   }
    //sb.append("\n");
    //}
    //System.out.println(sb);
    System.out.println("#" + test_case);
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        System.out.print(board[row][col] + " ");
      }
      System.out.println();
    }
  }
}