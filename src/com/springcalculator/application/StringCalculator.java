package com.springcalculator.application;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class StringCalculator {
    private static final String START_OF_SQUARE_BRACKET = "\\[";
    private static final String EMPTY_STRING = "";
    public static final int START_INDEX = 6;
    private Pattern DelimitersValidator = Pattern.compile("//(\\[(\\D+)])+\n(.*)");
    private Matcher DelimitersMatcher;

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        } else {
            List<String> numbersList = getNumbers(numbers);
            return sum(numbersList);
        }
    }

    private int sum(List<String> numbersList) {
        return numbersList.stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    List<String> getNumbers(String text) {
        String delimiters = getDelimiters(text);
        return separateNumbers(text, delimiters);
    }

    private List<String> separateNumbers(String text, String delimiters) {
        if (isDelimiterValid(text)) {
            text = text.substring(START_INDEX);
        }
        return asList(text.split(delimiters));
    }

    private String getDelimiters(String string) {
        String delimiters;
        if (isDelimiterValid(string)) {
            String customDelimiter = DelimitersMatcher.group(1);
            String removeSquareBracket = removeBracket(customDelimiter);
            String giveDelimiter = Pattern.quote(removeSquareBracket);
            delimiters = giveDelimiter;
        } else {
            delimiters = ",|\n";
        }
        return delimiters;
    }

    private String removeBracket(String customDelimiter) {
        return customDelimiter
                        .replaceAll(START_OF_SQUARE_BRACKET, EMPTY_STRING)
                        .replaceAll("]", EMPTY_STRING);
    }

    private boolean isDelimiterValid(String string) {
        DelimitersMatcher = DelimitersValidator.matcher(string);
        return DelimitersMatcher.matches();
    }

}
