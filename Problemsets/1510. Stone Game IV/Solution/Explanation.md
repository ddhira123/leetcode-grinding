# Overview

## Game rules

1. Alice starts first
2. In each turn, a player takes a non-zero square number of stones, i.e. $1,4,9,16,\dots$ from the rest of stones.
3. In the beginning, there are $n$ stones.
4. If there is no more stones at a player's turn, he/she loses the game.

## Goal

> If there's any way for Alice to win the game, return `true`. Otherwise, return `false`. Both plays optimally.

## Implication

To achieve the goal, adhering to 4th rule, Alice can win if she makes Bob loses in any possible scenarios.

## Simulate The Game

> Odd turns are Alice's, Even turns are Bob's 


Let's simulate 2 examples:

### Example 1 : $n = 5$

1.  $4,1,0 \rightarrow $ Alice loses
    
    - First, Alice takes 4
    - Bob takes 1
    - No stone left, Alice loses

1.  $1,4,0 \rightarrow $ Alice loses

Remember, both plays *optimally*, so the scenario $1,1,1,1,1,0$ will not happen as **Bob does not want Alice to be winner.**

Thus, $n = 5$ results `false`.

### Example 2 : $n = 8$

1.  $4,4,0 \rightarrow $ Alice loses

1.  $1,4,1,1,1,0 \rightarrow $ Alice wins

1.  $1,1,4,1,1,0 \rightarrow $ Alice wins

1.  $1,1,1,4,1,0 \rightarrow $ Alice wins

1.  $1,1,1,1,4,0 \rightarrow $ Alice wins

Since there's scenario where Bob plays optimally but still loses, then the answer is `true`.

# Approach 1 : Backtracking (TLE)

1. Let $m$ be the leftover stones after turns. Initially, it equals $n$

2. For each square number $i$ in range $1 \le i \le m$:
    - If taking $i$ stones makes the opponent losing (no way to win), then the player in current turn wins. Recursively go to next turn with new $m \leftarrow m - i$

3. As taking any possible $i$ doesn't make the current player winning, then he/she loses the game if there is $m$ stones in his/her current turn.

## Complexity:

- Time Complexity : $\Large \displaystyle O(n^{\frac{n}{2}})$
    
    There are $\sqrt{m}$ choices in each turn with the recursion depth is at most $n$.

- Space complexity : $O(n)$


# Approach 2 : Backtracking + Memoization / Top-down DP

The answer for one $n$ is fixed. Thus, if we had calculate that once, we don't need to recalculate it.

You can see at **Example 2** above, there are many situation when $n = 7$, and we need only 1 sample that returns "true" to mark it as winning.

Thus, we can simply add an array $dp$ to store answers for each integers from $0$ to $n$, with basis $dp[0] = false$.

## Algorithm

1. Let $m$ be the leftover stones after turns. Before the game starts, it equals $n$

1. If $dp[m]$ is `true`, then return `true`.

2. For each square number $i$ in range $1 \le i \le m$:
    - If taking $i$ stones makes the opponent losing (no way to win) in each possible next turns strategies (recursive calls with $m \leftarrow m - i$), then the player in current turn wins, then set $dp[m]$ to `true`.

3. As taking any possible $i$ doesn't make the current player winning, then he/she loses the game if there is $m$ stones in his/her current turn.


## Complexity:

- Time Complexity : $O(n\sqrt{n})$
    
    There are $\sqrt{m}$ choices in each turn with the recursion depth is at most $n$.

- Space complexity : $O(n)$

## Code

```java
class Solution {
    boolean[] dp;
    private boolean solve(int n) {
        if(dp[n]) return true;
        for(int i=1; i*i <= n; i++) {
            // Ensure Bob loses if Alice takes i^2 stones, and vice-versa during Bob's turn.
            if(!solve(n-i*i))
                return dp[n] = true;
        }
        return false;
    }
    public boolean winnerSquareGame(int n) {
        dp = new boolean[n+1];
        return solve(n);
    }
}
```


# Approach 3 : Tabulation / Bottom-up DP

In the previous approaches the final determining condition for Alice's winning is if we can get Bob losing.

In other words: 

- Let $i$ be the square numbers, i.e. $1^2, 2^2, \cdots$.

- Let $m$ be any integer.

> If $dp[m]$ is false, then $dp[i + m]$ is true.

> Why? If Alice takes $i$ stones, then Bob is left with $m$ stones, which guarantees his losing as $dp[m] = false$. 


## Algorithm

Then, let's build our tabulation:

- The basis is $dp[0] = false$, because Alice absolutely loses if the game starts with 0 stone.

- For each $m$ from $0$ to $n$ ($m$ is every integer where $dp[m]$ is false):
    - For each square numbers $i$ from $1$ to $n - m$:
        - $dp[i + m] \leftarrow  true$

- Return $dp[n]$


> We can use static precomputing as the $dp$ array is consistent towards every test cases.

## Complexity 

- Time Complexity : $O(n\sqrt{n})$
- Space complexity : $O(n)$

With static precomputing, it approximates $O(1)$, adhering Big-O rules

- Time Complexity : $O(100000\sqrt{100000}) \approx O(1)$
- Space complexity : $O(100001) \approx O(1)$


## Code

```java
class Solution {
    static boolean[] dp = new boolean[100001];
    static {
        for(int i=0; i<100001; i++) {
            // If this false, then Alice wants Bob to somehow reach this amount pf leftover stones
            if(dp[i]) continue;

            // If dp[i] is losing state, then dp[i + j^2] will be winning state for Alice
            // Since taking j^2 stones, leaves Bob with i stones, which guarantees losing for Bob
            for (int j = 1; j * j < 100001 - i; j++)
                dp[i + j * j] = true;
        }
    }
    public boolean winnerSquareGame(int n) {
        return dp[n];
    }
}
```
