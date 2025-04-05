import java.util.*;
import java.io.*;

public class 정한슬{
	static BufferedReader br;
	static StringTokenizer st;
	
	static int sequenceLength;
	static int[] sequence;
	static int[] checkSequence;
	static int maxCount = 0;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		initInput();
		br.close();
	
		checkIncreaseCount();
		for (int value : checkSequence) {
			maxCount = Math.max(value, maxCount);
		}
		System.out.println(maxCount);
	}
	
	private static void checkIncreaseCount() {
		checkSequence = new int[sequenceLength];
		Arrays.fill(checkSequence, 1); // 자기 자신은 항상 포함되므로
		
		
		for (int idx = 0; idx < sequenceLength; idx++) {
			for (int idx2 = 0; idx2 < idx; idx2++) {
				if (sequence[idx] > sequence[idx2]) {
					checkSequence[idx] = Math.max(checkSequence[idx], checkSequence[idx2] + 1);
				}
			}
		}
	}
	
	private static void initInput() throws IOException {
		sequenceLength = Integer.parseInt(br.readLine().trim());
		sequence = new int[sequenceLength];
		
		st = new StringTokenizer(br.readLine().trim());
		for (int valueIdx = 0; valueIdx < sequenceLength; valueIdx++) {
			sequence[valueIdx] = Integer.parseInt(st.nextToken());
		}
	}
}
