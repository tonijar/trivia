package com.adaptionsoft.games.trivia.runner;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.QuestionsDB;

public class GameRunnerTest {
	
	SpyPrintStream outputStream;
	GameRunner runner;
	Game game;
	
	@Before
	public void setUp() {
		outputStream = new SpyPrintStream();
		System.setOut(outputStream);

		runner = new GameRunner();
		game = new Game();
		runner.setaGame(game);
	}

	@Test
	public void launchGameWithRandomOneAndChetWinsWithSixCoinsAndLocationSix() {
		
		Random mockRandom = Mockito.mock(Random.class);
		Mockito.when(mockRandom.nextInt()).thenReturn(1);
		runner.setRand(mockRandom);
		runner.setRandRoll(mockRandom);
		
		runner.main(null);
		
		Assert.assertEquals(3,runner.getaGame().howManyPlayers());
		Assert.assertTrue(runner.getaGame().isPlayable());
		
		Assert.assertTrue(outputStream.contains("Chet's new location is 6"));
		Assert.assertTrue(outputStream.contains("Sports Question 3"));
		Assert.assertTrue(outputStream.contains("Chet now has 6 Gold Coins."));
	}
	
	@Test
	public void launchGameWithRollAlwaysTwoAndRandomAnswerOne() {
		
		Random mockRandom = Mockito.mock(Random.class);
		Mockito.when(mockRandom.nextInt()).thenReturn(1);
		Random mockRandomRoll = Mockito.mock(Random.class);
		Mockito.when(mockRandomRoll.nextInt()).thenReturn(2);
		runner.setRand(mockRandom);
		runner.setRandRoll(mockRandomRoll);
		
		runner.main(null);
		
		Assert.assertEquals(3,runner.getaGame().howManyPlayers());
		Assert.assertTrue(runner.getaGame().isPlayable());
		
		Assert.assertTrue(outputStream.contains("Chet's new location is 6"));
		Assert.assertTrue(outputStream.contains("Sports Question 3"));
		Assert.assertTrue(outputStream.contains("Chet now has 6 Gold Coins."));
	}
	
	@Test
	public void launchGameWithRollAlwaysTwoAndRandomAnswerOneWithCustomQuestions() {
		
		Random mockRandom = Mockito.mock(Random.class);
		Mockito.when(mockRandom.nextInt()).thenReturn(1);
		Random mockRandomRoll = Mockito.mock(Random.class);
		Mockito.when(mockRandomRoll.nextInt()).thenReturn(2);
		runner.setRand(mockRandom);
		runner.setRandRoll(mockRandomRoll);
		QuestionsDB mockQuestionsDB = Mockito.mock(QuestionsDB.class);
		Mockito.when(mockQuestionsDB.getQuestion(Mockito.anyString())).thenReturn("This is a custom question");
		
		runner.getaGame().setQuestionsDB(mockQuestionsDB);
		
		runner.main(null);
		
		Assert.assertEquals(3,runner.getaGame().howManyPlayers());
		Assert.assertTrue(runner.getaGame().isPlayable());
		
		Assert.assertTrue(outputStream.contains("Chet's new location is 6"));
		Assert.assertFalse(outputStream.contains("Sports Question 3"));
		Assert.assertTrue(outputStream.contains("Chet now has 6 Gold Coins."));
		Assert.assertTrue(outputStream.contains("This is a custom question"));
	}

}

class SpyPrintStream extends PrintStream {
	List<String> buffer = new ArrayList<String>();
	
	public SpyPrintStream() {
		super(Mockito.mock(OutputStream.class));
	}
	
	public void println(String str) {
		buffer.add(str);
	}
	
	public void println(Object obj) {
		buffer.add(obj.toString());
	}
	
	public boolean contains(String str) {
		return buffer.contains(str);
	}
}