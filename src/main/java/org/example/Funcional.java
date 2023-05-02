package org.example;

import java.util.ArrayList;
import java.util.Random;

import static org.example.Helper.*;

public class Funcional {

	static class Test{
		boolean min;
		boolean swipe;
		boolean end;
		int beforeElem;
		int beforeIndex;
		int afterElem;
		int afterIndex;
		Test(){}
	}




	public static int searchMax(int[] taskSum){
		int max = taskSum[0];
		for(int i : taskSum){
			if(max < i){
				max = i;
			}
		}
		return max;
	}

	public static int searchMax(int[] taskSum, Test test, int param){
		int max = taskSum[0];
		for(int i = 0; i < taskSum.length; i++){
			if(max < taskSum[i]){
				max = taskSum[i];
				if(param == 1) {
					test.beforeElem = max;
					test.beforeIndex = i;
				} else if(param == 2){
					test.afterElem = max;
					test.afterIndex = i;
				}
			}
		}
		return max;
	}

	public static int searchMin(int[] taskSum){
		int min = taskSum[0];
		for(int i : taskSum){
			if(min > i){
				min = i;
			}
		}
		return min;
	}

	public static int searchMaxIndex(int[] genMas){
		int max = genMas[0];
		int indexMax = 0;
		for(int i = 0; i < genMas.length; i++){
			if(genMas[i] > max){
				max = genMas[i];
				indexMax = i;
			}
		}
		return indexMax;
	}


	public static int[] crossover(int[] mas1, int[] mas2, int delimiter){
		int[] newGen = new int[mas1.length];
		for(int i = 0; i < delimiter; i++){
			newGen[i] = mas1[i];
		}
		for(int i = delimiter; i < mas1.length; i++){
			newGen[i] = mas2[i];
		}
		System.out.print("Crossover ");
		return newGen;
	}

	public static int[] countingTaskSum(ArrayList<Integer>[] fenMatrix){
		int[] taskSum = new int[fenMatrix.length];
		for(int i = 0; i < fenMatrix.length; i++){
			for(int j : fenMatrix[i]){
				taskSum[i] += j;
			}
		}
		return taskSum;
	}


	public static int countingBestElem(int[][] taskMas, int[] genMas, int countProc){

		ArrayList<Integer>[] fenMatrix = genFenMatrix(countProc);

		for(int i = 0; i < genMas.length; i++){
			for(int j = 0; j < countProc; j++){
				int borders = (255/countProc)*(j+1);
				if(genMas[i] <= borders){
					fenMatrix[j].add(taskMas[i][j]);
					break;
				}
				if(j == countProc-1) {
					fenMatrix[j].add(taskMas[i][j]);
				}
			}
		}

		int[] taskSum = countingTaskSum(fenMatrix);
		printFenMatrix(fenMatrix, taskSum);
		return searchMax(taskSum);
	}

	public static int countingBestElem(int[][] taskMas, int[] genMas, int n, Test test, int param){

		ArrayList<Integer>[] fenMatrix = genFenMatrix(n);

		for(int i = 0; i < genMas.length; i++){
			for(int j = 0; j < n; j++){
				int borders = (255/n)*(j+1);
				if(genMas[i] <= borders ){
//					System.out.println(genMas[i] + " <= " + borders + "  | " + j);
					fenMatrix[j].add(taskMas[i][j]);
					break;
				}
				if(j == n-1) {
					fenMatrix[j].add(taskMas[i][j]);
				}
			}
		}

		int[] taskSum = countingTaskSum(fenMatrix);
		printFenMatrix(fenMatrix, taskSum);

		return searchMax(taskSum, test, param);
	}



	public static void mutation(int[] gen, Test test, int iteratorCount){

			Random random = new Random();
			int indexElem = random.nextInt(gen.length);


			indexElem = searchMaxIndex(gen);
			int beforeElem = gen[indexElem];
			int beforeIndex = supportSearchGroup(beforeElem, 4);
			if (beforeIndex == supportSearchGroup(test.beforeElem, 4)) {
				test.end = true;
			}

			for(int l = 0; l < iteratorCount; l++) {

				System.out.print(gen[indexElem] + " => ");

			String str = Integer.toBinaryString(gen[indexElem]);
			StringBuffer stringBuffer = new StringBuffer(str);

			for (int i = stringBuffer.length(); i < 8; i++) {
				stringBuffer.insert(0, 0);
			}

			str = String.valueOf(stringBuffer);
			char[] chars = str.toCharArray();
			System.out.print(str);

			int indexBin = random.nextInt(chars.length);
			System.out.print("[" + indexBin + "] => ");

			if (chars[indexBin] == '0') {
				chars[indexBin] = '1';
			} else {
				chars[indexBin] = '0';
			}

			str = "";
			for (char i : chars) {
				str += i;
			}
			System.out.print(str + " = ");

			int elem = Integer.parseInt(str, 2);
			System.out.println(elem);


			if (beforeElem > elem && beforeIndex != supportSearchGroup(elem, 4)) {
				test.min = true;
			}


			gen[indexElem] = elem;
			printMas(gen);
		}
	}

