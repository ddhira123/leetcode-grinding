# Intuition
<!-- Describe your first thoughts on how to solve this problem. -->

**How to get minimum cost:**

> Maximizing the number of candies with highest cost that adhere the rules.

**The rule of free candy:**

> The lowest cost of 3 candies you take from the pool of candies.

- If the count of the rest of candies is less than 3, then no free candy.
- When 3 candies are picked, then one with lowest cost amongst them is free.

### Algorithm To Make it Optimized? : Sorting

- Sort the candies.
- Iterate the candies from highest cost to lowest cost:
    - If you had taken 2 paid candies, then **take this candy for free.**
    - Else, add this candy's cost to the answer (this is paid candy).



# Complexity
- Time complexity: $$O(n\ \log n)$$
<!-- Add your time complexity here, e.g. $$O(n)$$ -->

- Space complexity: $$O(1)$$
<!-- Add your space complexity here, e.g. $$O(n)$$ -->
