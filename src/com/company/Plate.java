package com.company;

/**
 * Created by asus on 08.01.2017.
 */
public class Plate {
    private char firstLetter;
    private int digit;
    private char secondLetter;
    private char thirdLetter;
    private String region;

    public Plate(int number, String region, char firstLetter, char secondLetter, char thirdLetter) {
        this.firstLetter = firstLetter;
        this.digit = number;
        this.secondLetter = secondLetter;
        this.thirdLetter = thirdLetter;
        this.region = region;
    }

    public String getNumberPlate() {
        String number;
        if (this.digit < 100)
            number = String.format("%03d", digit);
        else
            number = String.valueOf(digit);
        return firstLetter + number + secondLetter + thirdLetter + region;
    }
}
