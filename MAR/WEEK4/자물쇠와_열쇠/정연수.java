import java.util.*;

class Solution {
    
    public boolean solution(int[][] key, int[][] lock) {
        int point = key.length - 1;
        for(int i = 0; i < point + lock.length; i++){
            for(int j = 0; j < point + lock.length; j++){
                
                for(int r = 0; r < 4; r++){
                    int[][] newLock = new int[lock.length + 2 * point][lock.length + 2 * point];
                    for(int y = 0; y < lock.length; y++){
                        for(int x = 0; x < lock.length; x++){
                            newLock[y + point][x + point] = lock[y][x]; // lock 확장해서 만듦 
                        }
                    }
                    match(newLock, key, r, i, j);
                    if (check(newLock, point, lock.length)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void match(int[][] lock, int[][] key, int r, int y, int x){
        int len = key.length;
        for(int i = 0; i < key.length; i++){
            for(int j = 0; j < key.length; j++){
                if(r == 0){
                    lock[y + i][x + j] += key[i][j];
                }else if (r == 1){
                    lock[y + i][x + j] += key[j][len - i - 1];
                }else if (r == 2){
                    lock[y + i][x + j] += key[len - i - 1][len - j - 1];
                }else if (r == 3){
                    lock[y + i][x + j] += key[len - j - 1][i];
                }
            }
        }
    
    }
    
    public boolean check(int[][] lock, int point, int len){
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(lock[point + i][point + j] != 1) return false;
            }
        }
        return true;
    }
}
