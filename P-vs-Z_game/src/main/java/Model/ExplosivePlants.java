package Model;

import javafx.scene.image.ImageView;

public class ExplosivePlants extends Plant{

    private Power destructionPower;
    private int destructionRange;

    public ExplosivePlants(String plantName, int plantPrice, int health, ImageView plantView , Power destructionPower, int destructionRange) {
        super(plantName, plantPrice, health, plantView);
        this.destructionPower = destructionPower;
        this.destructionRange = destructionRange;
    }


    public Power getDestructionPower() {
        return destructionPower;
    }

    public void setDestructionPower(Power destructionPower) {
        this.destructionPower = destructionPower;
    }

    public int getDestructionRange() {
        return destructionRange;
    }

    public void setDestructionRange(int destructionRange) {
        this.destructionRange = destructionRange;
    }
}
