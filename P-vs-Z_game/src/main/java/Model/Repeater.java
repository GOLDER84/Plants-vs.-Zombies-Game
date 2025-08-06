package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Repeater extends WarriorPlants{
    public Repeater() {
        super("repeater", 200, 10, new ImageView(new Image("file:src/main/resources/Gifs/repeater.gif")), Power.HIGH, Power.HIGH, BulletType.NORMAL);
    }
}
