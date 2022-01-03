package maze;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static Maze maze;

    public static void main(String[] args) {
        while (true) {
            System.out.println(getMenu());
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Bye");
                    System.exit(0);
                case 1:
                    System.out.println("Enter the size of a new maze");
                    int size = scanner.nextInt();
                    scanner.nextLine();
                    maze = new Maze(size);
                    maze.printMaze();
                    break;
                case 2:
                    File inFile = new File(scanner.nextLine());
                    try  {
                        FileInputStream fileInputStream
                                = new FileInputStream(inFile);
                        ObjectInputStream objectInputStream
                                = new ObjectInputStream(fileInputStream);
                        maze = (Maze) objectInputStream.readObject();
                        objectInputStream.close();
                    } catch (Exception e) {
                        System.out.println("The file" + inFile + "does not exist");
                    }
                    break;
                case 3:
                    try {
                        File outFile = new File(scanner.nextLine());
                        FileOutputStream fileOutputStream
                                = new FileOutputStream(outFile);
                        ObjectOutputStream objectOutputStream
                                = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(maze);
                        objectOutputStream.flush();
                        objectOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    maze.printMaze();
                    break;
                case 5:
                    maze.findEscape();
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
        }
    }

    private static String getMenu() {
        if (maze == null) {
            return "=== Menu ===\n" +
                    "1. Generate a new maze\n" +
                    "2. Load a maze\n" +
                    "0. Exit";
        }
        return "=== Menu ===\n" +
                "1. Generate a new maze\n" +
                "2. Load a maze\n" +
                "3. Save the maze\n" +
                "4. Display the maze\n" +
                "5. Find the escape.\n" +
                "0. Exit";
    }
}
