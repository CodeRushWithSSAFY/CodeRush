import java.util.*;
import java.io.*;

class Solution {
    int answer = 0;
    
    int solution(int[][] land) {

        for (int i = 1; i < land.length; i++) {
            for (int j = 0; j < 4; j++) {
                land[i][j] += Math.max(land[i - 1][(j + 1) % 4], Math.max(land[i - 1][(j + 2) % 4], land[i - 1][(j + 3) % 4]));
                answer = Math.max(answer, land[i][j]);
            }
        }
        
        return answer;
    }
}
