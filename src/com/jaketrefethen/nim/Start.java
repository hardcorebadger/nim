package com.jaketrefethen.nim;

public class Start {
	
	public static Game game = new Game(new int[]{3,3,3});
	public static boolean computersTurn = true;
	
	public static void main(String[] args) {
		setupGame();
		playGame();
	}
	
	public static void setupGame() {
		System.out.println("==== Let's Play Some Nim ====");
		System.out.println("= How Many Towers? ");
		int t = Integer.parseInt(Game.input.nextLine());
		int[] towers = new int[t];
		for (int i = 0; i < t; i++) {
			System.out.println("= How Many Donuts on Tower [" + i + "]?");
			towers[i] = Integer.parseInt(Game.input.nextLine());
		}
		System.out.println("= Who Goes First? [ai/human]");
		if (Game.input.nextLine().startsWith("ai"))
			computersTurn = true;
		else
			computersTurn = false;
		System.out.println("==== Awesome Let's Play  ====");
		game = new Game(towers);
	}
	
	public static void playGame() {
		System.out.println("==== Let's Play Some Nim ====");
		System.out.println("====   Starting Towers   ====");
		game.print();
		System.out.println("=============================");
		while (!game.over()) {
			if (computersTurn) {
				System.out.println("====   Computer's Turn   ====");
				Game.input.nextLine();
				game = game.getComputerMove();
			} else {
				System.out.println("====      Your Turn      ====");
				game = game.getPlayerMove();
			}
			game.print();
			System.out.println("=============================");
			computersTurn = !computersTurn;
		}
		if (computersTurn) {
			System.out.println("====   Computer's Turn   ====");
			System.out.println("====      You Win!      ====");
			System.out.println("=============================");
		} else {
			System.out.println("====      Your Turn      ====");
			System.out.println("====      You Lose!      ====");
			System.out.println("=============================");
		}
	}

}
