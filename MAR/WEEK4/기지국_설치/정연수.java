import java.util.*;

class Solution {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
    
        List<Integer> blanks = new ArrayList<>();    
        
        int pivot = 1;
        for (int idx = 0; idx < stations.length; idx++) {
            int start = stations[idx] - w;
            int end = stations[idx] + w;
            
            int blank = start - pivot;
            if (blank > 0) {
                blanks.add(blank);            
            }
            pivot = end + 1;
        }
        if (pivot <= n) {
            blanks.add(n + 1 - pivot);            
        }

        for (int idx = 0; idx < blanks.size(); idx++) {
            answer += blanks.get(idx) / (2 * w + 1);
            if (blanks.get(idx) % (2 * w + 1) != 0) answer += 1;
        }
        return answer;
    }
}
