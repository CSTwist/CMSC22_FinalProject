import javafx.scene.image.ImageView;

public abstract class TankSuperClass {
    private static ImageView tankImageView;
    public TankSuperClass(String Image){
        tankImageView = new ImageView(Image);
        tankImageView.setFitWidth(60);
        tankImageView.setPreserveRatio(true);
        tankImageView.setSmooth(true);
        tankImageView.setCache(true);
    }

    public static ImageView getTank(){
        return tankImageView;
    }
}
