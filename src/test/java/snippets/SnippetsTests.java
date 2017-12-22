package snippets;

import java.util.OptionalInt;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SnippetsTests {

    @Test
    public void gcd_of_array_containing_1_to_5_is_1() throws Exception {
        OptionalInt gcd = Snippets.arrayGcd(new int[]{1, 2, 3, 4, 5});
        assertThat(gcd).isNotEmpty();
        assertThat(gcd).hasValue(1);
    }

    @Test
    public void gcd_of_array_containing_4_8_and_12_is_4() throws Exception {
        OptionalInt gcd = Snippets.arrayGcd(new int[]{4, 8, 12});
        assertThat(gcd).isNotEmpty();
        assertThat(gcd).hasValue(4);
    }

    @Test
    public void lcm_of_array_containing_1_to_5_is_60() throws Exception {
        OptionalInt lcm = Snippets.arrayLcm(new int[]{1, 2, 3, 4, 5});
        assertThat(lcm).isNotEmpty();
        assertThat(lcm).hasValue(60);
    }

    @Test
    public void lcm_of_array_containing_4_8_and_12_is_24() throws Exception {
        OptionalInt lcm = Snippets.arrayLcm(new int[]{4, 8, 12});
        assertThat(lcm).isNotEmpty();
        assertThat(lcm).hasValue(24);
    }

    @Test
    public void max_of_array_containing_10_1_and_5_is_10() throws Exception {
        OptionalInt max = Snippets.arrayMax(new int[]{10, 1, 5});
        assertThat(max).hasValue(10);
    }

    @Test
    public void min_of_array_containing_10_1_and_5_is_10() throws Exception {
        OptionalInt min = Snippets.arrayMin(new int[]{10, 1, 5});
        assertThat(min).hasValue(1);
    }

    @Test
    public void chunk_breaks_input_array__with_odd_length() throws Exception {
        int[][] chunks = Snippets.chunk(new int[]{1, 2, 3, 4, 5}, 2);
        assertThat(chunks)
                .containsExactly(
                        new int[]{1, 2},
                        new int[]{3, 4},
                        new int[]{5}
                );
    }

    @Test
    public void chunk_breaks_input_array__with_event_length() throws Exception {
        int[][] chunks = Snippets.chunk(new int[]{1, 2, 3, 4, 5, 6}, 2);
        assertThat(chunks)
                .containsExactly(
                        new int[]{1, 2},
                        new int[]{3, 4},
                        new int[]{5, 6}
                );
    }
}
