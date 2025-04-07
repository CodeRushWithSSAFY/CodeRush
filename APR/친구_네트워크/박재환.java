package APR.친구_네트워크;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        int TC = Integer.parseInt(br.readLine().trim());
        for(int testCase=0; testCase<TC; testCase++) {
            init();
        }
        br.close();
        System.out.println(sb);
    }

    static StringTokenizer st;
    static int relationCnt;		// 각 테스트에서의 관계의 수
    static Map<String, Integer> nameMap;	// 각 사람의 이름 -> 인덱스 저장 정보
    static int idx;							// 사람의 이름과 매핑할 인덱스
    static void init() throws IOException {
        idx = 0;
        nameMap = new HashMap<>();
        relationCnt = Integer.parseInt(br.readLine().trim());

        make();

        while(relationCnt-- > 0) {
            st = new StringTokenizer(br.readLine().trim());

            int nameA = convertName(st.nextToken());
            int nameB = convertName(st.nextToken());

            findNetWorking(nameA, nameB);
        }
    }

    /*
     * Union-Find 를 사용한다.
     */
    static void findNetWorking(int nodeA, int nodeB) {
        union(nodeA, nodeB);

        sb.append(sizes[find(nodeA)]).append('\n');
    }

    static int[] parents;
    static int[] sizes;
    static void make() {
        parents = new int[relationCnt*2];
        sizes = new int[relationCnt*2];

        for(int idx=0; idx<relationCnt*2; idx++) {
            parents[idx] = idx;
            sizes[idx] = 1;		// 자기 자신만 포함
        }
    }

    static int find(int node) {
        if(parents[node] == node) return node;

        return parents[node] = find(parents[node]);
    }

    static void union(int nodeA, int nodeB) {
        int rootA = find(nodeA);
        int rootB = find(nodeB);

        if(rootA == rootB) return;

        // 더 작은 트리를 큰 트리에 합침 -> 높이 변화 X
        if (sizes[rootA] < sizes[rootB]) {
            parents[rootA] = rootB;
            sizes[rootB] += sizes[rootA];  // rootB의 집합 크기 갱신
        } else {
            parents[rootB] = rootA;
            sizes[rootA] += sizes[rootB];  // rootA의 집합 크기 갱신
        }
    }

    // Map 에 저장하여 각 이름의 인덱스 관리하기
    static int convertName(String name) {
        if(nameMap.containsKey(name)) return nameMap.get(name);

        // 처음 들어오는 친구이름이라면, idx 값 증가
        nameMap.put(name, idx);
        return idx++;
    }
}

/*
 * 어떤 사이트의 친구 관계가 생긴 순으로 주어졌을 때, 두 사람의 친구 네트워크에 몇 명이 있는가
 * 친구 네트워크 : 친구 관계만으로 이동할 수 있는 사이클
 *
 * 친구 관계가 생길 때마다, 두 사람의 친구 네트워크에 몇 명이 있는지 구하라
 */
