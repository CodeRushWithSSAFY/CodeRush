class Solution {
    int[] servers;

    public int solution(int[] players, int m, int k) {
        int answer = 0;
        int server = 0;
        servers = new int[24];

        for(int i = 0; i < players.length; i++){
            if (players[i] >= m && players[i] > servers[i] * m) {
                server = players[i] / m - servers[i];
                if (players[i] / m == servers[i]) continue;
                answer += server;
                for (int j = i; j < Math.min(i + k, players.length); j++) {
                    servers[j] += server;
                }
            }
        }

        return answer;
    }
}
