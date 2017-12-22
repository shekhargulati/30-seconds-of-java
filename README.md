# 30-seconds-of-code-java

Curated collection of useful Java 8 snippets that you can understand in 30 seconds or less. This is Java 8 fork of [30-seconds-of-code](https://github.com/Chalarangelo/30-seconds-of-code).

## Array

### arrayGcd

Calculates the greatest common denominator (gcd) of an array of numbers.

Use Array.stream().reduce() and the gcd formula (uses recursion) to calculate the greatest common denominator of an array of numbers.

```java
public static OptionalInt arrayGcd(int[] numbers) {
    return Arrays.stream(numbers)
            .reduce((a, b) -> gcd(a, b));
}

private static int gcd(int a, int b) {
    if (b == 0) {
        return a;
    }
    return gcd(b, a % b);
}
```

### arrayLcm

Calculates the lowest common multiple (lcm) of an array of numbers.

Use Array.stream().reduce() and the lcm formula (uses recursion) to calculate the lowest common multiple of an array of numbers.

```java
public static OptionalInt arrayLcm(int[] numbers) {
    IntBinaryOperator lcm = (x, y) -> (x * y) / gcd(x, y);
    return Arrays.stream(numbers)
            .reduce((a, b) -> lcm.applyAsInt(a, b));
}

private static int gcd(int a, int b) {
    if (b == 0) {
        return a;
    }
    return gcd(b, a % b);
}
```

### arrayMax

Returns the maximum value in an array.

```java
public static OptionalInt arrayMax(int[] numbers) {
    return Arrays.stream(numbers).max();
}
```

### arrayMin

Returns the minimum value in an array.

```java
public static OptionalInt arrayMin(int[] numbers) {
    return Arrays.stream(numbers).min();
}
```
