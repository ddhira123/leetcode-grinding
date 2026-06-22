# Overview

One instance of the word `"balloon"` consists all of these:
- 1 `'b'`
- 1 `'a'`
- 2 `'l'`
- 2 `'o'`
- 1 `'n'`

Given a string `text`, that we can treat it as pool of usable letters, we need to answer *How many instances of* `"balloon"` *we can make?*

Let's say we have counted the number of those letters in `text` and resulted this:
- `b` = the count of `'b'`
- `a` = the count of `'a'`
- `l` = the count of `'l'`
- `o` = the count of `'o'`
- `n` = the count of `'n'`

Remember, in one instance of `"balloon"`, each of the letter `'l'` and `'o'` appears **twice**.

Therefore, the formula to answer this question will be:

$$
\min(b,a,\lfloor\frac{l}{2}\rfloor, \lfloor\frac{o}{2}\rfloor,n)
$$

# Algorithm

1. Initialize these 5 variables with value `0` in the beginning:
    - `b` = the count of `'b'`
    - `a` = the count of `'a'`
    - `l` = the count of `'l'`
    - `o` = the count of `'o'`
    - `n` = the count of `'n'`

2. Enumerate each letter in `text` and increment its counts accordingly.

3. Return the minimum value of the value 5 variables (with $l$ and $o$ values are the floored half).

## Code
```java
class Solution {
    public int maxNumberOfBalloons(String text) {
        int a = 0, b = 0, l = 0, n = 0, o = 0;
        for(char c : text.toCharArray()) {
            switch(c) {
                case 'a' -> a++;
                case 'b' -> b++;
                case 'l' -> l++;
                case 'o' -> o++;
                case 'n' -> n++;
            }
        }
        return Math.min(Math.min(Math.min(Math.min(a, b), l / 2), o / 2), n);
    }
}
```