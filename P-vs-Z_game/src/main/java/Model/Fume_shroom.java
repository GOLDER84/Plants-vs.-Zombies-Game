package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fume_shroom extends WarriorPlants{

    public Fume_shroom() {
        super("fume_shroom", 75 , 10, new ImageView(new Image("file:src/main/resources/Gifs/fume_shroom.gif")), Power.HIGH, Power.MEDIUM, BulletType.SMOKY);
    }
}
