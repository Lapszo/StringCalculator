package com.springcalculator.test;

import com.springcalculator.application.StringCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void shouldReturnSumOnTwoNumber(){
        assertThat(stringCalculator.add("1,2")).isEqualTo(3);
    }

}
