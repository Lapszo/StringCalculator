package com.springcalculator.test;

import com.springcalculator.application.StringCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void shouldReturnZeroOnEmptyString(){
        assertThat(stringCalculator.add("")).isEqualTo(0);
    }

    @Test
    public void shouldReturnNumberOnOneNumber(){
        assertThat(stringCalculator.add("1")).isEqualTo(1);
    }

    @Test
    public void shouldReturnSumOnTwoNumbers(){
        assertThat(stringCalculator.add("1,2")).isEqualTo(3);
    }

    @Test
    public void shouldReturnSumOnMultipleNumbers(){
        assertThat(stringCalculator.add("1,2,3")).isEqualTo(6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnLackOfOneArgument() {
        stringCalculator.add("1,,3");
    }
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnOneArgumentIsNotNumber() {
        stringCalculator.add("1,s,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnComaAsFirstCharacter() {
        stringCalculator.add(",1,2,3");
    }

    @Test
    public void shouldReturnSumOnDelimitersAreComasAndNewLines() {
        assertThat(stringCalculator.add("1,2\n3")).isEqualTo(6);
        assertThat(stringCalculator.add("1\n2\n3")).isEqualTo(6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnNewLineAsFirstCharacter() {
        stringCalculator.add("\n1,2\n3");
    }

    @Test
    public void shouldReturnSumOnSingleDelimiter() {
        assertThat(stringCalculator.add("//[;]\n1;2;3;4;5;6")).isEqualTo(21);
        assertThat(stringCalculator.add("//[*]\n1*2*3")).isEqualTo(6);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionOnNegativeNumbers() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stringCalculator.add("-1,2,-3"))
                .withMessage("Negatives not allowed: -1,-3");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stringCalculator.add("//[;]\n1;-2;3"))
                .withMessage("Negatives not allowed: -2");
    }

    @Test
    public void shouldReturnSumAndIgnoredNumbersBiggerThan1000() {
        assertThat(stringCalculator.add("//[;]\n1;1001;2")).isEqualTo(3);
        assertThat(stringCalculator.add("1,1001")).isEqualTo(1);
    }

    @Test
    public void shouldReturnSumOnAnyLengthOfDelimiter() {
        assertThat(stringCalculator.add("//[***]\n1***2***3")).isEqualTo(6);
        assertThat(stringCalculator.add("//[;;]\n1;;2;;3")).isEqualTo(6);
        assertThat(stringCalculator.add("//[*;]\n1*;2*;3")).isEqualTo(6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnInvalidPartOfDelimiter() {
        stringCalculator.add("//[**]\n1**2*;3");
    }

    @Test
    public void shouldReturnSumOnMultipleSingleCharacterDelimiters() {
        assertThat(stringCalculator.add("//[*][;]\n1*2;3")).isEqualTo(6);
    }


}
