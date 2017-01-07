package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    private static ArrayList<String> regionNumbers = new ArrayList<>();
    private static ArrayList<String> numberPlatesArrayList = new ArrayList<>();
    private static ArrayList<String> numberPlatesArrayListSorted;
    private static HashSet<String> numberPlatesHashSet = new HashSet<>();
    private static TreeSet<String> numberPlatesTreeSet = new TreeSet<>();

    private static final String FORMAT_INFO = (char) 27 + "[37m";
    private static final String FORMAT_SUCCESS = (char) 27 + "[32m";
    private static final String FORMAT_FAIL = (char) 27 + "[31m";
    private static final String FORMAT_END = (char) 27 + "[0m";

    private enum SearchType {
        UNSORTED, HASH_SET, TREE_SET, BINARY
    }

    public static void main(String[] args) {

        generateNumberPlates();

        for (; ; ) {
            System.out.println("Введите номер для поиска: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine().trim().toUpperCase();
                if (!input.isEmpty()) {
                    long start = System.nanoTime();
                    int position = search(input, numberPlatesArrayList);
                    handleResult(position, input, getElapsedTime(start), SearchType.UNSORTED);

                    start = System.nanoTime();
                    position = Collections.binarySearch(numberPlatesArrayListSorted, input);
                    handleResult(position, input, getElapsedTime(start), SearchType.BINARY);

                    start = System.nanoTime();
                    position = search(input, numberPlatesHashSet);
                    handleResult(position, input, getElapsedTime(start), SearchType.HASH_SET);

                    start = System.nanoTime();
                    position = search(input, numberPlatesTreeSet);
                    handleResult(position, input, getElapsedTime(start), SearchType.TREE_SET);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static float getElapsedTime(long start) {
        return (System.nanoTime() - start) / 1000000f;
    }

    private static void handleResult(int result, String input, float elapsed, SearchType type) {
        String searchTarget = "";
        NumberFormat formatter = new DecimalFormat("#0.000000");
        switch (type) {
            case UNSORTED:
                searchTarget = "Несортированный";
                break;
            case HASH_SET:
                searchTarget = "HashSet";
                break;
            case TREE_SET:
                searchTarget = "TreeSet";
                break;
            case BINARY:
                searchTarget = "Бинарный поиск";
                break;
        }
        if (result < 0) {
            System.out.println(FORMAT_FAIL + "\r" + searchTarget + ". Номера " + input + " нет в базе" + "(" + formatter.format(elapsed) + "ms)" + FORMAT_END);
        } else
            System.out.println(FORMAT_SUCCESS + "\r" + searchTarget + ". Номер " + input + " найден" + "(" + formatter.format(elapsed) + "ms)" + FORMAT_END);
    }

    private static int search(String data, Collection<String> set) {
        for (String item : set) {
            if (item.equals(data))
                return 1;
        }
        return -1;
    }


    private static void generateNumberPlates() {
        regionNumbers = getRegions();
        System.out.print(FORMAT_INFO + "Идет генерация номеров..." + FORMAT_END);
        char[] allowedLetters = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
        for (String region : regionNumbers) {
            for (char allowedLetter : allowedLetters) {
                fillCommonList(region, allowedLetter);
            }
            fillSpecialList(region);
        }
        numberPlatesArrayListSorted = new ArrayList<>(numberPlatesArrayList);
        Collections.shuffle(numberPlatesArrayList);
        Collections.sort(numberPlatesArrayListSorted);

        System.out.println("\r" + FORMAT_INFO + "Номеров в базе: " + FORMAT_SUCCESS + numberPlatesArrayList.size() + FORMAT_END);
        System.out.println(numberPlatesArrayList.get(1500000));
    }


    private static void fillSpecialList(String region) {
        String[] moscowRegionList = {"77", "97", "99", "177", "197"};
        String[] piterRegionList = {"78", "98"};
        for (int y = 0; y < 1000; y++) {
            addToCollections(generate(y, region, 'Е', 'К', 'Х'));
            if (Arrays.asList(moscowRegionList).contains(region)) {
                addToCollections(generate(y, region, 'А', 'М', 'Р'));
                addToCollections(generate(y, region, 'А', 'О', 'О'));
                addToCollections(generate(y, region, 'А', 'М', 'О'));
                addToCollections(generate(y, region, 'В', 'О', 'О'));
                addToCollections(generate(y, region, 'С', 'О', 'О'));
                addToCollections(generate(y, region, 'М', 'М', 'Р'));
                addToCollections(generate(y, region, 'М', 'М', 'Р'));
                addToCollections(generate(y, region, 'Р', 'М', 'Р'));
            }
            if (Arrays.asList(piterRegionList).contains(region)) {
                addToCollections(generate(y, region, 'О', 'К', 'О'));
                addToCollections(generate(y, region, 'О', 'А', 'О'));
                addToCollections(generate(y, region, 'О', 'О', 'С'));
                addToCollections(generate(y, region, 'О', 'О', 'М'));
                addToCollections(generate(y, region, 'О', 'Т', 'Т'));
            }

        }
    }

    private static void addToCollections(String data) {
        numberPlatesArrayList.add(data);
        numberPlatesHashSet.add(data);
        numberPlatesTreeSet.add(data);
    }

    private static void fillCommonList(String region, char allowedLetter) {
        for (int y = 0; y < 1000; y++) {
            addToCollections(generate(y, region, allowedLetter, allowedLetter, allowedLetter));
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
