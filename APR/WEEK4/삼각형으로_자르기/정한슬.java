import java.util.*;
import java.io.*;

/*
 * 3점의 좌표를 알때, 삼각형 넓이 구하기
 * 1. S=(도형 둘레 위의 점의 개수)× 1/ 2 - 1 + (도형 내부의 점의 개수)
 * 
 * 2. S = 1/2 * |x1*y2+ x2*y3 + x3*y1 - x2*y1 - x3*y2 - x1*y3|
 * */
public class 정한슬 {
	static final int SELECT_COUNT = 3;
	
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	static int dotCount;
	static int[][] polygonDots;
	static double maxArea;
	static int[] selectPoints;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		initInput();
		selectPoints = new int[SELECT_COUNT];
		combination(0, 0);
		bw.write(String.valueOf(maxArea));
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void combination(int startIdx, int selectCount) {
		if (selectCount == SELECT_COUNT) {
//			for (int count = 0; count < SELECT_COUNT; count++) {
//				System.out.println("x: " + polygonDots[selectPoints[count]][0] + " y: " +  polygonDots[selectPoints[count]][1]);
//			}
//			
			maxArea = Math.max(maxArea, calMaxArea());
//			System.out.println();
			return;
		}
		
		for (int idx = startIdx; idx < dotCount; idx++) {
			selectPoints[selectCount] = idx;
			combination(idx + 1, selectCount + 1);
		}
	}
	
	private static double calMaxArea() { 
		//1번으로 하려다가 안에 있는 점 구하기 귀찮아서 pass.... 
//		float area = 0.0f;
		int[] point1 = polygonDots[selectPoints[0]];
		int[] point2 = polygonDots[selectPoints[1]];
		int[] point3 = polygonDots[selectPoints[2]];
//		
//		float inclination1 = 1.0f* Math.abs(point1[1] - point2[1]) / Math.abs(point1[0] - point2[0]);
//		float inclination2 = 1.0f* Math.abs(point2[1] - point3[1]) / Math.abs(point2[0] - point3[0]);
//		float inclination3 = 1.0f* Math.abs(point1[1] - point3[1]) / Math.abs(point1[0] - point3[0]);
//		
//		int inLineCount = 0;
//		for (int x = point1[0]; x < point2[0]; x++) {
//			if (inclination1 * x >= Math.max(point1[1], point2[1])) break;
//			inLineCount++;
//		}
//		
//		for (int x = point2[0]; x < point3[0]; x++) {
//			if (inclination2 * x >= Math.max(point2[1], point3[1])) break;
//			inLineCount++;
//		}
//		
//		for (int x = point1[0]; x < point3[0]; x++) {
//			if (inclination3 * x >= Math.max(point1[1], point3[1])) break;
//			inLineCount++;
//		}
//		
//		int inAreaCount = 0;
//		for (int x = point1[0]; x < point3[0]; x++) {
//			if (inclination3 * x >= Math.max(point1[1], point3[1])) break;
//			inLineCount++;
//		}
//		
//		area += inLineCount* (0.5f) - 1 + inAreaCount *(1.0f);

		int s1 = point1[0]*point2[1] + point2[0]*point3[1] + point3[0]*point1[1];
		int s2 = point2[0]*point1[1] + point3[0]*point2[1] + point1[0]*point3[1];
		double area = 0.5 * Math.abs(s1 - s2);	
		return area;
	}
	
	private static void initInput() throws IOException {
		dotCount = Integer.parseInt(br.readLine());
		
		polygonDots = new int[dotCount][2];

		for (int dot = 0; dot < dotCount; dot++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			polygonDots[dot] = new int[] {x, y};
		}
	}
}
