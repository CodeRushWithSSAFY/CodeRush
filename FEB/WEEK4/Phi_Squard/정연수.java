import java.io.*;
import java.util.*;

class Phi {
    // 원래의 크기 먹은 후 크기
    long size;
    int index;
    long origin;

    Phi(int size, int index) {
        this.size = size;
        this.index = index;
        this.origin = size;
    }

    public void sync() {
        this.origin = this.size;
    }
    
    public String toString() {
        return size + "\n" + index;
    }
}

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static int N;
    private static ArrayDeque<Phi> q;

    private static void predation() {
        while(true) {
            ArrayDeque<Phi> tmp = new ArrayDeque<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Phi cur = q.poll();
                if (tmp.size() == 0) {
                    tmp.add(cur);
                    continue;
                }
                Phi front = tmp.peekLast();
                // 앞에서 하나 먹음
                if (front.origin == 0) {
                    tmp.pollLast();
                    front = tmp.peekLast();
                    // 내가 같거나 크면 먹을 수 있지만, 앞에가 크면 나를 못 먹음
                    if (front.size <= cur.origin) {
                        front.size += cur.size;
                        front.index = cur.index;
                        front.origin = cur.origin;
                    } else {
                        tmp.add(cur);
                    }
                    continue;
                }
                // cur의 origin은 size와 같다.
                if (front.origin >= cur.origin) {
                    front.size += cur.size;
                    tmp.add(new Phi(0, 0));
                    continue;
                }
                // 앞의 미생물이 나를 먹을때는 갱신전의 미생물 크기 기준으로 행야합니다.
                if (front.size <= cur.origin) {
                    front.size += cur.size;
                    front.index = cur.index;
                    front.origin = cur.origin;
                    continue;
                }
                tmp.add(cur);
            }
            if (tmp.peekLast().size == 0) tmp.pollLast();
            // q를 갱신해준다.
            while(!tmp.isEmpty()) {
                Phi phi = tmp.poll();
                phi.sync();
                q.add(phi);
            }
            // 하나밖에 안남으면 끝난거
            if (q.size() == 1) {
                System.out.println(q.poll());
                return;
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        q = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            q.add(new Phi(Integer.parseInt(st.nextToken()), i));
        }
        predation();
    }
}
