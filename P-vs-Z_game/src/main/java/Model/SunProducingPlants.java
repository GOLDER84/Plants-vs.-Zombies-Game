package Model;

import javafx.scene.image.ImageView;

public class SunProducingPlants extends Plant{
    private Power speedProducing;
    private int amount;

    public SunProducingPlants(String plantName, int plantPrice, int health, ImageView plantView , Power speedProducing, int amount) {
        super(plantName, plantPrice, health, plantView);
        this.speedProducing = speedProducing;
        this.amount = amount;
    }


    public Power getSpeedProducing() {
        return speedProducing;
    }

    public void setSpeedProducing(Power speedProducing) {
        this.speedProducing = speedProducing;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
