import java.util.*;

class Solution {
    int length;
    long startCnt;
    long[] wordCnt; // 길이별로 가능한 단어 개수 세기
    int[] word;
    List<String> deleteWord;
    long originalN;
    
    public void dfs(int idx, long cnt) {
        if (idx == length) {
            return ;
        }
            
        for (int alphabet = 0; alphabet < 26; alphabet++) {
            int restLength = length - idx - 1; // 현재 알파벳 빼고 뒤 알파벳
            int diff = alphabet + 1;
            
            // 현재 알파벳으로 정했을 때의 n이 범위 안에 있으면
            System.out.println(alphabet + " " + (cnt + diff * (long)Math.pow(26, restLength)));
            if (originalN <= cnt + diff * (long)Math.pow(26, restLength)) {
                word[idx] = alphabet;
                dfs(idx + 1, cnt + (diff - 1) * (long)Math.pow(26, restLength));
                return ;
            }
            
            
        }
    }
    
    public String solution(long n, String[] bans) {
        String answer = "";
        originalN = n;
        
        Arrays.sort(bans, (s1, s2) -> {
            int lengthCompare = Integer.compare(s1.length(), s2.length());
                
            // 길이가 같으면 알파벳 순으로 비교
            if (lengthCompare == 0) {
                return s1.compareTo(s2);
            }
            return lengthCompare;
        });
        
        wordCnt = new long[12];
        
        for (int len = 1; len <= 11; len++) {
            wordCnt[len] = (long)Math.pow(26, (long)len);
        }
        
        int deleteCnt = 0; // n번째 전에 삭제해야하는 단어들 개수
        for (int idx = 0; idx < bans.length; idx++) {
            int len = bans[idx].length();
            wordCnt[len]--;
            
            // 제거해야할 단어가 원래 몇번째인지 구하기
            long originalOrder = 0;
            for (int bansLen = 1; bansLen < len; bansLen++) {
                originalOrder += (long)Math.pow(26, bansLen);
            }
            
            for (int bansIdx = 0; bansIdx < len; bansIdx++) {
                int diff = bans[idx].charAt(bansIdx) - 'a'; // 이거 왜 +1 안해도 되지
                originalOrder += (long)diff * (long)Math.pow(26, len - bansIdx - 1);
            }
            if (originalOrder - deleteCnt < n) {
                deleteCnt++;
            }
        }
        originalN += deleteCnt;
        
        
        long cnt = 0;
        for (int len = 1; len <= 11; len++) {
            // n이 len 길이 암호 범위일 때 break
            if (n > cnt && n <= cnt + wordCnt[len]) {
                length = len;
                break;
            }
                
            cnt += wordCnt[len];
        }
        
        startCnt = 0;
        for (int i = 1; i <= length - 1; i++) {
            startCnt += (long)Math.pow(26, i);
        }
        
        word = new int[length];
        
        dfs(0, startCnt);
        
        StringBuilder sb = new StringBuilder();
        for (int idx = 0; idx < length; idx++) {
            sb.append((char)(word[idx] + 'a'));
        }
        
        answer = sb.toString();
        
        return answer;
    }
}

// int를 long으로 바꾸니까 풀렸다..
