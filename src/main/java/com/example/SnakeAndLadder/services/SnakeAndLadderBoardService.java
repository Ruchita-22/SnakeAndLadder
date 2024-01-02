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
    private boolean shouldAllowTokenCutIfMultiplePlayerLandedOnSamePosition;

    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NUMBER_OF_DICE = 1;
    private List<String> rankingList;
    public SnakeAndLadderBoardService(int size){
        this.board = new Board(size);
        this.players = new LinkedList<Player>();
        this.numberOfDice = DEFAULT_NUMBER_OF_DICE;
    }
    public SnakeAndLadderBoardService(){
        this(DEFAULT_BOARD_SIZE);
    }

    public void setPlayers(List<Player> playerList){
        this.rankingList = new ArrayList<>();
        this.players = new LinkedList<>();
        this.initialNumberOfPlayer = playerList.size();

        Map<String, Integer> playerPeices = new HashMap<>();
        for (Player player : playerList){
            players.add(player);
            playerPeices.put(player.getId(),0);     // initially each player position is 0
        }
        this.board.setPlayerPeices(playerPeices);
    }
    public void setLadder(List<Ladder> ladders) {
        board.setLadders(ladders);
    }
    public void setSnake(List<Snake> snakes) {
        board.setSnakes(snakes);
    }

    public List<String> startGame(){
        while (!isGameComplete()) {

            int totalDiceValue = getTotalValueAfterDiceRoll();

            Player currentPlayer = players.poll();

            move(totalDiceValue, currentPlayer);

            if(!hasPlayerWon(currentPlayer)) {
                players.add(currentPlayer);             // not won added again in queue
            } else {
                //System.out.println(currentPlayer.getName() + " wins the game");
                board.getPlayerPeices().remove(currentPlayer.getId());

                rankingList.add(currentPlayer.getName());
                if(players.size() == 1){        // if only one player left in the queue
                    rankingList.add(players.poll().getName());
                }
            }
        }
        System.out.println("Game End");
        return rankingList;
    }

    private boolean isGameComplete() {
        int currentNumberOfPlayers = players.size();
        if(shouldGameContinueTillLastPlayer == true){
            return currentNumberOfPlayers == 0  ? true : false;                 // to get ranking of player
        }
        else {
            return currentNumberOfPlayers < this.initialNumberOfPlayer;      // if players in queue < initial player
        }
    }

    private  int getTotalValueAfterDiceRoll(){
        if(shouldAllowMultipleDiceRollOnSix == true){           // on getting 6 dice is rolled again
            int diceValue = DiceService.roll();
            if(diceValue == 6) {
                return diceValue + getTotalValueAfterDiceRoll();
            }
            else return diceValue;
        } else {
            return DiceService.roll();
        }
    }
    private void move(int totalDiceValue, Player player){
        int oldPosition = board.getPlayerPeices().get(player.getId());
        int newPosition = oldPosition + totalDiceValue;
        int boardSize = board.getSize();

        if(newPosition > boardSize) {
            newPosition = oldPosition;
        } else {
            newPosition = getNewPositionAfterGoingThroughSnakeAndLadder(newPosition);
        }
        // to cut the token
        if(shouldAllowTokenCutIfMultiplePlayerLandedOnSamePosition == true){
            for(String p : board.getPlayerPeices().keySet()){
                if(board.getPlayerPeices().get(p) == newPosition){
                    board.getPlayerPeices().put(p,0);
                }
            }
        }
        board.getPlayerPeices().put(player.getId(), newPosition);

        System.out.println(player.getName() + " rolled a "+ totalDiceValue+ " and moved from "+ oldPosition+ " to "+ newPosition);

    }
    private int getNewPositionAfterGoingThroughSnakeAndLadder(int newPostion){
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
