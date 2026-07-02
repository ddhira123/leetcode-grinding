# Overview

## Given

- A `grid` sized $n \times n$.
- Each cell in `grid` may contain either $0$ or $1$:
    - $0$ : the cell is safe
    - $1$ : the cell contains thief

## Our task:

Find the **maximum safeness factor** of all paths from top-left corner $(0,0)$ to bottom-right corner $(n-1, n-1)$.

> The safeness factor of a cell is the **Manhattan distance** from the cell to the **nearest cell containing thief.**

> The safeness factor of a path is defined as the **minimum Manhattan distance** from any cell in the path to any thief in the `grid`

## Key Observation:

1. Manhattan distance between two cells is the sum of the absolute differences of their row and column indices.

    E.g.: We want to measure the Manhattan distance from cell $X$ to cell $Y$
    $$
    X = (1, 0) \\
    Y = (2, 3) \\
    \text{Manhattan Distance}(X,Y) = |2 - 1 | + |3-0| = 4 
    $$

2. All the cells in the grid contain either $0$ or $1$, representing empty cells and cells containing thieves respectively.

3. You start from the top-left corner $(0, 0)$ and can move to adjacent cells in any of the **four directions** (up, down, left, right).

4. The maximum level of safety one can achieve while traversing from the starting point to the destination is by ensuring the **least proximity** to any cell containing a thief.

5. Safeness Factor

    Since we can only go to any of 4-directional neighboring valid cells from a cell. Then, the safeness factor of a cell at $(i,j)$, a.k.a. $SF[i][j]$ is:
    
    $\min(SF[i-1][j], SF[i+1][j], SF[i][j-1], SF[i][j+1])$


    E.g.: We have $3 \times 3$ `grid` like this
    <table>
    <tr>
    <td>0</td><td>0</td><td>1</td>
    </tr>
    <tr>
    <td>0</td><td>0</td><td>0</td>
    </tr>
    <tr>
    <td>0</td><td>0</td><td>0</td>
    </tr>
    </table>

    The safeness factor of each cell in the grid (safeness grid) is:
    <table>
    <tr>
    <td>2</td><td>1</td><td>0</td>
    </tr>
    <tr>
    <td>3</td><td>2</td><td>1</td>
    </tr>
    <tr>
    <td>4</td><td>3</td><td>2</td>
    </tr>
    </table>

    There are 2 paths with **maximum** safeness factors $= 2$ :
    - $(0,0)\rightarrow(1,0)\rightarrow(2,0)\rightarrow(2,1)\rightarrow(2,2)$
        
        with calculation:  $\min(2,3,4,3,2) = 2$
    
    - $(0,0)\rightarrow(1,0)\rightarrow(1,1)\rightarrow(2,1)\rightarrow(2,2)$
        
        with calculation:  $\min(2,3,2,3,2) = 2$



# Approach 1 : BFS + Binary Search

## Algorithm

### Multi-source Breadth-First Search

To make it faster on searching path with maximum safeness factor, we count the safeness factor for each cell in the `grid` like in the Key Observation.

The traversal with DFS or single source BFS may do the job. However, we are given 2D `grid`, that may contain thief. The thief is kind of epicenter that may impacts the safeness factors in other cells (see the Key Observation - Safeness factor). So, recalculations will be necessary and it's not efficient.

Since we know that thief cell is epicenter that impacts surrounding cells, it'd be more efficient to do **multi-source Breadth-First Search**. We start the traversals from each *thief cell* and goes through each surrounding cells in every possible directions.

Multi-source BFS example : 

- We have this `grid`

    <table>
    <tr>
    <td>0</td><td>0</td><td>1</td>
    </tr>
    <tr>
    <td>1</td><td>0</td><td>0</td>
    </tr>
    <tr>
    <td>0</td><td>0</td><td>0</td>
    </tr>
    </table>

