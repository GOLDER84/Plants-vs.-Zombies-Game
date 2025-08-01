package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Conehead_Zombie extends ResistantZombies{
    public Conehead_Zombie() {
        super(5, Power.LOW, Power.LOW, new Image("file:src/main/resources/Gifs/conehead_zombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/ConeheadZombieAttack.gif"));
    }
}
