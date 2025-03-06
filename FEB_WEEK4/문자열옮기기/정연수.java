import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Solution {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	private static StringTokenizer st;
	private static ArrayDeque<Character> q;
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine().trim());
		int testCnt = Integer.parseInt(st.nextToken());
		
		for (int test = 0; test < testCnt; test++) {
			st = new StringTokenizer(br.readLine().trim());
			String input = st.nextToken();
			st = new StringTokenizer(br.readLine().trim());
			int commandCnt = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine().trim());
			long command = 0;
			for (int index = 0; index < commandCnt; index++) {
				command += Long.parseLong(st.nextToken());
			}
			if (command > 0) {
				bw.write(input.substring((int) (command % input.length())) + input.substring(0, (int) (command % input.length())));
			} else if (command < 0) {
				bw.write(input.substring((int) (Math.abs(input.length() + command % input.length()) % input.length())) + input.substring(0, (int) (Math.abs(input.length() + command  % input.length()) % input.length())));
			} else {
             	bw.write(input);   
            }
			bw.write("\n");
		}
		bw.flush();
		bw.close();
	}
}
