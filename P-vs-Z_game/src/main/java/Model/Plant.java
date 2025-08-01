// src/main/java/Model/Plant.java
package Model;

import javafx.scene.image.ImageView;

public class Plant {
    private String plantName;
    private int plantPrice;
    private ImageView plantView;
    private int health;
    private boolean alive;

    public Plant(String plantName, int plantPrice, int health, ImageView plantView) {
        this.plantName = plantName;
        this.plantPrice = plantPrice;
        this.health = health;
        this.plantView = plantView;
        this.alive = true;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getPlantPrice() {
        return plantPrice;
    }

    public void setPlantPrice(int plantPrice) {
        this.plantPrice = plantPrice;
    }

    public ImageView getPlantView() {
        return plantView;
    }

    public void setPlantView(ImageView plantView) {
        this.plantView = plantView;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}