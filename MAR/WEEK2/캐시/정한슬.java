import java.util.*;

/*
    LRU(Least Recently Used) 캐시 교체 알고리즘
    1. 캐시 사이즈에 채워질때 까지는 cache miss로 취급
    2. 캐시에 있는 것을 다시 실행할 때는 cache hit이고 최근 사용 한것으로도 기록.
    3. 만일 캐시에 있는 것이 아닌 새로운 것을 실행할 때는 최근 사용이 가장 적은 것을 빼고 넣는다 -> cache miss

    <해결방법>
    1. 캐시 사이즈 만큼은 city를 그냥 넣고 cache miss 값 만큼 실행시간을 더한다.
        -> 해당 방법을 굳이 먼저 해주니 틀림. cache miss가 나면 캐시 사이즈를 넘지 않는 선에서 그냥 넣는다.
    2. 캐시 사이즈만큼 다 채운 뒤에도 cache miss가 나면 최근에 사용하지 않은 것을 맨뒤에서 지우고
    현재 city를 맨 앞에 넣어준다.
    3. 캐시 히트되면 해당 시티를 지우고 맨 처음에 넣는다. (최근 사용 처리)
*/
class 정한슬 {
  static final int CACHE_HIT = 1;
  static final int CACHE_MISS = 5;

  static int executionTime;
  static Deque<String> process;

  public int solution(int cacheSize, String[] cities) {
    executionTime = 0;

    if (cacheSize == 0) {
      return cities.length * CACHE_MISS;
    }

    process = new ArrayDeque<>();

    for (int idx = 0; idx < cities.length; idx++) {
      String curCity = cities[idx].toLowerCase();
      if (process.contains(curCity)) {
        executionTime += CACHE_HIT;

        process.remove(curCity);
        process.addFirst(curCity);
      } else {
        executionTime += CACHE_MISS;
        if (process.size() == cacheSize) {
          process.pollLast();
        }
        process.addFirst(curCity);
      }
    }
    return executionTime;
  }
}