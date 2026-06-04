class Solution {
    private int countWaviness(int x) {
        if(x < 101) return 0;
        int res = 0;
        int prev = x % 10;

        x /= 10;
        int cur = x % 10;

        x /= 10;
        while(x > 0) {
            int next = x % 10;
            if((cur < prev && cur < next) || (cur > prev && cur > next)) res++;
            prev = cur;
            cur = next;
            x /= 10;
        } 
        return res;
    }
    public int totalWaviness(int num1, int num2) {
        int tot = 0;
        for(int i=num1; i<=num2; i++) {
            tot += countWaviness(i);
        }
        return tot;
    }
}