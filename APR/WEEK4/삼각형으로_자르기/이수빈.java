import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int posCnt;
    static List<int[]> pos;
    static double maxResult;

    static int[] xPos, yPos;
    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        posCnt = Integer.parseInt(br.readLine().trim());

        pos = new ArrayList<>();
        for (int cnt = 0; cnt < posCnt; cnt++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            pos.add(new int[]{x, y});
        }
        xPos = new int[3];
        yPos = new int[3];
        maxResult = 0;
    }

    public static void selectPos(int cnt, int startIdx) {
        if (cnt == 3) {

            int temp1 = (xPos[0] * yPos[1]) + (xPos[1] * yPos[2]) + (xPos[2] * yPos[0]);
            int temp2 = (yPos[0] * xPos[1]) + (yPos[1] * xPos[2]) + (yPos[2] * xPos[0]);
            double result = (double)Math.abs(temp1 - temp2) / 2;
            maxResult = Double.max(result, maxResult);
            return ;
        }

        for (int idx = startIdx; idx < posCnt; idx++) {
            int[] select = pos.get(idx);
            int x = select[0];
            int y = select[1];

            xPos[cnt] = x;
            yPos[cnt] = y;

            selectPos(cnt + 1, idx + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        selectPos(0, 0);
        System.out.println(maxResult);
    }
}
