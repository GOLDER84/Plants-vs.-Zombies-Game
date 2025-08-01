package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Peashooter extends WarriorPlants{
    public Peashooter() {
        super("Peashooter", 100, 10, new ImageView(new Image("file:src/main/resources/Gifs/peashooter.gif")), Power.MEDIUM, Power.MEDIUM, BulletType.NORMAL);
    }

//    public Peashooter() {
//        super("Peashooter", 100, new ImageView(new Image("file:src/main/resources/Gifs/peashooter.gif")), Power.MEDIUM, Power.MEDIUM, BulletType.NORMAL);
//    }
}
