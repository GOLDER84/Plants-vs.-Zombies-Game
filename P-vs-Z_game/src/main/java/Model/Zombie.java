package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.pvsz_game.*;

public class Zombie {
    private int healthLevel;
    private Power advanceSpeed;
    private Power damagePower;
    private Image animationFrames;
    private ImageView zombieView;
    private boolean isEating = false;
    private Image eatingAnimation;
    private boolean alive = true;
    private Stage1Controller stage1Controller;
    private Stage2Controller stage2Controller;
    private Stage3Controller stage3Controller;
    private Stage4Controller stage4Controller;
    private Stage5Controller stage5Controller;
    private Stage6Controller stage6Controller;
    public void setMapController(Stage1Controller stage1Controller) {
        this.stage1Controller = stage1Controller;
    }
    public void setMapController(Stage2Controller stage2Controller) {
        this.stage2Controller = stage2Controller;
    }
    public void setMapController(Stage3Controller stage3Controller) {
        this.stage3Controller = stage3Controller;
    }
    public void setMapController(Stage4Controller stage4Controller) {
        this.stage4Controller = stage4Controller;
    }
    public void setMapController(Stage5Controller stage5Controller) {
        this.stage5Controller = stage5Controller;
    }
    public void setMapController(Stage6Controller stage6Controller) {
        this.stage6Controller = stage6Controller;
    }

    // Add this method to the Zombie class
    public Object getMapController() {
        if (stage1Controller != null) {
            return stage1Controller;
        } else if (stage2Controller != null) {
            return stage2Controller;
        } else if (stage3Controller != null) {
            return stage3Controller;
        } else if (stage4Controller != null) {
            return stage4Controller;
        } else if (stage5Controller != null) {
            return stage5Controller;
        } else if (stage6Controller != null) {
            return stage6Controller;
        }
        return null;
    }


    public Zombie(int healthLevel, Power advanceSpeed, Power damagePower , Image animationFrames) {
        this.healthLevel = healthLevel;
        this.advanceSpeed = advanceSpeed;
        this.damagePower = damagePower;
        this.animationFrames = animationFrames;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    public Power getAdvanceSpeed() {
        return advanceSpeed;
    }

    public void setAdvanceSpeed(Power advanceSpeed) {
        this.advanceSpeed = advanceSpeed;
    }

    public Power getDamagePower() {
        return damagePower;
    }

    public void setDamagePower(Power damagePower) {
        this.damagePower = damagePower;
    }

    public Image getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(Image animationFrames) {
        this.animationFrames = animationFrames;
    }

    public ImageView getZombieView() {
        return zombieView;
    }

    public void setZombieView(ImageView zombieView) {
        this.zombieView = zombieView;
    }

    public void takeDamage(int amount) {
        if (!alive) return;

        healthLevel -= amount;
        if (healthLevel <= 0) {
            alive = false;
            zombieView.setImage(new Image("file:src/main/resources/Gifs/Head.gif"));
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    if (zombieView != null && zombieView.getParent() instanceof AnchorPane parent) {
                        parent.getChildren().remove(zombieView);
                    }
                    if (stage1Controller != null) {
                        stage1Controller.handleZombieDeath(this);
                    } else if (stage2Controller != null) {
                        stage2Controller.handleZombieDeath(this);
                    } else if (stage3Controller != null) {
                        stage3Controller.handleZombieDeath(this);
                    } else if (stage4Controller != null) {
                        stage4Controller.handleZombieDeath(this);
                    } else if (stage5Controller != null) {
                        stage5Controller.handleZombieDeath(this);
                    } else if (stage6Controller != null) {
                        stage6Controller.handleZombieDeath(this);
                    }
                });
            }).start();

        }
    }

    public void explodeDeath() {
        if (!alive) return;
        alive = false;
        zombieView.setImage(new Image("file:src/main/resources/Gifs/BoomDie.gif"));
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Duration of the BoomDie.gif
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                if (zombieView != null && zombieView.getParent() instanceof AnchorPane parent) {
                    parent.getChildren().remove(zombieView);
                }
                if (stage1Controller != null) {
                    stage1Controller.handleZombieDeath(this);
                } else if (stage2Controller != null) {
                    stage2Controller.handleZombieDeath(this);
                } else if (stage3Controller != null) {
                    stage3Controller.handleZombieDeath(this);
                } else if (stage4Controller != null) {
                    stage4Controller.handleZombieDeath(this);
                } else if (stage5Controller != null) {
                    stage5Controller.handleZombieDeath(this);
                } else if (stage6Controller != null) {
                    stage6Controller.handleZombieDeath(this);
                }
            });
        }).start();
    }

    public boolean isDead() {
        return !alive;
    }

    public void setEatingAnimation(Image eatingAnimation) {
        this.eatingAnimation = eatingAnimation;
    }

    public Image getEatingAnimation() {
        return eatingAnimation;
    }

    public boolean isEating() {
        return isEating;
    }

    public void setEating(boolean eating) {
        isEating = eating;
    }
}