- The *safeness grid* initially are **"empty" matrix** sized $n \times n$ (can be initialized with `-1` or `INT_MAX`)
- Traversal Steps:
    > valid cells are the cells within the index boundary $0 \le i,j <n$. unvisited cells are the cells that haven't counted for its safeness factor and not in the Queue.

    1. Get *thief cells* into the Queue: $\{(0,2), (1,0)\}$

        Set their safeness factor to $0$
        <table>
        <tr>
        <td>-1</td><td>-1</td><td>0</td>
        </tr>
        <tr>
        <td>0</td><td>-1</td><td>-1</td>
        </tr>
        <tr>
        <td>-1</td><td>-1</td><td>-1</td>
        </tr>
        </table>

    1. Check the First *Thief Cell* $(0,2)$ 

        Then, pop current cell from the Queue and insert neighboring valid unvisited cells into the Queue, then update their safeness factors. Thus the Queue becomes  

        $$\{(1,0), (0,1), (1,2)\}$$

        Current safeness factor grid is:

        
        <table>
        <tr>
        <td>-1</td><td>1</td><td>0</td>
        </tr>
        <tr>
        <td>0</td><td>-1</td><td>1</td>
        </tr>
        <tr>
        <td>-1</td><td>-1</td><td>-1</td>
        </tr>
        </table>

    1. Check Second *Thief Cell* $(1,0)$ 

    
        Then, pop current cell from the Queue and insert neighboring valid unvisited cells $\{(0,0),(1,1),(2,0)\}$ into the Queue. Thus the Queue becomes  

        $$\{(0,1), (1,2), (0,0), (1,1), (2,0)\}$$


        Current safeness factor grid is:

        <table>
        <tr>
        <td>1</td><td>1</td><td>0</td>
        </tr>
        <tr>
        <td>0</td><td>1</td><td>1</td>
        </tr>
        <tr>
        <td>1</td><td>-1</td><td>-1</td>
        </tr>
        </table>

        
    1. Check The Query Front Cell $(0,1)$ 
    
        Then, pop current cell from the Queue, all neighboring cells are already inserted / checked. Thus the Queue is  

        $$\{(1,2), (0,0), (1,1), (2,0)\}$$


        Current safeness factor grid is the same as previous step.
        
    1. Check The Query Front Cell $(1,2)$ 
    
        Then, pop current cell from the Queue and insert neighboring valid unvisited cells $\{(2,2)\}$ into the Queue, then update their safeness factors.. Thus the Queue is  

        $$\{(0,0), (1,1), (2,0), (2,2)\}$$

        Current safeness factor grid is:

        <table>
        <tr>
        <td>1</td><td>1</td><td>0</td>
        </tr>
        <tr>
        <td>0</td><td>1</td><td>1</td>
        </tr>
        <tr>
        <td>1</td><td>-1</td><td>2</td>
        </tr>
        </table>

    1. Check The Query Front Cell $(0,0)$ 
    
        Then, pop current cell from the Queue, all neighboring cells are already inserted / checked. Thus the Queue is  

        $$\{(1,1), (2,0), (2,2)\}$$


        Current safeness factor grid is the same as previous step.

    1. Check The Query Front Cell $(1,1)$ 
    
        Then, pop current cell from the Queue and insert neighboring valid unvisited cells $\{(2,1)\}$ into the Queue, then update their safeness factors.. Thus the Queue is  

        $$\{(2,0), (2,2), (2,1)\}$$

        Current safeness factor grid is:

        <table>
        <tr>
        <td>1</td><td>1</td><td>0</td>
        </tr>
        <tr>
        <td>0</td><td>1</td><td>1</td>
        </tr>
        <tr>
        <td>1</td><td>2</td><td>2</td>
        </tr>
        </table>

    1. The rest cells in Queue had their safeness factor calculated, and that safeness grid is already final since then.


Now, we have $n \times n$ safeness grid, we need to find a path with maximum safeness factor. 

Brute-forcing all paths? Too slow! It's estimated $O(n ^ {n^2})$.

### Binary Search + BFS

Instead of brute-force, we can take a heuristic approach: Binary Search the possible safeness factors, then validate it.

The boundaries are already known: the **lowest** and **highest safeness factors** among every cells in the safeness grid.

