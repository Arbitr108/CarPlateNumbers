package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by asus on 08.01.2017.
 */
public class PlatesRepository {
    private static ArrayList<String> numberPlatesArrayList = new ArrayList<>();
    private static ArrayList<String> numberPlatesArrayListSorted;
    private static HashSet<String> numberPlatesHashSet = new HashSet<>();
    private static TreeSet<String> numberPlatesTreeSet = new TreeSet<>();

    public PlatesRepository() {

    }

    private static void generateNumberPlatesCollections() {
        ArrayList<String> regionNumbers = getRegions();
        char[] allowedLetters = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};

        for (String region : regionNumbers) {
            for (char allowedLetter : allowedLetters) {
                fillCommonList(region, allowedLetter);
            }
            fillRegionUniqueSpecialNumbersList(region);
        }

        numberPlatesArrayListSorted = new ArrayList<>(numberPlatesArrayList);
        numberPlatesHashSet.addAll(numberPlatesArrayList);
        numberPlatesTreeSet.addAll(numberPlatesArrayList);

    }

    private static void fillRegionUniqueSpecialNumbersList(String region) {
        String[] moscowRegionNumbersList = {"77", "97", "99", "177", "197"};
        String[] piterRegionNumbersList = {"78", "98"};
        for (int y = 0; y < 1000; y++) {
            addToCollection(generate(y, region, 'Е', 'К', 'Х'));
            if (Arrays.asList(moscowRegionNumbersList).contains(region)) {
                addToCollection(generate(y, region, 'А', 'М', 'Р'));
                addToCollection(generate(y, region, 'А', 'О', 'О'));
                addToCollection(generate(y, region, 'А', 'М', 'О'));
                addToCollection(generate(y, region, 'В', 'О', 'О'));
                addToCollection(generate(y, region, 'С', 'О', 'О'));
                addToCollection(generate(y, region, 'М', 'М', 'Р'));
                addToCollection(generate(y, region, 'М', 'М', 'Р'));
                addToCollection(generate(y, region, 'Р', 'М', 'Р'));
            }
            if (Arrays.asList(piterRegionNumbersList).contains(region)) {
                addToCollection(generate(y, region, 'О', 'К', 'О'));
                addToCollection(generate(y, region, 'О', 'А', 'О'));
                addToCollection(generate(y, region, 'О', 'О', 'С'));
                addToCollection(generate(y, region, 'О', 'О', 'М'));
                addToCollection(generate(y, region, 'О', 'Т', 'Т'));
            }
        }
    }

    private static String generate(int order, String region, char firstLetter, char secondLetter, char thirdLetter) {
        String number;
        if (order < 10)
            number = "00" + String.valueOf(order);
        else if (order >= 10 && order < 100)
            number = "0" + String.valueOf(order);
        else
            number = String.valueOf(order);
        return firstLetter + number + secondLetter + thirdLetter + region;
    }

    private static void addToCollection(String data) {
        numberPlatesArrayList.add(data);
    }

    private static void fillCommonList(String region, char allowedLetter) {
        for (int y = 0; y < 1000; y++) {
            addToCollection(generate(y, region, allowedLetter, allowedLetter, allowedLetter));
        }
    }


    private static ArrayList<String> getRegions() {
        ArrayList<String> regionNumbers = new ArrayList<>();
        //Стандартные коды
        for (int i = 1; i < 100; i++) {
            regionNumbers.add(i < 10 ? '0' + String.valueOf(i) : String.valueOf(i));
        }
        //Доп. коды
        regionNumbers.add("102");
        regionNumbers.add("116");
        regionNumbers.add("118");
        regionNumbers.add("121");
        regionNumbers.add("125");
        regionNumbers.add("138");
        regionNumbers.add("150");
        regionNumbers.add("152");
        regionNumbers.add("154");
        regionNumbers.add("159");
        regionNumbers.add("161");
        regionNumbers.add("163");
        regionNumbers.add("164");
        regionNumbers.add("173");
        regionNumbers.add("174");
        regionNumbers.add("177");
        regionNumbers.add("197");
        regionNumbers.add("199");
        return regionNumbers;
    }
}
