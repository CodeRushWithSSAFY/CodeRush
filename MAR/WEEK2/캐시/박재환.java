package WEEK2.캐시;

import java.util.ArrayDeque;
import java.util.Queue;

public class 박재환 {
    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = {
                "Jeju", "Pangyo", "NewYork", "newyork"
        };

        System.out.println(new Solution().solution(cacheSize,cities));
    }

    static class Solution {
        Queue<String> cache;    // DB 캐시를 구현한다.
        int progressTime;       // 실행 시간
        public int solution(int cacheSize, String[] cities) {
            if(cacheSize == 0) return 5*cities.length;

            progressTime = 0;
            cache = new ArrayDeque<>();

            // 각 도시를 검색한다.
            for(String city : cities) {
                // 대소문자를 구분하지 않는다.
                city = city.toLowerCase();

                // 찾으려는 도시가 검색 기록에 있다면 -> hit
                if(cache.contains(city)) {
                    // 검색 기록을 최근에 한 검색 기록으로 업데이트한다.
                    cache.remove(city);
                    cache.offer(city);
                    progressTime += 1;
                } else {    // 찾으려는 도시가 없다면 -> miss

                    // 캐시를 업데이트 해준다.
                    if(cache.size() == cacheSize) { // 캐시가 가득 차 있다면
                        cache.poll();   // 가장 오래 사용되지 않은 기록을 삭제한다.
                    }

                    cache.offer(city);

                    progressTime += 5;
                }
            }
            return progressTime;
        }
    }
}

/*
    LRU 알고리즘을 사용한다. -> 가장 최근에 사용되지 않은 페이지를 교체한다.
    Queue 를 사용하여 구현한다.

    지도에서 도시 이름을 검색하면 해당 도시와 관련된 맛집 게시물들을 데이터베이스에서 읽어 보여주는 서비스이다.
    hit 시에는 1, miss 시에는 5 의 시간이 걸린다.
 */