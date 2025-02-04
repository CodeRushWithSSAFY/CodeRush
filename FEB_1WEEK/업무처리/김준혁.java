package FEB_1WEEK_2;


import java.io.*;
import java.util.*;

import java.util.Queue;
import java.util.LinkedList;

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
	

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		height = sc.nextInt();
		taskNum = sc.nextInt();
		workDay = sc.nextInt();
		
		
		tree = BinaryTree.init(height, taskNum, sc);
		sum = 0;
		Node root = tree[0];
		
		
		int day = 1;
		while(day <= workDay) {
			
			// root 
			if(day % 2 == 1) {
				if(!root.taskLeftQueue.isEmpty()) {
					sum += root.taskLeftQueue.poll();
				}
			}else {
				if(!root.taskRightQueue.isEmpty()) {
					sum += root.taskRightQueue.poll();
				}
			}
			
			// not root
			for(int nodeIdx = 1; nodeIdx < BinaryTree.nodeNum; nodeIdx++) {
				Node currNode = tree[nodeIdx];
				if(currNode instanceof LeafNode) { // 리프 노드일때 
                    LeafNode leaf = (LeafNode)currNode;
					if(!leaf.taskQueue.isEmpty()) {
						if(leaf.isLeft) {
							tree[leaf.parentIdx].taskLeftQueue.add(leaf.taskQueue.poll());
						}else {
							tree[leaf.parentIdx].taskRightQueue.add(leaf.taskQueue.poll());
						}
					}
				}else if(day % 2 == 1) {	// 홀수 날짜일 때  
					if(!currNode.taskLeftQueue.isEmpty()) {
						if(currNode.isLeft) {
							tree[currNode.parentIdx].taskLeftQueue.add(currNode.taskLeftQueue.poll());
						}else {
							tree[currNode.parentIdx].taskRightQueue.add(currNode.taskLeftQueue.poll());
						}
					}
				}else {	// 짝수 날짜일 때  
					if(!currNode.taskRightQueue.isEmpty()) {
						if(currNode.isLeft) {
							tree[currNode.parentIdx].taskLeftQueue.add(currNode.taskRightQueue.poll());
						}else {
							tree[currNode.parentIdx].taskRightQueue.add(currNode.taskRightQueue.poll());
						}
					}
				
				}
			}
			
			day++;
		}
		
		
		
		System.out.println(sum);
		sc.close();
		
	}
}



class Node {
	
	
	public int childLeftIdx, childRightIdx;
	public int parentIdx;
	public Queue<Integer> taskLeftQueue;
	public Queue<Integer> taskRightQueue;
	public boolean isLeft;
	
	
	
	Node(boolean isLeft, int idx){
		this.taskLeftQueue = new LinkedList<Integer>();
		this.taskRightQueue = new LinkedList<Integer>();
		this.isLeft = isLeft;
		this.childLeftIdx = idx * 2 + 1;
		this.childRightIdx = idx * 2 + 2;
		this.parentIdx = (int)Math.floor((idx-1)/2);
	}
	
}

class LeafNode extends Node{
	
	public Queue<Integer> taskQueue;
	
	LeafNode(boolean isLeft, int idx) {
		super(isLeft, idx);
		this.taskQueue = new LinkedList<Integer>();
	}
	
}



class BinaryTree {
	
	public static Node[] tree;
	public static int nodeNum;
	
	
	public static Node[] init(int height, int taskNum, Scanner sc) {
		
		nodeNum = (int)Math.pow(2, height + 1) - 1;
		int leafNum = (int)Math.pow(2, height);
		int nonLeafNum = nodeNum - leafNum;
		
		tree = new Node[nodeNum];
		
		for(int i = 0; i < nonLeafNum; i++) {
			if(i%2 == 1) {	// 홀수 : LeftChild
				tree[i] = new Node(true, i);
			}else {
				tree[i] = new Node(false, i);
			}
		}
		
		for(int i = nonLeafNum; i < nodeNum; i++) {
			LeafNode leafNode;
			if(i%2 == 1) {	// 홀수 : LeftChild
				leafNode = new LeafNode(true, i);
			}else {
				leafNode = new LeafNode(false, i);
			}
			
			for(int j = 0; j < taskNum; j++) {
				leafNode.taskQueue.add(sc.nextInt());
			}
			
			tree[i] = leafNode;
		}
		
		
		return tree;
	}
	
}
