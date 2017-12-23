package snippets;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
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
}
