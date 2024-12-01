package com.hidarisoft.adventofcode2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventofCode2024Application {

    public static void main(String[] args) {
        ClassLoader classLoader = AdventofCode2024Application.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("inputDay01.txt");

        if (inputStream == null) {
            System.out.println("Arquivo não encontrado em resources.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<Integer> rightList = new ArrayList<>();
            List<Integer> leftList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" {3}");
                rightList.add(Integer.parseInt(numbers[0]));
                leftList.add(Integer.parseInt(numbers[1]));
            }
            Integer somaFinal = 0;

            Collections.sort(rightList);
            Collections.sort(leftList);

            for (int i = 0; i < rightList.size(); i++) {
                somaFinal += Math.abs((rightList.get(i) - leftList.get(i)));
            }

            System.out.println(somaFinal);

            Map<Integer, Integer> rightMap = new HashMap<>();

            for (int num : rightList) {
                rightMap.put(num, rightMap.getOrDefault(num, 0) + 1);
            }

            int similarityScore = 0;

            for (int num : leftList) {
                int countRight = rightMap.getOrDefault(num, 0);
                similarityScore += num * countRight;
            }

            System.out.println(similarityScore);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
