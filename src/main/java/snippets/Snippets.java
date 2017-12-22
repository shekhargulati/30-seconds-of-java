package snippets;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
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

}
