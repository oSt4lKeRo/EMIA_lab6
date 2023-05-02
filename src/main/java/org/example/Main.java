package org.example;

import static org.example.Funcional.*;
import static org.example.Helper.*;

public class Main {
	public static void main(String[] args) {

		int[][] taskMas = generateMas(1);

		//////////////////////Параметры
		int countPros = 4;
		int countGen = 4;
		int repeat = 2;


		int[][] genMas = generateGenMas(taskMas.length, countGen,0);

		printMas(taskMas);
		System.out.println();
		printMas(genMas);

		int[] generation = new int[genMas.length];
		for (int i = 0; i < genMas.length; i++) {
			generation[i] = countingBestElem(taskMas, genMas[i], countPros);
		}
		printMas(generation);

		int bestElem = searchMin(generation);
		System.out.println("bestElem: " + bestElem + "\n");


		int count = 1;
		int iteratorCount = 1;

		while (count != 5){

			System.out.println("Iterator: " + iteratorCount + " Count: " + count + " new generation Mas: ");

			int[] newGenerationMas = searchNewGeneration(taskMas, genMas, generation, countPros, repeat);
			printMas(newGenerationMas);

			System.out.println("\b");

			int newBestElem = searchMin(newGenerationMas);

			if (newBestElem < bestElem) {
				bestElem = newBestElem;
				System.out.println("-----\nnew best elem: " + bestElem + "\n-----\n");
				count = 1;
			} else {
				count++;
			}

			generation = newGenerationMas;
			iteratorCount++;

			System.out.println("\n\n\n");
		}

		System.out.println(bestElem + " " + (iteratorCount-1));

	}
}