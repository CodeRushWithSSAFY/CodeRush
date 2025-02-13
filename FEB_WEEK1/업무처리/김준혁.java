package Solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import java.util.Queue;

/**
 * 
 * CodeRush 1주차 2번 문제 - 업무 처리
 * 
 * ## 이진 트리
 * 
 * #0. 클래스
 * 	- class Node : 부모 노드의 인덱스, 왼쪽/오른쪽 업무 큐, (자신이)왼쪽 자식인지 여부 포함
 * 	- class LeafNode(extends Node) : 왼쪽/오른쪽 구분이 없는 업무 큐 포함. class Node를 상속받음 
 * 	- class BinaryTree : 이진 트리 배열과 트리의 정보(높이, 전체 노드 개수 등) 포함. init() 메서드로 이진 트리 초기화 가능
 *	
 * #1. 이진 트리를 표현하는 1차원 배열 tree 순회
 *	1-1. 루트 노드 (0번 인덱스)
 *		- 홀수 날짜에는 왼쪽 큐(taskLeftQueue), 짝수 날짜에는 오른쪽 큐(taskRightQueue) 확인 후 업무가 있으면 꺼내서 sum에 합치기
 *
 *	1-2. 일반 노드 (1번 인덱스 ~ (non 리프 노드 개수 - 1) 인덱스)
 *		- 홀수 날짜에는 왼쪽 큐(taskLeftQueue), 짝수 날짜에는 오른쪽 큐(taskRightQueue) 확인 후 업무가 있으면 꺼내서 부모 노드의 업무 큐에 전달 
 *		- 이때 노드의 isLeft 값을 확인하여 true면 왼쪽 업무 큐에, false면 오른쪽 업무 큐에 전달
 *
 *	1-3. 리프 노드 (non 리프 노드 개수 인덱스 ~ 전체 노드 개수 인덱스)
 *		- 날짜 상관 없이 업무 큐(taskQueue) 확인 후 업무가 있으면 꺼내서 부모 노드의 업무 큐에 전달
 *		- 이때 노드의 isLeft 값을 확인하여 true면 왼쪽 업무 큐에, false면 오른쪽 업무 큐에 전달		
 *
 * #2. 최종 정답인 sum을 출력
 *
 */




public class Main {
	
	public static int sum;		// 완료한 업무 번호의 합 (최종 정답)
	public static Node[] tree;	// 이진트리 => Node 객체를 원소로 가지는 1차원 배열
	public static int height; 	// H : 이진 트리 높이
	public static int taskNum; 	// K : 각 리프노드에 주어지는 업무의 개수
	public static int workDay; 	// R : 일을 할 수 있는 날의 수
	

	public static void main(String[] args) throws IOException {

		// 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
       
        	height = Integer.parseInt(st.nextToken());
        	taskNum = Integer.parseInt(st.nextToken());
        	workDay = Integer.parseInt(st.nextToken());

		
		// 이진 트리 초기화 
		tree = BinaryTree.init(height, taskNum, st, br);
		sum = 0;
		
		
		Node rootNode = tree[0];
		int day = 1;
		
		while(day <= workDay) {
			boolean oddDay = day % 2 == 1;
			
			// rootNode 
			if(oddDay) {	// 홀수 날짜 : 왼쪽 큐 확인
				if(!rootNode.taskLeftQueue.isEmpty()) {
					sum += rootNode.taskLeftQueue.poll();
				}
			}else {		// 짝수 날짜 : 오른쪽 큐 확인
				if(!rootNode.taskRightQueue.isEmpty()) {
					sum += rootNode.taskRightQueue.poll();
				}
			}
			
			// 일반 노드 => 노드 배열 순회 
			for(int nodeIdx = 1; nodeIdx < BinaryTree.nonLeafNum; nodeIdx++) {
				Node currNode = tree[nodeIdx];			// 현재 노드
				Node parentNode = tree[currNode.parentIdx]; 	// 부모 노드
				
				if(oddDay) {	// 홀수 날짜일 때 : 왼쪽 큐 확인
					if(!currNode.taskLeftQueue.isEmpty()) {
						if(currNode.isLeft) {	// 본인이 왼쪽 자식이면 부모의 왼쪽 큐에 업무 추가
							parentNode.taskLeftQueue.add(currNode.taskLeftQueue.poll());
						}else {			// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가
							parentNode.taskRightQueue.add(currNode.taskLeftQueue.poll());
						}
					}
				}else {		// 짝수 날짜일 때 : 오른쪽 큐 확인
					if(!currNode.taskRightQueue.isEmpty()) {
						if(currNode.isLeft) {	// 본인이 왼쪽 자식이면 부모의 왼쪽 큐에 업무 추가
							parentNode.taskLeftQueue.add(currNode.taskRightQueue.poll());
						}else {			// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가
							parentNode.taskRightQueue.add(currNode.taskRightQueue.poll());
						}
					}
				
				}
			}
			
			// 리프 노드일때 => 날짜 상관없이 업무 처리
			for(int nodeIdx = BinaryTree.nonLeafNum; nodeIdx < BinaryTree.nodeNum; nodeIdx++) {
                		LeafNode leaf = (LeafNode)tree[nodeIdx];	// 현재 노드
                		Node parentNode = tree[leaf.parentIdx]; 	// 부모 노드
				if(!leaf.taskQueue.isEmpty()) {
					if(leaf.isLeft) {	// 본인이 왼쪽 자식이면 부모의 왼쪽 큐에 업무 추가 
						parentNode.taskLeftQueue.add(leaf.taskQueue.poll());
					}else {			// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가 
						parentNode.taskRightQueue.add(leaf.taskQueue.poll());
					}
				}
			}
			// 날짜 증가
			day++;
		}
		
		System.out.println(sum);
		
	}
}


