package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main {

    public static void main(String[] args) {

        PlateRepository platesRepository = new PlateRepository();
        platesRepository.printStatistics();

        for (; ; ) {
            System.out.println("Введите номер для поиска: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine().trim().toUpperCase();
                if (!input.isEmpty()) {
                    long start = System.nanoTime();
                    int position = platesRepository.search(input, SearchType.UNSORTED);
                    handleResult(position, input, getElapsedTime(start), SearchType.UNSORTED);

                    start = System.nanoTime();
                    position = platesRepository.search(input, SearchType.BINARY);
                    handleResult(position, input, getElapsedTime(start), SearchType.BINARY);

                    start = System.nanoTime();
                    position = platesRepository.search(input, SearchType.HASH_SET);
                    handleResult(position, input, getElapsedTime(start), SearchType.HASH_SET);

                    start = System.nanoTime();
                    position = platesRepository.search(input, SearchType.TREE_SET);
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
            System.out.println(Format.FAIL + "\r" + searchTarget + ". Номера " + input + " нет в базе" + "(" + formatter.format(elapsed) + "ms)" + Format.END);
        } else
            System.out.println(Format.SUCCESS + "\r" + searchTarget + ". Номер " + input + " найден" + "(" + formatter.format(elapsed) + "ms)" + Format.END);
    }
}
