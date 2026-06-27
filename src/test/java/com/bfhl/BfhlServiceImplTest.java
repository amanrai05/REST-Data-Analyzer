package com.bfhl;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link BfhlServiceImpl} covering all processing scenarios.
 *
 * <p>Test cases cover:</p>
 * <ul>
 *   <li>Example A from the specification</li>
 *   <li>Example B from the specification</li>
 *   <li>Example C from the specification (multi-char alphabet strings)</li>
 *   <li>Empty input data</li>
 *   <li>Numbers-only input</li>
 *   <li>Alphabets-only input</li>
 *   <li>Special characters only</li>
 *   <li>Odd number classification</li>
 *   <li>Sum correctness</li>
 *   <li>Alternating caps logic</li>
 *   <li>Response metadata (user_id, email, roll_number)</li>
 * </ul>
 */
@DisplayName("BfhlServiceImpl — Unit Tests")
class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
        // Inject @Value fields manually for unit testing
        ReflectionTestUtils.setField(service, "userId",     "john_doe_17091999");
        ReflectionTestUtils.setField(service, "email",      "john@xyz.com");
        ReflectionTestUtils.setField(service, "rollNumber", "ABCD123");
    }

    // ─── Example A ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example A: mixed array — numbers, letters, special char")
    void exampleA_fullFlow() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse res = service.processData(req);

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getOddNumbers()).containsExactly("1");
        assertThat(res.getEvenNumbers()).containsExactly("334", "4");
        assertThat(res.getAlphabets()).containsExactly("A", "R");
        assertThat(res.getSpecialCharacters()).containsExactly("$");
        assertThat(res.getSum()).isEqualTo("339");
        assertThat(res.getConcatString()).isEqualTo("Ra");
    }

    // ─── Example B ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example B: mixed array — multiple numbers, letters, special chars")
    void exampleB_fullFlow() {
        BfhlRequest req = new BfhlRequest(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse res = service.processData(req);

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getOddNumbers()).containsExactly("5");
        assertThat(res.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(res.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(res.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(res.getSum()).isEqualTo("103");
        assertThat(res.getConcatString()).isEqualTo("ByA");
    }

    // ─── Example C ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example C: multi-char alphabet strings only")
    void exampleC_multiCharAlphabets() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse res = service.processData(req);

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(res.getSpecialCharacters()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
        // Chars in order: A,A,B,C,D,D,O,E → reversed: E,O,D,D,C,B,A,A
        // Alternating caps: E,o,D,d,C,b,A,a → "EoDdCbAa"
        assertThat(res.getConcatString()).isEqualTo("EoDdCbAa");
    }

    // ─── Edge Cases ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Empty data list produces zeroed response")
    void emptyData_returnsEmptyLists() {
        BfhlRequest req = new BfhlRequest(Collections.emptyList());
        BfhlResponse res = service.processData(req);

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getAlphabets()).isEmpty();
        assertThat(res.getSpecialCharacters()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
        assertThat(res.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Numbers only — correct odd/even split and sum")
    void numbersOnly_correctSplit() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("3", "6", "9", "100"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).containsExactly("3", "9");
        assertThat(res.getEvenNumbers()).containsExactly("6", "100");
        assertThat(res.getAlphabets()).isEmpty();
        assertThat(res.getSpecialCharacters()).isEmpty();
        assertThat(res.getSum()).isEqualTo("118");
        assertThat(res.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Alphabets only — uppercase conversion and single concat_string char")
    void alphabetsOnly_uppercaseAndConcat() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("z", "m"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getAlphabets()).containsExactly("Z", "M");
        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getSpecialCharacters()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
        // chars: z, m → reversed: m, z → alternating: M(upper), z(lower) = "Mz"
        assertThat(res.getConcatString()).isEqualTo("Mz");
    }

    @Test
    @DisplayName("Special characters only — all placed in specialCharacters list")
    void specialCharsOnly() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("@", "#", "!"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getSpecialCharacters()).containsExactly("@", "#", "!");
        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getEvenNumbers()).isEmpty();
        assertThat(res.getAlphabets()).isEmpty();
        assertThat(res.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Single alphabet element produces correct concat_string")
    void singleAlphabet_concatIsUppercase() {
        BfhlRequest req = new BfhlRequest(List.of("x"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getAlphabets()).containsExactly("X");
        assertThat(res.getConcatString()).isEqualTo("X");
    }

    @Test
    @DisplayName("Zero is an even number")
    void zero_isEven() {
        BfhlRequest req = new BfhlRequest(List.of("0"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getEvenNumbers()).containsExactly("0");
        assertThat(res.getOddNumbers()).isEmpty();
        assertThat(res.getSum()).isEqualTo("0");
    }

    @Test
    @DisplayName("Response metadata fields are populated correctly")
    void responseMetadata_isCorrect() {
        BfhlRequest req = new BfhlRequest(List.of("1"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getUserId()).isEqualTo("john_doe_17091999");
        assertThat(res.getEmail()).isEqualTo("john@xyz.com");
        assertThat(res.getRollNumber()).isEqualTo("ABCD123");
        assertThat(res.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Large numbers are handled correctly")
    void largeNumbers_handled() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("999999", "1000000"));
        BfhlResponse res = service.processData(req);

        assertThat(res.getOddNumbers()).containsExactly("999999");
        assertThat(res.getEvenNumbers()).containsExactly("1000000");
        assertThat(res.getSum()).isEqualTo("1999999");
    }
}
