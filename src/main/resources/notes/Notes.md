

- exception handing we have not done so far
- play with two dice and we have not taken input for dice
- we have not used any design pattern

We implemented this also
- cut someone token
- dice rolled again on getting 6
- ranking of player is not done


##### Board

    @Data
    public class Board {
        private int size;
        private List<Snake> snakes;
        private List<Ladder> ladders;
        private Map<String, Integer> playerPeices;
        public Board(int size){
            this.size = size;
            this.snakes = new ArrayList<>();
            this.ladders = new ArrayList<>();
            this.playerPeices = new HashMap<>();
        }
    }

##### Ladder

    @Data
    @AllArgsConstructor
    public class Ladder {
        private int start;
        private int end;
    }

##### Snake

    @Data
    @AllArgsConstructor
    public class Snake {
        private int start;
        private int end;
    }

##### Player

    @Data
    public class Player {
        private String id;
        private String name;

        public Player(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
        }
    }

##### DiceService

    public class DiceService {
        public static int roll(){
            return new Random().nextInt(6)+1;
        }
    }

##### SnakeAndLadderBoardService

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


