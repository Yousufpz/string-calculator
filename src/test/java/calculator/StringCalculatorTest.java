package test.java.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import main.java.calculator.StringCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {
    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    @Test
    void shouldReturnZeroForEmptyString() {
        assertEquals(0, calculator.add(""));
    }

    @ParameterizedTest
    @CsvSource({"1,1", "5,5", "10,10"})
    void shouldReturnNumberForSingleNumber(String input, int expected) {
        assertEquals(expected, calculator.add(input));
    }

    @Test
    void shouldReturnSumForMultipleNumbers() {
        assertEquals(15, calculator.add("1,2,3,4,5"));
    }

    @Test
    void shouldHandleNewLinesBetweenNumbers() {
        assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    void shouldSupportCustomDelimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test
    void shouldThrowForNegativeNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            calculator.add("1,-2,3,-4")
        );
        String expectedMessage = "Negatives not allowed: [-2, -4]";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldIgnoreNumbersBiggerThan1000() {
        assertEquals(2, calculator.add("2,1001"));
    }

    @Test
    void shouldHandleDelimitersOfAnyLength() {
        assertEquals(6, calculator.add("//[***]\n1***2***3"));
    }

    @Test
    void shouldHandleMultipleDelimiters() {
        assertEquals(6, calculator.add("//[*][%]\n1*2%3"));
    }

    @Test
    void shouldHandleMultipleDelimitersWithLengthLongerThanOneChar() {
        assertEquals(6, calculator.add("//[**][%%]\n1**2%%3"));
    }

    @Test
    void shouldHandleMixOfCommasAndNewLines() {
        assertEquals(10, calculator.add("1\n2,3\n4"));
    }

    @Test
    void shouldHandleCustomDelimiterWithMultipleNumbers() {
        assertEquals(10, calculator.add("//;\n1;2;3;4"));
    }


    @Test
    void shouldHandleMaxIntegerValue() {
        assertEquals(0, calculator.add(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    void shouldHandleLargeNumberOfInputs() {
        String largeInput = String.join(",", new String[1000]);
        assertEquals(0, calculator.add(largeInput));
    }

    @Test
    void shouldHandleAllZeros() {
        assertEquals(0, calculator.add("0,0,0,0,0"));
    }

    @Test
    void shouldHandleLeadingAndTrailingSpaces() {
        assertEquals(6, calculator.add(" 1,2,3 "));
    }

    @Test
    void shouldHandleMultipleConsecutiveDelimiters() {
        assertEquals(6, calculator.add("//[*]\n1**2***3"));
    }
}