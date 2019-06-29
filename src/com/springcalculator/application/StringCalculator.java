package com.springcalculator.application;



public class StringCalculator {

    public int add(String numbers) {
        if(numbers.isEmpty())
        {
            return 0;
        } else if(numbers.contains(",")) {
            String[] separated = numbers.split(",");
            return Integer.parseInt(separated[0]) + Integer.parseInt(separated[1]);
        }
        else {
            return Integer.parseInt(numbers);
        }
    }

}
