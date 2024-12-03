import java.awt.*;
import javafx.scene.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
 import javafx.stage.Stage; 

public class Player2Tank{
    private static ImageView tankImageView;
    private Player2Tank(){
        tankImageView = new ImageView("resources/CyanTank.png");
        tankImageView.setFitWidth(60);
        tankImageView.setPreserveRatio(true);
        tankImageView.setSmooth(true);
        tankImageView.setCache(true);
    }

    public static ImageView tankInstantiator(){
        new Player2Tank();
        return tankImageView;
    }
}