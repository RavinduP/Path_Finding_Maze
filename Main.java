import java.util.Scanner;
import java.util.Random;

// Node class for linked list
class Node {
    int[] data;
    Node next;

    // Constructor to initialize node with data
    public Node(int[] data) {
        this.data = data;
        this.next = null;
    }
}

// Queue class for FIFO operations
class Queue {
    Node head;

    // Constructor to initialize an empty queue
    public Queue() {
        this.head = null;
    }

    // Method to add an element to the queue
    public void enqueue(int[] data) {
        Node newNode = new Node(data); // Create a new node with data
        if (head == null) {
            head = newNode; // If queue is empty, set head to the new node
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next; // Traverse to the end of the queue
        }
        current.next = newNode; // Add new node at the end
    }

    // Method to remove and return the element at the front of the queue
    public int[] dequeue() {
        if (head == null) {
            System.out.println("List is empty"); // If queue is empty, print message
            return null;
        }
        int[] data = head.data; // Retrieve data from head node
        head = head.next;
        return data;
    }

    // Method to check if the queue is empty
    public boolean isEmpty() {
        return head == null;
    }
}

// Main class representing the grid and path planning
public class Main {
    private final int[][] grid; // 2D array to represent the grid
    private final int rows;
    private final int cols;
    private static int startRow;
    private static int startCol;
    private static int endRow;
    private static int endCol;
    private final Queue queue; // Queue for path finding
    private static String startOrientation;
    private static String endOrientation;
    private final int[] nextToStartCell;
    private final int[] previousToEndCell;

    private static CoordinateList shortestPathCoordinates; //Custom data structure to store coordinates of cells in shortest path

    // Constructor to initialize the grid
    public Main(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        initializeGrid();
        markStartAndEndPoints();
        addObstacles(); // Add obstacles randomly
        nextToStartCell = new int[2];
        previousToEndCell = new int[2];
        this.queue = new Queue();
        shortestPathCoordinates = new CoordinateList(); // Initialize data structure for storing shortest path
    }

    private void enqueue(int[] cell) {
        queue.enqueue(cell);
    }

    private int[] dequeue() {
        return queue.dequeue();
    }

