package tictactoe;
import java.util.Scanner;

public class Main {
    private static char[][] grid = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };
    private static char turn = 'X';
    private static int emptyCell = 9;
    private static boolean xWins = false;
    private static boolean oWins = false;
    private static String state;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printGrid();

        //main game loop
        while (true) {
            int x, y;
            System.out.print("Enter the coordinates: ");
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
            } catch(NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (x < 1 || x > 3 || y < 1|| y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (grid[x - 1][y - 1] == 'X' || grid[x - 1][y - 1] == 'O') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            grid[x - 1][y - 1] = turn;
            emptyCell--;

            printGrid();


            state = checkState();
            if (!state.equals("Game not finished")) {
                System.out.print(state);
                break;
            }

            switch (turn) {
                case 'X':
                    turn = 'O';
                    break;
                case 'O':
                    turn = 'X';
                    break;
            }

        }
    }


    public static void printGrid() {
        int n = 3;
        int m = 3;
        System.out.println("---------");
        for (int i = 0; i < n; i++) {
            System.out.print("| ");
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j] +" ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }


    //method checks the state of the game grid
    public static String checkState() {
        //first check if either player wins
        if (grid[0][0] != ' ' && grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2]) {
            if (grid[0][0] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[1][0] != ' ' && grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2]) {
            if (grid[1][0] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[2][0] != ' ' && grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2]) {
            if (grid[2][0] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[0][0] != ' ' && grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0]) {
            if (grid[0][0] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[0][1] != ' ' && grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1]) {
            if (grid[0][1] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[0][2] != ' ' && grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2]) {
            if (grid[0][2] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            if (grid[0][0] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }
        if (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            if (grid[0][2] == 'X') {
                xWins = true;
            } else {
                oWins= true;
            }
        }

        if (xWins) {
            return "X wins";
        } else if (oWins) {
            return  "O wins";
        } else if (emptyCell <= 0) {
            return "Draw";
        } else {
            return "Game not finished";
        }
    }
}
