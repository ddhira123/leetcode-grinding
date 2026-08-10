> [Link to Leetcode](https://leetcode.com/problems/stone-game-iv/description/)

> Difficulty : <strong style="color: red">Hard</strong>

# Overview

Alice and Bob take turns playing a game, with Alice starting first.

Initially, there are $n$ stones in a pile. On each player's turn, that player makes a move consisting of removing **any non-zero square number** of stones in the pile.

Also, if a player cannot make a move, he/she loses the game.

Given a positive integer $n$, return `true` if and only if Alice wins the game otherwise return `false`, assuming both players play optimally.

# Examples

## Example 1

<pre><strong>Input:</strong> n = 1
<strong>Output:</strong> true
<strong>Explanation: </strong>Alice can remove 1 stone winning the game because Bob doesn't have any moves.</pre>

## Example 2

<pre><strong>Input:</strong> n = 2
<strong>Output:</strong> false
<strong>Explanation: </strong>Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -&gt; 1 -&gt; 0).
</pre>

## Example 3

<pre><strong>Input:</strong> n = 2
<strong>Output:</strong> false
<strong>Explanation: </strong>Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -&gt; 1 -&gt; 0).
</pre>

# Constraints

- $1 \le n \le 10^5$


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Topics</h2>
  
  ---

  </summary>

  
- Math
- Dynamic Programming
- Game Theory
  
</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>

  Given some number of stones, Alice can win if she can force Bob onto a losing state. Use dynamic programming to keep track of winning and losing states.
</details>
