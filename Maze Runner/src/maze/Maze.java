package maze;

import java.io.Serializable;
import java.util.Random;

public class Maze implements Serializable {
    private final boolean[][] maze;
    private boolean[][] solution;
    //indices of the rows of the entrance and exit
    private int entrance, exit;
    private final int size;

    public Maze(int size) {
        this.size = size;
        maze = new boolean[size][size];
        generateMaze(size);
    }

    public void generateMaze(int size) {
        int graphWidth = (size + 1) / 2;
        int graphHeight = (size + 1) / 2;
        Graph graph = new Graph(graphWidth * graphHeight);

        Random random = new Random();

        for (int i = 0; i < graphHeight - 1; i++) {
            for (int j = 0; j < graphWidth - 1; j++) {
                graph.setEdgeWeight(i * graphWidth + j, i * graphWidth + j + 1, random.nextInt(5) + 1);
                graph.setEdgeWeight(i * graphWidth + j, (i + 1) * graphWidth + j, random.nextInt(5) + 1);
            }
        }

        graph.setEdgeWeight(graphHeight * graphWidth - 1, graphHeight * graphWidth - 2, random.nextInt(5) + 1);
        graph.setEdgeWeight(graphHeight * graphWidth - 1, graphHeight * graphWidth - graphWidth - 1, random.nextInt(5) + 1);

        Graph mazeGraph = graph.getMinimalSpanningTree();

        //making entrance
        entrance = 1 + 2 * random.nextInt((size - 1) / 2);
        maze[entrance][0] = true;


        for (int i = 0; i < graphHeight - 1; i++) {
            for (int j = 0; j < graphWidth - 1; j++) {
                //the 1 + .. is to avoid top and left border from being passage (except for entrance)
                maze[1 + i * 2][1 + j * 2] = true;

                if (mazeGraph.getEdgeWeight(i * graphWidth + j, i * graphWidth + j + 1) != 0) {
                    maze[i * 2 + 1][j * 2 + 2] = true;
                }
                if(mazeGraph.getEdgeWeight(i * graphWidth + j, (i + 1) * graphWidth + j) != 0){
                    maze[2 * i + 2][j * 2 + 1] = true;
                }
            }
        }

        //wallifying bottom and right borders of the maze
        for (int i = 0; i < size; i++) {
            maze[size - 1][i] = false;
        }

        for (int i = 0; i < size; i++) {
            maze[i][size - 1] = false;
        }

        //making exit
        exit = 1 + 2 * random.nextInt((size - 1) / 2);
        maze[exit][size - 1] = true;


    }

    public boolean[][] getMaze() {
        return maze;
    }

    public void printMaze() {
        for (boolean[] row : maze) {
            for (boolean cell : row) {
                System.out.print(cell ? "  " : "\u2588\u2588");
            }
            System.out.println();
        }
    }

    public void findEscape() {
        solution = new boolean[size][size];
        solveMaze(entrance,0);
        printSolution();
    }

    private boolean solveMaze(int r, int c) {
        if((r == exit) && (c == size - 1)) {
            solution[r][c] = true;
            return true;
        }

        //check if indices are inside the bounds,
        // ,if solution[r][c] is unvisited ,and if there is a passage in the main maze
        if(r>=0 && c>=0 && r<size && c<size && !solution[r][c] && maze[r][c])
        {
            //if safe to visit then visit the cell
            solution[r][c] = true;
            //going down
            if(solveMaze(r+1, c))
                return true;
            //going right
            if(solveMaze(r, c+1))
                return true;
            //going up
            if(solveMaze(r-1, c))
                return true;
            //going left
            if(solveMaze(r, c-1))
                return true;

            //backtracking
            solution[r][c] = false;
            return false;
        }
        return false;
    }

    private void printSolution() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //if node = 1 in solution array then its a part of the solution
                if(solution[i][j]) {
                    System.out.print("//");
                } else {
                    System.out.print(maze[i][j] ? "  " : "\u2588\u2588");
                }
            }
            System.out.println();
        }
    }

}