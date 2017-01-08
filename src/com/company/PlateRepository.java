package com.company;

import java.util.*;

/**
 * Created by asus on 08.01.2017.
 */
public class PlateRepository {
    private static ArrayList<String> numberPlatesArrayList;
    private static ArrayList<String> numberPlatesArrayListSorted;
    private static HashSet<String> numberPlatesHashSet;
    private static TreeSet<String> numberPlatesTreeSet;
    private static ArrayList<String> regionNumbers;


    public PlateRepository() {
        System.out.print(Format.INFO + "Идет генерация номеров..." + Format.END);
        numberPlatesArrayList = new ArrayList<>();
        regionNumbers = getRegions();
        getGeneralSeriaPlates();
        getSpecialSeriaPlates();

        System.out.print(Format.INFO + "\rПодготовка данных..." + Format.END);
        numberPlatesArrayListSorted = new ArrayList<>(numberPlatesArrayList);
        numberPlatesHashSet = new HashSet<>(numberPlatesArrayList);
        numberPlatesTreeSet = new TreeSet<>(numberPlatesArrayList);
        Collections.shuffle(numberPlatesArrayList);
        Collections.sort(numberPlatesArrayListSorted);
    }

    public static int search(String data, SearchType type) {
        switch (type) {
            case UNSORTED:
                return search(data, numberPlatesArrayList);
            case HASH_SET:
                return search(data, numberPlatesHashSet);
            case TREE_SET:
                return search(data, numberPlatesTreeSet);
            case BINARY:
                return Collections.binarySearch(numberPlatesArrayListSorted, data);
        }
        throw new RuntimeException("The unknown SearchType");
    }

    private static int search(String data, Collection<String> set) {
        for (String item : set) {
            if (item.equals(data))
                return 1;
        }
        return -1;
    }

    private static void getGeneralSeriaPlates() {
        char[] allowedLetters = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
        for (String region : regionNumbers) {
            for (char allowedLetter : allowedLetters) {
                for (int y = 0; y < 1000; y++) {
                    addToCollection(PlateGenerator.generate(y, region, allowedLetter, allowedLetter, allowedLetter));
                }
            }
        }
    }

    private static void getSpecialSeriaPlates() {
        String[] moscowRegionNumbersList = {"77", "97", "99", "177", "197"};
        String[] piterRegionNumbersList = {"78", "98"};
        for (String region : regionNumbers) {
            for (int y = 0; y < 1000; y++) {
                addToCollection(PlateGenerator.generate(y, region, 'Е', 'К', 'Х'));
                if (Arrays.asList(moscowRegionNumbersList).contains(region)) {
                    addToCollection(PlateGenerator.generate(y, region, 'А', 'М', 'Р'));
                    addToCollection(PlateGenerator.generate(y, region, 'А', 'О', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'А', 'М', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'В', 'О', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'С', 'О', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'М', 'М', 'Р'));
                    addToCollection(PlateGenerator.generate(y, region, 'Р', 'М', 'Р'));
                } else if (Arrays.asList(piterRegionNumbersList).contains(region)) {
                    addToCollection(PlateGenerator.generate(y, region, 'О', 'К', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'О', 'А', 'О'));
                    addToCollection(PlateGenerator.generate(y, region, 'О', 'О', 'С'));
                    addToCollection(PlateGenerator.generate(y, region, 'О', 'О', 'М'));
                    addToCollection(PlateGenerator.generate(y, region, 'О', 'Т', 'Т'));
                }
            }
        }
    }

    private static void addToCollection(String data) {

        numberPlatesArrayList.add(data);
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
