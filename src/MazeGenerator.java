import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGenerator extends Application {
    private static final int CELL_SIZE = 20; // Size of each maze cell
    private static final int GRID_WIDTH = 25; // Width of the grid (number of cells)
    private static final int GRID_HEIGHT = 25; // Height of the grid (number of cells)
    private final Cell[][] grid = new Cell[GRID_WIDTH][GRID_HEIGHT];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(CELL_SIZE * GRID_WIDTH, CELL_SIZE * GRID_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        initializeGrid();
        generateMaze(0, 0);
        drawMaze(gc);

        Scene scene = new Scene(new javafx.scene.layout.StackPane(canvas));
        stage.setScene(scene);
        stage.setTitle("Maze Generator");
        stage.show();
    }

    private void initializeGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    private void generateMaze(int x, int y) {
        grid[x][y].visited = true;

        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, -1}); // Up
        directions.add(new int[]{0, 1});  // Down
        directions.add(new int[]{-1, 0}); // Left
        directions.add(new int[]{1, 0});  // Right
        Collections.shuffle(directions); // Randomize directions

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (isInBounds(nx, ny) && !grid[nx][ny].visited) {
                grid[x][y].removeWall(dir[0], dir[1]);
                grid[nx][ny].removeWall(-dir[0], -dir[1]);
                generateMaze(nx, ny);
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < GRID_WIDTH && y < GRID_HEIGHT;
    }

    private void drawMaze(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CELL_SIZE * GRID_WIDTH, CELL_SIZE * GRID_HEIGHT);

        gc.setStroke(Color.BLACK);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                grid[x][y].draw(gc);
            }
        }
    }

    class Cell {
        final int x, y;
        boolean[] walls = {true, true, true, true}; // {Top, Bottom, Left, Right}
        boolean visited = false;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void removeWall(int dx, int dy) {
            if (dx == 0 && dy == -1) walls[0] = false; // Top
            if (dx == 0 && dy == 1) walls[1] = false;  // Bottom
            if (dx == -1 && dy == 0) walls[2] = false; // Left
            if (dx == 1 && dy == 0) walls[3] = false;  // Right
        }

        void draw(GraphicsContext gc) {
            double x1 = x * CELL_SIZE;
            double y1 = y * CELL_SIZE;

            if (walls[0]) gc.strokeLine(x1, y1, x1 + CELL_SIZE, y1); // Top
            if (walls[1]) gc.strokeLine(x1, y1 + CELL_SIZE, x1 + CELL_SIZE, y1 + CELL_SIZE); // Bottom
            if (walls[2]) gc.strokeLine(x1, y1, x1, y1 + CELL_SIZE); // Left
            if (walls[3]) gc.strokeLine(x1 + CELL_SIZE, y1, x1 + CELL_SIZE, y1 + CELL_SIZE); // Right
        }
    }
}