Goal: 
> Find the **Biggest possible safeness factor** that exists in any possible paths from top left to bottom right.

#### Binary Search

<ul>
<li>Initialize <code>start</code> with <b>smallest</b> element within the safeness grid and <code>end</code> with <b>biggest</b> element within the safeness grid variables.</li>
<li>Initialize <code>res</code> to store the maximum safeness value.</li>
<li>Loop through the <code>grid</code> to find the maximum safeness factor and assign it to <code>end</code>.</li>
<li>While <code>start</code> is less than or equal to <code>end</code>:
<ul>
<li>Calculate <code>mid</code>.</li>
<li>Check if a valid safeness exists for <code>mid</code> using BFS.</li>
<li>Update <code>res</code> if valid safeness is found.</li>
<li>Update <code>start</code> or <code>end</code> based on the result of BFS Check.</li>
</ul>
</li>
</ul>

#### BFS Check Steps:
<ol>
<li data-length="1">
<p>Take the <code>sf</code> grid and the minimum safeness value as input.</p>
</li>
<li data-length="1">
<p>Initialize variables:</p>
<ul>
<li><code>n</code> as the size of the <code>grid</code>.</li>
<li><code>q</code> as a queue of coordinates to perform the breadth-first search (BFS).</li>
<li><code>visited</code> as a 2-D array to mark visited cells.</li>
</ul>
</li>
<li data-length="1">
<p>Check if the source and destination cells satisfy the minimum safeness.</p>
</li>
<li data-length="1">
<p>Perform a breadth-first search (BFS) to find a valid path:</p>
<ul>
<li>Initialize a queue <code>q</code> to contain the coordinates.</li>
<li>Add the source cell (<code>0</code>, <code>0</code>) to the queue.</li>
<li>While the queue is not empty:
<ul>
<li>Retrieve the front element <code>curr</code> from the queue.</li>
<li>Explore neighboring cells in all directions:
<ul>
<li>If the neighboring cell is valid, unvisited and has a safeness value greater than or equal to the minimum safeness value:
<ul>
<li>Mark the cell as visited and push it to the queue.</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
<li>If a valid path is found, return <code>true</code>.</li>
</ul>
</li>
<li data-length="1">
<p>Return <code>false</code> if no valid path is found.</p>
</li>
</ol>


Continuing from the example:

1. Determine boundaries from the $SF$ grid

    $sf$ grid:
    <table>
    <tr>
    <td>1</td><td>1</td><td>0</td>
    </tr>
    <tr>
    <td>0</td><td>1</td><td>1</td>
    </tr>
    <tr>
    <td>1</td><td>2</td><td>2</td>
    </tr>
    </table>

    <pre>
    <b>start</b> = 0
    <b>end</b> = 2</pre>

2. Our guess for `mid` is $\frac{(0 + 2)}{2} = 1$
3. Validate the `mid = 1` with BFS:
    1. Both `sf[0][0]` and `sf[n-1][n-1]` are greater than or equal `1`, so we will do the path check.
    2. Initially:

        ```
        queue = [[0,0]]
        visited[0][0] = true
        ```
    3. Pop the cell at front of `queue`, and Check its valid unvisited neighboring cells and mark them all as visited:
        - $(1,0) \rightarrow $ `sf[1][0] = 0 < mid`, so we won't search any path through this.
        - $(0,1) \rightarrow $ `sf[0][1] = 1 = mid`, insert this to `queue`

        Thus, `queue = [[0,1]]`

    3. Pop the cell at front of `queue`, and Check its valid unvisited neighboring cells and mark them all as visited:
        - $(0,2) \rightarrow $ `sf[0][2] = 0 < mid`, so we won't search any path through this.
        - $(1,1) \rightarrow $ `sf[1][1] = 1 = mid`, insert this to `queue`

        Thus, `queue = [[1,1]]`

    3. Pop the cell at front of `queue`, and Check its valid unvisited neighboring cells and mark them all as visited:
        - $(1,2) \rightarrow $ `sf[1][2] = 1 = mid`, insert this to `queue`
        - $(2,1) \rightarrow $ `sf[2][1] = 2 > mid`, insert this to `queue`

        Thus, `queue = [[1,2],[2,1]]`

    3. Pop the cell at front of `queue`, and Check its valid unvisited neighboring cells and mark them all as visited:
        - $(2,2) \rightarrow $ `sf[2][2] = 1 = mid`, it's the **bottom-right** of the `grid`.

        Thus, there is a valid path from $(0,0)$ to $(2,2)$ with safeness factor `1`.
