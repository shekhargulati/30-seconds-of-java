package snippets;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Snippets {

    /**
     * Calculates the greatest common denominator (gcd) of an array of numbers
     *
     * @param numbers Array of numbers
     * @return gcd of array of numbers
     */
    public static OptionalInt arrayGcd(int[] numbers) {
        return Arrays.stream(numbers)
                .reduce((a, b) -> gcd(a, b));
    }

    /**
     * Calculates the lowest common multiple (lcm) of an array of numbers.
     *
     * @param numbers Array of numbers
     * @return lcm of array of numbers
     */
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

    /**
     * Returns the maximum value in an array.
     *
     * @param numbers Array of numbers
     * @return maximum value in an array
     */
    public static OptionalInt arrayMax(int[] numbers) {
        return Arrays.stream(numbers).max();
    }

    /**
     * Returns the minimum value in an array.
     *
     * @param numbers Array of numbers
     * @return minimum value in an array
     */
    public static OptionalInt arrayMin(int[] numbers) {
        return Arrays.stream(numbers).min();
    }

    /**
     * Chunks an array into smaller arrays of a specified size.
     *
     * @param numbers Input array of numbers
     * @param size    The chunk size
     * @return Smaller chunks
     */
    public static int[][] chunk(int[] numbers, int size) {
        return IntStream.iterate(0, i -> i + size)
                .limit((long) Math.ceil((double) numbers.length / size))
                .mapToObj(cur -> Arrays.copyOfRange(numbers, cur, cur + size > numbers.length ? numbers.length : cur + size))
                .toArray(int[][]::new);
    }

    /**
     * Counts the occurrences of a value in an array.
     *
     * @param numbers Array of numbers
     * @param value   the value for which we have to count occurrences
     * @return count of total number of occurrences of the value
     */
    public static long countOccurrences(int[] numbers, int value) {
        return Arrays.stream(numbers)
                .filter(number -> number == value)
                .count();
    }

    /**
     * Deep flattens an array.
     *
     * @param input A nested array containing integers
     * @return flattened array
     */
    public static int[] deepFlatten(Object[] input) {
        return Arrays.stream(input)
                .flatMapToInt(o -> {
                    if (o instanceof Object[]) {
                        return Arrays.stream(deepFlatten((Object[]) o));
                    }
                    return IntStream.of((Integer) o);
                }).toArray();
    }

    /**
     * Returns the difference between two arrays.
     *
     * @param first  the first array
     * @param second the second array
     * @return Elements in first that are not in second
     */
    public static int[] difference(int[] first, int[] second) {
        Set<Integer> set = Arrays.stream(second).boxed().collect(Collectors.toSet());
        return Arrays.stream(first)
                .filter(v -> !set.contains(v))
                .toArray();
    }

    /**
     * Filters out all values from an array for which the comparator function does not return true.
     *
     * @param first      the first array
     * @param second     the second array
     * @param comparator the comparator function
     * @return the resulting array
     */
    public static int[] differenceWith(int[] first, int[] second, IntBinaryOperator comparator) {
        return Arrays.stream(first)
                .filter(a ->
                        Arrays.stream(second)
                                .noneMatch(b -> comparator.applyAsInt(a, b) == 0)
                ).toArray();
    }

    /**
     * Returns all the distinct values of an array.
     *
     * @param elements ints
     * @return distinct values
     */
    public static int[] distinctValuesOfArray(int[] elements) {
        return Arrays.stream(elements).distinct().toArray();
    }

    /**
     * Removes elements in an array until the passed function returns true. Returns the remaining elements in the array.
     *
     * @param elements
     * @param condition
     * @return
     */
    public static int[] dropElements(int[] elements, IntPredicate condition) {
        while (elements.length > 0 && !condition.test(elements[0])) {
            elements = Arrays.copyOfRange(elements, 1, elements.length);
        }
        return elements;
    }

    /**
     * Returns a new array with n elements removed from the right
     *
     * @param elements
     * @param n        number of elements to remove
     * @return array after removing n elements
     */
    public static int[] dropRight(int[] elements, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n is less than 0");
        }
        return n < elements.length
                ? Arrays.copyOfRange(elements, 0, elements.length - n)
                : new int[0];
    }

    /**
     * Returns every nth element in an array.
     *
     * @param elements
     * @param nth
     * @return
     */
    public static int[] everyNth(int[] elements, int nth) {
        return IntStream.range(0, elements.length)
                .filter(i -> i % nth == nth - 1)
                .map(i -> elements[i])
                .toArray();
    }

    /**
     * Filters out the non-unique values in an array.
     * <p>
     * Use Array.stream().filter() for an array containing only the unique values.
     *
     * @param elements input array
     * @return unique values in the array
     */
    public static int[] filterNonUnique(int[] elements) {
        return Arrays.stream(elements)
                .filter(el -> indexOf(elements, el) == lastIndexOf(elements, el))
                .toArray();
    }

    /**
     * Find index of element in the array. Return -1 in case element does not exist.
     * <p>
     * Uses IntStream.range().filter() to find index of the element in the array.
     *
     * @param elements input array
     * @param el       element to find
     * @return index of the element
     */
    public static int indexOf(int[] elements, int el) {
        return IntStream.range(0, elements.length)
                .filter(idx -> elements[idx] == el)
                .findFirst()
                .orElse(-1);
    }

    /**
     * Find last index of element in the array. Return -1 in case element does not exist.
     * <p>
     * Uses IntStream.iterate().limit().filter() to find index of the element in the array.
     *
     * @param elements input array
     * @param el       element to find
     * @return index of the element
     */
    public static int lastIndexOf(int[] elements, int el) {
        return IntStream.iterate(elements.length - 1, i -> i - 1)
                .limit(elements.length)
                .filter(idx -> elements[idx] == el)
                .findFirst()
                .orElse(-1);
    }

    /**
     * Flattens an array.
     *
     * @param elements input array
     * @return flattened array
     */
    public static int[] flatten(Object[] elements) {
        return Arrays.stream(elements)
                .flatMapToInt(el -> el instanceof int[]
                        ? Arrays.stream((int[]) el)
                        : IntStream.of((int) el)
                ).toArray();
    }

    /**
     * Flattens an array up to the specified depth.
     *
     * @param elements input array
     * @param depth    depth to which to flatten array
     * @return flattened array
     */
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

    /**
     * Groups the elements of an array based on the given function.
     *
     * @param elements input array
     * @param func     function
     * @param <T>      type parameter
     * @return grouped elements in a Map
     */
    public static <T, R> Map<R, List<T>> groupBy(T[] elements, Function<T, R> func) {
        return Arrays.stream(elements).collect(Collectors.groupingBy(func));
    }

    /**
     * Returns all the elements of an array except the last one.
     * Use Arrays.copyOfRange() to return all except the last one
     *
     * @param elements
     * @param <T>
     * @return
     */
    public static <T> T[] initial(T[] elements) {
        return Arrays.copyOfRange(elements, 0, elements.length - 1);
    }

    /**
     * Initializes an array containing the numbers in the specified range where start and end are inclusive.
     *
     * @param end
     * @param start
     * @return
     */
    public static int[] initializeArrayWithRange(int end, int start) {
        return IntStream.rangeClosed(start, end).toArray();
    }

    public static int[] initializeArrayWithValues(int n, int value) {
        return IntStream.generate(() -> value).limit(n).toArray();
    }

    public static int[] intersection(int[] first, int[] second) {
        Set<Integer> set = Arrays.stream(second).boxed().collect(Collectors.toSet());
        return Arrays.stream(first)
                .filter(set::contains)
                .toArray();
    }

    public static <T extends Comparable<? super T>> int isSorted(T[] arr) {
        final int direction = arr[0].compareTo(arr[1]) < 0 ? 1 : -1;
        for (int i = 0; i < arr.length; i++) {
            T val = arr[i];
            if (i == arr.length - 1) return direction;
            else if ((val.compareTo(arr[i + 1]) * direction > 0)) return 0;
        }
        return direction;
    }

    public static <T> String join(T[] arr, String separator, String end) {
        return IntStream.range(0, arr.length)
                .mapToObj(i -> new SimpleEntry<>(i, arr[i]))
                .reduce("", (acc, val) -> val.getKey() == arr.length - 2
                        ? acc + val.getValue() + end
                        : val.getKey() == arr.length - 1 ? acc + val.getValue() : acc + val.getValue() + separator, (fst, snd) -> fst);
    }

    public static <T> String join(T[] arr, String separator) {
        return join(arr, separator, separator);
    }

    public static <T> String join(T[] arr) {
        return join(arr, ",");
    }

    public static <T> T nthElement(T[] arr, int n) {
        if (n > 0) {
            return Arrays.copyOfRange(arr, n, arr.length)[0];
        }
        return Arrays.copyOfRange(arr, arr.length + n, arr.length)[0];
    }

    public static <T, R> Map<T, R> pick(Map<T, R> obj, T[] arr) {
        return Arrays.stream(arr)
                .filter(obj::containsKey)
                .collect(Collectors.toMap(k -> k, obj::get));
    }

    public static Map<String, Object>[] reducedFilter(Map<String, Object>[] data, String[] keys, Predicate<Map<String, Object>> fn) {
        return Arrays.stream(data)
                .filter(fn)
                .map(el -> Arrays.stream(keys).filter(el::containsKey)
                        .collect(Collectors.toMap(Function.identity(), el::get)))
                .toArray((IntFunction<Map<String, Object>[]>) Map[]::new);
    }

    public static <T> T sample(T[] arr) {
        return arr[(int) Math.floor(Math.random() * arr.length)];
    }

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

}
