package WEEK4.가사_검색;

import java.util.*;
import java.io.*;

public class 박재환 {
	public static void main(String[] args) {
		String[] words = {"frodo", "front", "frost", "frozen", "frame", "kakao"};
		String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
		// result = [3, 2, 4, 1, 0]
		
		System.out.println(
				Arrays.toString(
						new 박재환().solution(words, queries)
						));
	}
	
	public int[] solution(String[] words, String[] queries) {
		int[] answer = new int[queries.length];
		
		// 1. 키워드 정리
		removeDuplicatedKeyword(words);
		
		// 2. 키워드로 가사 내용을 검색한다.
		findMatchedWord(queries, answer);
		
		return answer;
	}
	
	/*
	 * Map 에 저장되어 있는 words 기준으로 queries 에 일치하는 문자가 있는지 확인한다. 
	 */
	void findMatchedWord(String[] queries, int[] answer) {
		for(int idx=0; idx<queries.length; idx++) {
			String keyword = queries[idx];
			int keyLen = keyword.length();
			
			// 길이가 같은 문자가 없다면 패스한다. 
			if(!frontMap.containsKey(keyLen)) {
				answer[idx] = 0;
				continue;
			}
			
			// 같은 길이의 문자가 있다면
			// 비교위치를 찾는다.
			
			List<String> compareList;
			String left, right;
			boolean isPrefix = keyword.charAt(0) == '?';	// 접두사, 접미사 구분
			
			if(isPrefix) {	// 와일드 카드가 앞에 있음 - 접미사 비교
				String reverse = new StringBuilder(keyword).reverse().toString();
				compareList = endMap.get(keyLen);
				left = reverse.replace("?", "a");
				right = reverse.replace("?", "z");
			} else {	// 와일드 카드가 앞에 있음 - 접두사 비교
				compareList = frontMap.get(keyLen);
				left = keyword.replace("?", "a");
				right = keyword.replace("?", "z");
			}
			
			answer[idx] = compareListToKeyWord(compareList, left, right);
		}
	}
	
	/*
	 * 키워드와, 해당하는 길이의 단어 List 를 비교한다.
	 * 
	 * 각 List 를 사전순으로 정렬해두었다. 
	 * 현재 키워드의 위치를 각 List 에서 Lower Bound, Upper Bound 를 구해 개수를 구한다. 
	 */
	int compareListToKeyWord(List<String> compareList, String left, String right) {
		int lIdx = findLeftIdx(compareList, left);
		int rIdx = findRightIdx(compareList, right);
		
		return rIdx - lIdx;
	}
	
	// Lower Bound : 현재 문자보다 큰 가장 왼쪽의 원소 위치
	int findLeftIdx(List<String> list, String target) {
		int l = 0, r =list.size();
		
		while(l < r) {
			int mid = l + (r-l)/2;
			
			// target 이 더 큰 범위인 경우
			if(list.get(mid).compareTo(target) < 0) {
				l = mid+1;
			} else {
				r = mid;
			}
		}
		
		return l;
	}
	
	// Upper Bound : 현재 문자와 같거나 큰 원소의 위치
	int findRightIdx(List<String> list, String target) {
		int l = 0, r =list.size();

		while(l < r) {
			int mid = l + (r-l)/2;
			
			// target 이 더 큰 범위인 경우
			if(list.get(mid).compareTo(target) <= 0) {
				l = mid+1;
			} else {
				r = mid;
			}
		}
		
		return l;
	}
	/*
	 * 검색 키워드는 중복될 수 있다. -> Map 사용하여 각 키워드 당 하나의 결과만 보관한다. XX
	 * 
	 * words 를 기준으로 map 을 생성한다. 
	 * 
	 * 와일드 카드는 맨 앞부터 혹은 맨 뒤부터
	 * -> 접두사와 접미사를 확인해야한다. 
	 * 
	 * 2 개의 Map 을 사용한다. 
	 * 각 word 의 단어 길이 순을 Map 을 생성한다. 
	 */
	Map<Integer, List<String>> frontMap;	// 접두사 ( 와일드 카드가 뒤에 위치 )
	Map<Integer, List<String>> endMap;		// 접미사 ( 와일드 카드가 앞에 위치 )
	void removeDuplicatedKeyword(String[] words) {
		frontMap = new HashMap<>();
		endMap = new HashMap<>();
		
		for(String key : words) {
			int keyLen = key.length();
			
			frontMap.computeIfAbsent(keyLen, e -> new ArrayList<>()).add(key);
			endMap.computeIfAbsent(keyLen, e -> new ArrayList<>())
				.add(new StringBuilder(key).reverse().toString());
		}
		
		// 이진 탐색을 위해 정렬 
		for(int len : frontMap.keySet()) {
			Collections.sort(frontMap.get(len));
			Collections.sort(endMap.get(len));
		}
	}
}

/*
 * 노래 가사에 특정 키워드가 몇 개 포함되어 있는지 확인한다. 
 * 
 * 키워드는 와일드카드 문자중 하나인 ? 가 포함된 패턴 형태의 문자열을 뜻한다.
 * fro?? -> frodo, front, frost 가능
 * 		 -> frozen, frame 불가능
 * 
 * 가사에 사용된 모든 단어 배열 words 가 주어진다.
 * 찾고자하는 키워드 queries 가 주어진다. 
 * 각 키워드별로 매치된 단어가 몇 개인지 순서대로 배열에 담아 반환한다. 
 * 
 * 문자열 연산하지 않고 해시 값으로 연산하면 되려나? 
 */