3. Update `start`, `end`, and `res`:
    - `start = 2`
    - `end = 2`
    - `res = 1`
4. Our new `mid` is $2$.
5. When we validate, we find that `sf[0][0] = 1 < mid`, so there's no any valid path with safeness factor $2$. Thus, we end the search and return the answer $1$.

#### Combine them up

1. Do multi-source BFS to get the **safeness grid**.
2. Do the Binary Search + BFS to search for the answer.

## Complexity

- **Time Complexity** : $O(n^2 \log(n))$

    - Seach Thief Cells : $O(n^2)$
    - Multi-Source BFS : $O(n^2)$
    - Binary Search + BFS : $O(\log(2n) \times n^2)$

- **Space Complexity** : $O(n^2)$

    Each of `queue` and `visited` array  takes $O(n^2)$


## Code

```java
class Solution {

    // Directions for moving to neighboring cells: right, left, down, up
    final int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] sf = new int[n][n];
        Queue<int[]> multiSourceQueue = new LinkedList<>();

        // To make modifications and navigation easier, the grid is converted into a 2-d array.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    // Push thief coordinates to the queue
                    multiSourceQueue.add(new int[]{i, j});
                    // Mark thief cell with 0
                    sf[i][j] = 0;
                } else {
                    // Mark empty cell with -1
                    sf[i][j] = -1;
                }
            }
        }

        // Calculate safeness factor for each cell using BFS
        while (!multiSourceQueue.isEmpty()) {
            int size = multiSourceQueue.size();
            while (size-- > 0) {
                int[] curr = multiSourceQueue.poll();
                // Check neighboring cells
                for (int[] d : dir) {
                    int di = curr[0] + d[0];
                    int dj = curr[1] + d[1];
                    int val = sf[curr[0]][curr[1]];
                    // Check if the neighboring cell is valid and unvisited
                    if (isValidCell(sf, di, dj) && sf[di][dj] == -1) {
                        // Update safeness factor and push to the queue
                        sf[di][dj] = val + 1;
                        multiSourceQueue.add(new int[]{di, dj});
                    }
                }
            }
        }

        // Binary search for maximum safeness factor
        int start = 0;
        int end = 0;
        int res = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Set end as the maximum safeness factor possible
                end = Math.max(end, sf[i][j]);
            }
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (isValidSafeness(sf, mid)) {
                // Store valid safeness and search for larger ones 
                res = mid; 
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return res;
    }

    // Check if a path exists with given minimum safeness value
    private boolean isValidSafeness(int[][] grid, int minSafeness) {
        int n = grid.length;

        // Check if the source and destination cells satisfy minimum safeness
        if (grid[0][0] < minSafeness || grid[n - 1][n - 1] < minSafeness) {
            return false;
        }

        Queue<int[]> traversalQueue = new LinkedList<>();
        traversalQueue.add(new int[]{0, 0});
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;

        // Breadth-first search to find a valid path
        while (!traversalQueue.isEmpty()) {
            int[] curr = traversalQueue.poll();
            if (curr[0] == n - 1 && curr[1] == n - 1) {
                return true; // Valid path found
            }
            // Check neighboring cells
            for (int[] d : dir) {
                int di = curr[0] + d[0];
                int dj = curr[1] + d[1];
                // Check if the neighboring cell is valid, unvisited and satisfying minimum safeness
                if (isValidCell(grid, di, dj) && !visited[di][dj] && grid[di][dj] >= minSafeness) {
                    visited[di][dj] = true;
                    traversalQueue.add(new int[]{di, dj});
                }
            }
        }

        return false; // No valid path found
    }

    // Check if a given cell lies within the grid
    private boolean isValidCell(int[][] mat, int i, int j) {
        int n = mat.length;
        return i >= 0 && j >= 0 && i < n && j < n;
    }
}
```

