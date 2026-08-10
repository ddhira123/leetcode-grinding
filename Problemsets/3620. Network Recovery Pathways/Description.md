> [Link to Leetcode](https://leetcode.com/problems/network-recovery-pathways/description)

> Difficulty : <strong style="color: red">Hard</strong>

# Overview

You are given a directed acyclic graph of <code>n</code> nodes numbered from 0 to <code>n − 1</code>. This is represented by a 2D array <code data-end="201" data-start="194">edges</code> of length<font face="monospace"> <code>m</code></font>, where <code data-end="255" data-start="227">edges[i] = [u<sub>i</sub>, v<sub>i</sub>, cost<sub>i</sub>]</code> indicates a one‑way communication from node <code data-end="304" data-start="300">u<sub>i</sub></code> to node <code data-end="317" data-start="313">v<sub>i</sub></code> with a recovery cost of <code data-end="349" data-start="342">cost<sub>i</sub></code>.

Some nodes may be offline. You are given a boolean array <code data-end="416" data-start="408">online</code> where <code data-end="441" data-start="423">online[i] = true</code> means node <code data-end="456" data-start="453">i</code> is online. Nodes 0 and <code>n − 1</code> are always online.

A path from 0 to <code>n − 1</code> is <strong data-end="541" data-start="532">valid</strong> if:

<ul>
	<li>All intermediate nodes on the path are online.</li>
	<li data-end="676" data-start="605">The total recovery cost of all edges on the path does not exceed <code>k</code>.</li>
</ul>

For each valid path, define its <strong data-end="694" data-start="685">score</strong> as the minimum edge‑cost along that path.

Return the <strong>maximum</strong> path score (i.e., the largest <strong>minimum</strong>-edge cost) among all valid paths. If no valid path exists, return -1.

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<p><strong>Input:</strong> 
<pre>edges = [[0,1,5],[1,3,10],[0,2,3],[2,3,4]]
online = [true,true,true,true]
k = 10</pre>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2025/06/06/graph-10.png" style="width: 239px; height: 267px;"></p>

<p>The graph has two possible routes from node 0 to node 3:</p>

- <p data-end="228" data-start="212">Path <code>0 → 1 → 3</code></p>

	- <p data-end="315" data-start="236">Total cost = <code>5 + 10 = 15</code>, which exceeds k (<code>15 &gt; 10</code>), so this path is invalid.</p>

- <p data-end="337" data-start="321">Path <code>0 → 2 → 3</code></p>

	- <p data-end="397" data-start="345">Total cost = <code>3 + 4 = 7 &lt;= k</code>, so this path is valid.</p>
	- <p data-end="462" data-start="405">The minimum edge‐cost along this path is <code>min(3, 4) = 3</code>.

There are no other valid paths. Hence, the maximum among all valid path‐scores is 3.</p>

<p><strong class="example">Example 2:</strong></p>

<p><strong>Input:</strong> <span class="example-io">edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12</span></p>

<p><strong>Output:</strong> <span class="example-io">6</span></p>

<p><strong>Explanation:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2025/06/06/graph-11.png" style="width: 343px; height: 194px;"></p>

- Node 3 is offline, so any path passing through 3 is invalid.</p>
- Consider the remaining routes from 0 to 4:</p>

	- Path <code>0 → 1 → 4</code>
		- Total cost = <code>7 + 5 = 12 &lt;= k</code>, so this path is valid.
		
		- The minimum edge‐cost along this path is <code>min(7, 5) = 5</code>.
	
	- Path <code>0 → 2 → 3 → 4</code>
		- Node 3 is offline, so this path is invalid regardless of cost.
		- Path <code>0 → 2 → 4</code>
		- Total cost = <code>6 + 6 = 12 &lt;= k</code>, so this path is valid.
		- The minimum edge‐cost along this path is <code>min(6, 6) = 6</code>.
- Among the two valid paths, their scores are 5 and 6. Therefore, the answer is 6.

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li data-end="42" data-start="20"><code data-end="40" data-start="20">n == online.length</code></li>
	<li data-end="63" data-start="45"><code data-end="61" data-start="45">2 &lt;= n &lt;= 5 * 10<sup>4</sup></code></li>
	<li data-end="102" data-start="66"><code data-end="100" data-start="66">0 &lt;= m == edges.length &lt;= </code><code>min(10<sup>5</sup>, n * (n - 1) / 2)</code></li>
	<li data-end="102" data-start="66"><code data-end="127" data-start="105">edges[i] = [u<sub>i</sub>, v<sub>i</sub>, cost<sub>i</sub>]</code></li>
	<li data-end="151" data-start="132"><code data-end="149" data-start="132">0 &lt;= u<sub>i</sub>, v<sub>i</sub> &lt; n</code></li>
	<li data-end="166" data-start="154"><code data-end="164" data-start="154">u<sub>i</sub> != v<sub>i</sub></code></li>
	<li data-end="191" data-start="169"><code data-end="189" data-start="169">0 &lt;= cost<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
	<li data-end="213" data-start="194"><code data-end="211" data-start="194">0 &lt;= k &lt;= 5 * 10<sup>13</sup></code></li>
	<li data-end="309" data-start="216"><code data-end="227" data-start="216">online[i]</code> is either <code data-end="244" data-is-only-node="" data-start="238">true</code> or <code data-end="255" data-start="248">false</code>, and both <code data-end="277" data-start="266">online[0]</code> and <code data-end="295" data-start="282">online[n − 1]</code> are <code data-end="306" data-start="300">true</code>.</li>
	<li data-end="362" data-is-last-node="" data-start="312">The given graph is a directed acyclic graph.</li>
</ul>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Topics</h2>
  
  ---

  </summary>

  - Array
  - Binary Search
  - Priority Queue
  - Dynamic Programming
  
</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 1</h2>
  
  ---

  </summary>
	Binary Search for the answer.

</details>


<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 2</h2>
  
  ---

  </summary>
	
Check whether there's a path with all edge costs at least the guessed number

</details>

<br/>
<details>
  <summary><h2 style="display: inline; border-bottom: none !important;">Hint 3</h2>
  
  ---

  </summary>

Implement the check function using either Dijkstra or DP (via topological sorting, since the graph is a DAG).

</details>
