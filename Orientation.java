public class Orientation {
    // Array of directions represented as integer pairs {rowDifference, colDifference}
    private final int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1},  {1, 0},  {1, 1}};

    // Method to calculate orientation from currentCell to nextCell
    public String calculateOrientation(int[] currentCell, int[] nextCell) {
        // Calculate the row and column differences between currentCell and nextCell
        int rowDifference = nextCell[0] - currentCell[0];
        int colDifference = nextCell[1] - currentCell[1];


        for (int i = 0; i < directions.length; i++) {

            if (rowDifference == directions[i][0] && colDifference == directions[i][1]) {

                return getDirectionName(i);
            }
        }

        return "Unknown";
    }

    // Method to get direction name based on direction index
    private String getDirectionName(int directionIndex) {
        // Switch statement to return the direction name based on directionIndex
        if (directionIndex == 0) {
            return "Northwest";
        } else if (directionIndex == 1) {
            return "North";
        } else if (directionIndex == 2) {
            return "Northeast";
        } else if (directionIndex == 3) {
            return "West";
        } else if (directionIndex == 4) {
            return "East";
        } else if (directionIndex == 5) {
            return "Southwest";
        } else if (directionIndex == 6) {
            return "South";
        } else if (directionIndex == 7) {
            return "Southeast";
        }
        return "Unknown";
    }
}