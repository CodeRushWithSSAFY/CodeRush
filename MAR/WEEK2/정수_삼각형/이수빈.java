class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        for (int row = 1; row < triangle.length; row++) {
            // 첫번째
            triangle[row][0] += triangle[row - 1][0];
            // 중간
            for (int col = 1; col < triangle[row].length - 1; col++) {
                if (triangle[row - 1][col - 1] > triangle[row - 1][col])
                    triangle[row][col] += triangle[row - 1][col - 1];
                else
                    triangle[row][col] += triangle[row - 1][col];
            }
            // 마지막
            triangle[row][triangle[row].length - 1] += triangle[row - 1][triangle[row].length - 2];
        }
        
        for (int col = 0; col < triangle[triangle.length - 1].length; col++) {
            answer = Math.max(triangle[triangle.length - 1][col], answer);
        }
        return answer;
    }
}
