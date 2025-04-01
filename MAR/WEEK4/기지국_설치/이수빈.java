/*
    기지국 최소 설치하기
*/
class Solution {
    
    public int solution(int n, int[] stations, int w) {
        int range = 2 * w + 1;
        
        int cnt = 0;
        int maxApt = 0;
        for (int idx = 0; idx < stations.length; idx++) {
            int prevAptIdx = stations[idx] - w - 1; // 범위 직전 아파트
            int curStation = stations[idx];
            
            if (prevAptIdx > maxApt) {
                if ((prevAptIdx - maxApt) % range == 0) cnt += (prevAptIdx - maxApt) / range;
                else cnt += (prevAptIdx - maxApt) / range + 1;
            }
            
            maxApt = curStation + w;
        }
        
        if (maxApt < n) {
            if ((n - maxApt) % range == 0) cnt += (n - maxApt) / range;
            else cnt += (n - maxApt) / range + 1;
        }

        return cnt;
    }
}
