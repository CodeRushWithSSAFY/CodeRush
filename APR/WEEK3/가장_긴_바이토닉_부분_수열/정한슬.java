import java.util.*;
import java.io.*;

/*
 * 바이토닉 수열이란? 해당 인덱스 기준으로 왼쪽은 증가, 오른쪽은 감소하는 수열
 * 가장 긴 바이토닉 부분 수열을 구하기 위해선
 * 왼쪽 배열(증가 부분 수열)
 * 오른 쪽 배열(증가 감소 수열)을 만든 다음
 * 특정 인덱스에서 왼쪽 배열값과 오른쪽 배열값을 더한 값이 가장 긴 바이토닉 부분 수열일 것.
 * -> 이때 left[i] + right[i] 하고 + 1을 해야 자기자신까지 포함할 수 있다.
 * 
 * 증가 부분 수열을 만드는 방법
 * 1. n^2으로 dp
 * 2. 이분 탐색으로 가장 긴 부분 수열의 길이를 저장
 * */
public class 정한슬 {
	static BufferedReader br;
	static StringTokenizer st;
	static BufferedWriter bw;
	
	static int sequenceLength;
	static int[] sequence;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		
		int[] left = makeLeftArr();
		int[] right = makeRightArr();
		bw.write(String.valueOf(getMaxLengthBitonic(left, right)));
		bw.flush();
		bw.close();
	}
	
	private static int[] makeRightArr() {
		int[] right = new int[sequenceLength];
		// 증가하는 부분 수열 찾기
		for (int idx1 = 0; idx1 < sequenceLength; idx1++) {
			for (int idx2 = 0; idx2 < idx1; idx2++) {
				if (sequence[idx1] > sequence[idx2]) {
					right[idx1] = Math.max(right[idx1], right[idx2] + 1);
				}
			}
		}
//		System.out.println("right: " + Arrays.toString(right));
		
		return right;
	}
	
	private static int[] makeLeftArr() {
		int[] left = new int[sequenceLength];
		// 감소하는 수열 찾기
		for (int idx1 = sequenceLength - 1; idx1 >= 0; idx1--) {
			for (int idx2 = sequenceLength - 1; idx2 > idx1; idx2--) {
				if (sequence[idx1] > sequence[idx2]) {
					left[idx1] = Math.max(left[idx1], left[idx2] + 1);
				}
			}
		}
		
//		System.out.println("left: " + Arrays.toString(left));
		return left;
	}
	
	private static int getMaxLengthBitonic(int[] left, int[] right) {
		int maxLength = 0;
		
		for (int idx = 0; idx < sequenceLength; idx++) {
			maxLength = Math.max(left[idx] + right[idx], maxLength);
		}
		
		return maxLength + 1;
	}
	
	private static void initInput() throws IOException {
		sequenceLength = Integer.parseInt(br.readLine().trim());
		
		sequence = new int[sequenceLength];
		st = new StringTokenizer(br.readLine().trim());
		for (int idx = 0; idx < sequenceLength; idx++) {
			sequence[idx] = Integer.parseInt(st.nextToken());
		}
	}
}
