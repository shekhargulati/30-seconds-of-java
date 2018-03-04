# 30 Seconds Of Java [![Build Status](https://travis-ci.org/shekhargulati/little-java-functions.svg?branch=master)](https://travis-ci.org/shekhargulati/little-java-functions)

> Curated collection of useful little Java 8 functions that you can understand quickly.

## Table of Contents

### Array

<details>
<summary>View contents</summary>

* [`chunk`](#chunk)
* [`countOccurrences`](#countoccurrences)
* [`deepFlatten`](#deepflatten)
* [`difference`](#difference)
* [`differenceWith`](#differencewith)
* [`distinctValuesOfArray`](#distinctvaluesofarray)
* [`dropElements`](#dropelements)
* [`dropRight`](#dropright)
* [`everyNth`](#everynth)
* [`filterNonUnique`](#filternonunique)
* [`flatten`](#flatten)
* [`flattenDepth`](#flattendepth)
* [`groupBy`](#groupby)
* [`head`](#head)
* [`initial`](#initial)
* [`initializeArrayWithRange`](#initializearraywithrange)
* [`initializeArrayWithValues`](#initializearraywithvalues)
* [`intersection`](#intersection)
* [`isSorted`](#issorted)
* [`join`](#join)
* [`nthElement`](#nthelement)
* [`pick`](#pick)
* [`reducedFilter`](#reducedfilter)
* [`remove`](#remove)
* [`sample`](#sample)
* [`sampleSize`](#samplesize)
* [`shuffle`](#shuffle)
* [`similarity`](#similarity)
* [`sortedIndex`](#sortedindex)
* [`symmetricDifference`](#symmetricdifference)
* [`tail`](#tail)
* [`take`](#take)
* [`takeRight`](#takeright)
* [`union`](#union)
* [`without`](#without)
* [`zip`](#zip)
* [`zipObject`](#zipobject)

</details>

### Math

<details>
<summary>View contents</summary>

* [`average`](#average)
* [`gcd`](#gcd)
* [`lcm`](#lcm)
* [`findNextPositivePowerOfTwo`](#findnextpositivepoweroftwo)
* [`isEven`](#iseven)
* [`isPowerOfTwo`](#ispoweroftwo)
* [`generateRandomInt`](#generaterandomint)

</details>

### String

<details>
<summary>View contents</summary>

* [`anagrams`](#anagrams)
* [`byteSize`](#bytesize)
* [`capitalize`](#capitalize)
* [`capitalizeEveryWord`](#capitalizeeveryword)
* [`countVowels`](#countvowels)
* [`escapeRegExp`](#escaperegexp)
* [`fromCamelCase`](#fromcamelcase)
* [`isAbsoluteURL`](#isabsoluteurl)
* [`isLowerCase`](#islowercase)
* [`isUpperCase`](#isuppercase)
* [`isPalindrome`](#ispalindrome)
* [`isNumeric`](#isnumeric)
* [`mask`](#mask)
* [`reverseString`](#reversestring)
* [`sortCharactersInString`](#sortcharactersinstring)
* [`splitLines`](#splitlines)
* [`toCamelCase`](#tocamelcase)
* [`toKebabCase`](#tokebabcase)
* [`match`](#match)
* [`toSnakeCase`](#tosnakecase)
* [`truncateString`](#truncatestring)
* [`words`](#words)
* [`stringToIntegers`](#stringtointegers)


</details>

### IO

<details>
<summary>View contents</summary>

* [`convertInputStreamToString`](#convertinputstreamtostring)
* [`readFileAsString`](#readfileasstring)
* [`getCurrentWorkingDirectoryPath`](#getcurrentworkingdirectorypath)
* [`tmpDirName`](#tmpdirname)

</details>

### Exception

<details>
<summary>View contents</summary>

* [`stackTraceAsString`](#stacktraceasstring)

</details>

### System

<details>
<summary>View contents</summary>

- [`osName`](#osname)
- [`isDebuggerEnabled`](#isdebuggerenabled)

</details>

### Class

<details>
<summary>View contents</summary>

- [`getAllInterfaces`](#getallinterfaces)
- [`IsInnerClass`](#isinnerclass)

</details>

### Enum

<details>
<summary>View contents</summary>

- [`getEnumMap`](#getenummap)

</details>

## Array

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

### concat

```java
public static <T> T[] concat(T[] first, T[] second) {
    return Stream.concat(
            Stream.of(first),
            Stream.of(second)
    ).toArray(i -> (T[]) Arrays.copyOf(new Object[0], i, first.getClass()));
}
```

### countOccurrences

Counts the occurrences of a value in an array.

Use Arrays.stream().filter().count() to count total number of values that equals the specified value.

```java
public static long countOccurrences(int[] numbers, int value) {
    return Arrays.stream(numbers)
            .filter(number -> number == value)
            .count();
}
```

### deepFlatten

Deep flattens an array.

Use recursion. Use Arrays.stream().flatMapToInt()

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

Create a Set from b, then use Arrays.stream().filter() on a to only keep values not contained in b.

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

Uses Arrays.stream().filter and Arrays.stream().noneMatch() to find the appropriate values.

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

Uses Arrays.stream().distinct() to discard all duplicated values.

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

Use Arrays.stream().filter() for an array containing only the unique values.

```java
public static int[] filterNonUnique(int[] elements) {
    return Arrays.stream(elements)
            .filter(el -> indexOf(elements, el) == lastIndexOf(elements, el))
            .toArray();
}
```

### flatten

Flattens an array.

Use Arrays.stream().flatMapToInt().toArray() to create a new array.


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

### intersection

Returns a list of elements that exist in both arrays.

Create a Set from second, then use Arrays.stream().filter() on a to only keep values contained in b.

```java
public static int[] intersection(int[] first, int[] second) {
    Set<Integer> set = Arrays.stream(second).boxed().collect(Collectors.toSet());
    return Arrays.stream(first)
            .filter(set::contains)
            .toArray();
}
```

### isSorted

Returns `1` if the array is sorted in ascending order, `-1` if it is sorted in descending order or `0` if it is not sorted.

Calculate the ordering `direction` for the first two elements.Use for loop to iterate over array items and compare them in pairs. Return `0` if the `direction` changes or the `direction` if the last element is reached.

```java
public static <T extends Comparable<? super T>> int isSorted(T[] arr) {
    final int direction = arr[0].compareTo(arr[1]) < 0 ? 1 : -1;
    for (int i = 0; i < arr.length; i++) {
        T val = arr[i];
        if (i == arr.length - 1) return direction;
        else if ((val.compareTo(arr[i + 1]) * direction > 0)) return 0;
    }
    return direction;
}
```

### join

Joins all elements of an array into a string and returns this string. Uses a separator and an end separator.

Use IntStream.range to zip index with the array item. Then, use `Stream.reduce`  to combine elements into a string. 

```java
public static <T> String join(T[] arr, String separator, String end) {
    return IntStream.range(0, arr.length)
            .mapToObj(i -> new SimpleEntry<>(i, arr[i]))
            .reduce("", (acc, val) -> val.getKey() == arr.length - 2
                    ? acc + val.getValue() + end
                    : val.getKey() == arr.length - 1 ? acc + val.getValue() : acc + val.getValue() + separator, (fst, snd) -> fst);
}
```

### nthElement

Returns the nth element of an array.

Use `Arrays.copyOfRange()` to get an array containing the nth element at the first place. 

```Java
public static <T> T nthElement(T[] arr, int n) {
    if (n > 0) {
        return Arrays.copyOfRange(arr, n, arr.length)[0];
    }
    return Arrays.copyOfRange(arr, arr.length + n, arr.length)[0];
}
```

### pick

Picks the key-value pairs corresponding to the given keys from an object.

Use `Arrays.stream` to filter all the keys that are present in the `arr`. Then, convert all the keys present into a Map using `Collectors.toMap`.

```java
public static <T, R> Map<T, R> pick(Map<T, R> obj, T[] arr) {
    return Arrays.stream(arr)
            .filter(obj::containsKey)
            .collect(Collectors.toMap(k -> k, obj::get));
}
```

### reducedFilter

Filter an array of objects based on a condition while also filtering out unspecified keys.

Use `Arrays.stream().filter()` to filter the array based on the predicate `fn` so that it returns the objects for which the condition is true. For each filtered Map object, create a new Map with keys present in the `keys`. Finally, collect all the Map object into an array.

```java
public static Map<String, Object>[] reducedFilter(Map<String, Object>[] data, String[] keys, Predicate<Map<String, Object>> fn) {
    return Arrays.stream(data)
            .filter(fn)
            .map(el -> Arrays.stream(keys).filter(el::containsKey)
                    .collect(Collectors.toMap(Function.identity(), el::get)))
            .toArray((IntFunction<Map<String, Object>[]>) Map[]::new);
}
```

### sample

Returns a random element from an array.

Use `Math.random()` to generate a random number, multiply it by `length` and round it of to the nearest whole number using `Math.floor()`. This method also works with strings.

```java
public static <T> T sample(T[] arr) {
    return arr[(int) Math.floor(Math.random() * arr.length)];
}
```

### sampleSize

Gets `n` random elements at unique keys from `array` up to the size of `array`.

Shuffle the array using the [Fisher-Yates algorithm](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle). Use `Array.copyOfRange()` to get the first `n` elements.

```java
public static <T> T[] sampleSize(T[] input, int n) {
    T[] arr = Arrays.copyOf(input, input.length);
    int length = arr.length;
    int m = length;
    while (m > 0) {
        int i = (int) Math.floor(Math.random() * m--);
        T tmp = arr[i];
        arr[i] = arr[m];
        arr[m] = tmp;
    }
    return Arrays.copyOfRange(arr, 0, n > length ? length : n);
}
```

### shuffle

Randomizes the order of the values of an array, returning a new array.

Uses the [Fisher-Yates algorithm](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle) to reorder the elements of the array.

```java
public static <T> T[] shuffle(T[] input) {
    T[] arr = Arrays.copyOf(input, input.length);
    int length = arr.length;
    int m = length;
    while (m > 0) {
        int i = (int) Math.floor(Math.random() * m--);
        T tmp = arr[i];
        arr[i] = arr[m];
        arr[m] = tmp;
    }
    return arr;
}
```

### similarity

Returns an array of elements that appear in both arrays.

Use `Arrays.stream().filter()` to remove values that are not part of `second`, determined using `Arrays.stream().anyMatch()`.

```java
public static <T> T[] similarity(T[] first, T[] second) {
    return Arrays.stream(first)
            .filter(a -> Arrays.stream(second).anyMatch(b -> Objects.equals(a, b)))
            // Make a new array of first's runtime type, but empty content:
            .toArray(i -> (T[]) Arrays.copyOf(new Object[0], i, first.getClass()));
}
```

### sortedIndex

Returns the lowest index at which value should be inserted into array in order to maintain its sort order.

Check if the array is sorted in descending order (loosely). Use `IntStream.range().filter()` to find the appropriate index where the element should be inserted.

```java
public static <T extends Comparable<? super T>> int sortedIndex(T[] arr, T el) {
    boolean isDescending = arr[0].compareTo(arr[arr.length - 1]) > 0;
    return IntStream.range(0, arr.length)
            .filter(i -> isDescending ? el.compareTo(arr[i]) >= 0 : el.compareTo(arr[i]) <= 0)
            .findFirst()
            .orElse(arr.length);
}
```

### symmetricDifference

Returns the symmetric difference between two arrays.

Create a `Set` from each array, then use `Arrays.stream().filter()` on each of them to only keep values not contained in the other. Finally, concatenate both arrays and create a new array and return it.

```java
public static <T> T[] symmetricDifference(T[] first, T[] second) {
    Set<T> sA = new HashSet<>(Arrays.asList(first));
    Set<T> sB = new HashSet<>(Arrays.asList(second));

    return Stream.concat(
            Arrays.stream(first).filter(a -> !sB.contains(a)),
            Arrays.stream(second).filter(b -> !sA.contains(b))
    ).toArray(i -> (T[]) Arrays.copyOf(new Object[0], i, first.getClass()));
}
```

### tail

Returns all elements in an array except for the first one.

Return `Arrays.copyOfRange(1)` if the array's `length` is more than `1`, otherwise, return the whole array.

```java
public static <T> T[] tail(T[] arr) {
    return arr.length > 1
            ? Arrays.copyOfRange(arr, 1, arr.length)
            : arr;
}
```

### take

Returns an array with n elements removed from the beginning.

```java
public static <T> T[] take(T[] arr, int n) {
    return Arrays.copyOfRange(arr, 0, n);
}
```

### takeRight

Returns an array with n elements removed from the end.

Use `Arrays.copyOfRange()` to create a slice of the array with `n` elements taken from the end.

```java
public static <T> T[] takeRight(T[] arr, int n) {
    return Arrays.copyOfRange(arr, arr.length - n, arr.length);
}
```

### union

Returns every element that exists in any of the two arrays once.

Create a `Set` with all values of `a` and `b` and convert to an array.

```Java
public static <T> T[] union(T[] first, T[] second) {
    Set<T> set = new HashSet<>(Arrays.asList(first));
    set.addAll(Arrays.asList(second));
    return set.toArray((T[]) Arrays.copyOf(new Object[0], 0, first.getClass()));
}
```

### without

Filters out the elements of an array, that have one of the specified values.

Use `Arrays.strean().filter()` to create an array excluding(using `!Arrays.asList(elements).contains()`) all given values.

```java
public static <T> T[] without(T[] arr, T... elements) {
    List<T> excludeElements = Arrays.asList(elements);
    return Arrays.stream(arr)
            .filter(el -> !excludeElements.contains(el))
            .toArray(i -> (T[]) Arrays.copyOf(new Object[0], i, arr.getClass()));
}
```

### zip

Creates an array of elements, grouped based on the position in the original arrays.

```java
public static List<Object[]> zip(Object[]... arrays) {
    OptionalInt max = Arrays.stream(arrays).mapToInt(arr -> arr.length).max();
    return IntStream.range(0, max.getAsInt())
            .mapToObj(i -> Arrays.stream(arrays)
                    .map(arr -> i < arr.length ? arr[i] : null)
                    .toArray())
            .collect(Collectors.toList());
}
```

### zipObject

Given an array of valid property identifiers and an array of values, return an object associating the properties to the values.

```java
public static Map<String, Object> zipObject(String[] props, Object[] values) {
    return IntStream.range(0, props.length)
            .mapToObj(i -> new SimpleEntry<>(props[i], i < values.length ? values[i] : null))
            .collect(
                    HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
}
```

## Maths

### average

Returns the average of an of two or more numbers.

```java
public static double average(int[] arr) {
    return IntStream.of(arr)
            .average()
            .orElseThrow(() -> new IllegalArgumentException("Array is empty"));
}
```

### gcd

Calculates the greatest common denominator (gcd) of an array of numbers.

Use Arrays.stream().reduce() and the gcd formula (uses recursion) to calculate the greatest common denominator of an array of numbers.

```java
public static OptionalInt gcd(int[] numbers) {
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

### lcm

Calculates the lowest common multiple (lcm) of an array of numbers.

Use Arrays.stream().reduce() and the lcm formula (uses recursion) to calculate the lowest common multiple of an array of numbers.

```java
public static OptionalInt lcm(int[] numbers) {
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

### findNextPositivePowerOfTwo

Finds the next power of two greater than or equal to the value.

This method uses left ship operator to shift 1 by the value on the right side. The right side is calculated using the `Integer.numberOfLeadingZeros` method.

The `Integer.numberOfLeadingZeros` give the number of zeros leading the value. For example, calling `Integer.numberOfLeadingZeros(3)` would give value as 30. This is because 3 is represented in binary as `11`. As integer has 32 bits, so there are 30 bits with 0.  The right side of the left shift operator becomes `32-30 = 2`. Left shifting 1 by 2 i.e. `001 << 2` would be `100`. `100` in decimal is equal to `4`.

```java
public static int findNextPositivePowerOfTwo(int value) {
    return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
}
```

### isEven

Check if the number is even.

This method uses bitwise & operator. The `0b1` is the binary representation of 1. Since, Java 7 you can write binary literals by prefixing them with either `0b` or `0B`.  The & operator will return 0 when number is even. For example, `IsEven(4)` would result in `100` `&` `001`.  The result of `&` will be `000`.

```java
public static boolean isEven(final int value) {
    return (value & 0b1) == 0;
}
```

### isPowerOfTwo

Checks if a value is positive power of two.

To understand how it works let's assume we made a call `IsPowerOfTwo(4)`.

As value is greater than 0, so right side of the `&&` operator will be evaluated. 

The result of `(~value + 1)` is equal to value itself. `~100 + 001` => `011 + 001` => `100`. This is equal to value.

The result of `(value & value)` is value. `100` & `100` => `100`.

This will value the expression to true as value is equal to value.

```Java
public static boolean isPowerOfTwo(final int value) {
    return value > 0 && ((value & (~value + 1)) == value);
}
```

### generateRandomInt

Generate a random integer between `Integer.MIN_VALUE` and `Integer.MAX_VALUE`.

```java
public static int generateRandomInt() {
    return ThreadLocalRandom.current().nextInt();
}
```

## String

### anagrams

Generates all anagrams of a string (contains duplicates).

```java
public static List<String> anagrams(String input) {
    if (input.length() <= 2) {
        return input.length() == 2
                ? Arrays.asList(input, input.substring(1) + input.substring(0, 1))
                : Collections.singletonList(input);
    }
    return IntStream.range(0, input.length())
            .mapToObj(i -> new SimpleEntry<>(i, input.substring(i, i + 1)))
            .flatMap(entry ->
                    anagrams(input.substring(0, entry.getKey()) + input.substring(entry.getKey() + 1))
                            .stream()
                            .map(s -> entry.getValue() + s))
            .collect(Collectors.toList());
}
```

### byteSize

Returns the length of a string in bytes.

```java
public static int byteSize(String input) {
    return input.getBytes().length;
}
```

### capitalize

Capitalizes the first letter of a string.

```Java
public static String capitalize(String input, boolean lowerRest) {
    return input.substring(0, 1).toUpperCase() +
            (lowerRest
                    ? input.substring(1, input.length()).toLowerCase()
                    : input.substring(1, input.length()));
}
```

### capitalizeEveryWord

Capitalizes the first letter of every word in a string.

```java
public static String capitalizeEveryWord(final String input) {
    return Pattern.compile("\\b(?=\\w)").splitAsStream(input)
            .map(w -> capitalize(w, false))
            .collect(Collectors.joining());
}
```

### countVowels

Retuns `number` of vowels in provided string.

```java
public static int countVowels(String input) {
    return input.replaceAll("[^aeiouAEIOU]", "").length();
}
```

### escapeRegExp

Escapes a string to use in a regular expression.

```java
public static String escapeRegExp(String input) {
    return Pattern.quote(input);
}
```

### fromCamelCase

Converts a string from camelcase.

```java
public static String fromCamelCase(String input, String separator) {
    return input
            .replaceAll("([a-z\\d])([A-Z])", "$1" + separator + "$2")
            .toLowerCase();
}
```

### isAbsoluteUrl

Returns `true` if the given string is an absolute URL, `false` otherwise.

```java
public static boolean isAbsoluteUrl(String url) {
    return Pattern.compile("^[a-z][a-z0-9+.-]*:").matcher(url).find();
}
```

### isLowerCase

Checks if a string is lower case.

```java
public static boolean isLowerCase(String input) {
    return Objects.equals(input, input.toLowerCase());
}
```

### isUpperCase

Checks if a string is upper case.

```java
public static boolean isUpperCase(String input) {
    return Objects.equals(input, input.toUpperCase());
}
```

### isPalindrome

Checks if a string is palindrome.

```java
public static boolean isPalindrome(String input) {
    String s = input.toLowerCase().replaceAll("[\\W_]", "");
    return Objects.equals(
            s,
            new StringBuilder(s).reverse().toString()
    );
}
```

### isNumeric

Checks if a string is numeric.

```java
public static boolean isNumeric(final String input) {
    return IntStream.range(0, input.length())
            .allMatch(i -> Character.isDigit(input.charAt(i)));
}
```

### mask

Replaces all but the last `num` of characters with the specified mask character.

```Java
public static String mask(String input, int num, String mask) {
    int length = input.length();
    return num > 0
            ?
            input.substring(0, length - num).replaceAll(".", mask)
                    + input.substring(length - num)
            :
            input.substring(0, Math.negateExact(num))
                    + input.substring(Math.negateExact(num), length).replaceAll(".", mask);
}
```

### reverseString

Reverses a string.

```java
public static String reverseString(String input) {
    return new StringBuilder(input).reverse().toString();
}
```

### sortCharactersInString

Alphabetically sorts the characters in a string.

```java
public static String sortCharactersInString(String input) {
    return Arrays.stream(input.split("")).sorted().collect(Collectors.joining());
}
```

 ### splitLines

Splits a multiline string into an array of lines.

```java
public static String[] splitLines(String input) {
    return input.split("\\r?\\n");
}
```

### toCamelCase

Converts a string to camelcase.

```java
public static String toCamelCase(String input) {
    Matcher matcher = Pattern.compile("[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]|[0-9]+").matcher(input);
    List<String> matchedParts = new ArrayList<>();
    while (matcher.find()) {
        matchedParts.add(matcher.group(0));
    }
    String s = matchedParts.stream()
            .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase())
            .collect(Collectors.joining());
    return s.substring(0, 1).toLowerCase() + s.substring(1);
}

```

### toKebabCase

Converts a string to kebab case.

```java
public static String toKebabCase(String input) {
    Matcher matcher = Pattern.compile("[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]|[0-9]+").matcher(input);
    List<String> matchedParts = new ArrayList<>();
    while (matcher.find()) {
        matchedParts.add(matcher.group(0));
    }
    return matchedParts.stream()
            .map(String::toLowerCase)
            .collect(Collectors.joining("-"));
}
```

### match

```java
public static List<String> match(String input, String regex) {
    Matcher matcher = Pattern.compile(regex).matcher(input);
    List<String> matchedParts = new ArrayList<>();
    while (matcher.find()) {
        matchedParts.add(matcher.group(0));
    }
    return matchedParts;
}

```

### toSnakeCase

Converts a string to snake case.

```java
public static String toSnakeCase(String input) {
    Matcher matcher = Pattern.compile("[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]|[0-9]+").matcher(input);
    List<String> matchedParts = new ArrayList<>();
    while (matcher.find()) {
        matchedParts.add(matcher.group(0));
    }
    return matchedParts.stream()
            .map(String::toLowerCase)
            .collect(Collectors.joining("_"));
}
```

### truncateString

Truncates a string up to a specified length.

```java
public static String truncateString(String input, int num) {
    return input.length() > num
            ? input.substring(0, num > 3 ? num - 3 : num) + "..."
            : input;
}
```

### words

Converts a given string into an array of words.

```Java
public static String[] words(String input) {
    return Arrays.stream(input.split("[^a-zA-Z-]+"))
            .filter(s -> !s.isEmpty())
            .toArray(String[]::new);
}
```

### stringToIntegers

Converts a String of numbers separated by space to an array of ints.

```Java
public static int[] stringToIntegers(String numbers) {
        return Arrays.stream(numbers.split(" ")).mapToInt(Integer::parseInt).toArray();
}
```


## IO

### convertInputStreamToString

Converts InputStream to a String.

```java
public static String convertInputStreamToString(final InputStream in) throws IOException {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = in.read(buffer)) != -1) {
        result.write(buffer, 0, length);
    }
    return result.toString(StandardCharsets.UTF_8.name());
}
```

### readFileAsString

Reads content of a file to a String

```java
public String readFileAsString(Path path) throws IOException {
    return new String(Files.readAllBytes(path));
}
```

### getCurrentWorkingDirectoryPath

```java
public static String getCurrentWorkingDirectoryPath() {
    return FileSystems.getDefault().getPath("").toAbsolutePath().toString();
}
```

### tmpDirName

Returns the value of `java.io.tmpdir` system property. It appends separator if not present at the end.

```java
public static String tmpDirName() {
    String tmpDirName = System.getProperty("java.io.tmpdir");
    if (!tmpDirName.endsWith(File.separator)) {
        tmpDirName += File.separator;
    }

    return tmpDirName;
}
```

## Exception

### stackTraceAsString

Converts exception stack trace to a String.

```java
public static String stackTraceAsString(final Throwable throwable) {
    final StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw));
    return sw.toString();
}
```
## System

### osName

Gets the name of the operating system as a lower case String.

```java
public static String osName() {
    return System.getProperty("os.name").toLowerCase();
}
```

### isDebuggerEnabled

Checks if debugger is attached to the JVM.

```java
public static boolean isDebuggerAttached() {
    final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    return runtimeMXBean.getInputArguments()
            .stream()
            .anyMatch(arg -> arg.contains("-agentlib:jdwp"));

}
```

## Class

### getAllInterfaces

This methods returns all the interfaces implemented by the given class and its superclasses.

This method works by concatenating two streams. The first stream is recursively built by creating a stream with the interface and all the interfaces implemented by the the interface. The second stream does the same for the super classes. The result is the concatenation of the two streams after removing the duplicates.

```java
public static List<Class<?>> getAllInterfaces(Class<?> cls) {
    return Stream.concat(
            Arrays.stream(cls.getInterfaces()).flatMap(intf ->
                    Stream.concat(Stream.of(intf), getAllInterfaces(intf).stream())),
            cls.getSuperclass() == null ? Stream.empty() : getAllInterfaces(cls.getSuperclass()).stream()
    ).distinct().collect(Collectors.toList());
}
```

### isInnerClass

This method checks if the specified class is an inner class or static nested class

```Java
public static boolean isInnerClass(final Class<?> cls) {
    return cls != null && cls.getEnclosingClass() != null;
}
```

## Enum

### getEnumMap

Converts to enum to Map where key is the name and value is Enum itself.

```java
public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
            .collect(Collectors.toMap(Enum::name, Function.identity()));
}
```

## In News

1. Jetbrains Java Annotated Monthly covered little-java-functions in their [January newsletter](https://blog.jetbrains.com/idea/2018/01/java-annotated-monthly-january-2018/).
2. JavaChannel podcast covered little-java-functions in their [episode 11](https://javachannel.org/posts/javachannels-interesting-links-podcast-episode-11/).
3. little-java-functions was Github trending repository on 8th January 2018.

## Thanks

This project started as a Java fork of [30-seconds-of-code](https://github.com/Chalarangelo/30-seconds-of-code). Thanks to the project collaborators for the effort.
