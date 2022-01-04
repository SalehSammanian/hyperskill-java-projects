# Simple Search Engine
This program allows the user to pass it a .txt file that contains names and emails associated with those names, afterwards the user is given
a choice to print all people in the file or find a paricular person using a query. Then the user is prompted to choose a searching strategy .

### Searching strategies:
- ALL: prints people with info matching ALL keywords from the query.
- ANY: prints any person with atleast one keyword from the query.
- NONE: prints all people EXCEPT those that have one of the query words in their info.

This project utilizes the template method design pattern and the inverted index data structure.

Argument for passing file name:
>-file fileName

## Example:
````
=== Menu ===
1. Find a person
2. Print all people
0. Exit
> 2

=== List of people ===
Stephany War Galley
Feromard Marburies fernando_xxarbury@test.com
Kristyn Nix-Mixer nix-kris@test.com
Regenia Enderpearl
...
...
...
Kaaycee Graay
Victorinaa Froehlich victory@test.com
Roseaanne Graay
Ericaa Raadford hisaam@test.com
Elyse Paaulinger
Maalenaa Schwommer test1
Maalenaa Schozmmer test2
Maalenaa Schommer test3
Maalenaa Schommxers test5

=== Menu ===
1. Find a person
2. Print all people
0. Exit
> 1

Select a matching strategy: ALL, ANY, NONE
> ALL

Enter a name or email to search all suitable people.
> Maalenaa

Found 12 people:
Maalenaa Schommert
Maalenaa Schwommer test1
Maalenaa Schozmmer test2
Maalenaa Graaey
Maalenaa Schommer test3
Maalenaa Schommers test5
Maalenaa Schommers test1
Maalenaa Schommesr test2
Maalenaa Graaey
Maalenaa Schwommer test3
Maalenaa Schommxers test5
Maalenaa Schomcmer
````
