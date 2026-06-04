# Intuition
<!-- Describe your first thoughts on how to solve this problem. -->

There are at most $10^5$ numbers in the interval, with each number is at most $10^5$ (5 digits).

If we enumerate each digit of each number, it's at most $5 \times 10^5$ calculation.
Therefore, we can easily brute-force.

# Approach : Brute-force with 3 Pointers

We will use 3 pointers, looping from least significant digit (right) to most significant digit (left):
- `prev` : the right digit
- `cur` : the middle digit
- `next`: the left digit

Waviness test:
- If `cur > prev && cur > next`, then `cur` is **a peak**.
- If `cur < prev && cur < next`, then `cur` is **a valley**.

## Algorithm

-   Let `count` be the count of all **peaks** and **valleys**. Initially, set it to $0$
-   For each number $i$ in a closed interval `[num1, num2]`, we count the waviness of $i$:
    - If $i < 100$, then its waviness is $0$.
    - Let $x$ store the value of $i$. 
        
        We will use this for counting its waviness.

    - Let `prev` be the latest digit of $x$
        
        This will be right pointer.

        `prev`$ \leftarrow x \text{ MOD }10$

    - Divide $x$ with $10$.
    - Let `cur` be the second-latest digit of $x$.

        This will be middle pointer to check peak/valley.
        
        `cur` $ \leftarrow x \text{ MOD }10$
    - Divide $x$ with $10$.

    - While $x > 0$:
        - Let `next` be the left pointer.

            `next` $ \leftarrow x \text{ MOD }10$

        - Do the **Waviness test**, if `cur` is either **a peak** or **a valley**, then increment the count.

        - Update `prev` with value of `cur`.
        - Update `cur` with value of `next`.
        - Update $x$ with value of $(x \div 10)$

- The `count` is now the answer.

## Complexity

Let $num_2$ be the larger given number.

- **Time complexity**: $O(num_2 \cdot \log_{10} num_2)$

    We need to traverse all integers in the interval `[num1,num2]`. In the worst case, the interval contains $O(num_
2)$ numbers. 

    Calculating the fluctuation value of each number requires traversing its digits, which takes $O(\log_{10}num_2)$ time. 

    Therefore, the overall time complexity is $O(num_2 \cdot \log_{10} num_2)$.

- **Space complexity**: $O(1)$.

## Code

```java
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
```
