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

### chunk

Chunks an array into smaller arrays of specified size.

```java
public static int[][] chunk(int[] numbers, int size) {
    return IntStream.iterate(0, i -> i + size)
            .limit((long) Math.ceil((double) numbers.length / size))
            .mapToObj(cur -> Arrays.copyOfRange(numbers, cur, cur + size > numbers.length ? numbers.length : cur + size))
            .toArray(int[][]::new);
}
```

### countOccurrences

Counts the occurrences of a value in an array.

Use Array.stream().filter().count() to count total number of values that equals the specified value.

```java
public static long countOccurrences(int[] numbers, int value) {
    return Arrays.stream(numbers)
            .filter(number -> number == value)
            .count();
}
```

### deepFlatten

Deep flattens an array.

Use recursion. Use Array.stream().flatMapToInt()

```java
public static int[] deepFlatten(Object[] input) {
    return Arrays.stream(input)
            .flatMapToInt(o -> {
                if (o instanceof Object[]) {
                    return Arrays.stream(deepFlatten((Object[]) o));
                }
                return IntStream.of((Integer) o);
            }).toArray();
}
```
