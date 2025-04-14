import java.io.*;
import java.util.*;

/*
  트라이 이용해서 풀이
  insert 메서드와 startWith 메서드만 구현
  일치하는지 확인하지 않아도 되서 단순히 map에 있는지 없는지만 확인하는 것으로 startWith 메서드를 구현했습니다.
*/
public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	static String input;
	
	static int stringCount;
	static int frontCount;
	static String[] isFrontArr;
	static int isFrontCount;
	static Trie trie;
	
	static class Node {
		Map<Character, Node> child;
		boolean isEndOfWord;
		
		public Node () {
			this.child = new HashMap<>();
			this.isEndOfWord = false;
		}
	}
	
	static class Trie {
		Node root;
		
		public Trie () {
			this.root = new Node();
		}
		
		public void insert(String str) {
			Node node = this.root;
			
			for (int idx = 0; idx < str.length(); idx++) {
				char c = str.charAt(idx);
				
				node.child.putIfAbsent(c, new Node());
				
				node = node.child.get(c);
			}
		}
		
		public boolean startWith(String str) {
			Node node = this.root;
			
			for (int idx = 0; idx < str.length(); idx++) {
				char c = str.charAt(idx);
				
				if (node.child.containsKey(c)) {
					node = node.child.get(c);
				} else {
					return false;
				}
			}
			return true;
		}
	}
	
		
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();

		br.close();
		
		checkFrontCount();
		
		bw.write(String.valueOf(isFrontCount));
		bw.flush();
		bw.close();
	}
	
	private static void checkFrontCount() {
		isFrontCount = 0;
		Arrays.sort(isFrontArr);
		
		for (int frontIdx = 0; frontIdx < frontCount; frontIdx++) {
			String startSubString = isFrontArr[frontIdx];
			if (trie.startWith(startSubString)) {
				isFrontCount++;
			}
		}
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		stringCount = Integer.parseInt(st.nextToken());
		frontCount = Integer.parseInt(st.nextToken());
		
		trie = new Trie();
		
		for (int stringIdx = 0; stringIdx < stringCount; stringIdx++) {
			input = br.readLine().trim();
			trie.insert(input);
		}

		isFrontArr = new String[frontCount];
		for (int stringIdx = 0; stringIdx < frontCount; stringIdx++) {
			input = br.readLine().trim();
			isFrontArr[stringIdx] = input;
		}
	}
}
