import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int[][] newArr = new int[triangle.length][triangle[triangle.length - 1].length];
        newArr[0][0] = triangle[0][0];
        for (int idx = 0; idx < triangle.length - 1; idx++) {
            for (int inner = 0; inner < triangle[idx].length; inner++) {
                newArr[idx + 1][inner] = Math.max(newArr[idx][inner] + triangle[idx + 1][inner], newArr[idx + 1][inner]);
                newArr[idx + 1][inner + 1] = Math.max(newArr[idx][inner] + triangle[idx + 1][inner + 1], newArr[idx + 1][inner + 1]);
            }
        }
        for (int idx = 0; idx < newArr[newArr.length - 1].length; idx++) {
            answer = Math.max(answer, newArr[newArr.length - 1][idx]);
        }
        return answer;
    }
}
