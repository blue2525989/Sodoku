package com.sodoku;

import java.util.Scanner;

public class Sodoku {
	// entire game is currently in main, divide this up later
	public static void main(String[] args) {
		int[][] grid = readPuzzle();
		
		if (!isValid(grid)) {
			System.out.println("Invalid input");
			//System.exit(0);
		} else if (search(grid)) {
			System.out.println("\nThe solution is:\n");
			printGrid(grid);
		} else {
			System.out.println("No solution.");
		}
	}
	
	// read the puzzle in
	public static int[][] readPuzzle() {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter a Sodoku puzzle:\n"
				+ "Use 0 to repersent a blank space.\n");
		int[][] grid = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = in.nextInt();
			}
		}
		in.close();
		return grid;
	}
	
	// obtain the list of free cells
	public static int[][] getFreeCellList(int[][] grid) {
		int numberOfFreeCells = 0;
		// determine number of free cells
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(grid[i][j] == 0) {
					numberOfFreeCells++;
				}
			}
		}
		// store free cell positions into freeCellList
		int[][] freeCellList = new int[numberOfFreeCells][2];
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					freeCellList[count][0] = i;
					freeCellList[count++][1] = j;
				}
			}
		}
		return freeCellList;
	}
	
	// print the values on the grid
	public static void printGrid(int[][] grid) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}		
	}
	
	// search for the solution
	public static boolean search(int[][] grid) {
		int[][] freeCellList = getFreeCellList(grid);
		int k = 0;
		boolean found = false;
		
		while(!found) {
			int i = freeCellList[k][0];
			int j = freeCellList[k][1];
			if(grid[i][j] == 0) {
				grid[i][j] = 1;
			}
			
			if (isValid(i, j, grid)) {
				if (k + 1 == freeCellList.length) {
					found = true;
				} else {
					k++;
				}
			} else if(grid[i][j] < 9) {
				grid[i][j] = grid[i][j] + 1;
			} else {
				while (grid[i][j] == 9) {
					grid[i][j] = 0;
					if(k == 0) {
						return false;
					}
					k--;
					i = freeCellList[k][0];
					j = freeCellList[k][1];
				}
				
				grid[i][j] = grid[i][j] + 1;
			}
		}
		
		return true;
	}
	
	// check whether grid is valid or not
	public static boolean isValid(int i, int j, int[][] grid) {
		for (int column = 0; column < 9; column++) {
			if (column != j && grid[i][column] == grid[i][j]) {
				return false;
			}
		}
		for (int row = 0; row < 9; row++) {
			if (row != i && grid[row][j] == grid[i][j]) {
				return false;
			}
		}
		return true;
	}
	
	// check whether the fixed cells are valid in the grid
	public static boolean isValid(int[][] grid) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(grid[i][j] != 0 && !isValid(i, j, grid)) {
					return false;
				}
			}
		}
		return true;
	}

}
