package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static char[] allowedLetters = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
    private static ArrayList<String> regionNumbers = new ArrayList<>();
    public static final String FORMAT_INFO = (char) 27 + "[37m";
    public static final String FORMAT_SUCCESS = (char) 27 + "[32m";
    public static final String FORMAT_FAIL = (char) 27 + "[31m";
    public static final String FORMAT_END = (char) 27 + "[0m";
    private static ArrayList<String> numberPlatesArrayList;

    public static void main(String[] args) {

        initCarPlateNumbersDb();
        for (; ; ) {
            System.out.println("Введите номер для поиска: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine().trim().toUpperCase();
                if (!input.isEmpty()) {
                    long start = System.currentTimeMillis();
                    int position = search(input);
                    long elapsed = System.currentTimeMillis() - start;
                    if (position < 0) {
                        System.out.println(FORMAT_FAIL + "\rНомера " + input + " нет в базе" + "(" + elapsed + "ms)" + FORMAT_END);
                    } else
                        System.out.println(FORMAT_SUCCESS + "\rНомер " + input + " найден" + "(" + elapsed + "ms)" + FORMAT_END);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initCarPlateNumbersDb() {
        initRegions();
        generateNumberPlates();
    }

    private static int search(String data) {

        for (int i = 0; i < numberPlatesArrayList.size(); i++) {
            if (data.equals(numberPlatesArrayList.get(i)))
                return i;
        }
        return -1;
    }

    public static void generateNumberPlates() {
        System.out.print(FORMAT_INFO + "Идет генерация номеров..." + FORMAT_END);
        String[] moscowRegionList = {"77", "97", "99", "177", "197"};
        String[] piterRegionList = {"78", "98"};
        numberPlatesArrayList = new ArrayList<>();
        for (String region : regionNumbers) {
            for (int i = 0; i < allowedLetters.length; i++) {
                for (int y = 0; y < 1000; y++) {
                    String number;
                    if (y < 10)
                        number = "00" + String.valueOf(y);
                    else if (y >= 10 && y < 100)
                        number = "0" + String.valueOf(y);
                    else
                        number = String.valueOf(y);
                    numberPlatesArrayList.add(allowedLetters[i] + number + allowedLetters[i] + allowedLetters[i] + region);
                }
            }
            for (int y = 0; y < 1000; y++) {
                String number;
                if (y < 10)
                    number = "00" + String.valueOf(y);
                else if (y >= 10 && y < 100)
                    number = "0" + String.valueOf(y);
                else
                    number = String.valueOf(y);
                numberPlatesArrayList.add("Е" + number + "КХ" + region);
            }

            if (Arrays.asList(moscowRegionList).contains(region)) {
                for (int y = 0; y < 1000; y++) {
                    String number;
                    if (y < 10)
                        number = "00" + String.valueOf(y);
                    else if (y >= 10 && y < 100)
                        number = "0" + String.valueOf(y);
                    else
                        number = String.valueOf(y);
                    numberPlatesArrayList.add("А" + number + "МР" + region);
                    numberPlatesArrayList.add("А" + number + "ОО" + region);
                    numberPlatesArrayList.add("А" + number + "МО" + region);
                    numberPlatesArrayList.add("В" + number + "ОО" + region);
                    numberPlatesArrayList.add("С" + number + "ОО" + region);
                    numberPlatesArrayList.add("М" + number + "ОО" + region);
                    numberPlatesArrayList.add("М" + number + "МР" + region);
                    numberPlatesArrayList.add("Р" + number + "МР" + region);
                }
            }

            if (Arrays.asList(piterRegionList).contains(region)) {
                for (int y = 0; y < 1000; y++) {
                    String number;
                    if (y < 10)
                        number = "00" + String.valueOf(y);
                    else if (y >= 10 && y < 100)
                        number = "0" + String.valueOf(y);
                    else
                        number = String.valueOf(y);
                    numberPlatesArrayList.add("О" + number + "КО" + region);
                    numberPlatesArrayList.add("О" + number + "АО" + region);
                    numberPlatesArrayList.add("О" + number + "ОС" + region);
                    numberPlatesArrayList.add("О" + number + "ОМ" + region);
                    numberPlatesArrayList.add("О" + number + "ТТ" + region);
                }
            }

        }
        System.out.println("\r" +
                FORMAT_INFO + "Номеров в базе: " + FORMAT_SUCCESS + numberPlatesArrayList.size() + FORMAT_END);
    }

    public static void initRegions() {
        for (int i = 1; i < 100; i++) {
            regionNumbers.add(i < 10 ? '0' + String.valueOf(i) : String.valueOf(i));
        }
        /**
         *
         102  Код региона Республика Башкортостан    (также 02)
         116  Код региона Республика Татарстан    (также 16)
         118  Код региона Удмуртская Республика    (также 18)
         121  Код региона Чувашская Республика    (также 21)
         125  Код региона Приморский край    (также 25)
         138  Код региона Иркутская область    (также 38)
         150  Код региона Московская область    (также 50, 90)
         152  Код региона Нижегородская область    (также 52)
         154  Код региона Новосибирская область    (также 54)
         159  Код региона Пермская область    (также 59)
         161  Код региона Ростовская область    (также 61)
         163  Код региона Самарская область    (также 63)
         164  Код региона Саратовская область    (также 64)
         173  Код региона Ульяновская область    (также 73)
         174   Код региона Челябинская область    (также 74)
         177  Код региона г. Москва    (также 77, 97, 99, 197, 199)
         197  Код региона г. Москва    (также 77, 97, 99, 177, 199)
         199  Код региона г. Москва    (также 77, 97, 99, 177, 197)
         */
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
    }
}
