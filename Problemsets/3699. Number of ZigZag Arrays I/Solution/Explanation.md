# Intuition

### Numbers

The numbers in array are limited in range $[l, r]$.

The thing that matters in the array is the **order** of numbers, whether it's increasing or decreasing. So, we only need to keep track the varying numbers in the pool, we will call it as $m$ in this problem.

Thus, 
$$m = r - l + 1$$


Illustration:

$l = 3, r = 6 \rightarrow pool = [3,4,5,6]$

To simplify it, we can treat it as $[1,2,3,4]$. Both have 4 elements.

### ZigZag

> Let's say $a_0$ is first element in the array, $a_1$ is second element, and so on.

2 possible forms, depending from starting direction:

- Repetition of **Increasing first then Decreasing** $[ \uparrow, \downarrow]$

    In this pattern: $a_0 > a_1$ and $a_2 > a_3$

- Repetition of **Decreasing first then Increasing** $[ \downarrow, \uparrow]$

    In this pattern: $a_0 < a_1$ and $a_2 < a_3$

Thus, **switching direction** on changing parity of $i$ would be necessary.

### Symmetrical

If there is a valid ZigZag array starting with **increasing** direction available, then the counterpart is also available by switching the starting direction to its counterpart.

Hence, from the 2 possible starting directions, we only need to examine 1, and then multiple the result by $2$.

# Observing Pattern

> Let $i$ be the length of the array at a time

Let's take a look at this example, we will observe only the increasing-first direction 

$n = 3, l=1, r=3$

1. $m = r - l + 1 = 2$
1. At $i=1$, the ZigZag arrays are

$$[1], [2], [3]$$

2. At $i=2$, we traverse $x$ from $1$ to $m$:

    - $x=1 \rightarrow$ Not even 2 elements yet.
    - $x=2 \rightarrow [1, 2]$
    - $x=3 \rightarrow [1, 3], [2,3]$

    Thus, we got 3 ZigZag arrays with length $2 \rightarrow [1,2], [1,3], [2,3]$

3. At $i=3$, we already have the 3 arrays from previous step, then we traverse $x$ from $m$ to $1$: 
    - $x=3 \rightarrow$ None, we cannot append $3$ to any existing arrays.
    - $x = 2$

        We can append this to the arrays with length $2$ that their last element is at least $3$, so we get $[1,3,2], [2,3,2]$

    - $x=1$
    
        We can append this to the arrays with length $2$ that their last element is at least $2$. so we get these from arrays in previous step (length $2$):
        - From the arrays ended with $3 \rightarrow [1,3,1], [2,3,1]$ 
        - From the arrays ended with $2 \rightarrow [1,2,1]$ 
    Thus, we got 3 ZigZag arrays with length $5 \rightarrow [1,2,1], [1,3,1], [2,3,1], [1,3,2], [2,3,2]$

From the start-by-increasing observation, we got $5$ arrays.

The arrays that are start-by-decreasing by the same amount.

Therefore, the answer is the sum of both, which is $5 \times 2 = 10$, this stands for:
- $[1,2,1], [1,3,1], [2,3,1], [1,3,2], [2,3,2]$ (start by increasing)
- $[2,1,2], [3,1,3], [2,1,3], [3,1,2], [3,2,3]$ (start by decreasing)

---

> From step 3 above, when $x = 1$, did you see a repetition? Something that follows along every time, right?

As we can see when following the decreasing pattern, and we do the calculation of $x$ from $m$ to $1$, every ZigZag arrays from previous step ($i-1$), that are appendable by previous $x$ in current step $i$ (which means $x+1$), are obviously appendable by current $x$.

    Both [1,3] and [2,3] are appendable by 2, so they are appendable by 1 too.

This principle will go the same way for increasing, but just the previous $x$ being the smaller one.

## Formulating DP

Let's say we have $dp[i][dir][x]$, the number of varying ZigZag arrays with length $i$, the last direction $dir$ (`0` for increasing, `1` for decreasing), with last element being $x$.

Thus:
$$dp[i][0][x] = \sum_{j=0}^{x-1}dp[i-1][1][j]$$

$$dp[i][1][x] = \sum_{j=x+1}^{m-1}dp[i-1][0][j]$$

> In one $i$, there are $m$ numbers that need to be examined, for all $x$ in $[0,m)$, wouldn't this be $O(m^2)$ for each $i$?

## Prefix Sum

We know that we can do the alternating loop with direction (from $0$ to $m$ or its counterpart) in favor of the direction in current step (increasing / decreasing).

- When looping the increasing direction, we loop the $x$ from the smallest to biggest that is applicable to $x$ at previous $i$. As this loop of $x$ goes on, we can simply add these values along the way.
- When looping the decreasing direction, we loop the $x$ from the biggest to smallest that is applicable to $x$ at previous $i$. As this loop of $x$ goes on, we can simply add these values along the way.

## Further Optimization

We only need values from steps $i-1$ to calculate values at step $i$.

So, 1D array with temporary variable to store prefix sum works.

```java
    int prefixSum = 0;
    if(i % 2 == 0) {
        // Increasing direction
        // The higher the current number, 
        // more possible lower number prefixes before
        // , and this adds up from previous count.
        for(int j=0; j<m; j++) {
            int tmp = dp[j];
            dp[j] = prefixSum;

            // Add the numbers from previous length (i)
            prefixSum = (prefixSum + tmp) % MOD;
        }
    } else {
        // Decreasing
        // The lower the current number, there's more possible higher number prefixes before
        // , and this adds up from previous count.
        for(int j=m-1; j>=0; j--) {
            int tmp = dp[j];
            dp[j] = prefixSum;
            
            // Add the numbers from previous length (i)
            prefixSum = (prefixSum + tmp) % MOD;
        }
    }
```


# Algorithm : Dynamic Programming + Prefix Sum

1. Let `dp` be array sized $m$, with all ones. This is for single element arrays.
2. Iterate the array sizes ($i$) from $2$ to $n$:
    - Let `prefixSum` storing the prefix sum value for all $x$ in current $i$.
    - If $i$ is divisible by $2$, then examine the increasing direction
    - Else, examine the decreasing direction.
3. Return the sum of `dp` multiplied by $2$.




# Full Code

```java
class Solution {
    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        int MOD = 1000000007;
        // Get the number of possible arrays with each possible starting number
        int[] dp = new int[m];

        // Every array with length 1 is a valid zig zag array.
        Arrays.fill(dp, 1);

        // Process from length 2 onwards to n
        // Process only ^^^^^ patterns
        // As it's symmetrical, the result will be just x2 (^^^^^ and vvvvv).
        for(int i=2; i<=n; i++) {
            // We use prefix sum here.
            int prefixSum = 0;
            if(i % 2 == 0) {
                // Increasing
                // The higher the current number, there's more possible lower number prefixes before
                // , and this adds up from previous count.
                for(int j=0; j<m; j++) {
                    int tmp = dp[j];
                    dp[j] = prefixSum;
                    prefixSum = (prefixSum + tmp) % MOD;
                }
            } else {
                // Decreasing
                // The lower the current number, there's more possible higher number prefixes before
                // , and this adds up from previous count.
                for(int j=m-1; j>=0; j--) {
                    int tmp = dp[j];
                    dp[j] = prefixSum;
                    prefixSum = (prefixSum + tmp) % MOD;
                }
            }
        }

        int res = 0;
        for(int x : dp) {
            res = (res + x) % MOD;
        }
        return (res * 2) % MOD;
    }
}
```