# Approach 2 : BFS + Priority Queue

Instead of guessing heuristically, we can traverse the subpaths from the highest safeness factor to the lowest. Pretty much like Dijkstra's Algorithm, The first path to arrive at the bottom right corner of the grid is the answer.

## Algorithm

### Multi-source BFS

This one is the same as previous approach

### BFS + Priority Queue

- <p>Initialize a priority queue <code>pq</code> to prioritize cells with a higher safeness factor. Push the starting cell to <code>pq</code>.</p>

- Perform BFS to find the path with the maximum safeness factor:
    - While the priority queue <code>pq</code> is not empty:
        - Retrieve the top element <code>curr</code> from <code>pq</code>.
        - If the destination is reached, **return the safeness factor** of the path.
        - Explore neighboring cells:
            - If the neighboring cell is valid and not marked as visited:
                - Update the safeness factor for the path and mark the cell as visited.


### Combine All

1. Do the multi-source BFS
2. Do the BFS + Priority Queue.

## Complexity


- **Time Complexity** : $O(n^2 \log(n))$

    - Seach Thief Cells : $O(n^2)$
    - Multi-Source BFS : $O(n^2)$
    - Priority Queue : $O(n^2 \log(n))$

- **Space Complexity** : $O(n^2)$

    Each of `queue`, `pq` and `visited` array  takes $O(n^2)$

## Code

```java
class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        if(grid.get(0).get(0) == 1 || grid.get(n-1).get(n-1) == 1)
            return 0;

        Queue<int[]> q = new LinkedList<>();
        int[][] sf = new int[n][n];

        // Search Thief Cells
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(grid.get(i).get(j) == 1) {
                    sf[i][j] = 0;
                    q.offer(new int[]{i, j});
                } else {
                    sf[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // Multi source BFS
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            
            if(r < n - 1 && sf[r + 1][c] > sf[r][c] + 1) {
                sf[r + 1][c] = sf[r][c] + 1;
                q.offer(new int[]{r + 1, c});
            }
            if(c < n - 1 && sf[r][c + 1] > sf[r][c] + 1) {
                sf[r][c + 1] = sf[r][c] + 1;
                q.offer(new int[]{r, c + 1});
            }
            if(c > 0 && sf[r][c - 1] > sf[r][c] + 1) {
                sf[r][c - 1] = sf[r][c] + 1;
                q.offer(new int[]{r, c - 1});
            }
            if(r > 0 && sf[r - 1][c] > sf[r][c] + 1) {
                sf[r - 1][c] = sf[r][c] + 1;
                q.offer(new int[]{r - 1, c});
            }
        }

        // BFS + Priority Queue
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);
        pq.offer(new int[]{0, 0, sf[0][0]});
        sf[0][0] = -1;

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int r = cur[0];
            int c = cur[1];
            int safeness_ = cur[2];

            if(r == n - 1 && c == n - 1)
                return safeness_;
            
            
            if(r < n - 1 && sf[r + 1][c] != -1) {
                pq.offer(new int[]{r + 1, c, Math.min(safeness_, sf[r + 1][c])});
                sf[r + 1][c] = -1;
            }
            if(c < n - 1 && sf[r][c + 1] != -1) {
                pq.offer(new int[]{r, c + 1, Math.min(safeness_, sf[r][c + 1])});
                sf[r][c + 1] = -1;
            }
            if(c > 0 && sf[r][c - 1] != -1) {
                pq.offer(new int[]{r, c - 1, Math.min(safeness_, sf[r][c - 1])});
                sf[r][c - 1] = -1;
            }
            if(r > 0 && sf[r - 1][c] != -1) {
                pq.offer(new int[]{r - 1, c, Math.min(safeness_, sf[r - 1][c])});
                sf[r - 1][c] = -1;
            }

        }

        return 0;
    }
}
```