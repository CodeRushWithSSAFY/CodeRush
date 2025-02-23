package 문자열_옮기기;

import java.util.*;
import java.io.*;

// SWEA D3
// 22979. 문자열 옮기기
// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AZPOBiaqNo8DFAWB
public class 박재환 {
    static BufferedReader br;
    static BufferedWriter bw;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int TC = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for(int tc=0; tc<TC; tc++) {
            // 입력
            init();

            for(char c : deque) {
                sb.append(c);
            }
            sb.append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static String str;  // 연산을 수행할 문자열
    static Deque<Character> deque;  // 문자열 연산을 위해 사용할 Deque
    private static void init() throws IOException {
        deque = new ArrayDeque<>();

        str = br.readLine().trim();
        // 문자열을 Deque에 저장
        for(char c : str.toCharArray()){
            deque.offer(c);
        }

        int commandNum = Integer.parseInt(br.readLine().trim());    // 연산 수행 횟수
        StringTokenizer st = new StringTokenizer(br.readLine().trim()); // 명령어를 입력 받는다.

        // 명령어 수행
        for(int command=0; command < commandNum; command++) {
            int cmd = Integer.parseInt(st.nextToken()) % str.length(); // 명령어
            doCommand(cmd);
        }
    }

    // 명령어를 수행한다.
    private static void doCommand(int cmd) {
        // 주어진 횟수만큼 명령을 수행한다.
        for(int cnt=0; cnt<Math.abs(cmd); cnt++) {
            if(cmd > 0) {   // 앞 -> 뒤
                char c = deque.pollFirst();
                deque.offerLast(c);
            } else if(cmd < 0) {    // 뒤 -> 앞
                char c = deque.pollLast();
                deque.offerFirst(c);
            }
        }
    }
}

/*
    문자열 S 에 연산을 수행한다.
    X > 0 : S 의 첫 번째 글자를 마지막으로 X 회 이동시킨다.
    X < 0 : S 의 마지막 글자를 첫번째로 X 회 이동
    X = 0 : 아무일도 일어나지 않음

    앞뒤로 이동하는 연산이 많이 이루어지므로 Deque 를 사용한다.
 */