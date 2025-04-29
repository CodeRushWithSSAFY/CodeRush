import java.util.*;

public class 정한슬 {
	private final static Character WILD = '?';
	
	static class Trie{
		Node root;
		
		public Trie() {
			root = new Node();
		}
		
		public void insert(String str) {
			
			Node node = this.root;
			for(char c : str.toCharArray()) {
				if (!node.child.containsKey(c)) {
					node.child.put(c, new Node());
				} 
				node = node.child.get(c);
			}
			// 맨 마지막의 node가 단어의 마지막임을 명시
			node.isEndOfWord = true;
		}
		
		public int isStartWith(String starter) {
			Node node = this.root;

			int checkLength = starter.length();
			char[] chars = starter.toCharArray();
			for(int idx = 0; idx < chars.length; idx++) {
				char c = chars[idx];
				if (c == WILD) {
					checkLength -= idx;
					break;
				} else {
					if (!node.child.containsKey(c)) {
						return 0;
					} 
					node = node.child.get(c);
				}
			}
			
//			System.out.println("fitWordCount: " + fitWordCount);
//			System.out.println("checkLength: " + checkLength);
			
			// 남은 길이만큼 내려가며 탐색 시작
            return countMatchedWords(node, checkLength);
		}
		
		private int countMatchedWords(Node node, int depth) {
		    if (depth == 0) {
		        return node.isEndOfWord ? 1 : 0;
		    }

		    int count = 0;
		    for (Node next : node.child.values()) {
		        count += countMatchedWords(next, depth - 1);
		    }
		    return count;
		}
	}
	
	static class ReverseTrie {
		Node root;
		
		public ReverseTrie() {
			root = new Node();
		}
		
		public void insert(String str) {
			char[] chars = str.toCharArray();
			Node node = this.root;
			for(int idx = chars.length - 1; idx >= 0; idx--) {
				char c = chars[idx];
				if (!node.child.containsKey(c)) {
					node.child.put(c, new Node());
				} 
				node = node.child.get(c);
			}
			// 맨 마지막의 node가 단어의 마지막임을 명시
			node.isEndOfWord = true;
		}
		
		public int isEndWith(String ender) {
			Node node = this.root;
			
			char[] chars = ender.toCharArray();
			int checkLength = 0;
			for(int idx = chars.length - 1; idx >= 0; idx--) {
				char c = chars[idx];
				if (c == WILD) {
					checkLength = idx + 1;
//					System.out.println("idx: " + idx);
					break;
				} else {
					if (!node.child.containsKey(c)) {
						return 0;
					} 
					node = node.child.get(c);
				}
			}
			
//			System.out.println("fitWordCount: " + fitWordCount);
//			System.out.println("checkLength: " + checkLength);
//			System.out.println("ReverseTrie EndWith!! checkLength: " + checkLength);
			// 남은 길이만큼 내려가며 탐색 시작
            return countMatchedWords(node, checkLength);
        }
		
		private int countMatchedWords(Node node, int depth) {
		    if (depth == 0) {
		        return node.isEndOfWord ? 1 : 0;
		    }

		    int count = 0;
		    for (Node next : node.child.values()) {
		        count += countMatchedWords(next, depth - 1);
		    }
		    return count;
		}
	}
	
	static class Node {
		Map<Character, Node> child;		
		boolean isEndOfWord;
		
		public Node() {
			child = new HashMap<>();
			isEndOfWord = false;
		}
		
		@Override
		public String toString() {
			return child.toString();
		}
	}
	
	static Map<Integer, Trie> trieMap;
	static Map<Integer, ReverseTrie> reverseTrieMap;
	
	private int findWordCount(String str) {
		char[] chars = str.toCharArray();
		
		int len = str.length();
	
		if (chars[0] == WILD) { // ??로 시작하는 단어 일때, endWith 메서드를 사용해서 찾아야한다.
			return reverseTrieMap.get(len) == null ? 0 : reverseTrieMap.get(len).isEndWith(str);
		} 
		// ??으로 끝나는 단어일때 그럼 startWith 메서드로 fit한 단어를 찾아야함.
		return trieMap.get(len) == null ? 0 : trieMap.get(len).isStartWith(str);
	}
	
	public int[] solution(String[] words, String[] queries) {
		int length = queries.length;
		int[] answer = new int[length];
		
		trieMap = new HashMap<>();
		reverseTrieMap = new HashMap<>();		
		for (String word : words) {
			int len = word.length();
			
			trieMap.putIfAbsent(len, new Trie());
			reverseTrieMap.putIfAbsent(len, new ReverseTrie());
			
			trieMap.get(len).insert(word);
			reverseTrieMap.get(len).insert(word);
		}
		
		Map<String, Integer> checkDuplication = new HashMap<>();
		for (int idx = 0; idx < length; idx++) {
//			System.out.println("find idx: " + idx);
			String query = queries[idx];
			if (checkDuplication.get(query) == null) {
				answer[idx] = findWordCount(queries[idx]);
				checkDuplication.put(query, answer[idx]);
			} else {
				answer[idx] = checkDuplication.get(query);
			}
		}
        return answer;
    }
	
	public static void main(String[] args) {
		PROG_가사검색_정한슬 sol = new PROG_가사검색_정한슬();
		String[] qWords = {"frodo", "front", "frost", "frozen", "frame", "kakao"};
		String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
		System.out.println(Arrays.toString(sol.solution(qWords, queries)));
	}
}
