package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScreenDoorZombie extends ResistantZombies {
    private boolean hasLostScreenDoor = false;
    private Image normalWalkingAnimation;
    private Image normalEatingAnimation;
    private boolean hitByFumeShroom = false;

    public ScreenDoorZombie() {
        super(20, Power.LOW, Power.MEDIUM, new Image("file:src/main/resources/Gifs/Walk_news.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/Attack_news.gif"));
        normalWalkingAnimation = new Image("file:src/main/resources/Gifs/normal_zombie.gif");
        normalEatingAnimation = new Image("file:src/main/resources/Gifs/ZombieAttack.gif");
    }

    @Override
    public void takeDamage(int amount) {
        int oldHealth = getHealthLevel();
        super.takeDamage(amount);

        if (oldHealth >= 10 && getHealthLevel() < 10 && !hasLostScreenDoor && !isDead() && !hitByFumeShroom) {
            loseScreenDoor();
        }
    }

    private void loseScreenDoor() {
        hasLostScreenDoor = true;
        ImageView zombieView = getZombieView();

        if (zombieView != null) {
            boolean wasEating = isEating();
            Platform.runLater(() -> {
                if (wasEating) {
                    setEatingAnimation(normalEatingAnimation);
                    zombieView.setImage(normalEatingAnimation);
                } else {
                    setAnimationFrames(normalWalkingAnimation);
                    zombieView.setImage(normalWalkingAnimation);
                }
            });
        }
    }

    @Override
    public void setEating(boolean eating) {
        super.setEating(eating);
        if (hasLostScreenDoor && !isDead() && !hitByFumeShroom && getZombieView() != null) {
            Platform.runLater(() -> {
                if (eating) {
                    getZombieView().setImage(normalEatingAnimation);
                } else {
                    getZombieView().setImage(normalWalkingAnimation);
                }
            });
        }
    }
    public void markHitByFumeShroom() {
        this.hitByFumeShroom = true;
    }
}