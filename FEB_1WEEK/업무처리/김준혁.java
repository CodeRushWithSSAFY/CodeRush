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
 * 시험때문에 주석은 나중에 달겠습니다...ㅠㅠ  
 * 
 */




public class Main {
	
	public static int sum;
	public static Node[] tree;
	public static int height; 	// H
	public static int taskNum; 	// K
	public static int workDay; 	// R
	

	public static void main(String[] args) throws IOException {
		
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
			}else {				// 짝수 날짜 : 오른쪽 큐 확인
				if(!rootNode.taskRightQueue.isEmpty()) {
					sum += rootNode.taskRightQueue.poll();
				}
			}
			
			// 일반 노드 => 노드 배열 순회 
			for(int nodeIdx = 1; nodeIdx < BinaryTree.nonLeafNum; nodeIdx++) {
				Node currNode = tree[nodeIdx];				// 현재 노드
				Node parentNode = tree[currNode.parentIdx]; // 부모 노드
				
				if(oddDay) {	// 홀수 날짜일 때 : 왼쪽 큐 확인
					if(!currNode.taskLeftQueue.isEmpty()) {
						if(currNode.isLeft) {	// 본인이 왼쪽 자식이면 부모의 왼쪽 큐에 업무 추가
							parentNode.taskLeftQueue.add(currNode.taskLeftQueue.poll());
						}else {					// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가
							parentNode.taskRightQueue.add(currNode.taskLeftQueue.poll());
						}
					}
				}else {						// 짝수 날짜일 때 : 오른쪽 큐 확인
					if(!currNode.taskRightQueue.isEmpty()) {
						if(currNode.isLeft) {	// 본인이 왼쪽 자식이면 부모의 왼쪽 큐에 업무 추가
							parentNode.taskLeftQueue.add(currNode.taskRightQueue.poll());
						}else {					// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가
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
					}else {				// 본인이 오른쪽 자식이면 부모의 오른쪽 큐에 업무 추가 
						parentNode.taskRightQueue.add(leaf.taskQueue.poll());
					}
				}
			}
			
			day++;
		}
		
		System.out.println(sum);
		
	}
}



class Node {
	
	public int parentIdx;
	public Queue<Integer> taskLeftQueue;
	public Queue<Integer> taskRightQueue;
	public boolean isLeft;
	
	
	Node(boolean isLeft, int idx){
		this.taskLeftQueue = new ArrayDeque<Integer>();
		this.taskRightQueue = new ArrayDeque<Integer>();
		this.isLeft = isLeft;
		this.parentIdx = (int)Math.floor((idx-1)/2);
	}
	
}


class LeafNode extends Node{
	
	public Queue<Integer> taskQueue;
	
	LeafNode(boolean isLeft, int idx) {
		super(isLeft, idx);
		this.taskQueue = new ArrayDeque<Integer>();
	}
	
}



class BinaryTree {
	
	public static Node[] tree;
	public static int nodeNum;
	public static int leafNum;
	public static int nonLeafNum;
	
	
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
