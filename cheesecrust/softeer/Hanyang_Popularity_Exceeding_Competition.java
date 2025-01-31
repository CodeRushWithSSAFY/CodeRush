import java.io.*;
import java.util.*;
import java.lang.Math.*;

class Celeb {
    public int popular;
    public int celebrity;

    Celeb(int p, int c) {
        popular = p;
        celebrity = c;
    }
}

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    
    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(br.readLine());
        List<Celeb> celeb = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            celeb.add(new Celeb(p, c));    
        }

        int totalPopular = 0;
        for (int i = 0; i < n; i++) {
            Celeb curCeleb = celeb.get(i);
            if (Math.abs(curCeleb.popular - totalPopular) <= curCeleb.celebrity) {
                totalPopular++;
            }
        }

        System.out.println(totalPopular);
    }
}
