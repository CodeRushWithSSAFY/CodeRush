class Solution {
    int[] serverCnt;
    int serverMaxUserCnt, serverMaxTime;
    
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        serverMaxUserCnt = m;
        serverMaxTime = k;
        serverCnt = new int[24];
        
        for (int time = 0; time < 24; time++) {
            int needTotalServerCnt = players[time] / serverMaxUserCnt;
            
            if (serverCnt[time] < needTotalServerCnt) {
                int addServerCnt = needTotalServerCnt - serverCnt[time];
                
                for (int serverTime = time; serverTime < time + serverMaxTime; serverTime++) {
                    if (serverTime >= 24) break;
                    serverCnt[serverTime] += addServerCnt;
                }
                answer += addServerCnt;
            }
        }
        
        return answer;
    }
}
