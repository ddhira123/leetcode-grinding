> [Link to Leetcode](https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum/description)

> Difficulty : <strong style="color: green">Easy</strong>

# Overview

You are given a **0-indexed** array of integers $nums$.

A prefix $nums[0\dots i]$ is sequential if, for all $1 \le j \le i$, $nums[j] = nums[j - 1] + 1$. 

In particular, the prefix consisting only of $nums[0]$ is sequential.

Return *the **smallest** integer $x$ missing from nums such that $x$ is greater than or equal to the sum of the **longest** sequential prefix*.

# Examples

## Example 1

<pre><strong>Input:</strong> nums = [1,2,3,2,5]
<strong>Output:</strong> 6
<strong>Explanation:</strong> The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

## Example 2

<pre><strong>Input:</strong> nums = [3,4,5,1,12,14,13]
<strong>Output:</strong> 15
<strong>Explanation:</strong> The longest sequential prefix of nums is [3,4,5] with a sum of 12. 12, 13, and 14 belong to the array while 15 does not. Therefore 15 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

# Constraints

- $1 \le nums.length \le 50$
- $1 \le nums[i] \le 50$



<br/>
<details>
  <summary><h1 style="display: inline; border-bottom: none !important;">Topics</h1>
  
  ---

  </summary>

  - Array
  - Hash Table
  
</details>

<br/>

# Hints

<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>

Iterate the array $nums$ from $1$ until it meets an index $i$, where $nums[i] \neq nums[i-1] + 1$. Thus $nums[0\dots i-1]$ is the longest sequential prefix.

</details>

