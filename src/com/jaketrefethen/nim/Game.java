package com.jaketrefethen.nim;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	public static Scanner input = new Scanner(System.in);
	
	private int[] towers;
	
	public Game(int[] s) {
		towers = s;
	}
	
	public ArrayList<Game> getMoves() {
		ArrayList<Game> moves = new ArrayList<Game>();
		for (int i = 0; i < towers.length; i++) {
			for (int j = 1; j <= towers[i]; j++) {
				int[] t = towers.clone();
				t[i] -= j;
				moves.add(new Game(t));
			}
		}
		return moves;
	}
	
	public Game getPlayerMove() {
		if (over()) {
			System.out.println("Computer Won!");
			return this;
		}
		String s = input.nextLine();
		String[] args = s.split("-");
		if (args.length != 2) {
			System.out.println("Invalid Move");
			return getPlayerMove();
		}
			
		int tower = Integer.parseInt(s.split("-")[0]) - 1;
		int amount = Integer.parseInt(s.split("-")[1]);

		int[] t = towers.clone();
		if (t.length <= tower || tower < 0 || amount < 1 || amount > towers[tower]) {
			System.out.println("Invalid Move");
			return getPlayerMove();
		}
		t[tower] -= amount;
		if (t[tower] < 0)
			t[tower] = 0;
		return new Game(t);
	}
	
	public Game getComputerMove() {
		
		if (over()) {
			System.out.println("You Won!");
			return this;
		}
		
		// exactly the same as max but save 
		// the game so we can play it
		
		ArrayList<Game> moves = getMoves();
		Game bestMove = moves.get(0);
		int max = Integer.MIN_VALUE;
		for (Game move : moves) {
			// play to minimize
			int min = move.min(max);
			// pick the maximum result
			if (min > max) {
				max = min;
				bestMove = move;
			}
		}

		if (max < 1)
			System.out.println("\"I feel like I already lost...\"");
		return bestMove;
	}
	
	public int min(int alpha) {
		// if the game is over, the computer won
		if (over())
			return 1;
		
		int min = Integer.MAX_VALUE;
		for (Game move : getMoves()) {
			// play to maximize
			int max = move.max(min);
			// pick minimum result
			if (max < min) {
				min = max;
				// using running min as beta, prune
				if (min <= alpha)
					return min;
			}
		}
		
		return min;
	}
	
	public int max(int beta) {
		// if the game is over, the player won
		if (over())
			return 0;
		
		int max = Integer.MIN_VALUE;
		for (Game move : getMoves()) {
			// play to minimize
			int min = move.min(max);
			// pick the maximum result
			if (min > max) {
				max = min;
				// using running max as alpha, prune
				if (max >= beta)
					return max;
			}
		}
		
		return max;
	}
	
	public boolean over() {
		for (int i = 0; i < towers.length; i++) {
			if (towers[i] != 0)
				return false;
		}
		return true;
	}
	
	public void print() {
		int highest = 0;
		for (int i = 0; i < towers.length; i++) {
			if (towers[i] > highest)
				highest = towers[i];
		}
		System.out.println("");
		System.out.println("");
		for (int r = highest; r > 0; r--) {
			for (int i = 0; i < towers.length; i++) {
				if (towers[i] >= r)
					System.out.print(" _ ");
				else
					System.out.print("   ");
			}
			System.out.println("");
		}
		for (int i = 0; i < towers.length; i++) {
			System.out.print("   ");
		}
		System.out.println("");
		for (int i = 0; i < towers.length; i++) {
				System.out.print(" " + (i+1) + " ");
		}
		System.out.println("");
		System.out.println("=============================");
	}
	

}
