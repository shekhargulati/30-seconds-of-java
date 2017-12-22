# 30-seconds-of-code-java

Curated collection of useful Java 8 snippets that you can understand in 30 seconds or less. This is Java 8 fork of [30-seconds-of-code](https://github.com/Chalarangelo/30-seconds-of-code).

## Array

### arrayGcd

Calculates the greatest common denominator (gcd) of an array of numbers.

Use Array.stream().reduce() and the gcd formula (uses recursion) to calculate the greatest common denominator of an array of numbers.

```java
public static OptionalInt arrayGcd(int[] numbers) {
    return Arrays.stream(numbers)
            .reduce(Snippets::gcd);
}

private static int gcd(int a, int b) {
    if (b == 0) {
        return a;
    }
    return gcd(b, a % b);
}
```
