package com.springcalculator.application;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class StringCalculator {
    private static final String START_OF_SQUARE_BRACKET = "\\[";
    private static final String EMPTY_STRING = "";
    private static final int START_INDEX = 3;
    private Pattern DelimitersValidator = Pattern.compile("//(\\[(\\D+)])+\n(.*)");
    private Matcher DelimitersMatcher;
    private int startIndex;

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        } else {
            List<String> numbersList = getNumbers(numbers);
            checkNegativeNumbers(numbersList);
            return sum(numbersList);
        }
    }

    private void checkNegativeNumbers(List<String> numbersList) {
        String negatives = numbersList.stream()
                .filter(s -> s.contains("-"))
                .collect(joining(","));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }
    }
    private int sum(List<String> numbersList) {
        return numbersList.stream()
                .filter(s -> Integer.parseInt(s) <= 1000)
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private List<String> getNumbers(String text) {
        String delimiters = getDelimiters(text);
        return separateNumbers(text, delimiters);
    }

    private List<String> separateNumbers(String text, String delimiters) {
        if (isDelimiterValid(text)) {
            text = text.substring(startIndex);
        }
        return asList(text.split(delimiters));
    }

    private String getDelimiters(String string) {
        String delimiters;
        if (isDelimiterValid(string)) {
            String customDelimiter = DelimitersMatcher.group(1);
            setStartIndex(customDelimiter);
            String removeSquareBracket = removeBracket(customDelimiter);
            delimiters = Pattern.quote(removeSquareBracket);
        } else {
            delimiters = ",|\n";
        }
        return delimiters;
    }

    private void setStartIndex(String customDelimiter) {
        startIndex = customDelimiter.length() + START_INDEX;
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
