import java.util.*;
import java.lang.*;

class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int n = landDuration.length;
        int m = waterDuration.length;
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                ans = Math.min(ans, landDuration[i] + Math.max(waterStartTime[j] + waterDuration[j], landStartTime[i]));
                ans = Math.min(ans, waterDuration[j] + Math.max(landStartTime[i] + landDuration[i], waterStartTime[j]));
            }
        }
        return ans;
    }
}