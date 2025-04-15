package WEEK3.가장_긴_바이토닉_부분_수열;

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
    static int arrLen;      // 수열의 길이
    static int[] inputArr;  // 입력된 수열
    static void init() throws IOException {
        arrLen = Integer.parseInt(br.readLine().trim());
        inputArr = new int[arrLen];

        st = new StringTokenizer(br.readLine().trim());
        for(int idx=0; idx<arrLen; idx++) {
            inputArr[idx] = Integer.parseInt(st.nextToken());
        }

        getLIS();
    }

    /*
        정방향으로 LIS 를 구한다.
        역방향으로 LIS 를 구한다.

        중간치가 최대인 값을 구한다.
     */
    static int[] lisArr, reLisArr;      // 정방향, 역방향
    static void getLIS() {
        lisArr = new int[arrLen];
        reLisArr = new int[arrLen];

        // 1. 정방향으로 증가하는 부분 수열을 구한다.
        List<Integer> list = new ArrayList<>();
        list.add(inputArr[0]);
        lisArr[0] = 1;
        for(int idx=1; idx<arrLen; idx++) {
            if(list.get(list.size()-1) < inputArr[idx]) {   // 현재 마지막 원소보다 큰 수일 경우
                list.add(inputArr[idx]);
                lisArr[idx] = list.size();
            } else {
                int insertIdx = binarySearch(list, inputArr[idx]);
                list.set(insertIdx, inputArr[idx]);
                lisArr[idx] = insertIdx+1;  // ⚠️ list 는 전체 lis 의 최대 길이 -> num 을 끝으로 같는 lis의 길이가 아님
            }
        }

        // 2. 역방향으로 증가하는 부분 수열을 구한다.
        list = new ArrayList<>();
        list.add(inputArr[arrLen-1]);
        reLisArr[arrLen-1] = 1;
        for(int idx=arrLen-2; idx>-1; idx--) {
            if(list.get(list.size()-1) < inputArr[idx]) {   // 현재 마지막 원소보다 큰 수일 경우
                list.add(inputArr[idx]);
                reLisArr[idx] = list.size();
            } else {
                int insertIdx = binarySearch(list, inputArr[idx]);
                list.set(insertIdx, inputArr[idx]);
                reLisArr[idx] = insertIdx+1;  // ⚠️ list 는 전체 lis 의 최대 길이 -> num 을 끝으로 같는 lis의 길이가 아님
            }
        }

//        System.out.println(Arrays.toString(lisArr));
//        System.out.println(Arrays.toString(reLisArr));

        int max = Integer.MIN_VALUE;
        for(int idx=0; idx<arrLen; idx++) {
            max = Math.max(max, lisArr[idx] + reLisArr[idx]);
        }

        // 중복되는 중간 값을 제거한다.
        System.out.println(max-1);
    }

    /*
        num 이상인 가장 작은 수를 반환한다.
     */
    static int binarySearch(List<Integer> list, int target) {
        int l = 0, r = list.size()-1;

        while(l < r) {
            int mid = l + (r-l)/2;

            if(list.get(mid) < target) {    // 찾으려는 수보다 작다면
                l = mid+1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}