package banking;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Connection conn;

    public static void main(String[] args) {

        //name of the database is passed as an argument
        String url = "jdbc:sqlite:" + args[1];

        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            // create a new table "card"
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS card" +
                    "(id INT PRIMARY KEY, " +
                    "number TEXT NOT NULL, " +
                    "pin TEXT NOT NULL, " +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCard();
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String cardNum = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.nextLine();

                    String loginSql = "SELECT number FROM card WHERE number = " + cardNum + " AND pin = " + pin;
                    try {
                        Statement stmt = conn.createStatement();
                        ResultSet acc = stmt.executeQuery(loginSql);
                        if(!acc.next()) {
                            System.out.println("Wrong card number or PIN!");
                        } else {
                            CreditCard card = new CreditCard(cardNum, pin);
                            System.out.println("You have successfully logged in!");
                            printAccountOptions(card);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
            }

        }
    }

    private static void printMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

    private static void createCard() {
        String firstSixDigits = "400000";
        StringBuilder sb;
        Random random = new Random();

        while (true) {
            sb = new StringBuilder();

            //generate unique card number except last digit(checksum)
            for (int i = 0; i < 9; i++) {
                int n = random.nextInt(10);
                sb.append(n);
            }
            sb.insert(0, firstSixDigits);
            int lastNum = generateCheckSum(sb.toString());
            sb.append(lastNum);

            try {
                if (!uniqueNumExists(sb.toString())) {
                    break;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        String cardNumber = sb.toString();
        String pin = String.format("%04d", random.nextInt(10000));
        CreditCard card = new CreditCard(cardNumber, pin);

        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" +
                cardNumber + "\n" +
                "Your card PIN:\n" +
                pin);

        //inserting new card into DB using a prepared statement
        String sql = "INSERT INTO card(id, number, pin, balance) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(card.getAccNum()));
            pstmt.setString(2, card.getCardNum());
            pstmt.setString(3, card.getPin());
            pstmt.setInt(4, card.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static boolean uniqueNumExists(String cardNum) throws SQLException{
        System.out.println("reached");
        String IdSql = "SELECT number FROM card WHERE number = " + cardNum;
        Statement IdStmt = conn.createStatement();
        ResultSet resultSet = IdStmt.executeQuery(IdSql);
        //returns true if a card of the same num is in the resultset else returns false
        return resultSet.next();
    }


    //using luhn's algorithm to generate last digit of the credit card number (checksum)
    private static int generateCheckSum(String firstFifteenDigits) {
        // array of the first 15 digits of the credit card number
        int[] integerCardNum = new int[15];
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            integerCardNum[i] = firstFifteenDigits.charAt(i) - '0';
        }

        for (int i = 0; i <= 14; i++) {
            if (i % 2 == 0) {
                integerCardNum[i] *= 2;
            }

            if (integerCardNum[i] > 9) {
                integerCardNum[i] -= 9;
            }

            sum += integerCardNum[i];
        }


        if (sum % 10 == 0) {
            return 0;
        }

        int num = sum % 10;
        return 10 - num;
    }

    private static void printAccountOptions(CreditCard card) throws SQLException {
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    String sql = "SELECT balance FROM card WHERE id = " + card.getAccNum();
                    Statement stmt = conn.createStatement();
                    ResultSet balance = stmt.executeQuery(sql);
                    System.out.println("Balance: " + balance);
                    break;
                case 2:
                    System.out.println("Enter income:");
                    int amount = scanner.nextInt();
                    scanner.nextLine();
                    String updateBalanceSql = "UPDATE card SET balance = balance + " + amount + " WHERE Id = " + card.getAccNum();
                    Statement updateStmt = conn.createStatement();
                    updateStmt.executeUpdate(updateBalanceSql);
                    System.out.println("Income was added!");
                    card.setBalance(card.getBalance() + amount);
                    break;
                case 3:
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String cardNum = scanner.nextLine();

                    if (cardNum.equals(card.getCardNum())) {
                        System.out.println("You can't transfer money to the same account!");
                        break;
                    }else if (!validateLuhnsAlgorithm(cardNum)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                        break;
                    } else if (!cardExists(cardNum)) {
                        System.out.println("Such a card does not exist.");
                        break;
                    }

                    System.out.println("Enter how much money you want to transfer:");
                    int amountToTransfer = scanner.nextInt();
                    scanner.nextLine();


                    String retrieveBalance = "SELECT balance FROM card WHERE id = " + card.getAccNum();
                    Statement balanceStatement = conn.createStatement();
                    ResultSet cardBalanceResult = balanceStatement.executeQuery(retrieveBalance);
                    int cardBalance = (Integer) cardBalanceResult.getObject("balance");
                    if (cardBalance < amountToTransfer) {
                        System.out.println("Not enough money!");
                        break;
                    } else {
                        String receivingAcc = "UPDATE card SET balance = balance + " + amountToTransfer + " WHERE number = " + cardNum;
                        String sendingAcc = "UPDATE card SET balance = balance - " + amountToTransfer + " WHERE Id = " + card.getAccNum();
                        Statement transferStmt = conn.createStatement();
                        transferStmt.executeUpdate(receivingAcc);
                        transferStmt.executeUpdate(sendingAcc);
                        card.setBalance(card.getBalance() - amountToTransfer);
                        System.out.println("Success");
                    }
                    break;
                case 4:
                    String deleteQuery = "DELETE FROM card WHERE id = " + card.getAccNum();
                    Statement deleteStmt = conn.createStatement();
                    deleteStmt.execute(deleteQuery);
                    System.out.println("The account has been closed!");
                    return;
                case 5:
                    System.out.println("You have successfully logged out!");
                    return;
                case 0:
                    System.out.println("Bye!");
                    conn.close();
                    System.exit(0);
            }
        }
    }

    private static boolean validateLuhnsAlgorithm(String cardNum) {
        int[] ints = new int[cardNum.length()];
        for (int i = 0; i < cardNum.length(); i++) {
            ints[i] = Integer.parseInt(cardNum.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int anInt : ints) {
            sum += anInt;
        }
        return sum % 10 == 0;
    }

    private static boolean cardExists(String cardNum) throws SQLException{
        String retrieveCardSql = "SELECT * FROM card WHERE number = " + cardNum;
        Statement checkIfCardExists = conn.createStatement();
        ResultSet re = checkIfCardExists.executeQuery(retrieveCardSql);
        return re.next();
    }
}