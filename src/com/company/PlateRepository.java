package com.company;

import java.util.*;

/**
 * Created by asus on 08.01.2017.
 */
public class PlateRepository {
    private ArrayList<String> numberPlatesArrayList;
    private ArrayList<String> numberPlatesArrayListSorted;
    private HashSet<String> numberPlatesHashSet;
    private TreeSet<String> numberPlatesTreeSet;
    private ArrayList<String> regionNumbers;


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

    public boolean search(String data, SearchType type) {
        switch (type) {
            case UNSORTED:
                return numberPlatesArrayList.contains(data);
            case HASH_SET:
                return numberPlatesHashSet.contains(data);
            case TREE_SET:
                return numberPlatesTreeSet.contains(data);
            case BINARY:
                return Collections.binarySearch(numberPlatesArrayListSorted, data) >= 0;
        }
        throw new RuntimeException("The unknown SearchType");
    }

    public void add(Plate plate) {
        numberPlatesArrayList.add(plate.getNumberPlate());
    }

    public void printStatistics() {
        System.out.println("\r" + Format.INFO + "Номеров в базе: " + Format.SUCCESS + numberPlatesArrayList.size() + Format.END);
    }

    private void getGeneralSeriaPlates() {
        char[] allowedLetters = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
        for (String region : regionNumbers) {
            for (char allowedLetter : allowedLetters) {
                for (int y = 0; y < 1000; y++) {
                    add(new Plate(y, region, allowedLetter, allowedLetter, allowedLetter));
                }
            }
        }
    }

    private void getSpecialSeriaPlates() {
        String[] moscowRegionNumbersList = {"77", "97", "99", "177", "197"};
        String[] piterRegionNumbersList = {"78", "98"};
        for (String region : regionNumbers) {
            for (int y = 0; y < 1000; y++) {
                add(new Plate(y, region, 'Е', 'К', 'Х'));
                if (Arrays.asList(moscowRegionNumbersList).contains(region)) {
                    add(new Plate(y, region, 'А', 'М', 'Р'));
                    add(new Plate(y, region, 'А', 'О', 'О'));
                    add(new Plate(y, region, 'А', 'М', 'О'));
                    add(new Plate(y, region, 'В', 'О', 'О'));
                    add(new Plate(y, region, 'С', 'О', 'О'));
                    add(new Plate(y, region, 'М', 'М', 'Р'));
                    add(new Plate(y, region, 'Р', 'М', 'Р'));
                } else if (Arrays.asList(piterRegionNumbersList).contains(region)) {
                    add(new Plate(y, region, 'О', 'К', 'О'));
                    add(new Plate(y, region, 'О', 'А', 'О'));
                    add(new Plate(y, region, 'О', 'О', 'С'));
                    add(new Plate(y, region, 'О', 'О', 'М'));
                    add(new Plate(y, region, 'О', 'Т', 'Т'));
                }
            }
        }
    }

    private ArrayList<String> getRegions() {
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
