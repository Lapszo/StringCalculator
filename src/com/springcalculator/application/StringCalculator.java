package com.springcalculator.application;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class StringCalculator {

    private static final String DIVIDER_DELIMITER = "|";
    private static final String END_OF_SQUARE_BRACKET = "]";
    private static final String DIVIDER_DELIMITER_ESCAPED_CHAR = "\\|";
    private static final String START_OF_SQUARE_BRACKET_ESCAPED_CHAR = "\\[";
    private static final String EMPTY_STRING = "";
    private static final int START_INDEX = 3;
    private Pattern delimitersValidator = Pattern.compile("//(\\[(\\D+)])+\n(.*)");
    private Matcher delimitersMatcher;
    private int startIndex;
    private boolean validDelimiters;

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        } else {
            validDelimiters = areDelimitersValid(numbers);
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
        if (validDelimiters) {
            text = text.substring(startIndex);
        }
        return asList(text.split(delimiters));
    }

    private String getDelimiters(String text) {
        String delimiters;
        if (validDelimiters) {
            String customDelimiters = delimitersMatcher.group(1);
            startIndex = customDelimiters.length() + START_INDEX;
            delimiters  = getAllCharacters(customDelimiters);
        } else {
            delimiters = ",|\n";
        }
        return delimiters;
    }

    private String getAllCharacters(String customDelimiter) {
        String removeSquareBracket = removeBrackets(customDelimiter);
        return Pattern.compile(DIVIDER_DELIMITER_ESCAPED_CHAR)
                .splitAsStream(removeSquareBracket)
                .map(Pattern::quote)
                .collect(joining(DIVIDER_DELIMITER));
    }

    private String removeBrackets(String customDelimiters) {
        return customDelimiters.replaceFirst(START_OF_SQUARE_BRACKET_ESCAPED_CHAR, EMPTY_STRING)
                        .replaceAll(START_OF_SQUARE_BRACKET_ESCAPED_CHAR, DIVIDER_DELIMITER)
                        .replaceAll(END_OF_SQUARE_BRACKET , EMPTY_STRING);
    }

    private boolean areDelimitersValid(String text) {
        delimitersMatcher = delimitersValidator.matcher(text);
        return delimitersMatcher.matches();
    }

}