// 노드(직원 한 명)를 표현하는 클래스
class Node {
	
	public int parentIdx;			// 부모 노드의 인덱스
	public Queue<Integer> taskLeftQueue;	// 왼쪽 업무 큐
	public Queue<Integer> taskRightQueue;	// 오른쪽 업무 큐
	public boolean isLeft;			// 자신이 왼쪽 자식인지 여부
	
	
	Node(boolean isLeft, int idx){
		this.taskLeftQueue = new ArrayDeque<Integer>();
		this.taskRightQueue = new ArrayDeque<Integer>();
		this.isLeft = isLeft;
		this.parentIdx = (int)Math.floor((idx-1)/2);
	}
	
}

// 리프 노드(말단 직원)를 표현하는 클래스
class LeafNode extends Node{
	
	public Queue<Integer> taskQueue;	// 업무 큐(말단 직원용 => 왼쪽/오른쪽 구분 없음)
	
	LeafNode(boolean isLeft, int idx) {
		super(isLeft, idx);
		this.taskQueue = new ArrayDeque<Integer>();
	}
}


// 이진 트리를 표현하는 클래스
class BinaryTree {
	
	public static Node[] tree;	// 이진 트리 본체 (1차원 배열)
	public static int nodeNum;	// 전체 노드 개수
	public static int leafNum;	// 리프 노드 개수
	public static int nonLeafNum;	// non 리프 노드 개수

	
	// 이진 트리를 초기화하는 메서드
	// 이진 트리(tree)에 실제 Node 객체를 생성하여 추가
	// 홀수 인덱스에서 생성되는 Node 객체는 isLeft를 true로 초기화 (짝수 인덱스에서는 false)
	// 리프 노드의 경우 각 노드마다 주어진 업무를 입력 받음
	// 이진 트리 tree를 반환
	public static Node[] init(int height, int taskNum, StringTokenizer st, BufferedReader br) throws IOException {
		
		nodeNum = (int)Math.pow(2, height + 1) - 1;
		leafNum = (int)Math.pow(2, height);
		nonLeafNum = nodeNum - leafNum;
		
		tree = new Node[nodeNum];
		
		// 일반 노드
		for(int i = 0; i < nonLeafNum; i++) {
			if(i%2 == 1) {	// 홀수 : LeftChild
				tree[i] = new Node(true, i);
			}else {
				tree[i] = new Node(false, i);
			}
		}
		
		// 리프 노드 
		for(int i = nonLeafNum; i < nodeNum; i++) {
			LeafNode leafNode;
			if(i%2 == 1) {	// 홀수 : LeftChild
				leafNode = new LeafNode(true, i);
			}else {
				leafNode = new LeafNode(false, i);
			}
			
			// 업무 입력받기
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < taskNum; j++) {
				leafNode.taskQueue.add(Integer.parseInt(st.nextToken()));
			}
			
			tree[i] = leafNode;
		}
		
		
		return tree;
	}
	
}
