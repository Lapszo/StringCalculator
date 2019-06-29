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
       return asList(text.split(","));
    }


}