	public static int[] searchNewGeneration(int[][] taskMas, int[][] genMas, int[] generation, int countPros, int countIterator) {

		int[] newGenerations = new int[genMas.length];
		Random random = new Random();
		for (int i = 0; i < genMas.length; i++) {

			int delimiter = random.nextInt(taskMas.length - 1);
			int indexSecondGenMas = 0;
			int indexSecondGenMas2 = 0;
			while (true) {
				indexSecondGenMas = random.nextInt(genMas.length);
				if (indexSecondGenMas != i) {
					break;
				}
			}
			while (true) {
				indexSecondGenMas2 = random.nextInt(genMas.length);
				if (indexSecondGenMas2 != i && indexSecondGenMas != indexSecondGenMas2) {
					break;
				}
			}

			int oneGenBestElem = countingBestElem(taskMas, genMas[indexSecondGenMas], countPros);
			int twoGenBestElem = countingBestElem(taskMas, genMas[indexSecondGenMas2], countPros);
			if(oneGenBestElem >= twoGenBestElem){
				indexSecondGenMas = oneGenBestElem;
			} else {
				indexSecondGenMas = twoGenBestElem;
			}

			int[] newGen1 = genMas[i];
			int[] newGen2 = genMas[indexSecondGenMas];



			int count = 0;
			if (random.nextInt(100) <= 70) {
				for(int l = 0; l < countIterator; l++) {
					newGen1 = crossover(genMas[i], genMas[indexSecondGenMas], delimiter);
				}
				count++;
			}
			if (random.nextInt(100) <= 70) {
				for(int l = 0; l < countIterator; l++) {
					newGen2 = crossover(genMas[indexSecondGenMas], genMas[i], delimiter);
				}
				count++;
			}
//			if (count == 0) {
//
//				printMas(genMas[i]);
//
//				mutation(newGen1);
//
//				printMas(newGen1);
//				System.out.println("-----");
//
//				int[] resultMas = new int[2];
//
//				resultMas[0] = generation[i];
//				resultMas[1] = countingBestElem(taskMas, newGen1, countPros);
//
//				printMas(resultMas);
//				System.out.println("   " + searchMin(resultMas));
//				System.out.println();
//
//				newGenerations[i] = searchMin(resultMas);
//
//			} else {

			printMas(genMas[i]);
			printMas(genMas[indexSecondGenMas]);
			System.out.println("-----");

			System.out.print("О" + i + " + O" + indexSecondGenMas + ": ");

			printMas(newGen1);
			Test test1 = new Test();
			if (random.nextInt(100) <= 100) {
				System.out.println();
				countingBestElem(taskMas, newGen1, countPros, test1, 1);

				System.out.print("Мутация: ");
				for(int l = 0; l < countIterator; l++) {
					mutation(newGen1, test1, countIterator);
				}
			}

			System.out.print("О" + indexSecondGenMas + " + O" + i + ": ");
			printMas(newGen2);

			Test test2 = new Test();
			if (random.nextInt(100) <= 100) {
				System.out.println();
				countingBestElem(taskMas, newGen2, countPros, test2, 1);

				System.out.print("Мутация: ");
				for(int l = 0; l < countIterator; l++) {
					mutation(newGen2, test2, countIterator);
				}
//				}


				int[] resultMas = new int[genMas.length - 1];

				resultMas[0] = generation[i];
				resultMas[1] = countingBestElem(taskMas, newGen1, countPros, test1, 2);
				resultMas[2] = countingBestElem(taskMas, newGen2, countPros, test2, 2);

				/////////////////-------------------------------------------------Вывод
//				if (test1.min && (test1.beforeIndex != test1.afterIndex) && (supportSearchElemInFinMas(newGen1, taskMas, test1.beforeIndex, countPros) <= supportSearchElemInFinMas(newGen1, taskMas, test1.afterIndex, countPros)) && test1.end) {
//					System.out.println("Truuuuuee1");
//				}
//				if (test2.min && (test2.beforeIndex != test2.afterIndex) &&  (supportSearchElemInFinMas(newGen2, taskMas, test2.beforeIndex, countPros) <= supportSearchElemInFinMas(newGen2, taskMas, test2.afterIndex, countPros)) && test2.end) {
//					System.out.println("Truuuuuee2");
//				}

				printMas(resultMas);
				System.out.println("   " + searchMin(resultMas));
				System.out.println();

				newGenerations[i] = searchMin(resultMas);
			}
			System.out.println("\n");
		}
		return newGenerations;
	}


}
