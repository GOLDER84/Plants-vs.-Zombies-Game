package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sun_shroom extends SunProducingPlants{

    public Sun_shroom() {
        super("sun_shroom", 25 , 10, new ImageView(new Image("file:src/main/resources/Gifs/sun_shroom.gif")), Power.LOW, 15);
    }
}
