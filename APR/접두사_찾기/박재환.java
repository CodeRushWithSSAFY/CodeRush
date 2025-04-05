package APR.접두사_찾기;

import java.util.*;
import java.io.*;

public class 박재환 {
    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        br.close();
    }

    static StringTokenizer st;
    static Set<String> set;
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        int input = Integer.parseInt(st.nextToken());
        int find = Integer.parseInt(st.nextToken());

        // SET 은 add, contains에서 O(1) 의 시간복잡도를 갖는다.
        set = new HashSet<>();

        while(input-- > 0) {
            String str = br.readLine().trim();

            makeGroup(str);
        }

        int matchCnt = 0;
        while(find-- > 0) {
            String str = br.readLine().trim();

            if(set.contains(str)) matchCnt++;
        }
        System.out.println(matchCnt);
    }

    static void makeGroup(String str) {
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()) {
            sb.append(c);

            set.add(sb.toString());
        }
    }
}

/*
 * 문자열 S 의 접두사 -> 가장 앞에서부터 부분 문자열
 */