> [Link to Leetcode](https://leetcode.com/problems/total-waviness-of-numbers-in-range-i/description/)

> Difficulty : <strong style="color: orange">Medium</strong>


## Overview

You are given two integers `num1` and `num2` representing an inclusive range `[num1, num2]`.

The waviness of a number is defined as the total count of its **peaks** and **valleys**:

- A digit is a **peak** if it is **strictly greater** than both of its immediate neighbors.
- A digit is a **valley** if it is **strictly less** than both of its immediate neighbors.
- The first and last digits of a number **cannot** be peaks or valleys.
- Any number with fewer than 3 digits has a waviness of 0.

Return the total sum of waviness for all numbers in the range `[num1, num2]`.

## Examples

### Example 1:

**Input**: `num1 = 120, num2 = 130`

**Output**: $3$

**Explanation**:

&nbsp;&nbsp;In the range $[120, 130]$:

- $120$: middle digit $2$ is a peak, waviness = 1.
- $121$: middle digit $2$ is a peak, waviness = 1.
- $130$: middle digit $3$ is a peak, waviness = 1.
- All other numbers in the range have a waviness of 0.

&nbsp;&nbsp;Thus, **total waviness** is $1 + 1 + 1 = 3$.

### Example 2:

Input: `num1 = 198, num2 = 202`

Output: $3$

Explanation:

&nbsp;&nbsp;In the range $[198, 202]$:
- $198$: middle digit 9 is a peak, waviness = 1.
- $201$: middle digit 0 is a valley, waviness = 1.
- $202$: middle digit 0 is a valley, waviness = 1.
- All other numbers in the range have a waviness of 0.

&nbsp;&nbsp;Thus, **total waviness** is $1 + 1 + 1 = 3$.

<p>&nbsp;</p>

### Example 3:

Input: `num1 = 4848, num2 = 4848`

Output: $2$

Explanation:

&nbsp;&nbsp; The second digit ($8$) is a peak, and third digit ($4$) is a valley.

&nbsp;&nbsp;Thus, **total waviness** is $2$.

<p>&nbsp;</p>

## Constraints

$1 \le\ $`num1` $\le$ `num2` $\le 10^5$

<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Topics</h2>
  
  ---

  </summary>

  - Math
  - Dynamic Programming
  
</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>

  Use brute-force
</details>
