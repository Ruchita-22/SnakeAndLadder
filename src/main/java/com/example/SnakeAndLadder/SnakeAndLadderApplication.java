package com.example.SnakeAndLadder;

import com.example.SnakeAndLadder.models.Ladder;
import com.example.SnakeAndLadder.models.Player;
import com.example.SnakeAndLadder.models.Snake;
import com.example.SnakeAndLadder.services.SnakeAndLadderBoardService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

@SpringBootApplication
public class SnakeAndLadderApplication {

	public static void main(String[] args) {

		SpringApplication.run(SnakeAndLadderApplication.class, args);

		SnakeAndLadderBoardService snakeAndLadderBoardService;

		Scanner scanner = new Scanner(System.in);

		System.out.println("Do you want to give the size of board [Y/N] : ");
		String customBoard = scanner.next();

		if(customBoard.equals("Y")){
			System.out.println("Enter the size of the board : ");
			int size = scanner.nextInt();
			snakeAndLadderBoardService = new SnakeAndLadderBoardService(size);
		} else {
			snakeAndLadderBoardService = new SnakeAndLadderBoardService();
		}


		// input the snakes
		System.out.println("Enter number of snakes");
		int numberOfSnakes = scanner.nextInt();
		List<Snake> snakes = new ArrayList<>();
		for (int i = 0; i < numberOfSnakes; i++) {
			System.out.println("Enter snake "+(i+1)+" start and end");
			snakes.add(new Snake(scanner.nextInt(), scanner.nextInt()));
		}

		// input the ladder
		System.out.println("Enter number of ladder");
		int numberOfLadder = scanner.nextInt();
		List<Ladder> ladders = new ArrayList<>();
		for (int i = 0; i < numberOfLadder; i++) {
			System.out.println("Enter ladder "+(i+1)+" start and end");
			ladders.add(new Ladder(scanner.nextInt(), scanner.nextInt()));
		}

		// input the players
		System.out.println("Enter number of player");
		int numberOfPlayer = scanner.nextInt();
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < numberOfPlayer; i++) {
			System.out.println("Enter name of Player "+ (i+1));
			players.add(new Player(scanner.next()));
		}
		System.out.println("Should Allow Multiple Dice Roll On Six [Y/N] : ");
		String mutipleDiceRoll = scanner.next();
		System.out.println("Should Allow Token Cut when landed on same position [Y/N] : ");
		String tokenCut = scanner.next();
		System.out.println("Should game continue till last player [Y/N] : ");
		String rankingAllowed = scanner.next();


		snakeAndLadderBoardService.setPlayers(players);
		snakeAndLadderBoardService.setLadder(ladders);
		snakeAndLadderBoardService.setSnake(snakes);
		if (mutipleDiceRoll.equals("Y")) snakeAndLadderBoardService.setShouldAllowMultipleDiceRollOnSix(true);
		if (tokenCut.equals("Y")) snakeAndLadderBoardService.setShouldAllowTokenCutIfMultiplePlayerLandedOnSamePosition(true);
		if (rankingAllowed.equals("Y")) snakeAndLadderBoardService.setShouldGameContinueTillLastPlayer(true);

		// start game
		System.out.println("Ranking of Players "+snakeAndLadderBoardService.startGame());

	}

}
