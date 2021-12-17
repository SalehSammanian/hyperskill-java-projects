package search;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static HashMap<String, HashSet<Integer>> invertedIndex = new HashMap<>();
    private static Map<Integer, String> sources = new HashMap<>();
    private static boolean runMainLoop = true;

    public static void main(String[] args) {

        readFile(args[1]);

        System.out.println();

        while (runMainLoop) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String strategy = scanner.nextLine();
                    find(strategy);
                    break;
                case 2:
                    printAllPeople();
                    break;
                case 0:
                    System.out.println("Bye!");
                    runMainLoop = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
        }
    }

    private static void readFile(String fileName) {
        File file = new File(fileName);
        int i = 0;
        try(Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                String input = reader.nextLine();
                sources.put(i, input);
                String[] personInfo = input.split(" ");

                //building the inverted index
                for (String s: personInfo) {
                    s = s.toLowerCase();
                    if (!invertedIndex.containsKey(s)) {
                        invertedIndex.put(s, new HashSet<>());
                    }
                    invertedIndex.get(s).add(i);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    private static void printAllPeople() {
        System.out.println("=== List of people ===");

        for (String s: sources.values()) {
            System.out.println(s);
        }

    }

    private static void find(String matchingStrategy) {
        System.out.println("Enter a name or email to search all suitable people.");
        String query = scanner.nextLine().toLowerCase();
        ArrayList<String> found = new ArrayList<>();
        String[] queryWords = query.split(" ");

        switch (matchingStrategy) {
            case "ALL":
                found = findAll(queryWords);
                break;
            case "ANY":
                found = findAny(queryWords);
                break;
            case "NONE":
                found = findNone(queryWords);
                break;

        }

        for (String line: found) {
            System.out.println(line);
        }
    }

    private static ArrayList<String> findAll(String[] queryWords) {
        ArrayList<String> lines = new ArrayList<>();
        for (String line: sources.values()) {
            boolean toAdd = true;
            for (String word: queryWords) {
                if (!line.toLowerCase().contains(word)) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static ArrayList<String> findAny(String[] queryWords) {
        ArrayList<String> lines = new ArrayList<>();

        for (String line: sources.values()) {
            for (String word: queryWords) {
                if (line.toLowerCase().contains(word) && (!lines.contains(line))) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private static ArrayList<String> findNone(String[] queryWords) {
        ArrayList<String> lines = new ArrayList<>();
        for (String line: sources.values()) {
            boolean toAdd = true;
            for (String word: queryWords) {
                if (line.toLowerCase().contains(word)) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void printMenu() {
        System.out.println("=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit");
    }
}
