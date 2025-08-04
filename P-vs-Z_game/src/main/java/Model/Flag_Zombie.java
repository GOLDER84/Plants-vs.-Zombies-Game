package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag_Zombie extends SpecialZombies{


    public Flag_Zombie() {
        super(10 , Power.MEDIUM , Power.LOW , new Image("file:src/main/resources/Gifs/flag_zombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/FlagZombieAttack.gif"));
    }

    //    public Flag_Zombie(int healthLevel, Power advanceSpeed, Power damagePower, Image zombieView) {
//        super(healthLevel, advanceSpeed, damagePower, zombieView);
//    }
}
