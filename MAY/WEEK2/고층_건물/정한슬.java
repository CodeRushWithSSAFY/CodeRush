import java.util.*;
import java.io.*;

/*
 * 가장 많은 고층 건물이 보일 때를 선택하려면?? 
 * 건물 A와 B 사이에서 보이는 건물 수를 카운팅하는 방법
 * 1. 기울기 = (By - Ah) / (Bx - Ax) = a 를 구한다.
 * 2. y = a * (x - Ax) + Bx 기울기를 구한 것을 이용하여 기존 1차 방정식을 만든다.
 * 3. 만일 카운팅 할 빌딩(A ~ B 사이 빌딩)의 높이가 1차 방정식 y값보다 높거나 같으면 그 빌딩 이후는 보이지 않게 된다.
 * 4. idx별로 0 ~ buildingCount, 왼쪽/ 오른쪽을 각각 보이는 빌딩 수를 구하고 max 값 갱신
 * 
 * 이해하기 쉬운 예제
 * idx:      0   1   2   3   4
 * height:   1   3   1   4   2
 * */
public class 정한슬 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int buildingCount;
	static int[] buildings;
	static int maxBuildingCount = 0;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		br.close();
		
		checkBuildings();
		
		bw.write(String.valueOf(maxBuildingCount));
		bw.flush();
		bw.close();
	}
	
	private static void checkBuildings() {
	    for (int buildingIdx = 0; buildingIdx < buildingCount; buildingIdx++) {
	        int count = 0;

	        // 왼쪽
	        for (int leftIdx = buildingIdx - 1; leftIdx >= 0; leftIdx--) {
	            if (isVisible(buildingIdx, leftIdx)) count++;
//                else break; // 바로 안끊는 이유는, 기울기가 변할 때마다 달라질 수 있기 때문
	        }

	        // 오른쪽
	        for (int rightIdx = buildingIdx + 1; rightIdx < buildingCount; rightIdx++) {
	            if (isVisible(buildingIdx, rightIdx)) count++;
//                else break; 
	        }

	        maxBuildingCount = Math.max(maxBuildingCount, count);
	    }
	}

	private static boolean isVisible(int from, int to) {
	    int min = Math.min(from, to);
	    int max = Math.max(from, to);
	    double baseSlope = (double)(buildings[to] - buildings[from]) / (to - from);

	    for (int mid = min + 1; mid < max; mid++) {
	        double expectedHeight = buildings[from] + baseSlope * (mid - from);
	        if (buildings[mid] >= expectedHeight) {
	            return false; // 시야를 가림
	        }
	    }

	    return true; // 모두 통과함 = 보임
	}
	
	private static void initInput() throws IOException {
		buildingCount = Integer.parseInt(br.readLine().trim());
		
		buildings = new int[buildingCount];
		st = new StringTokenizer(br.readLine().trim());
		for (int buildingIdx = 0; buildingIdx < buildingCount; buildingIdx++) {
			buildings[buildingIdx] = Integer.parseInt(st.nextToken());
		}
	}
}
