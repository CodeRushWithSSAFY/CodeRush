
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * CodeRush 1주차 3번 문제 - 교차로   
 * 
 * 
 * #0. 클래스 및 변수  
 * 	0-1. class Road : 각 도로(A, B, C, D)를 표현하는 클래스
 * 		- Road rightRoad		: 오른쪽 도로 객체
 * 		- boolean rightIsEmpty 	: 오른쪽 도로가 비었는지 여부 
 * 		- Queue<Car> queue 		: 현재 도로에 진입한 차량을 보관하는 큐 
 * 
 * 	0-2. class Car : 각 차량을 표현하는 클래스   
 * 		- int time 	 : 진입 시간
 * 		- char road  : 진입 도로 
 * 		- int number : 차량 번호 
 *  
 *  0-3. Queue<Car> carList : 모든 차량(Car) 객체를 보관하는 큐  
 *  	-  모든 차량 정보를 입력 받아 Car 객체를 만들고, carList에 추가
 *  
 *  0-4. int t : 진행된 시간을 나타내는 변수  
 *  
 *  0-5. int[] result : 차량이 통과한 시간을 저장하는 배열 
 *  
 * 
 * #1. solve() 메서드
 * 
 * 	1-1. 반복문 : 모든 차량이 통과하거나 교착 상태가 될 때 까지 
 * 		1-1-1. carList를 peek -> 맨 앞 Car 객체의 진입시간과 t를 비교
 * 			=> if(Car.time == t) => Car.time 값이 t와 다른 Car가 나올때까지 carList에서 Car 객체를 꺼내, 해당 도로에 추가  
 * 		
 * 		1-1-2. 교차로 1회 통과 (passCar()메서드)
 * 			=> false를 반환받았다면 result의 남은 인덱스를 모두 -1로 수정 후 메서드 종료   
 * 
 * 		1-1-3. 진행시간 t를 1 증가 
 * 
 * 
 * #2. passCar(boolean carListIsEmpty) 메서드  
 * 	2-1. 모든 Road 객체에 대해서
 * 		2-1-1. 자신의 큐가 비었다면 왼쪽 도로에 접근 -> rightIsEmpty를 true로 바꾸기  
 * 		2-1-2. 자신의 큐가 비어있지 않다면 왼쪽 도로에 접근 -> rightIsEmpty를 false로 바꾸기
 * 		2-1-3. 모든 도로의 큐가 비어있지 않다면 교착상태 -> false를 반환 
 * 
 * 	2-2. rightIsEmpty가 true인 도로는 큐에서 Car 하나 꺼내기  
 *  	=> result[통과한 Car 번호] = 현재 시각
 *  	=> 통과한 Car 개수(passedNum)가 전체 차량 개수와 같다면 모든 차량 통과 완료 -> false 반환  
 *  
 *  2-3. 교착 상태라면 false를 반환하고, result의 남은 인덱스 모두 -1로 수정해야 함(solve 메서드에서)
 * 
 * 
 * #3. 정답 출력 : result[] 의 값을 차례대로 출력  
 *
 * 
 */


public class Main {
	
	public static int carNum; 	// N
	
	public static int t = 0;
	public static int passedNum = 0;  // 통과한 차량 개수 
	public static int[] result;
	
	public static Road[] roads = new Road[4];
	
	
	public static void main(String[] args) throws IOException {
		
		// Road 객체들의 rightRoad 설정 
		for(int roadIdx = 0; roadIdx < 4; roadIdx++) {
			roads[roadIdx] = new Road();
		}
				
		roads[0].rightRoad = roads[3];	// A의 오른쪽 : D 
		roads[1].rightRoad = roads[0];	// B의 오른쪽 : A 
		roads[2].rightRoad = roads[1];	// C의 오른쪽 : B 
		roads[3].rightRoad = roads[2];	// D의 오른쪽 : C 
		
		// 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		carNum = Integer.parseInt(st.nextToken());
		result = new int[carNum];
		for(int idx = 0; idx < carNum; idx++) {
			result[idx] = -1;
		}
		
		for(int idx = 0; idx < carNum; idx++) {
			st = new StringTokenizer(br.readLine());
			int time = Integer.parseInt(st.nextToken());
			int roadIdx = st.nextToken().charAt(0) - 'A';
			int number = idx;
			
			// 해당 도로의 carList에 추가 
			roads[roadIdx].carList.add(new Car(time, number));
		}
		
		
		// 문제 풀이 메서드 호출 
		solve();
		
		for(int answer : result) {
			System.out.println(answer);
		}
	}
	
	
	public static void solve() {
		boolean finish;
		
		while(true) {
			for(Road road : roads) {	 
				Car firstCar = road.carList.peek();
				// Car가 있으면서 현재 시간 t와 Car의 진입시간이 같으면 => waitQueue에 추가 
				if(firstCar != null && firstCar.time == t) {
					road.waitQueue.add(road.carList.poll());
				}
			}
			
			finish = passCar();
			
			if(finish) break;
			
			t++;
			
		}
		
	}
	
	
	public static boolean passCar() {
		int notEmptyRoadNum = 0;
		
		for(Road road : roads) {
			if(road.rightRoad.waitQueue.isEmpty()) {
				if(!road.waitQueue.isEmpty()) {
					Car car = road.waitQueue.peek();
					result[car.number] = t;
					road.carPassed = true;
					passedNum++;
				}
			}else {
				notEmptyRoadNum++;
			}
		}
		
		for(Road road : roads) {
			if(road.carPassed) {
				road.waitQueue.poll();
				road.carPassed = false;
			}
		}
		
		
		if(notEmptyRoadNum == 4 || passedNum == carNum) return true;
		
		return false;
	}
	
	
}




class Road{
	public Road rightRoad;  
	public Queue<Car> waitQueue;
	public Queue<Car> carList;
	public boolean carPassed;
	
	public Road() {
		this.carList = new ArrayDeque<Car>();
		this.waitQueue = new ArrayDeque<Car>();
		this.carPassed = false;
	}
}



class Car{
	public int time;
	public int number; 
	
	public Car(int time, int number) {
		this.time = time;
		this.number = number;
	}
}





