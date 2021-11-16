package com.company;
import java.util.Scanner;

        public class Main {
            private static char[][] theatre;
            private static int ticketsPurchased;
            private static int currentIncome;
            private static int totalIncome;
            private static int numOfSeats;

            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter the number of rows:");
                int rows = scanner.nextInt();
                System.out.println("Enter the number of seats in each row:");
                int seatsInRow = scanner.nextInt();
                theatre = new char[seatsInRow + 1][rows + 1];
                numOfSeats = rows * seatsInRow;
                if (rows * seatsInRow <= 60) {
                    totalIncome = rows * seatsInRow * 10;
                } else {
                    int frontHalf = rows / 2;
                    int backHalf = rows - frontHalf;
                    totalIncome = frontHalf * seatsInRow * 10;
                    totalIncome += backHalf * seatsInRow * 8;
                }

                for (int j = 0; j <= rows; j++) {
                    for (int i = 0; i <= seatsInRow; i++) {
                        if (i == 0 && j == 0) {
                            theatre[i][j] = ' ';
                        } else if (i == 0) {
                            theatre[i][j] = (char) (j + '0');
                        } else if (j == 0) {
                            theatre[i][j] = (char) (i + '0');
                        } else {
                            theatre[i][j] = 'S';
                        }
                    }
                }

                while (true) {
                    System.out.println("1. Show the seats");
                    System.out.println("2. Buy a ticket");
                    System.out.println("3. Statistics");
                    System.out.println("0. Exit");
                    int choice = scanner.nextInt();
                    if (choice == 0) {
                        break;
                    }
                    switch (choice) {
                        case 1:
                            System.out.println("Cinema:");
                            for (int j = 0; j <= rows; j++) {
                                for (int i = 0; i <= seatsInRow; i++) {
                                    System.out.print(theatre[i][j] + " ");
                                }
                                System.out.println();
                            }
                            break;
                        case 2:
                            int price = 0;
                            try {
                                while (true) {
                                    System.out.println("Enter a row number:");
                                    int rowNum = scanner.nextInt();
                                    System.out.println("Enter a seat number in that row:");
                                    int seatNum = scanner.nextInt();
                                    if (theatre[seatNum][rowNum] == 'B') {
                                        System.out.println("That ticket has already been purchased!");
                                    } else {
                                        theatre[seatNum][rowNum] = 'B';
                                        if (rows * seatsInRow <= 60 || (rowNum <= rows / 2 && rows * seatsInRow > 60)) {
                                            price = 10;
                                        } else {
                                            price = 8;
                                        }
                                        break;
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Wrong input!");
                            }

                            ticketsPurchased++;
                            currentIncome += price;
                            System.out.println("Ticket price: $" + price);
                            break;
                        case 3:
                            double percentage = ((double) ticketsPurchased / numOfSeats) * 100;
                            System.out.println("Number of purchased tickets: " + ticketsPurchased);
                            System.out.printf("Percentage: %.2f%%", percentage);
                            System.out.println();
                            System.out.println("Current income: $" + currentIncome);
                            System.out.println("Total income: $" + totalIncome);
                    }
                }
            }
        }
