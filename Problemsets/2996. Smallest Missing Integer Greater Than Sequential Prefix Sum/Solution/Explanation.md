# Overview

Longest sequential prefix must start from index $0$.

> Thus, if we find $i$, so that `nums[i] != nums[i-1] + 1`, `nums[0...i-1]` is the longest sequential prefix , and sum of that subarray is the $x$.

We are asked to find the **smallest** integer that has these 2 characteristics:
- is greater than or equals $x = \displaystyle \sum_{k=0}^{i-1} nums[k]$
- doesn't exist in $nums$.

# Approach: Simulation + Prefix Sum + Hash Table

## Algorithm

1. Let `exists` be the hash table / set / bit-set that records all integers exist in `nums`
1. Initialize `prefixSum` to equal $nums[0]$ in the beginning.
2. Iterate the `nums` from $i=1$:
    - If `nums[i] == nums[i-1] + 1`, then add `nums[i]` to `prefixSum`
    - Otherwise, stop the iteration process.
2. The constraint says $\max(nums[i]) = 50$.
    
    Thus, if `prefixSum > 50`, then **`prefixSum` is the answer**.

    Otherwise, search for the answer from `prefixSum` incrementally, until the integer that doesn't exist in `exists` found, and return that integer as the answer


## Complexity

- Time Complexity : $O(n)$
- Space Complexity : $O(50) \approx O(1)$

## Code 

### Code 1 : Simulation + Hash Table

```java
class Solution {
    public int missingInteger(int[] nums) {
        boolean[] exists = new boolean[51];
        for(int x : nums)
            exists[x] = true;

        int curPrefixLength = 1, curPrefixSum = nums[0];
        int n = nums.length;
        for(int i=1; i<n && nums[i] == nums[i-1] + 1; curPrefixSum += nums[i++], curPrefixLength++);

        int i = curPrefixSum;
        for(; i < 51 && exists[i]; i++);
        return i;
    }
}
```

### Code 2 : Simulation + Bit-set (Bitwise Manipulation)

```java
class Solution {
    public int missingInteger(int[] nums) {
        long exists = 0;
        for(int x : nums)
            exists |= (1L << x);

        int curPrefixLength = 1, curPrefixSum = nums[0];
        int n = nums.length;
        for(int i=1; i<n && nums[i] == nums[i-1] + 1; curPrefixSum += nums[i++], curPrefixLength++);
        for(; curPrefixSum < 51 && (exists & (1L << curPrefixSum)) != 0; curPrefixSum++);
        return curPrefixSum;
    }
}
```