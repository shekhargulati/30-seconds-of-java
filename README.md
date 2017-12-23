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

### difference

Returns the difference between two arrays.

Create a Set from b, then use Array.stream().filter() on a to only keep values not contained in b.

```java
public static int[] difference(int[] first, int[] second) {
    Set<Integer> set = Arrays.stream(second).boxed().collect(Collectors.toSet());
    return Arrays.stream(first)
            .filter(v -> !set.contains(v))
            .toArray();
}
```

### differenceWith

Filters out all values from an array for which the comparator function does not return true.

The comparator for int is implemented using IntBinaryOperator function.

Uses Array.stream().filter and Array.stream().noneMatch() to find the appropriate values.

```java
public static int[] differenceWith(int[] first, int[] second, IntBinaryOperator comparator) {
    return Arrays.stream(first)
            .filter(a ->
                    Arrays.stream(second)
                            .noneMatch(b -> comparator.applyAsInt(a, b) == 0)
            ).toArray();
}
```

### distinctValuesOfArray

Returns all the distinct values of an array.

Uses Array.stream().distinct() to discard all duplicated values.

```java
public static int[] distinctValuesOfArray(int[] elements) {
    return Arrays.stream(elements).distinct().toArray();
}
```

### dropElements

Removes elements in an array until the passed function returns true. Returns the remaining elements in the array.

Loop through the array, using Arrays.copyOfRange() to drop the first element of the array until the returned value from the function is true. Returns the remaining elements.

```java
public static int[] dropElements(int[] elements, IntPredicate condition) {
    while (elements.length > 0 && !condition.test(elements[0])) {
        elements = Arrays.copyOfRange(elements, 1, elements.length);
    }
    return elements;
}
```

### dropRight

Returns a new array with n elements removed from the right.

Check if n is shorter than the given array and use Array.copyOfRange() to slice it accordingly or return an empty array.

```java
public static int[] dropRight(int[] elements, int n) {
    if (n < 0) {
        throw new IllegalArgumentException("n is less than 0");
    }
    return n < elements.length
            ? Arrays.copyOfRange(elements, 0, elements.length - n)
            : new int[0];
}
```

### everyNth

Returns every nth element in an array.

Use IntStream.range().filter() to create a new array that contains every nth element of a given array.

```java
public static int[] everyNth(int[] elements, int nth) {
     return IntStream.range(0, elements.length)
             .filter(i -> i % nth == nth - 1)
             .map(i -> elements[i])
             .toArray();
 }
```

### indexOf

Find index of element in the array. Return -1 in case element does not exist.

Uses IntStream.range().filter() to find index of the element in the array.

```java
public static int indexOf(int[] elements, int el) {
    return IntStream.range(0, elements.length)
            .filter(idx -> elements[idx] == el)
            .findFirst()
            .orElse(-1);
}
```

### lastIndexOf

Find last index of element in the array. Return -1 in case element does not exist.

Uses IntStream.iterate().limit().filter() to find index of the element in the array.


```java
public static int lastIndexOf(int[] elements, int el) {
    return IntStream.iterate(elements.length - 1, i -> i - 1)
            .limit(elements.length)
            .filter(idx -> elements[idx] == el)
            .findFirst()
            .orElse(-1);
}
```

### filterNonUnique

Filters out the non-unique values in an array.

Use Array.stream().filter() for an array containing only the unique values.

```java
public static int[] filterNonUnique(int[] elements) {
    return Arrays.stream(elements)
            .filter(el -> indexOf(elements, el) == lastIndexOf(elements, el))
            .toArray();
}
```

### flatten

Flattens an array.

Use Array.stream().flatMapToInt().toArray() to create a new array.


```java
public static int[] flatten(Object[] elements) {
    return Arrays.stream(elements)
            .flatMapToInt(el -> el instanceof int[]
                    ? Arrays.stream((int[]) el)
                    : IntStream.of((int) el)
            ).toArray();
}
```

### flattenDepth

Flattens an array up to the specified depth.

```java
public static Object[] flattenDepth(Object[] elements, int depth) {
    if (depth == 0) {
        return elements;
    }
    return Arrays.stream(elements)
            .flatMap(el -> el instanceof Object[]
                    ? Arrays.stream(flattenDepth((Object[]) el, depth - 1))
                    : Arrays.stream(new Object[]{el})
            ).toArray();


}
```

### groupBy

Groups the elements of an array based on the given function.

Uses Arrays.stream().collect(Collectors.groupingBy()) to group based on the grouping function.

```java
public static <T, R> Map<R, List<T>> groupBy(T[] elements, Function<T, R> func) {
    return Arrays.stream(elements).collect(Collectors.groupingBy(func));
}
```

### initial

Returns all the elements of an array except the last one.
Use Arrays.copyOfRange() to return all except the last one

```java
public static <T> T[] initial(T[] elements) {
    return Arrays.copyOfRange(elements, 0, elements.length - 1);
}
```

### initializeArrayWithRange

Initializes an array containing the numbers in the specified range where start and end are inclusive.


```java
public static int[] initializeArrayWithRange(int end, int start) {
    return IntStream.rangeClosed(start, end).toArray();
}
```

### initializeArrayWithValues

Initializes and fills an array with the specified values.

```java
public static int[] initializeArrayWithValues(int n, int value) {
    return IntStream.generate(() -> value).limit(n).toArray();
}
```
