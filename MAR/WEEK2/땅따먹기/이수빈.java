class Solution {
    int solution(int[][] land) {
        int answer = 0;
        
        for (int row = 1; row < land.length; row++) {
            for (int col = 0; col < 4; col++) { // 현재 col
                int maxScore = 0;
                for (int prevCol = 0; prevCol < 4; prevCol++) { // 이전 col
                    if (prevCol == col) continue;
                    maxScore = Math.max(land[row - 1][prevCol], maxScore);
                }
                land[row][col] += maxScore;
            }
        }
        
        for (int col = 0; col < 4; col++) {
            answer = Math.max(answer, land[land.length - 1][col]);
        }

        return answer;
    }
}
