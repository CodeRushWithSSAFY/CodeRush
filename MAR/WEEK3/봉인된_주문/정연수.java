import java.util.*;

class Solution {

    private static String answer = "";

    private long convertToNumber(String s) {
        long result = 0;
        for (char c : s.toCharArray()) {
            result = result * 26 + (c - 'a' + 1);
        }
        return result;
    }

    // 숫자를 문자열로 변환하는 메소드
    private void convertToString(long n) {
        // 몇 자리 수가 필요한지 계산
        int length = 1;
        long total = 26;
        while (n > total) {
            n -= total;
            total *= 26;
            length++;
        }

        // 숫자를 문자열로 변환
        StringBuilder sb = new StringBuilder();
        long power = (long)Math.pow(26, length - 1);

        for (int i = 0; i < length; i++) {
            long digit = (n - 1) / power + 1;
            sb.append((char)('a' + digit - 1));
            n -= (digit - 1) * power;
            power /= 26;
        }

        answer = sb.toString();
    }

    public String solution(long n, String[] bans) {
        // banned 번호들을 숫자로 변환
        long[] parsedBans = new long[bans.length];
        for (int i = 0; i < bans.length; i++) {
            parsedBans[i] = convertToNumber(bans[i]);
        }

        // 정렬하여 더 효율적으로 검색
        Arrays.sort(parsedBans);

        // banned 번호를 고려하여 n 조정
        for (long bannedNum : parsedBans) {
            if (bannedNum > n) {
                break;
            }
            n++;
        }

        convertToString(n);
        return answer;
    }
}
