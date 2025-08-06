package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.pvsz_game.Stage3Controller;

public class Newspaper_Zombie extends SpecialZombies {
    private boolean hasLostNewspaper = false;
    private Image angryWalkingAnimation;
    private Image angryEatingAnimation;

    public Newspaper_Zombie() {
        super(20, Power.LOW, Power.MEDIUM, new Image("file:src/main/resources/Gifs/newspaperZombie.gif"));
        setEatingAnimation(new Image("file:src/main/resources/Gifs/HeadAttack_news.gif"));
        angryWalkingAnimation = new Image("file:src/main/resources/Gifs/news_angry.gif");
        angryEatingAnimation = new Image("file:src/main/resources/Gifs/news_angry_eat.gif");
    }

    @Override
    public void takeDamage(int amount) {
        int oldHealth = getHealthLevel();
        super.takeDamage(amount);

        if (oldHealth >= 10 && getHealthLevel() < 10 && !hasLostNewspaper && !isDead()) {
            loseNewspaper();
        }
    }

    private void loseNewspaper() {
        hasLostNewspaper = true;

        Image lostNewspaperAnimation = new Image("file:src/main/resources/Gifs/LostNewspaper.gif");
        ImageView zombieView = getZombieView();

        if (zombieView != null) {
            boolean wasEating = isEating();
            Platform.runLater(() -> zombieView.setImage(lostNewspaperAnimation));
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                Platform.runLater(() -> {
                    setAdvanceSpeed(Power.HIGH);
                    if (wasEating) {
                        setEatingAnimation(angryEatingAnimation);
                        zombieView.setImage(angryEatingAnimation);
                    } else {
                        setAnimationFrames(angryWalkingAnimation);
                        zombieView.setImage(angryWalkingAnimation);
                    }
                    Object controller = getMapController();
                    if (controller != null && controller instanceof Stage3Controller) {
                        ((Stage3Controller) controller).updateZombieMovement(this);
                    }
                });
            }).start();
        }
    }

    @Override
    public void setEating(boolean eating) {
        super.setEating(eating);
        if (hasLostNewspaper && !isDead() && getZombieView() != null) {
            Platform.runLater(() -> {
                if (eating) {
                    getZombieView().setImage(angryEatingAnimation);
                } else {
                    getZombieView().setImage(angryWalkingAnimation);
                }
            });
        }
    }
}