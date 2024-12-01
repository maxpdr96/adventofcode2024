package com.hidarisoft.adventofcode2024;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AdventofCode2024Application {

    public static void main(String[] args) {
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

    /**
     * Carrega os dados do arquivo de entrada em duas listas.
     *
     * @param fileName  Nome do arquivo de entrada.
     * @param rightList Lista para armazenar os números da primeira coluna.
     * @param leftList  Lista para armazenar os números da segunda coluna.
     * @return true se os dados foram carregados com sucesso, false caso contrário.
     */
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

    /**
     * Calcula a soma das diferenças absolutas entre as listas.
     *
     * @param rightList Lista direita.
     * @param leftList  Lista esquerda.
     * @return Soma das diferenças absolutas.
     */
    private static int calculateAbsoluteDifferenceSum(List<Integer> rightList, List<Integer> leftList) {
        Collections.sort(rightList);
        Collections.sort(leftList);

        int sum = 0;
        for (int i = 0; i < rightList.size(); i++) {
            sum += Math.abs(rightList.get(i) - leftList.get(i));
        }
        return sum;
    }

    /**
     * Calcula o Similarity Score entre duas listas.
     *
     * @param rightList Lista direita.
     * @param leftList  Lista esquerda.
     * @return Similarity Score.
     */
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
