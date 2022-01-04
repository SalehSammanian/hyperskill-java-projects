# Maze Runner

This program prompts the user for a size of the maze then generates a random maze of that size using a graph and prim's algorithm to find the Minimum Spanning Tree.
The user then can find the path in the maze using Depth First Search, display the maze, save the maze (serialized) or load a file containing a serialized maze.

## Example: 
````
=== Menu ===
1. Generate a new maze
2. Load a maze
0. Exit
>1
Enter the size of a new maze
>17
██████  ██████████████████████████
██████  ██████████████████████████
██  ██                          ██
██  ██████████████  ██████████████
██  ██              ██  ██  ██  ██
██  ██████████████  ██  ██  ██  ██
██      ██                      ██
██████  ██████████  ██  ██████████
██      ██          ██  ██      ██
██████  ██████████  ██████  ██████
██              ██              ██
██  ██  ██  ██  ██  ██████████████
██  ██  ██  ██              ██  ██
██  ██████████  ██  ██  ██████  ██
██  ██          ██  ██          ██
████████  ████████████████████████
████████  ████████████████████████

=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
0. Exit
>3
>maze.txt

=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
0. Exit
>0
Bye!
````
