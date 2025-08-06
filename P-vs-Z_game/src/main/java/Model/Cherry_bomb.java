package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry_bomb extends ExplosivePlants{
    public Cherry_bomb() {
        super("cherry_bomb", 150, 5, new ImageView(new Image("file:src/main/resources/Gifs/cherry_bomb.gif")), Power.HIGH, 9);
    }
}
