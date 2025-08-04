package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Puff_shroom extends WarriorPlants{
    public Puff_shroom() {
        super("puff_shroom", 0, 10, new ImageView(new Image("file:src/main/resources/Gifs/puff_shroom.gif")),  Power.LOW, Power.HIGH, BulletType.SMOKY);
    }
}
