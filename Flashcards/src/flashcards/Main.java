package flashcards;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Map<String, Card> cards = new LinkedHashMap<>();
    public static Map<String, Card> defKeyCards = new LinkedHashMap<>();
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<String> logs = new ArrayList<>();
    public static int highestNumberOfMistakes = 0;

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            for (String s: args) {
                sb.append(s).append(" ");
            }
            logs.add(sb.toString());

            if (args[0].equals("-import")) {
                importCards(args[1]);
            } else if (args.length > 2) {
                if (args[2].equals("-import")) {
                    importCards(args[3]);
                }
            }
        }

        while (true) {
            String options = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
            System.out.println(options);
            logs.add(options);
            String action = scanner.nextLine();
            logs.add(action);

            switch (action) {
                case "add":
                    add();
                    break;
                case "remove":
                    remove();
                    break;
                case "import":
                    System.out.println("File name:");
                    logs.add("File name:");
                    String importFileName = scanner.nextLine();
                    logs.add(importFileName);
                    importCards(importFileName);
                    break;
                case "export":
                    System.out.println("File name:");
                    logs.add("File name:");
                    String exportFileName = scanner.nextLine();
                    logs.add(exportFileName);
                    exportCards(exportFileName);
                    break;
                case "ask":
                    ask();
                    break;
                case "log":
                    log();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    logs.add("Bye bye!");
                    if (args.length > 0) {
                        if (args[0].equals("-export")) {
                            exportCards(args[1]);
                        } else if (args.length > 2) {
                            if (args[2].equals("-export")) {
                                exportCards(args[3]);
                            }
                        }
                    }
                    System.exit(0);
            }

        }
    }


    public static void ask() {
        System.out.println("How many times to ask?");
        logs.add("How many times to ask?");
        int n = scanner.nextInt();
        logs.add(scanner.nextLine());

        int i = 1;
        for (String term : cards.keySet()) {
            if (i > n) {
                break;
            }

            System.out.println("Print the definition of \"" + term + "\":");
            logs.add("Print the definition of \"" + term + "\":");
            String answer = scanner.nextLine();
            logs.add(answer);

            if (cards.get(term).getDefinition().equals(answer)) {
                System.out.println("Correct!");
                logs.add("Correct!");
            } else if (defKeyCards.containsKey(answer)) {
                System.out.println("Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\", but your definition is correct for \"" + defKeyCards.get(answer).getTerm() + "\".");
                cards.get(term).mistakeWasMade();
                logs.add("Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\", but your definition is correct for \"" + defKeyCards.get(answer).getTerm() + "\".");
                if (cards.get(term).getMistakesMade() > highestNumberOfMistakes) {
                    highestNumberOfMistakes = cards.get(term).getMistakesMade();
                }
            } else {
                System.out.println("Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\".");
                logs.add("Wrong. The right answer is \"" + cards.get(term).getDefinition() + "\".");
                cards.get(term).mistakeWasMade();
                if (cards.get(term).getMistakesMade() > highestNumberOfMistakes) {
                    highestNumberOfMistakes = cards.get(term).getMistakesMade();
                }
            }
            i++;
        }
    }

    public static void add() {
        String term, definition;

        System.out.println("The card:");
        logs.add("The card:");
        term = scanner.nextLine();
        logs.add(term);

        if (cards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            System.out.println();
            logs.add("The card \"" + term + "\" already exists.\n");
            return;
        }

        System.out.println("The definition of the card:");
        logs.add("The definition of the card:");
        definition = scanner.nextLine();
        logs.add(definition);
        if (defKeyCards.containsKey(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            System.out.println();
            logs.add("The definition \"" + definition + "\" already exists.\n");
            return;
        }

        Card card = new Card(term, definition);
        cards.put(term, card);
        defKeyCards.put(definition, card);
        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        logs.add("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    public static void remove() {
        System.out.println("Which card?");
        logs.add("Which card?");
        String card = scanner.nextLine();
        logs.add(card);
        if(cards.containsKey(card)) {
            String def = cards.get(card).getDefinition();
            cards.remove(card);
            defKeyCards.remove(def);
            System.out.println("The card has been removed.");
            logs.add("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + card + "\": there is no such card.");
            logs.add("Can't remove \"" + card + "\": there is no such card.");
        }
    }

    public static void importCards(String fileName) {
        File file = new File(fileName);
        int cardNum = 0;
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String t = fileScanner.nextLine();
                String def = fileScanner.nextLine();
                int numOfMistakes = fileScanner.nextInt();
                if (numOfMistakes > highestNumberOfMistakes) {
                    highestNumberOfMistakes = numOfMistakes;
                }
                fileScanner.nextLine();
                Card card = new Card(t, def, numOfMistakes);
                cards.put(t, card);
                defKeyCards.put(def, card);
                cardNum++;
            }
            System.out.println(cardNum + " cards have been loaded.");
            logs.add(cardNum + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logs.add("File not found.");
        }
    }

    public static void exportCards(String fileName) {
        int numOfCards = 0;
        try (FileWriter writer = new FileWriter("D:\\CS\\Projects\\Flashcards\\Flashcards\\task\\" + fileName)) {
            for (String s: cards.keySet()) {
                writer.write(s + "\n" + cards.get(s).getDefinition() + "\n" + cards.get(s).getMistakesMade() + "\n");
                numOfCards++;
            }
            System.out.println(numOfCards + " cards have been saved.");
            logs.add(numOfCards + " cards have been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log() {
        System.out.println("File name:");
        logs.add("File name:");
        String fileName = scanner.nextLine();
        logs.add(fileName);
        System.out.println("The log has been saved.");
        logs.add("The log has been saved.");

        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (String s: logs) {
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void hardestCard() {
        if (highestNumberOfMistakes == 0) {
            System.out.println("There are no cards with errors.");
            logs.add("There are no cards with errors.");
        }

        ArrayList<Card> termsWithHighestNumOfMistakes = new ArrayList<>();

        for (Card card: cards.values()) {
            if (card.getMistakesMade() == highestNumberOfMistakes) {
                termsWithHighestNumOfMistakes.add(card);
            }
        }

        if (termsWithHighestNumOfMistakes.size() == 1) {
            System.out.println("The hardest card is \"" + termsWithHighestNumOfMistakes.get(0).getTerm() + "\". You have "
                    + highestNumberOfMistakes + " errors answering it.");
            logs.add("The hardest card is \"" + termsWithHighestNumOfMistakes.get(0).getTerm() + "\". You have "
                    + highestNumberOfMistakes + " errors answering it.");
        } else if (highestNumberOfMistakes > 1) {
            StringBuilder sb = new StringBuilder("The hardest cards are ");
            for (int i = 0; i < termsWithHighestNumOfMistakes.size(); i++) {
                if(i == termsWithHighestNumOfMistakes.size() - 1) {
                    sb.append("\"" + termsWithHighestNumOfMistakes.get(i).getTerm() + "\".");
                } else {
                    sb.append("\"" + termsWithHighestNumOfMistakes.get(i).getTerm() + "\", ");
                }
            }
            sb.append(" You have " + highestNumberOfMistakes + " errors answering them.");
            System.out.println(sb);
            logs.add(sb.toString());
        }
    }

    public static void resetStats() {
        highestNumberOfMistakes = 0;
        for (Card card : cards.values()) {
            card.setMistakesMade(0);
        }
        System.out.println("Card statistics have been reset.");
        logs.add("Card statistics have been reset.");
    }
}
