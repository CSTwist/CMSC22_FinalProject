import javafx.scene.image.ImageView;

public class Player1Tank extends TankSuperClass{
    private static ImageView tankImageView;
    private Player1Tank(){
        super("resources/GreenTank.png");
        tankImageView = getTank();
    }
    
    public static ImageView tankInstantiator(){
        new Player1Tank();
        return tankImageView;
    }
}