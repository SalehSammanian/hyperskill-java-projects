# Flashcards
A program that allows the user to learn terms and their definitions. A user can either load the cards from a .txt file or add them manually in the command line.
The user also has the options to remove cards, export added cards to a .txt file and save a log of all that was printed to the terminal. 

Each card has a term, a definition and a number of mistakes made while studying the card.

When the user wants to study the cards, they can select the "ask" option and the choose the amount of cards to be asked about. The program will keep a record of 
mistakes made for each card. Additionally it will keep trak of the "hardest card" which is the one with the most amount of mistakes. The num of mistakes on all
cards can be reset by selecting the "reset stats" option.

To import a .txt file :
- -import filename

To export cards to a file:
- -export filename

--------------------------
Example .txt file with cards:
>great britain;london;1

>poland;warsaw;3

>russia;moscow;4

>germany;berlin;5

>austria;vienna;8

## Example:

````
Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> import
File name:
> capitals.txt
5 cards have been loaded.

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> add
The card:
> france
The definition of the card:
> paris
The pair ("france":"paris") has been added.

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> add
The card:
> france
The card "france" already exists.

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> add
The card:
> usa
The definition of the card:
> paris
The definition "paris" already exists.

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> quizFormat
How many times to quizFormat?
> 3

Print the definition of "great britain":
> london
Correct!

Print the definition of "poland":
> cracow
Wrong. The right answer is "warsaw".

Print the definition of "russia":
> berlin
Wrong. The right answer is "moscow", but your definition is correct for "germany".

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> hardest card
The hardest card is "austria". You have 8 errors answering it.

Input the action (add, remove, import, export, quizFormat, exit, log, hardest card, reset stats):
> log
File name:
> log.txt
The log has been saved.
````
