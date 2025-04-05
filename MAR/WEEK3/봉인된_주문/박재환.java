package WEEK3.봉인된_주문;

import java.util.Arrays;

public class 박재환 {
    public String solution(long n, String[] bans) {

        /*
            문자의 길이가 짧은 순으로
            사전 순으로 정렬한다.
         */
        Arrays.sort(bans, (a, b) -> {
            if(a.length() == b.length()) return a.compareTo(b);

            return a.length() - b.length();
        });

        // 오름차순으로 정렬된 원소들의 idx 를 차례로 구한다.
        // idx 가 찾고자하는 인덱스보다 작다면 삭제되는 원소이다.
        // 찾고자 하는 원소를 증가시킨다.
        for(String ban : bans) {
            if(getDicIdx(ban) <= n) n++;
            else break;
        }

        return getDicStr(n);
    }

    /*
        문자의 사전 인덱스를 구한다.
        26 진법을 생각한다.
        26^n
     */
    long getDicIdx(String str) {
        long idx = 0;
        for(char c : str.toCharArray()) {
            idx = idx * 26 + (c - 'a' + 1);
        }

        return idx;
    }

    /*
        특정 idx 값을 해당하는 문자로 변환한다.
        getDicIdx 역순
     */
    String getDicStr(long n) {
        StringBuilder sb = new StringBuilder();
        while(n > 0) {
            long div = (n - 1) % 26;
            sb.insert(0,(char) ('a' + div));
            n = (n - 1) / 26;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        long n = 30;
//        long n = 7388;
        String[] bans = {"d", "e", "bb", "aa", "ae"};
//        String[] bans = {"gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc"};
//        String[] bans = {"gq", "dn", "xj", "xi", "ug", "xg", "wq", "en","hc","d", "e", "bb", "aa", "ae"};
        System.out.println(new 박재환().solution(n, bans));
    }
}

/*
    각 주문은 알파벳 소문자 11 글자 이하로 구성

    1. 글자 수가 적은 주문부터 먼저 기록
    2. 글자 수가 같다면, 사전 순으로 기록

    순열로 세우기에는 27P11이면 수가 너무 많음

    1. 삭제되는 원소 개수 만큼 추가로 사전 생성?
    글자의 자릿수를 n 이라고 했을 때, n 자릿수의 모든 문자의 개수는 26^n

    구하려는 문자열의 자릿수를 구해서, 탐색 범위를 줄인다?

    2. 각 문자의 인덱스를 활용한다.

*/
