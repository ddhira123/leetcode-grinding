> [Link to Leetcode](https://leetcode.com/problems/maximum-number-of-balloons/description/)

> Difficulty : <strong style="color: green">Easy</strong>

## Overview 

Given a string `text`, you want to use the characters of `text` to form as many instances of the word `"balloon"` as possible.

You can use each character in `text` **at most once**. Return the *maximum number of instances* that can be formed.

## Examples

### Example 1:

<pre>
<b>Input:</b> nlaebolko
<b>Output:</b> 1
</pre>
<!-- <b>Explanation:</b> <span style="color:red">nla</span>e<span style="color:red">bol</span>k<span style="color:red">o</span> -->

### Example 2:

<pre>
<b>Input:</b> loonbalxballpoon
<b>Output:</b> 2
</pre>
<!-- <b>Explanation:</b> <span style="color:red">loonbal</span>x<span style="color:blue">ball</span>p<span style="color:blue">oon</span> -->


## Constraints
- 1 $\le$ `text.length` $\le$ 10<sup>4</sup>
- `text` consists of lower case English letters only.


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Topics</h2>
  
  ---

  </summary>

  - String
  - Hash Table
  - Counting
  
</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>

  Count the frequency of each required letter in "balloon"
</details>

<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 2</h2>
  
  ---

  </summary>

  Find the minimum instances of "balloon" can be made of those counts.
</details>

