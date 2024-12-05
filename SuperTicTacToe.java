import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public class SuperTicTacToe {

    public static final String line = " 1 | 2 | 3   ===|===|=== 4 | 5 | 6 ===|===|=== 7 | 8 | 9 ";
    public static ArrayList<Character> grid = new ArrayList<Character>();
    public static HashMap<Integer, Integer> gridHashMap = new HashMap<Integer, Integer>(); // Aligns index of spaces
    public static boolean isPlayerTurn = true;
    public static int playerPoints[] = new int[5];
    public static int cpuPoints[] = new int[5];
    public static int playerBoards[] = new int[5];
    public static int cpuBoards[] = new int[5];
    public static int pointIndex = 0;
    public static int boardIndex = 0;
    public static int boardFullIndex = 0;

    public static void main(String[] args) {
        int boardChoice = 0;
        int spaceChoice = 0;

        for (int i = 0; i < line.length(); i++) { // Load grid components into array
            grid.add(line.charAt(i));
            if (line.charAt(i) >= 49 && line.charAt(i) <= 57) { // Checks if loaded char is int
                gridHashMap.put(Integer.parseInt(line.charAt(i) + ""), i); // Assign key to each numbered space
            }

        }

        makeGrid();
        Scanner scnr = new Scanner(System.in);
        System.out.println();
        System.out.println("Which board would you like to play?"); // Sets boardChoice to player input
        boardChoice = scnr.nextInt();
        System.out.println();
        while (boardChoice < 1 || boardChoice > 9) {
            System.out.println("Please choose a valid space"); // Checking for valid input
            boardChoice = scnr.nextInt();
        }
        while (grid.get(boardChoice) == 'X' || grid.get(boardChoice) == 'O') {
            System.out.println("Please choose an empty space"); // Checking if chosen space is already filled
            boardChoice = scnr.nextInt();
        }

        System.out.println();
        System.out.println("Playing on Board " + boardChoice + ":"); // Display Board
        makeGrid();

        while (!winner(true) && !winner(false) && boardFullIndex < 9) {
            if (isPlayerTurn) { // Checks if it is player's turn
                System.out.println("What space would you like to play?"); // Sets spaceChoice to player input
                spaceChoice = scnr.nextInt();

                while (spaceChoice < 1 || spaceChoice > 9) {
                    System.out.println("Please choose a valid space"); // Error Handling
                    spaceChoice = scnr.nextInt();
                }
                while (grid.get(gridHashMap.get(spaceChoice)) == 'X'
                        || grid.get(gridHashMap.get(spaceChoice)) == 'O') { // Error Handling
                    System.out.println("Please choose an empty space");
                    spaceChoice = scnr.nextInt();
                }
                playerPoints[pointIndex] = spaceChoice; // Assign chosen space to points
                fillGrid(spaceChoice); // Commits change to board
                pointIndex++;
                boardFullIndex++;
                if (winner(true)) { // Checks for winner
                    playerBoards[boardIndex] = boardChoice;
                    System.out.println("You Win Board " + boardChoice + "!");
                } else if (winner(false)) {
                    cpuBoards[boardIndex] = boardChoice;

                    System.out.println("Computer Wins Board " + boardChoice + "!");
                }
                boardIndex++;
                isPlayerTurn = false; // Passes turn to computer

            } else {
                pointIndex--;
                Random random = new Random();
                int randInt = random.nextInt(9 - 1) + 1; // Chooses random space
                if (findNumber(randInt) >= 49 && findNumber(randInt) <= 57) {
                    fillGrid(randInt); // Commits decision
                    cpuPoints[pointIndex] = randInt; // Assign cpu space to points array
                    isPlayerTurn = true; // Passes turn to player
                }
                pointIndex++;
            }

        }
        System.out.println("You Win!");
        scnr.close();
    }

    public static void fillGrid(int space) {
        char fill;
        if (isPlayerTurn) {
            fill = 'X';
        } else
            fill = 'O';

        switch (space) { // Switch case replaces chosen board spot with appropriate letter
            case 1:
                grid.set(1, fill);
                break;
            case 2:
                grid.set(5, fill);
                break;
            case 3:
                grid.set(9, fill);
                break;
            case 4:
                grid.set(25, fill);
                break;
            case 5:
                grid.set(29, fill);
                break;
            case 6:
                grid.set(33, fill);
                break;
            case 7:
                grid.set(47, fill);
                break;
            case 8:
                grid.set(51, fill);
                break;
            case 9:
                grid.set(55, fill);
                break;

            default:
                break;
        }

        System.out.println();
        makeGrid();
        System.out.println();
    }

    public static void makeGrid() { // Method to construct tic-tac-toe grid
        for (int i = 0; i < grid.size(); i++) { // Build grid from components
            System.out.print(grid.get(i));
            if (i % 11 == 1 && i != 1) // Every 11 characters, start new line
                System.out.println();
        }
    }

    public static Boolean winner(boolean player) { // True input = player, False input = cpu
        int diff = 0;
        int diff2 = 0;
        if (player) {
            if (playerPoints != null) {
                // Win Conditions: 123,456,789,147,258,369,159,357
                // diff 1: 123,456,789
                // diff 2: 357
                // diff 3: 147,258,369
                // diff 4: 159
                if (playerPoints[2] != 0 && cpuPoints[2] != 0) {
                    insertionSort(playerPoints);
                    for (int i = 1; i < playerPoints.length; i++) {
                        diff = playerPoints[i] - playerPoints[i - 1];
                    }
                    for (int i = 2; i < playerPoints.length; i++) {
                        diff2 = playerPoints[i] - playerPoints[i - 1];
                    }
                    if (diff == diff2) {
                        return true;
                    }
                } else if (cpuPoints[2] != 0) {
                    insertionSort(cpuPoints);
                    for (int i = 1; i < cpuPoints.length; i++) {
                        diff = cpuPoints[i] - cpuPoints[i - 1];
                    }
                    for (int i = 2; i < cpuPoints.length; i++) {
                        diff2 = cpuPoints[i] - cpuPoints[i - 1];
                    }
                    if (diff == diff2 && diff != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void insertionSort(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int j = i;
            while (j > 0 && numbers[j] < numbers[j - 1]) { // Swap numbers[j] and numbers [j - 1]
                int temp = numbers[j];
                numbers[j] = numbers[j - 1];
                numbers[j - 1] = temp;
                j--;
            }
        }

    }

    private static int findNumber(int n) {
        return grid.get(gridHashMap.get(n));
    }

}
