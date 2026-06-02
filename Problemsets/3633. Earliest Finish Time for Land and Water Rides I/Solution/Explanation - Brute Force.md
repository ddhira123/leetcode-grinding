# Intuition
<!-- Describe your first thoughts on how to solve this problem. -->

From all schedules of Line Rides ($L$) and Water Rides ($W$), the tourist only needs **ONE** Land Ride and **ONE** Water Ride.


Possible Combinations:
1.   $L$ starts first, then do the $W$ **immediately** after $L$ ends.

    This is possible if `waterStartTime[j]` $\le$ `landStartTime[i] + landDuration[i]`
    <table>
    <tr>
        <td>L</td>
        <td>L</td>
        <td>L</td>
        <td>W</td>
        <td>W</td>
    </tr>
    </table>

2.   $W$ starts first, then do the $L$ **immediately** after $W$ ends.

    This is possible if `landStartTime[i]` $\le$ `waterStartTime[j] + waterDuration[j]`
    <table>
    <tr>
        <td>W</td>
        <td>W</td>
        <td>W</td>
        <td>L</td>
        <td>L</td>
    </tr>
    </table>

3.   $W$ starts first, but we can only start $L$ later on 

    `landStartTime[i]` $>$ `waterStartTime[j] + waterDuration[j]`
    <table>
    <tr>
        <td>W</td>
        <td>W</td>
        <td>W</td>
        <td>~</td>
        <td>L</td>
        <td>L</td>
    </tr>
    </table>
4.   $L$ starts first, but we can only start $W$ later on

    `waterStartTime[j]` $>$ `landStartTime[i] + landDuration[i]`
    <table>
    <tr>
        <td>L</td>
        <td>L</td>
        <td>~</td>
        <td>W</td>
        <td>W</td>
        <td>W</td>
    </tr>
    </table>

Only 4 possible combinations, with each array size at most $100$, we can just brute-force. These 4 combinations are highly focused on *At what time can second ride starts?*


# Approach 1: Brute-Force All Possible Combinations

- Initially, set the answer to largest integer.
- Iterate indexes $j$ throughout array `waterDuration`:
    - Iterate indexes $i$ throughout array `landDuration`:
        - Evaluate the finish time if we start $W$ first, then update the answer with the minimum value. This evaluates combination number 2 and 3.

            ```
            ans = min(ans, 
                        landDuration[i] + 
                            max(    // Get start time for 2nd event considering the prior event
                                waterStartTime[j] + waterDuration[j],   
                                landStartTime[i]
                            )
                        )
            ```
            
        - Evaluate the finish time if we start $L$ first, then update the answer with the minimum value. This evaluates combination number 1 and 4.
- Return the answer

## Complexity
- Time complexity: $O(m \times n)$
<!-- Add your time complexity here, e.g. $$O(n)$$ -->

- Space complexity: $O(1)$
<!-- Add your space complexity here, e.g. $$O(n)$$ -->

## Code

```java
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
```



# Approach 2 : 2-Step Traversals (Optimized Brute-Force)

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

    private int solve(
        int[] start1,
        int[] duration1,
        int[] start2,
        int[] duration2
    ) {
        int finish1 = Integer.MAX_VALUE;
        for (int i = 0; i < start1.length; i++) {
            finish1 = Math.min(finish1, start1[i] + duration1[i]);
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < start2.length; i++) {
            ans = Math.min(
                ans,
                Math.max(start2[i], finish1) + duration2[i]
            );
        }
        return ans;
    }

    public int earliestFinishTime(
        int[] landStartTime,
        int[] landDuration,
        int[] waterStartTime,
        int[] waterDuration
    ) {
        int land_water = solve(
            landStartTime,
            landDuration,
            waterStartTime,
            waterDuration
        );
        int water_land = solve(
            waterStartTime,
            waterDuration,
            landStartTime,
            landDuration
        );
        return Math.min(land_water, water_land);
    }
}
```
