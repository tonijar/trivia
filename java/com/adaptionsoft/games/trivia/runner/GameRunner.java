package com.adaptionsoft.games.trivia.runner;

import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {

	private static boolean notAWinner;

	private static Random rand = new Random();
	private static Random randRoll = new Random();

	private static Game aGame = new Game();

	public static Game getaGame() {
		return aGame;
	}

	public static void setaGame(Game game) {
		aGame = game;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public void setRandRoll(Random randRoll) {
		this.randRoll = randRoll;
	}

	public static void main(String[] args) {

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		do {

			aGame.roll(randRoll.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}

		} while (notAWinner);

	}
}
