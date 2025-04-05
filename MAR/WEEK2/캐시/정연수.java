import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        HashMap<String, Integer> cache = new HashMap<>();  // 캐시 (도시 이름을 키로 사용)
        LinkedList<String> lruList = new LinkedList<>();   // LRU 순서 관리
        
        for (String city : cities) {
            String cityLower = city.toLowerCase();  // 대소문자 구분 없이 처리
            
            // cache hit 확인
            if (cache.containsKey(cityLower)) {
                answer += 1;  // cache hit
                
                // LRU 순서 업데이트 (기존 위치에서 제거 후 맨 뒤에 추가)
                lruList.remove(cityLower);
                lruList.addLast(cityLower);
            } else {
                answer += 5;  // cache miss
                
                // 캐시가 가득 찼으면 가장 오래된 항목(LRU의 첫 번째 항목) 제거
                if (lruList.size() >= cacheSize) {
                    String oldestCity = lruList.pollFirst();  // 첫 번째 항목 제거 및 반환
                    if (oldestCity != null) {
                        cache.remove(oldestCity);
                    }
                }
                
                // 새 도시 추가
                if (cacheSize > 0) {
                    cache.put(cityLower, 1);  // 값은 존재 여부만 확인하므로 아무 값이나 가능
                    lruList.addLast(cityLower);
                }
            }
        }
        
        return answer;
    }
}
