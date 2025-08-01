package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Newspaper_Zombie extends SpecialZombies{
    public Newspaper_Zombie() {
        super(20, Power.MEDIUM, Power.MEDIUM, new Image("file:src/main/resources/Gifs/newspaperZombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/HeadAttack_news.gif"));
    }

//    public Newspaper_Zombie(int healthLevel, Power advanceSpeed, Power damagePower, ImageView zombieView) {
//        super(healthLevel, advanceSpeed, damagePower, zombieView);
//    }
}
