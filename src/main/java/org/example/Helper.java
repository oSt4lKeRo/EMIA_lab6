package org.example;

import java.util.ArrayList;
import java.util.Random;

import static org.example.Funcional.*;

public class Helper {


	public static int[][] generateMas(int result){

		if (result == 1) {
			int n = 12;
			int m = 4;
			int T1 = 10;
			int T2 = 20;
			int diff = T2 - T1;

			int[][] mas = new int[n][m];
			Random random = new Random();

			for (int i = 0; i < mas.length; i++) {
				for (int j = 0; j < mas[i].length; j++) {
					int value = random.nextInt(diff + 1);
					value += T1;
					mas[i][j] = value;
				}
			}
			return mas;
		} else if(result == 2) {

			int m = 3;
			int[] masElem = {10, 15, 20, 16, 11, 13, 14, 19};
			int[][] mas = new int[masElem.length][m];

			for(int i = 0; i < mas.length; i++){
				for(int j = 0; j < mas[i].length; j++){
					mas[i][j] = masElem[i];
				}
			}
			return mas;
		} else {
			int[][] mas = {{11, 16, 10}, {13, 15, 19}, {16, 15, 17}, {10, 13, 14}, {12, 19, 12}, {19, 20, 19}, {17, 19, 17}, {17, 15, 20}, {11, 13, 11}};
			return mas;
		}
	}

	public static int[] generateElemToGenMas(int n){

		int T1 = 0;
		int T2 = 255;
		int diff = T2 - T1;

		int[] mas = new int[n];
		Random random = new Random();

		for (int i = 0; i < mas.length; i++) {
			int value = random.nextInt(diff + 1);
			value += T1;

			mas[i] = value;
		}
		return mas;
	}

	public static int[] generateTaskMas(int[][] mas){
		int[] taskMas = new int[mas.length];
		for(int i = 0; i < taskMas.length; i++){
			taskMas[i] = mas[i][0];
		}
		return taskMas;
	}

	public static int[][] generateGenMas(int taskMasSize, int n, int index){

		if(index == 0) {
			int[][] mas = new int[n][taskMasSize];
			for (int i = 0; i < mas.length; i++) {
				mas[i] = generateElemToGenMas(taskMasSize);
			}
			return mas;
		} else {
			int mas[][] = {{31, 52, 183, 200, 49, 72, 115, 5},{152, 54, 201, 87, 21, 21, 160, 213}, {89, 51, 173, 240, 10, 5,  36, 81}};
			return mas;
		}
	}

	public static ArrayList<Integer>[] genFenMatrix(int n){
		ArrayList<Integer>[] fenMatrix = new ArrayList[n];
		for(int i = 0; i < fenMatrix.length; i++){
			fenMatrix[i] = new ArrayList<>();
		}
		return fenMatrix;
	}



	public static int[] deepCopy(int[] mas){
		int[] newMas = new int[mas.length];
		for(int i = 0; i < mas.length; i++){
			newMas[i] = mas[i];
		}
		return newMas;
	}

	public static void printMas(int[][] mas){
		for(int i = 0; i < mas.length; i++){
			for(int j = 0; j < mas[i].length; j++){
				System.out.print(mas[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}


	public static void printTaskMas(int[] taskMas){
		for(int i : taskMas){
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public static void printFenMatrix(ArrayList<Integer>[] fenMatrix) {

		for(ArrayList<Integer> i : fenMatrix){
			for(int j : i){
				System.out.print(j + "\t");
			}
			System.out.println();
		}
		System.out.println();

	}

	public static int supportSearchGroup(int elem, int n) {
		for (int j = 0; j < n; j++) {
			if (elem < (255 / n) * (j + 1)) {
				return j;
			}
		}
		return 0;
	}

	public static int supportSearchElemInFinMas(int[] genMas, int[] taskMas, int index, int n){

		ArrayList<Integer>[] fenMatrix = genFenMatrix(n);

		for(int i = 0; i < genMas.length; i++){
			for(int j = 0; j < n; j++){
				if(genMas[i] < (255/n)*(j+1)){
					fenMatrix[j].add(taskMas[i]);
					break;
				}
			}
		}
		int[] taskSum = countingTaskSum(fenMatrix);
		return taskSum[index];
	}

	public static void printFenMatrix(ArrayList<Integer>[] fenMatrix, int[] taskSum) {

		for(int i = 0; i < fenMatrix.length; i++){
			for(int j : fenMatrix[i]){
				System.out.print(j + "\t");
			}
			System.out.println(" | " + taskSum[i]);
		}
		System.out.println();
	}

	public static void printMas(int[] generation){
		for(int i : generation){
			System.out.print(i + "\t");
		}
		System.out.println();
	}
}
