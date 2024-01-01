package com.example.SnakeAndLadder.services;

import com.example.SnakeAndLadder.models.Board;
import com.example.SnakeAndLadder.models.Ladder;
import com.example.SnakeAndLadder.models.Player;
import com.example.SnakeAndLadder.models.Snake;
import lombok.Data;

import java.util.*;

@Data
public class SnakeAndLadderBoardService {

    private Board board;
    private int initialNumberOfPlayer;
    private Queue<Player> players;
    private boolean status;
    private int numberOfDice;
    private boolean shouldGameContinueTillLastPlayer;
    private boolean shouldAllowMultipleDiceRollOnSix;
    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NUMBER_OF_DICE = 1;
    public SnakeAndLadderBoardService(int size){
        this.board = new Board(size);
        this.players = new LinkedList<Player>();
        this.numberOfDice = DEFAULT_NUMBER_OF_DICE;
    }
    public SnakeAndLadderBoardService(){
        this(DEFAULT_BOARD_SIZE);
    }

    public void setPlayers(List<Player> playerList){
        this.players = new LinkedList<>();
        this.initialNumberOfPlayer = playerList.size();
        Map<String, Integer> playerPeices = new HashMap<>();
        for (Player player : playerList){
            players.add(player);
            playerPeices.put(player.getId(),0);
        }
        this.board.setPlayerPeices(playerPeices);
    }
    public void setLadder(List<Ladder> ladders) {
        board.setLadders(ladders);
    }
    public void setSnake(List<Snake> snakes) {
        board.setSnakes(snakes);
    }

    public void startGame(){
        while (!isGameComplete()) {

            int totalDiceValue = getTotalValueAfterDiceRoll();
            Player currentPlayer = players.poll();
            move(totalDiceValue, currentPlayer);
            if(!hasPlayerWon(currentPlayer)) {
                players.add(currentPlayer);
            } else {
                System.out.println(" Player: "+ currentPlayer.getName() + " has won the game");
                board.getPlayerPeices().remove(currentPlayer.getId());
            }
        }
    }

    private boolean isGameComplete() {
        int currentNumberOfPlayer = players.size();
        return currentNumberOfPlayer < this.initialNumberOfPlayer;
    }
    private  int getTotalValueAfterDiceRoll(){
        return DiceService.roll();
    }
    private void move(int position, Player player){
        int oldPosition = board.getPlayerPeices().get(player.getId());
        int newPosition = oldPosition+position;
        int boardSize = board.getSize();

        if(newPosition > boardSize) {
            newPosition = oldPosition;
        } else {
            newPosition = getNewPositionAfterGoingThroughSnakeANdLadder(newPosition);
        }
        board.getPlayerPeices().put(player.getId(), newPosition);
        System.out.println("Player : " + player.getName() + " rolled a position : "+ position+ " to new position : "+ newPosition);

    }
    private int getNewPositionAfterGoingThroughSnakeANdLadder(int newPostion){
        int prevPosition;

        do {
             prevPosition = newPostion;
             for(Snake snake : board.getSnakes()) {
                 if(snake.getStart() == newPostion) {
                     newPostion = snake.getEnd();
                 }
             }
            for(Ladder ladder : board.getLadders()) {
                if(ladder.getStart() == newPostion) {
                    newPostion = ladder.getEnd();
                }
            }
        } while (prevPosition != newPostion);
        return newPostion;
    }
    private boolean hasPlayerWon(Player player){
        return board.getSize() == board.getPlayerPeices().get(player.getId());
    }
}
