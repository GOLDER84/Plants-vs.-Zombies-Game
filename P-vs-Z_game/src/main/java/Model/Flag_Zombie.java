package Model;

import javafx.scene.image.Image;

public class Flag_Zombie extends SpecialZombies{


    public Flag_Zombie() {
        super(10 , Power.MEDIUM , Power.LOW , new Image("file:src/main/resources/Gifs/flag_zombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/FlagZombieAttack.gif"));
    }
}
