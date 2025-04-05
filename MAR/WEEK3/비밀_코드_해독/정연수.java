import java.util.*;

class Solution {
    
    private static int[] selected;
    private static int maxNum;
    private static int[][] testQ;
    private static int[] results;
    private static int answer = 0;

    private static boolean check() {
        for (int idx = 0; idx < results.length; idx++) {
            int[] test = testQ[idx];
            int result = results[idx];
            int cnt = 0;
            for (int i = 0; i < 5; i++) {
                int pivot = selected[i];
                for (int j = 0; j < 5; j++) {
                    if (pivot == test[j]) {
                        cnt++;
                        break;
                    }
                    if (pivot < test[j]) break;
                }
            }
            if (cnt != result) return false;
        }
        return true;
    }
    
    private static void makeCombination(int start, int cnt) {
        if (cnt == 5) {
            if (check()) answer++;
            return;
        }
        for (int idx = start; idx <= maxNum; idx++) {
            selected[cnt] = idx;
            makeCombination(idx + 1, cnt + 1);
        }
    }
    
    public int solution(int n, int[][] q, int[] ans) {
        maxNum = n;
        selected = new int[5];
        testQ = new int[ans.length][5];
        results = new int[ans.length];
        testQ = q;
        results = ans;
        makeCombination(1, 0);
        return answer;
    }
}
