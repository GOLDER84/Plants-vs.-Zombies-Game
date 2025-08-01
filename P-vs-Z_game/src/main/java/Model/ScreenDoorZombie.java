package Model;

import javafx.scene.image.Image;

public class ScreenDoorZombie extends ResistantZombies{
    public ScreenDoorZombie() {
        super(10, Power.LOW, Power.MEDIUM, new Image("file:src/main/resources/Gifs/Walk_news.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/Attack_news.gif"));

    }
}