    private boolean isEmpty() {
        return queue.isEmpty();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0; // Mark cell as empty
            }
        }
    }

    // Method to mark start and end points on the grid
    private void markStartAndEndPoints() {
        Scanner scanner = new Scanner(System.in);
        // Input for starting cell
        while (true) {
            System.out.println("\nEnter starting cell coordinates (row, column): ");
            System.out.print("\nRow: ");
            startRow = scanner.nextInt();
            System.out.print("Column: ");
            startCol = scanner.nextInt();

            // Validate starting cell coordinates
            if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols) {
                System.out.println("\nStarting cell coordinates are outside the grid size. " +
                        "\nPlease enter valid coordinates.");
                continue;
            }
            // Input for ending cell
            System.out.println("\nEnter ending cell coordinates (row, column): ");
            System.out.print("\nRow: ");
            endRow = scanner.nextInt();
            System.out.print("Column: ");
            endCol = scanner.nextInt();
            // Validate ending cell coordinates
            if (endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
                System.out.println("\nEnding cell coordinates are outside the grid size. Please enter valid coordinates.");
                continue;
            }
            // Check if starting cell coordinates are the same as end cell coordinates
            if (startRow == endRow && startCol == endCol) {
                System.out.println("\nStarting cell coordinates cannot be the same as end cell coordinates." +
                        "\n Please enter different coordinates.");
            } else {
                grid[startRow][startCol] = -1;
                grid[endRow][endCol] = -2;
                break;
            }
        }
        scanner.close();
    }

    // Method to add obstacles randomly
    private void addObstacles() {
        Random random = new Random();
        int obstacleCount = (int) (0.2 * (rows * cols)); // 20% of total cells as obstacles
        int placedObstacles = 0;

        // Place obstacles randomly, ensuring starting and ending cells are not covered
        while (placedObstacles < obstacleCount) {
            int randomRow = random.nextInt(rows);
            int randomCol = random.nextInt(cols);
            if (grid[randomRow][randomCol] == 0 && (randomRow != startRow || randomCol != startCol)
                    && (randomRow != endRow || randomCol != endCol)) {
                grid[randomRow][randomCol] = 1;
                placedObstacles++;
            }
        }
    }

    // Method to find the shortest path using BFS
    public void findShortestPath() {
        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2]; // 3D array to store parent coordinates

        // Add starting cell to the custom queue
        enqueue(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        // Run BFS
        while (!isEmpty()) {
            int[] current = dequeue();
            int row = current[0];
            int col = current[1];

            if (row == endRow && col == endCol) {
                // Found the end cell, backtrack to find the shortest path
                int currentRow = row;
                int currentCol = col;
                int prevRow = currentRow;
                int prevCol = currentCol;
                while (currentRow != startRow || currentCol != startCol) {
                    int[] prev = parent[currentRow][currentCol];
                    grid[currentRow][currentCol] = -3; // Mark as part of the path
                    prevRow = currentRow;
                    prevCol = currentCol;
                    currentRow = prev[0];
                    currentCol = prev[1];
                    shortestPathCoordinates.add(currentRow, currentCol);
                }
                nextToStartCell[0] = prevRow;
                nextToStartCell[1] = prevCol;

                previousToEndCell[0] = parent[endRow][endCol][0];
                previousToEndCell[1] = parent[endRow][endCol][1];

                // Calculate start orientation
                startOrientation = new Orientation().calculateOrientation(new int[]{startRow, startCol}, nextToStartCell);

                // Calculate end orientation
                endOrientation = new Orientation().calculateOrientation(previousToEndCell, new int[]{endRow, endCol});

                break;
            }

            // Explore neighbors
            int[][] neighbors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}; // Diagonal and cardinal directions
            for (int[] neighbor : neighbors) {
                int newRow = row + neighbor[0];
                int newCol = col + neighbor[1];
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        grid[newRow][newCol] != 1 && !visited[newRow][newCol]) {
                    enqueue(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = new int[]{row, col}; // Assign parent coordinates
                }
            }
        }
        grid[endRow][endCol] = -2; // End point
    }

    // Inner class representing a list of coordinates
    static class CoordinateList {
        private int[][] coordinates; // Array to store coordinates
        private int size;
        private static final int INITIAL_CAPACITY = 10;


        public CoordinateList() {
            this.coordinates = new int[INITIAL_CAPACITY][2];
            this.size = 0;
        }

        // Method to ensure capacity of the array
        private void ensureCapacity() {
            if (size >= coordinates.length) {
                int newCapacity = coordinates.length * 2;
                int[][] newCoordinates = new int[newCapacity][2];
                System.arraycopy(coordinates, 0, newCoordinates, 0, size);
                coordinates = newCoordinates;
            }
        }

        // Method to add coordinates to the list
        public void add(int row, int col) {
            ensureCapacity();
            coordinates[size][0] = row;
            coordinates[size][1] = col;
            size++;
        }

        // Method to print coordinates of the shortest path
        public void printCoordinates() {
            System.out.println("\nShortest Path Coordinates:");
            for (int i = size - 1; i >= 0; i--) {
                System.out.printf("(%d,%d) ", coordinates[i][0], coordinates[i][1]);
                System.out.print(" >> ");
            }
            System.out.println();
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows, cols;

        while (true) {
            try {
                System.out.print("\nEnter number of rows (must be greater than 4): ");
                rows = Integer.parseInt(scanner.nextLine());
                if (rows <= 4) {
                    throw new IllegalArgumentException("\nRows must be greater than 4.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Enter number of columns (must be greater than 4): ");
                cols = Integer.parseInt(scanner.nextLine());
                if (cols <= 4) {
                    throw new IllegalArgumentException("\nColumns must be greater than 4.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        Main grid = new Main(rows, cols); // Create grid object
        grid.findShortestPath(); // Find the shortest path
        System.out.println("\nGrid with obstacles");
        new RobotRepresent().displayGridNoPath(grid.grid, rows, cols); // Display grid with obstacles

        shortestPathCoordinates.printCoordinates(); // Print coordinates of the shortest path

        System.out.println("\nGrid with robot path");
        new RobotRepresent().displayGridPath(grid.grid, rows, cols); // Display grid with robot path

        System.out.println("\n---Starting Robot State---");
        System.out.printf("\tStarting robot position: (%d,%d)\n", startRow, startCol);
        System.out.printf("\tStarting robot orientation: %s\n", startOrientation);

        System.out.println("\n---Ending Robot State---");
        System.out.printf("\tEnding robot position: (%d,%d)\n", endRow, endCol);
        System.out.printf("\tEnding robot orientation: %s\n", endOrientation);
        scanner.close();
    }
}