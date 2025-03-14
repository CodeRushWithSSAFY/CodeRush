import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    
    public int solution(int cacheSize, String[] cities) {
        int time = 0;
        
        Deque<String> cache = new ArrayDeque<>();
        
        if (cacheSize == 0) {
            return 5 * cities.length;
        }
        
        for (int cityIdx = 0; cityIdx < cities.length; cityIdx++) {
            String city = cities[cityIdx].toLowerCase();
            
            // 캐시에 있으면
            if (cache.contains(city)) {
                cache.remove(city);
                cache.add(city);
                time++;
                
            // 캐시에 없으면
            } else {
                // 캐시 사이즈보다 작으면 그냥 넣고
                // 캐시 사이즈보다 크면 오래된 거 제거하고 넣기
                if (cache.size() >= cacheSize) {
                    cache.poll();
                }
                cache.add(city);
                time += 5;
            }
        }
        return time;
    }
}
