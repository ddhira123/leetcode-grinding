# Intuition
<!-- Describe your first thoughts on how to solve this problem. -->

There are 4 possible combinations ([in depth explanation here](../../3633.%20Earliest%20Finish%20Time%20for%20Land%20and%20Water%20Rides%20I/Solution/Explanation.md)) for doing the 2 Rides.

This problem has greater upper bound constraint that we can't simply brute force for each combinations of $i$ and $j$. But the linear enumeration is still viable.

# Approach : 2-Step Traversals (Optimized Brute-Force)

Instead of nested loop, we can make one as fixed pivot, evaluating one array at a time, focusing to get the minimum over time. Thus, it cuts from $O(m\times n)$ to $O(m + n)$.

We can make the flow of thinking into more target-oriented:

- $L$ starts first:
    - Evaluate each Land Rides, get the earliest time we can finish one Land Ride. Let this be `finish1`.
        
        `finish1` $\displaystyle \leftarrow \min_{i=0}^n ($ `landDuration[i] + landStartTime[i]` $)$

    - Evaluate each Water Rides, to get earliest time if we do $L$ first

        `ans` $\displaystyle \leftarrow \min_{j=0}^m ( $ `ans` $,$ `waterDuration[j] ` $\ + \max($ `finish1` $,$ `waterStartTime[j]` $))$

- $W$ starts first:
    - Do the same as when we evaluate $L$ starts first, but reverse the index and array.

The answer will be reflected on the minimum `ans` we got from the whole process.


## Complexity
- Time complexity: $O(m + n)$
<!-- Add your time complexity here, e.g. $$O(n)$$ -->

- Space complexity: $O(1)$
<!-- Add your space complexity here, e.g. $$O(n)$$ -->

## Code

```java
import java.util.*;
import java.lang.*;

class Solution {
    private int checkCombination(int[] st1, int[] d1, int[] st2, int[] d2) {
        int fin1 = Integer.MAX_VALUE;
        int m = d1.length;
        int n = d2.length;
        for(int i=0; i<m; i++) {
            fin1 = Math.min(fin1, st1[i] + d1[i]);
        }
        int fin2 = Integer.MAX_VALUE;
        for(int j=0; j<n; j++) {
            fin2 = Math.min(
                fin2,
                d2[j] + Math.max(st2[j], fin1)
            );
        }
        return fin2;
    }
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        return Math.min(
            checkCombination(landStartTime, landDuration, waterStartTime, waterDuration),
            checkCombination(waterStartTime, waterDuration, landStartTime, landDuration)
        );
    }
}
```
