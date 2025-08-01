package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Scaredy_shroom extends WarriorPlants{
    public Scaredy_shroom() {
        super("scaredy_shroom", 125, 10, new ImageView(new Image("file:src/main/resources/Gifs/scaredy_shroom.gif")), Power.HIGH, Power.HIGH, BulletType.SMOKY);
    }

//    public Scaredy_shroom() {
//        super("scaredy_shroom", 125, new ImageView(new Image("file:src/main/resources/Gifs/scaredy_shroom.gif")), Power.HIGH, Power.HIGH, BulletType.SMOKY);
//    }
}
