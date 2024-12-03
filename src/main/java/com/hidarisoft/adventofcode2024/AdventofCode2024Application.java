package com.hidarisoft.adventofcode2024;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdventofCode2024Application {

    public static void main(String[] args) {
        String day = "02";

        if (day.equals("01")) {
            DayOne();
        } else if (day.equals("02")) {
            DayTwo();
        } else if (day.equals("03")) {
            DayThree();
        }
    }

    private static void DayThree() {
    }

    private static void DayOne() {
        List<Integer> rightList = new ArrayList<>();
        List<Integer> leftList = new ArrayList<>();

        // Carregar os dados do arquivo
        if (!loadInputData("inputDay01.txt", rightList, leftList)) {
            System.out.println("Arquivo não encontrado ou erro ao carregar os dados.");
            return;
        }

        // Calcular soma das diferenças absolutas
        int somaFinal = calculateAbsoluteDifferenceSum(rightList, leftList);
        System.out.println("Soma das diferenças absolutas: " + somaFinal);

        // Calcular Similarity Score
        int similarityScore = calculateSimilarityScore(rightList, leftList);
        System.out.println("Similarity Score: " + similarityScore);
    }

    private static void DayTwo() {
        List<List<Integer>> reports = readReports("inputDay02.txt");
        int safeCount = 0;

        for (List<Integer> report : reports) {
            if (isSafe(report) || canBeSafeWithOneRemoval(report)) {
                safeCount++;
            }
        }

        System.out.println("Número de relatórios seguros: " + safeCount);
    }

    private static boolean canBeSafeWithOneRemoval(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            List<Integer> modifiedReport = new ArrayList<>(report);
            modifiedReport.remove(i);
            if (isSafe(modifiedReport)) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Integer>> readReports(String filePath) {
        List<List<Integer>> reports = new ArrayList<>();
        try (InputStream inputStream = AdventofCode2024Application.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> report = new ArrayList<>();
                String[] levels = line.trim().split("\\s+");
                for (String level : levels) {
                    report.add(Integer.parseInt(level));
                }
                reports.add(report);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return reports;
    }

    private static boolean isSafe(List<Integer> report) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 1; i < report.size(); i++) {
            int diff = report.get(i) - report.get(i - 1);

            // Verifica se a diferença está fora do intervalo permitido
            if (diff > 3 || diff < -3 || diff == 0) {
                return false;
            }

            if (diff > 0) {
                isIncreasing = false;
            }

            if (diff < 0) {
                isDecreasing = false;
            }
        }

        return (Boolean.TRUE.equals(isIncreasing) && Boolean.FALSE.equals(isDecreasing))
                || (Boolean.FALSE.equals(isIncreasing) && Boolean.TRUE.equals(isDecreasing));
    }


    private static boolean loadInputData(String fileName, List<Integer> rightList, List<Integer> leftList) {
        try (InputStream inputStream = AdventofCode2024Application.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" {3}");
                rightList.add(Integer.parseInt(numbers[0]));
                leftList.add(Integer.parseInt(numbers[1]));
            }
            return true;

        } catch (NullPointerException | IOException e) {
            return false;
        }
    }


    private static int calculateAbsoluteDifferenceSum(List<Integer> rightList, List<Integer> leftList) {
        Collections.sort(rightList);
        Collections.sort(leftList);

        int sum = 0;
        for (int i = 0; i < rightList.size(); i++) {
            sum += Math.abs(rightList.get(i) - leftList.get(i));
        }
        return sum;
    }


    private static int calculateSimilarityScore(List<Integer> rightList, List<Integer> leftList) {
        Map<Integer, Integer> rightFrequencyMap = new HashMap<>();
        for (int num : rightList) {
            rightFrequencyMap.merge(num, 1, Integer::sum);
        }

        int similarityScore = 0;
        for (int num : leftList) {
            similarityScore += num * rightFrequencyMap.getOrDefault(num, 0);
        }
        return similarityScore;
    }
}
