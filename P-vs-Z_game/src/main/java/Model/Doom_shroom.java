package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Doom_shroom extends ExplosivePlants{
    public Doom_shroom() {
        super("doom_shroom", 125, 5, new ImageView(new Image("file:src/main/resources/Gifs/doom_shroom.gif")), Power.HIGH, 45);
    }
//    public Doom_shroom() {
//        super("doom_shroom", 125, new ImageView(new Image("file:src/main/resources/Gifs/doom_shroom.gif")), Power.HIGH, 45);
//    }
}
