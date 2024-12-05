import javafx.scene.image.ImageView;

public class Player2Tank extends TankSuperClass{
    private static ImageView tankImageView;
    private Player2Tank(){
        super("resources/CyanTank.png");
        tankImageView = getTank();
    }

    public static ImageView tankInstantiator(){
        new Player2Tank();
        return tankImageView;
    }
}