package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Snow_pea extends WarriorPlants{

    public Snow_pea() {
        super("snow_pea", 175 , 10, new ImageView(new Image("file:src/main/resources/Gifs/snow_pea.gif")), Power.MEDIUM, Power.LOW, BulletType.SNOWY);
    }
}
