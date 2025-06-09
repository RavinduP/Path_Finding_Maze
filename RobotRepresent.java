public class RobotRepresent {
    // Method to display the grid without the robot's path
    public void displayGridNoPath(int[][] grid, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("+-----");
            }
            System.out.println("+");
            for (int j = 0; j < cols; j++) {
                // Check the value in the grid cell and print corresponding symbol
                if (grid[i][j] == 0) {
                    System.out.print("|     ");
                } else if (grid[i][j] == 1) {
                    System.out.print("| XXXX");
                } else if (grid[i][j] == -1) {
                    System.out.print("|  R  ");
                } else if (grid[i][j] == -2) {
                    System.out.print("|  E  ");
                } else if (grid[i][j] == -3) {
                    System.out.print("|     ");
                } else if (grid[i][j] == -4) {
                    System.out.print("|     ");
                } else if (grid[i][j] == -5) {
                    System.out.print("|     ");
                } else if (grid[i][j] == -6) {
                    System.out.print("|     ");
                }
            }
            System.out.println("|");
        }
        for (int j = 0; j < cols; j++) {
            System.out.print("+-----");
        }
        System.out.println("+");
    }
    // Method to display the grid with the robot's path
    public void displayGridPath(int[][] grid, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("+-----"); //print border
            }
            System.out.println("+");
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    System.out.print("|     ");
                } else if (grid[i][j] == 1) {
                    System.out.print("| XXXX");
                } else if (grid[i][j] == -1) {
                    System.out.print("|  R  ");
                } else if (grid[i][j] == -2) {
                    System.out.print("|  E  ");
                } else if (grid[i][j] == -3) {
                    System.out.print("|  *  ");
                } else if (grid[i][j] == -4) {
                    System.out.print("|  *  ");
                } else if (grid[i][j] == -5) {
                    System.out.print("|  *  ");
                } else if (grid[i][j] == -6) {
                    System.out.print("|  *  ");
                }
            }
            System.out.println("|");
        }
        for (int j = 0; j < cols; j++) {
            System.out.print("+-----"); //print border
        }
        System.out.println("+");
    }
}