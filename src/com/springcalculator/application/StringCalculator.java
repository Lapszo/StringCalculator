package com.springcalculator.application;

import java.util.List;
import static java.util.Arrays.asList;

public class StringCalculator {

    public int add(String numbers) {
        if(numbers.isEmpty()) {
            return 0;
        } else
        {
            List<String> numbersList = getNumbers(numbers);
            return sum(numbersList);
        }
    }

    private int sum(List<String> numbersList) {
        return numbersList.stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private List<String> getNumbers(String text) {
        String delimiters = getDelimiters(text);
        return separateNumbers(text, delimiters);
    }
    private String getDelimiters(String text) {
        String delimiters = ",|\n";
        return delimiters;
    }

    private List<String> separateNumbers(String text, String delimiters) {
        return asList(text.split(delimiters));
    }
}
