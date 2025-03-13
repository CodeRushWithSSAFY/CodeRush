package MAR.WEEK3.홀짝트리;

import java.util.*;

public class 박재환 {
	
	public static void main(String[] args) {
		int[] nodes = {11, 9, 3, 2, 4, 6};
		int[][] edges = {{9, 11}, {2, 3}, {6, 3}, {3, 4}};
		System.out.println(Arrays.toString(new Solution().solution(nodes, edges)));
	}

	/*
	 * 노드 관리를 어떻게?
	 * 주어지는 노드가 인덱스 순이 아닌, 랜덤한 숫자로 주어진다. 
	 * Map<Integer, List<>) 로 관리?
	 */
	static class Solution {
		Map<Integer, List<Integer>> graph; // 노드들의 연결 관계를 기록
	    Set<Integer> visited; // 체크가 완료된 노드를 저장
	    int normalTree, reverseTree; // 홀짝 트리, 역홀짝 트리 의 개수

	    public int[] solution(int[] nodes, int[][] edges) {
	        graph = new HashMap<>();

	        // Node 정보 입력
	        for (int node : nodes) {
	            graph.put(node, new ArrayList<Integer>());
	        }

	        // 연결 정보 입력
	        for (int[] edge : edges) {
	            int nodeA = edge[0];
	            int nodeB = edge[1];
	            graph.get(nodeA).add(nodeB);
	            graph.get(nodeB).add(nodeA);
	        }

	        normalTree = reverseTree = 0;
	        visited = new HashSet<>();

	        // 노드를 순차적으로 탐색한다.
	        for (int node : nodes) {
	            if (!visited.contains(node)) { // 아직 탐색하지 않은 노드만 탐색
	                Set<Integer> connNodes = new HashSet<>();
	                // 연결된 모든 노드를 구분하여 connNodes에 저장
	                separateGraph(node, -1, connNodes);

	                // 그래프 집합 확인 
//	                System.out.println(connNodes);
	                
	                /*
	                 * 문제 
	                 * 1. 각 노드를 루트 노드로 설정한다.
	                 * 		a. 자식 노드들을 차례로 탐색한다.
	                 * 			i. 해당 자식 노드 번호가 짝수라면
	                 * 				자식의 개수가 짝수 -> 짝수, 짝수 -> 짝수 노드
	                 * 				자식의 개수가 홀수 -> 짝수, 홀수 -> 역짝수 노드
	                 * 			ii. 해당 자식 노드 번호가 홀수라면
	                 * 				자식의 개수가 홀수 -> 홀수, 홀수 -> 홀수 노드
	                 * 				자식의 개수가 짝수 -> 홀수, 짝수 -> 역홀수 노드
	                 * 
	                 * 각 노드마다 돌아가며 루트로 지정한 경우를 탐색하는 결과 시간 초과가 발생함 
	                 * -> 한 번의 계산으로 노드들을 구한 뒤 판별해야함 
	                 */
	                defineTree(connNodes);
	            }
	        }

	        return new int[] { normalTree, reverseTree };
	    }
	    
	    
	    /*
	     * 각 노드가 루트 노드일 때의 진출 차수를 구한다.
	     * 
	     * 루트노드를 제외한 나머지 노드들은 진출 차수가 1 감소한다. 
	     * 짝수 노드 -> 역짝수 노드
	     * 홀수 노드 -> 역홀수 노드
	     * 역짝수 노드 -> 짝수 노드
	     * 역홀수 노드 -> 홀수 노드
	     * 의 규칙을 갖게 된다.
	     * 
	     * 홀짝 노드와 역홀짝 노드를 묶어서 생각한다. 
	     * 
	     * 만약 역홀짝 노드가 1개라면, 역홀짝 트리가 될 수 있고,
	     * 홀짝 노드가 1개라면, 홀짝 트리가 될 수 있다. 
	     * 
	     * 이때 트리는 역홀짝 트리, 홀짝 트리, 둘 다 될 수 있다. 
	     */
	    void defineTree(Set<Integer> connNodes) {
	    	int evenOdd = 0, reverseEvenOdd = 0;		// 홀짝, 역홀짝 노드 

	    	for(int node : connNodes) {
	    		if((node%2 == 0 && graph.get(node).size()%2 == 0) ||
	    				(node%2 == 1 && graph.get(node).size()%2 == 1)) {	// 홀짝 노드인 경우
	    			evenOdd++;
	    		} else if ((node%2 == 0 && graph.get(node).size()%2 == 1) ||
	    				(node%2 == 1 && graph.get(node).size()%2 == 0)) {	// 역홀짝 노드인 경우
	    			reverseEvenOdd++;
	    		}
	    	}
	    	
	    	// 역홀짝 혹은 홀짝 트리가 될 수 있는 경우
	    	if(evenOdd == 1) {	
	    		normalTree++;
	    	}
            if(reverseEvenOdd == 1) {
	    		reverseTree++;
	    	}
	    }
	    
	    /*
	     * 하나의 그래프를 구분한다.
	     * 연결되어 있는 모든 노드를 구분하여 Set에 저장한다.
	     *
	     * 현재 노드와 연결되어 있는 노드가 부모노드와 같다면 해당 간선은 탐색하지 않는다.
	     */
	    void separateGraph(int curNode, int parentNode, Set<Integer> connNodes) {
	        visited.add(curNode);
	        connNodes.add(curNode);

	        // 현재 노드와 연결되어 있는 모든 노드를 탐색한다.
	        for (int node : graph.get(curNode)) {
	            // 사이클 방지
	            if (node == parentNode) continue;

	            if (!visited.contains(node)) {
	                separateGraph(node, curNode, connNodes);
	            }
	        }
	    }
	}
}

/*
 * 루트 노드가 설정되지 않은 1개 이상의 트리가 있다. 
 * 
 * 노드 종류
 * 1. 홀수 노드 : 노드의 번호가 홀수이며 자식 노드의 개수가 홀수인 노드
 * 2. 짝수 노드 : 노드의 번호가 짝수이며 자식 노드의 개수가 짝수인 노드
 * 3. 역홀수 노드 : 노드의 번호가 홀수이며, 자식 노드의 개수가 짝수인 노드
 * 4. 역짝수 노두 : 노드의 번호가 짝수이며, 자식 노드의 개수가 홀수인 노드
 * 
 * 각 트리에 대해 루트 노드를 설정했을 때, 
 * 홀짝 트리가 될수 있는 트리의 개수
 * 역홀짝 트리가 될 수 있는 트리의 개수
 * 를 구해라 
 * 
 * 홀짝 트리 
 * -> 홀수 노드와 짝수 노드로만 이루어진 트리
 * 
 * 역홀짝 트리 
 * -> 역홀수 노드와 역짝수 노드로만 이루어진 트리 
 * 
 * 자식 노드가 없다면 홀/짝 트리
 * 
 * 결과값 
 * [홀짝 트리의 개수, 역홀짝 트리의 개수]
 * 
 * 1. 그래프를 구분하는 함수 
 * 
 * 2. 구분한 그래프에서 루트 노드를 변경하는 함수
 * 		a. 루트 노드에서 연결되어 있는 인접 노드들의 진입 차수를 1 감소 시킨다. 
 */