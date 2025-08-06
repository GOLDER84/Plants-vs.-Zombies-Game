package Model;

import javafx.scene.image.Image;

public class Normal_Zombie extends BasicZombies{
    public Normal_Zombie() {
        super(10, Power.LOW, Power.LOW, new Image("file:src/main/resources/Gifs/normal_zombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/ZombieAttack.gif"));
    }
}
