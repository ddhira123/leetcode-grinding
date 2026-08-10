# Overview

## Given

- `edges` , where `edges[i] = [u, v, c]`:
    - That array means there is a directed edge from node $u$ to node $v$ with cost $c$.

- `online`, where `online[u]` is the flag whether the node $u$ is online (`1`) or not (`0`).
- `k`, the maximum total cost acceptable for the path from node $0$ to node $n-1$ to be considered as valid path.

## Valid Path

Requirements of **valid** path:

- Starts from $0$ and ends at $n-1$
- All nodes in the path are **online**
- The total cost of all edges in the path is **at most** $k$.

## Goal

1. The **score** of a path is defined as the minimum edge cost along that path. Our goal is to maximize this score.
2. If **no valid path exists**, return $−1$.

## Initial Thoughts

To make our work simpler, _we take out all **offline nodes**, the edges that contain offline node and edges which its cost is larger than $k$._

> Remember, we only care about valid path.

Our goal is to maximize the score.

> The score is absolutely the cost of any edge in the graph that a part of it will be selected. Thus, its minimum and maximum possible values is between minimum edge cost and maximum edge cost. 

Can we solve this heuristically (binary search)?

> If $x$ is the **score** of a valid path, then the cost of every edge in that valid path is at least $x$. Thus, if we want to search for a path with score $x$, there can't be any edge in the path with cost $\le x$.

> So, we can take a heuristic approach with binary search on the **score guessing**, then validate whether there is a valid path with every edge cost is at least the guess, a.k.a. $x$.

> Our goal is to maximize the score, thus we narrow down the search of $x$ to larger score as long as a valid path with score $x$ exist.

The total cost of a valid path is capped at $k$, there can be multiple paths from any online node $u$ to another online node $v$.

> As it has only maximum total cost cap, we need to revisit the node $v$ if the total cost of current path is smaller than the other path we had traversed. Because, this can be another valid path! This pretty much suits Dijkstra's shortest path algorithm.


# Approach : Binary Search + Dijkstra

1. Initialize $l$, the lower bound for binary search. This will contain lowest edge cost in `edges`
1. Initialize $r$, the upper bound for binary search. This will contain highest edge cost in `edges`
1. Initialize the graph with adjacency list, so 
    
    $g[u] = [[v_1, c_1], [v_2, c_2], \dots]$

   $g[u]$ contains all online nodes $v_i$ that has a directed edge from $u$, alongside its cost $c_i$.

1. Initialize the answer to $-1$.
1. Do the loop on `edges` to register all valid edges, and update the $l$ and $r$
1. **Binary search**

    While $l \le r$:
        
    1. Let $m$ be the half of $l$ and $r$
    2. Check whether there's a valid path from $0$ to $n-1$ with score $m$:
        - If it's true:
            - Update the answer to $m$
            - $l \leftarrow m + 1$
        - Otherwise:
            - $r \leftarrow m - 1$

1. Return the answer.

## The Check Function


1. Let $dis$ be an array sized $n$, where $dis[v]$ is **minimum total cost** in a path from node $0$ to node $v$.

2. Let $pq$ be a priority queue with total cost ordering, each entry contains 2 values:
    - The next node $v$
    - The total cost in currently traversed path

3. Insert $(0, 0)$ to $pq$, as we start from node $0$, and no edges passed.

4. While $pq$ is not empty:
    
    1. Let `top` be the top element of $pq$, and pop it out. 
        - Let $u$ be the currently visited node `top[0]` 
        - Let $tc$ be total cost of current path.

    1. If $tc > k$, then we **stop entire check process and return `false`**, because the total cost of the path with minimum cost **exceeds $k$**.

    1. If $u \equiv n-1$, we achieved the target node with current path that satisfies the requirements and each edge's cost is greater than or equals $m$ (our guess). Thus, **stop entire check process and return `true`**

    1. If $dis[u] < tc$, then we ***stop*** traversing this path from $0$ to $u$, and continue the next `top`.

    1. For each **adjacent node** $v$, which the edge cost of $u \rightarrow v$ is at least $m$ :
        - Let $w$ be edge cost from $u$ to $v$
        - If $dis[v] > dis[u] + w$, then insert $(v, dis[u] + w)$ to $pq$.

5. **Return false**, as no valid path from $0$ to $n-1$ that satisfies the requirements, was found.

# Code


```java
class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        List<List<int[]>> g = new ArrayList<>(); 
        for(int i=0; i<n; i++)
            g.add(new ArrayList<>());

        int l = Integer.MAX_VALUE, r = 0;
        for(int[] e : edges) {
            if(online[e[0]] && online[e[1]] && e[2] <= k){
                g.get(e[0]).add(new int[]{e[1], e[2]});

                l = Math.min(l, e[2]);
                r = Math.max(r, e[2]);
            }
        }

        if(g.isEmpty() || g.get(0).isEmpty())
            return -1;

        int ans = -1;
        while(l <= r) {
            int m = l + (r - l) / 2;
            if(isValidPath(g, m, (long) k, n)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    
    }
    
    // Check function
    private boolean isValidPath(List<List<int[]>> g, int m, long k, int n) {
        long[] dis = new long[n]; // Distance from 0 to any node
        Arrays.fill(dis, Long.MAX_VALUE);
        PriorityQueue<long[]> pq = new PriorityQueue<long[]>((a, b) -> Long.compare(a[1], b[1]));

        dis[0] = 0;
        pq.offer(new long[]{0,0});

        while(!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];

            if(cur[1] > k)
                return false;

            if(u == n - 1)
                return true;
            
            if(cur[1] > dis[u])
                continue;
            
            for(int[] edge : g.get(u)) {
                int v = edge[0];
                int w = edge[1];

                if(w >= m && dis[v] > dis[u] + w) {
                    dis[v] = dis[u] + w;
                    pq.offer(new long[]{v, (long) dis[v]});
                }
            }
        }

        return false;
    }
}
```

    