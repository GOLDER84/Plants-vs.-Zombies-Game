package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ice_shroom extends ExplosivePlants{
    public Ice_shroom() {
        super("ice_shroom", 75, 10, new ImageView(new Image("file:src/main/resources/Gifs/ice_shroom.gif")), Power.LOW, 45);
    }
}
