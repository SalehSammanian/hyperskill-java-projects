# Simple Banking System

This program can create a bank account and stores it in a "card" table in a SQLite3 database using JDBC.

The program allows the user to generate an account with a unique card number using Luhm's algorithm, the user can then log into their account and add,
or transfer money to another card. The user can also close the account if they want to.

The account number in this case is always 16 digits long and always starts with 400000.

Argument to create the database when starting the program:
> -dbName

## Example:
````
1. Create an account
2. Log into account
0. Exit
> 1

Your card has been created
Your card number:
4000002688071370
Your card PIN:
0855

1. Create an account
2. Log into account
0. Exit
> 1

Your card has been created
Your card number:
4000003040832244
Your card PIN:
9550

1. Create an account
2. Log into account
0. Exit
> 2

Enter your card number:
> 4000002688071370
Enter your PIN:
> 0855

You have successfully logged in

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 2

Enter income
> 155
Income was added!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
> 4000002688071373
Probably you made mistake in the card number. Please try again!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
> 4000002688071370
You cannot send money to yourself!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 3

Transfer
Enter card number:
> 4000003040832244
Enter how much money you want to transfer:
> 155
Success!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 1

Balance: 0

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
> 4

The account has been closed!

1. Create an account
2. Log into account
0. Exit
> 2

Enter your card number:
> 4000002688071370
Enter your PIN:
> 0855

Wrong card number or PIN!
````
