package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sunflower extends SunProducingPlants{

    public Sunflower() {
        super("sunflower", 50 , 10, new ImageView(new Image("file:src/main/resources/Gifs/sunflower.gif")), Power.MEDIUM, 25);
    }
}
