import com.almasb.fxgl.app.scene.FXGLDefaultMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

public class AZTANKMainMenu extends FXGLDefaultMenu {

    private VBox buttonContainer;

    public AZTANKMainMenu() {
        super(MenuType.MAIN_MENU);
        showMainMenu();
    }

    private void showPlayerOptions() {
        // Clear existing buttons
        buttonContainer.getChildren().clear();
    
        // Create Player Options
        Button twoPlayersButton = new Button("2 Players");
        twoPlayersButton.setFont(Font.font(24));
        twoPlayersButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffaa00; -fx-text-fill: white;");
        twoPlayersButton.setOnAction(e -> startGameWithPlayers(2));
        twoPlayersButton.setTranslateX(FXGL.getAppWidth() / 2 - 60);
        twoPlayersButton.setTranslateY(FXGL.getAppHeight() / 2 -30);
    
        Button threePlayersButton = new Button("3 Players");
        threePlayersButton.setFont(Font.font(24));
        threePlayersButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffaa00; -fx-text-fill: white;");
        threePlayersButton.setOnAction(e -> startGameWithPlayers(3));
        threePlayersButton.setTranslateX(FXGL.getAppWidth() / 2 - 60);
        threePlayersButton.setTranslateY(FXGL.getAppHeight() / 2 + 70);
    
        Button backButton = new Button("Back");
        backButton.setFont(Font.font(24));
        backButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #888888; -fx-text-fill: white;");
        backButton.setOnAction(e -> showMainMenu());
        backButton.setTranslateX(FXGL.getAppWidth() / 2 - 40);
        backButton.setTranslateY(FXGL.getAppHeight() / 2 + 180);
    
        // Add buttons and back button container to the root
        getContentRoot().getChildren().addAll(twoPlayersButton, threePlayersButton, backButton);
    }

    public void showMainMenu() {
        // Clear player options and return to main menu
        if (this.buttonContainer != null) buttonContainer.getChildren().clear();

        // Load and set background image
        ImageView background = new ImageView(new Image(getClass().getResource("/resources/AZTank.png").toExternalForm()));
        background.setFitWidth(FXGL.getAppWidth());
        background.setFitHeight(FXGL.getAppHeight());

        // Create Play button
        Button playButton = new Button("Play");
        playButton.setFont(Font.font(24)); 
        playButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #00aaff; -fx-text-fill: white;");
        playButton.setOnAction(e -> showPlayerOptions());

        // Create Exit button
        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font(24)); 
        exitButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ff4444; -fx-text-fill: white;");
        exitButton.setOnAction(e -> FXGL.getGameController().exit());

        // Layout container for buttons
        buttonContainer = new VBox(40); 
        buttonContainer.getChildren().addAll(playButton, exitButton);
        buttonContainer.setTranslateX(FXGL.getAppWidth() / 2 - 40);
        buttonContainer.setTranslateY(FXGL.getAppHeight() / 2 - 30);

        // Add components to content root
        getContentRoot().getChildren().addAll(background, buttonContainer);
    }

    private void startGameWithPlayers(int numPlayers) {
        // Logic to start the game with the chosen number of players
        System.out.println("Starting game with " + numPlayers + " players...");
        fireNewGame();
    }
}
