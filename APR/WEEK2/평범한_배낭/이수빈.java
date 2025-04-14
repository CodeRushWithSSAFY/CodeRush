import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 	BOJ_12865_평범한배낭
 */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	
	static int itemCnt, maxWeight;
	static int[] weight, value;
	static int[] knapsack; // 무게 당 가치
	
	public static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine().trim());
		itemCnt = Integer.parseInt(st.nextToken());
		maxWeight = Integer.parseInt(st.nextToken());
		
		weight = new int[itemCnt];
		value = new int[itemCnt];
		
		for (int idx = 0; idx < itemCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			weight[idx] = Integer.parseInt(st.nextToken());
			value[idx] = Integer.parseInt(st.nextToken());
		}
	}
	
	public static void main(String[] args) throws IOException {
		init();
		
		knapsack = new int[maxWeight + 1];
		
		for (int itemIdx = 0; itemIdx < itemCnt; itemIdx++) {
			int curItemWeight = weight[itemIdx];
			int curItemValue = value[itemIdx];

			for (int w = maxWeight; w >= curItemWeight; w--) {
				// 현재 물건을 넣은 것이 최대인지
				knapsack[w] = Math.max(knapsack[w], knapsack[w - curItemWeight] + curItemValue);
			}
		}
		
		System.out.println(knapsack[maxWeight]);

	}
}
