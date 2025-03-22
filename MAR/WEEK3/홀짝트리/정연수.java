import java.util.*;

class Solution {
    static class Node {
        int num;
        int adj;
        
        Node(int num, int adj) {
            this.num = num;
            this.adj = adj;
        }
        
        // 홀짝은 true, 역은 false
        boolean type() {
            return num % 2 == (adj - 1) % 2;
        }
    }
    
    static int[] parent;
    
    private static int getParent(int item) {
        if (parent[item] == item) {
            return item;
        }
        return parent[item] = getParent(parent[item]);
    }
    
    private static void union(int left, int right) {
        int leftParent = getParent(left);
        int rightParent = getParent(right);
        
        if (leftParent < rightParent) {
            parent[rightParent] = leftParent;
        } else {
            parent[leftParent] = rightParent;
        }
    }
    
    public int[] solution(int[] nodes, int[][] edges) {
        int[] answer = {0, 0};
        Map<Integer, Node> graph = new HashMap<>();
        int maxNode = 0;
        
        // 노드 생성 및 최대 노드 번호 찾기
        for (int node : nodes) {
            graph.put(node, new Node(node, 0));
            maxNode = Math.max(maxNode, node);
        }
        
        // 부모 배열 초기화
        parent = new int[maxNode + 1];
        for (int node : nodes) {
            parent[node] = node;
        }
        
        // 간선 정보 처리
        for (int[] edge : edges) {
            graph.get(edge[0]).adj++;
            graph.get(edge[1]).adj++;
            union(edge[0], edge[1]);
        }
        
        // 트리 그룹화
        Map<Integer, List<Integer>> forestMap = new HashMap<>();
        for (int node : nodes) {
            int rootParent = getParent(node);
            forestMap.putIfAbsent(rootParent, new ArrayList<>());
            forestMap.get(rootParent).add(node);
        }
        
        // 각 트리에 대한 홀짝 패턴 분석
        for (List<Integer> tree : forestMap.values()) {
            int oddEvenCount = 0; // 홀짝 패턴 카운트
            int reverseCount = 0; // 역홀짝 패턴 카운트
            
            for (int nodeId : tree) {
                Node node = graph.get(nodeId);
                if (node.type()) {
                    oddEvenCount++;
                } else {
                    reverseCount++;
                }
            }
            
            // 홀짝 트리 판별
            if (oddEvenCount == tree.size() - 1 && reverseCount == 1) {
                answer[0]++;
            } 
            // 역홀짝 트리 판별 이때 else if 로 하면 안됨
            // 1 1 일 경우 둘다에 1씩 더해줘야함
            if (reverseCount == tree.size() - 1 && oddEvenCount == 1) {
                answer[1]++;
            }
        }
        
        return answer;
    }
}
