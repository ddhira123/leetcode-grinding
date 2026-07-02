> [Link to Leetcode](https://leetcode.com/problems/number-of-zigzag-arrays-i/description/)

> Difficulty : <strong style="color: red">Hard</strong>

## Overview 

<p>You are given three integers <code>n</code>, <code>l</code>, and <code>r</code>.</p>

<p>A <strong>ZigZag</strong> array of length <code>n</code> is defined as follows:</p>

<ul>
	<li>Each element lies in the range <code>[l, r]</code>.</li>
	<li>No <strong>two</strong> adjacent elements are equal.</li>
	<li>No <strong>three</strong> consecutive elements form a <strong>strictly increasing</strong> or <strong>strictly decreasing</strong> sequence.</li>
</ul>

<p>Return the total number of valid <strong>ZigZag</strong> arrays.</p>

<p>Since the answer may be large, return it <strong>modulo</strong> <code>10<sup>9</sup> + 7</code>.</p>

<p>A <strong>sequence</strong> is said to be <strong>strictly increasing</strong> if each element is strictly greater than its previous one (if exists).</p>

<p>A <strong>sequence</strong> is said to be <strong>strictly decreasing</strong> if each element is strictly smaller than its previous one (if exists).</p>

## Examples

### Example 1:

<b>Input:</b> `n = 3, l = 4, r = 5`

<b>Output:</b> 2

<b>Explanation:</b>

There are only 2 valid ZigZag arrays of length <code>n = 3</code> using values in the range `[4, 5]`:
- `[4, 5, 4]`
- `[5, 4, 5]`

### Example 2:

<strong>Input:</strong> `n = 3, l = 1, r = 3`

<p><strong>Output:</strong> <span class="example-io">10</span></p>

<p><strong>Explanation:</strong></p>

<p>There are 10 valid ZigZag arrays of length <code>n = 3</code> using values in the range <code>[1, 3]</code>:</p>

<ul>
	<li><code>[1, 2, 1]</code>, <code>[1, 3, 1]</code>, <code>[1, 3, 2]</code></li>
	<li><code>[2, 1, 2]</code>, <code>[2, 1, 3]</code>, <code>[2, 3, 1]</code>, <code>[2, 3, 2]</code></li>
	<li><code>[3, 1, 2]</code>, <code>[3, 1, 3]</code>, <code>[3, 2, 3]</code></li>
</ul>

<p>All arrays meet the ZigZag conditions.</p>

### Example 3

**Input:** `n=7, l=10, r-54`

**Output:** 302125987

## Constraints

- $3 \le n \le 2000$
- $1 \le l < r \le 2000$


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Topics</h2>
  
  ---

  </summary>

  - Dynamic Programming
  - Prefix Sum
  
</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>

  **Dynamic Programming:** let $dp[i][dir][x]$ be the number of ZigZag arrays with length $i$,  the last direction (increasing/decreasing) $dir$, and the last element is $x$.
</details>

<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 2</h2>
  
  ---

  </summary>

  If the required move is <code>up</code> (dir=1) do <code>dp[i+1][0][y] += sum(dp[i][1][x]) for x &lt; y</code>; if the required move is <code>down</code> (dir=0) do <code>dp[i+1][1][y] += sum(dp[i][0][x]) for x &gt; y</code>.
</details>
<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 3</h2>
  
  ---

  </summary>

  Speed up with prefix/suffix sums so each layer updates in O(<code>m</code>) instead of O(<code>m</code><sup>2</sup>); take values mod <code>10<sup>9</sup>+7</code>.
</details>

