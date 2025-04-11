import java.io.*;
import java.util.*;

/*
 * 단순한 0/1 knapsack 문제
 * 하향식으로 접근하는 이유는 무제한으로 넣을 수 없기 때문
 * */
public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int productCount;
	static int maxWeight;
	static int[][] productInfo;
	
	static int weight, value;
	static int[] maxValueByWeight;
		
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		initInput();
		br.close();
		findMaxValueByWeight();
		bw.write(String.valueOf(maxValueByWeight[maxWeight]));
		bw.flush();
		bw.close();
	}
	
	private static void findMaxValueByWeight() {
		maxValueByWeight = new int[maxWeight + 1];
		
		for (int productIdx = 0; productIdx < productCount; productIdx++) {
			for (int weight = maxWeight; weight >= productInfo[productIdx][0]; weight--) {
				maxValueByWeight[weight] = Math.max(maxValueByWeight[weight],
									maxValueByWeight[weight - productInfo[productIdx][0]] + productInfo[productIdx][1]);
			}
		}
	}
	
	private static void initInput() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		productCount = Integer.parseInt(st.nextToken());
		maxWeight = Integer.parseInt(st.nextToken());
		
		productInfo = new int[productCount][2];
		for (int info = 0; info < productCount; info++) {
			st = new StringTokenizer(br.readLine().trim());
			weight = Integer.parseInt(st.nextToken());
			value = Integer.parseInt(st.nextToken());
			productInfo[info] = new int[] {weight, value};
		}
	}
}
