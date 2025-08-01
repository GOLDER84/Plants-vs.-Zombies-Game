package Model;

import javafx.scene.image.ImageView;

public class WarriorPlants extends Plant{

    private Power attackPower;
    private Power speedPower;
    private BulletType bulletType;

    public WarriorPlants(String plantName, int plantPrice, int health, ImageView plantView , Power attackPower, Power speedPower, BulletType bulletType) {
        super(plantName, plantPrice, health, plantView);
        this.attackPower = attackPower;
        this.speedPower = speedPower;
        this.bulletType = bulletType;
    }


    public Power getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Power attackPower) {
        this.attackPower = attackPower;
    }

    public Power getSpeedPower() {
        return speedPower;
    }

    public void setSpeedPower(Power speedPower) {
        this.speedPower = speedPower;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }
}
