package org.example.pvsz_game;

import Controller.PlayerController;
import Model.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Stage5Controller {
    @FXML private ImageView a11, a12, a13, a14, a15, a16, a17, a18, a19;
    @FXML private ImageView a21, a22, a23, a24, a25, a26, a27, a28, a29;
    @FXML private ImageView a31, a32, a33, a34, a35, a36, a37, a38, a39;
    @FXML private ImageView a41, a42, a43, a44, a45, a46, a47, a48, a49;
    @FXML private ImageView a51, a52, a53, a54, a55, a56, a57, a58, a59;
    @FXML private ImageView p1 , p11 , p2 , p21 , p3 , p31 , p4 , p41;
    @FXML private Label sunCount;
    @FXML private ProgressBar zombieWaveBar;
    @FXML private ImageView waveHead;
    @FXML private AnchorPane gameLayer;
    private Stage stage;
    //254, 299

    private String selectedPlant = null;
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Plant> activePlants = new ArrayList<>();
    private final Map<ImageView, Plant> occupiedCells = new HashMap<>();
    private final Map<Plant, Integer> sunShroomProductionCount = new HashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private PlayerController playerController = PlayerController.getInstance();
    private boolean gameIsOver = false;
    private long lastPuffShroomPlantTime = 0;

    private int sunAmount = 50;

    private final Map<String, ImageView[]> plantSelectors = new HashMap<>();

    public void initialize() {
        HelloApplication.pauseMusic();
        String path = "src/main/resources/musics/gaming.wav";
        Media media = new Media(new File(path).toURI().toString());
        HelloApplication.mediaPlayer = new MediaPlayer(media);
        HelloApplication.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        HelloApplication.mediaPlayer.setVolume(0.3);
        HelloApplication.playMusic();
        setupPlantSelection();
        setupCellPlacement();
        updateSunCount();
        startZombieWaves();
        setupWaveHead();
    }

    private void setupWaveHead() {
        double barStartX = zombieWaveBar.getLayoutX();
        double barWidth = zombieWaveBar.getPrefWidth();
        double headWidth = waveHead.getFitWidth();

        zombieWaveBar.progressProperty().addListener((obs, oldValue, newValue) -> {
            double newX = (barStartX + barWidth) - (newValue.doubleValue() * barWidth) - (headWidth / 2);
            waveHead.setLayoutX(newX);
        });
        waveHead.setLayoutX((barStartX + barWidth) - (zombieWaveBar.getProgress() * barWidth) - (headWidth / 2));
    }

    private void setupPlantSelection() {
        plantSelectors.put("Sun_shroom", new ImageView[]{p1, p11});
        plantSelectors.put("Puff_shroom", new ImageView[]{p2, p21});
        plantSelectors.put("Fume_shroom", new ImageView[]{p3, p31});
        plantSelectors.put("Scaredy_shroom", new ImageView[]{p4, p41});

        for (String plantName : plantSelectors.keySet()) {
            ImageView normal = plantSelectors.get(plantName)[0];
            normal.setOnMouseClicked(event -> selectPlant(plantName));
        }
    }

    private void selectPlant(String plantName) {
        if (selectedPlant != null && !selectedPlant.equals(plantName)) {
            ImageView[] selectors = plantSelectors.get(selectedPlant);
            selectors[0].setOpacity(1);
            selectors[1].setOpacity(0);
            selectors[1].setDisable(true);
        }

        ImageView[] currentSelectors = plantSelectors.get(plantName);
        boolean isCurrentlySelected = currentSelectors[1].getOpacity() == 1;

        if (isCurrentlySelected) {
            currentSelectors[0].setOpacity(1);
            currentSelectors[1].setOpacity(0);
            currentSelectors[1].setDisable(true);
            selectedPlant = null;
        } else {
            currentSelectors[0].setOpacity(0);
            currentSelectors[1].setOpacity(1);
            currentSelectors[1].setDisable(false);
            selectedPlant = plantName;
        }
    }

    private void setupCellPlacement() {
        List<ImageView> cells = List.of(
                a11, a12, a13, a14, a15, a16, a17, a18, a19,
                a21, a22, a23, a24, a25, a26, a27, a28, a29,
                a31, a32, a33, a34, a35, a36, a37, a38, a39,
                a41, a42, a43, a44, a45, a46, a47, a48, a49,
                a51, a52, a53, a54, a55, a56, a57, a58, a59
        );

        for (ImageView cell : cells) {
            cell.setOnMouseClicked(event -> placePlant(cell));
        }

        gameLayer.setOnMouseClicked(event -> {
            if (event.getTarget() == gameLayer && selectedPlant != null) {
                ImageView[] selectors = plantSelectors.get(selectedPlant);
                selectors[0].setOpacity(1);
                selectors[1].setOpacity(0);
                selectors[1].setDisable(true);
                selectedPlant = null;
            }
        });
    }

    private void placePlant(ImageView cell) {
        if (selectedPlant == null || occupiedCells.containsKey(cell)) return;

        if ("Puff_shroom".equals(selectedPlant)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPuffShroomPlantTime < 15000) {
                System.out.println("Puff_shroom is on cooldown.");
                return;
            }
        }

        int cost = switch (selectedPlant) {
            case "Sun_shroom" -> 25;
            case "Puff_shroom" -> 0;
            case "Fume_shroom" -> 75;
            case "Scaredy_shroom" -> 25;
            default -> 0;
        };

        if (sunAmount < cost) return;

        Plant plant = switch (selectedPlant) {
            case "Sun_shroom" -> new Sun_shroom();
            case "Puff_shroom" -> new Puff_shroom();
            case "Fume_shroom" -> new Fume_shroom();
            case "Scaredy_shroom" -> new Scaredy_shroom();
            default -> null;
        };

        if (plant == null) return;

        if (plant instanceof Puff_shroom) {
            lastPuffShroomPlantTime = System.currentTimeMillis();
        }

        ImageView view = plant.getPlantView();
        view.setLayoutX(cell.getLayoutX());
        view.setLayoutY(cell.getLayoutY());
        view.setFitWidth(60);
        view.setFitHeight(60);
        gameLayer.getChildren().add(view);
        activePlants.add(plant);
        occupiedCells.put(cell, plant);
        sunAmount -= cost;
        updateSunCount();
        startPlantThread(plant);
    }

    private void updateSunCount() {
        sunCount.setText(String.valueOf(sunAmount));
    }

    private void startPlantThread(Plant plant) {
        new Thread(() -> {
            if (plant instanceof Puff_shroom puffShroom) {
                while (puffShroom.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    if (isZombieInPuffShroomRange(puffShroom)) {
                        Platform.runLater(() -> fireSmokyBullet(puffShroom));
                    }
                }
            } else if (plant instanceof Fume_shroom fumeShroom) {
                while (fumeShroom.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    if (isZombieInFumeShroomRange(fumeShroom)) {
                        Platform.runLater(() -> fireFumeBullet(fumeShroom));
                    }
                }
            } else if (plant instanceof Scaredy_shroom scaredyShroom) {
                while (scaredyShroom.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    handleScaredyShroomState(scaredyShroom);
                }
            } else if (plant instanceof Sun_shroom sunShroom) {
                sunShroomProductionCount.put(sunShroom, 0);
                while (sunShroom.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    Platform.runLater(() -> generateSun(sunShroom));
                }
            }
        }).start();
    }

//    private boolean isZombieInPuffShroomRange(Puff_shroom puffShroom) {
//        double plantY = puffShroom.getPlantView().getLayoutY();
//        double plantX = puffShroom.getPlantView().getLayoutX();
//        double range = 240; // Approx 3 cells
//
//        for (Zombie zombie : zombies) {
//            if (zombie.isDead()) continue;
//            ImageView zombieView = zombie.getZombieView();
//            double zombieY = zombieView.getLayoutY();
//            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();
//            if (Math.abs(zombieY - plantY) <= 50 && zombieX > plantX && zombieX < plantX + range) {
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean isZombieInPuffShroomRange(Puff_shroom puffShroom) {
        double plantY = puffShroom.getPlantView().getLayoutY();
        double plantX = puffShroom.getPlantView().getLayoutX();
        double range = 240; // Approx 3 cells (3 * 80px)

        for (Zombie zombie : zombies) {
            if (zombie.isDead()) continue;

            ImageView zombieView = zombie.getZombieView();
            double zombieY = zombieView.getLayoutY();
            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();

            boolean sameRow = Math.abs(zombieY - plantY) <= 50;
            boolean zombieInFront = zombieX > plantX;
            boolean inRange = zombieX < plantX + range;

            if (sameRow && zombieInFront && inRange) {
                return true;
            }
        }
        return false;
    }

    private boolean isZombieInFumeShroomRange(Fume_shroom fumeShroom) {
        double plantY = fumeShroom.getPlantView().getLayoutY();
        double plantX = fumeShroom.getPlantView().getLayoutX();
        double range = 320; // Approx 4 cells

//        for (Zombie zombie : zombies) {
//            if (zombie.isDead()) continue;
//            ImageView zombieView = zombie.getZombieView();
//            double zombieY = zombieView.getLayoutY();
//            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();
//            if (Math.abs(zombieY - plantY) <= 50 && zombieX > plantX && zombieX < plantX + range) {
//                return true;
//            }
//        }
//        return false;
        for (Zombie zombie : zombies) {
            if (zombie.isDead()) continue;

            ImageView zombieView = zombie.getZombieView();
            double zombieY = zombieView.getLayoutY();
            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();

            boolean sameRow = Math.abs(zombieY - plantY) <= 50;
            boolean zombieInFront = zombieX > plantX;
            boolean inRange = zombieX < plantX + range;

            if (sameRow && zombieInFront && inRange) {
                return true;
            }
        }
        return false;
    }

    private void handleScaredyShroomState(Scaredy_shroom scaredyShroom) {
        double plantY = scaredyShroom.getPlantView().getLayoutY();
        double plantX = scaredyShroom.getPlantView().getLayoutX();
        double oneCellRange = 80; // Approx 1 cell

        boolean isScared = false;
        for (Zombie zombie : zombies) {
            if (zombie.isDead()) continue;
            ImageView zombieView = zombie.getZombieView();
            double zombieY = zombieView.getLayoutY();
            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();
            if (Math.abs(zombieY - plantY) <= 50 && zombieX > plantX && zombieX < plantX + oneCellRange) {
                isScared = true;
                break;
            }
        }

        final boolean finalIsScared = isScared;
        Platform.runLater(() -> {
            if (finalIsScared) {
                scaredyShroom.getPlantView().setImage(new Image("file:src/main/resources/Gifs/scaredy_shroom_scared.gif"));
            } else {
                scaredyShroom.getPlantView().setImage(new Image("file:src/main/resources/Gifs/scaredy_shroom.gif"));
                if (isZombieInRow(scaredyShroom)) {
                    fireSmokyBullet(scaredyShroom);
                }
            }
        });
    }

//    private boolean isZombieInRow(WarriorPlants plant) {
//        double y = plant.getPlantView().getLayoutY();
//        for (Zombie z : zombies) {
//            if (z.isDead()) continue;
//            double zy = z.getZombieView().getLayoutY();
//            double zx = z.getZombieView().getLayoutX() + z.getZombieView().getTranslateX();
//            if (Math.abs(zy - y) <= 20 && zx > plant.getPlantView().getLayoutX()) {
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean isZombieInRow(WarriorPlants plant){
        double plantY = plant.getPlantView().getLayoutY();
        double plantX = plant.getPlantView().getLayoutX();

        for (Zombie zombie : zombies) {
            if (zombie.isDead()) continue;

            ImageView zombieView = zombie.getZombieView();
            double zombieY = zombieView.getLayoutY();
            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();

            boolean sameRow = Math.abs(zombieY - plantY) <= 50;

            boolean zombieInFront = zombieX > plantX;

            boolean inRange = zombieX < 900;

            if (sameRow && zombieInFront && inRange) {
                return true;
            }
        }
        return false;
    }

    private void fireSmokyBullet(WarriorPlants plant) {
//        if (gameIsOver) return;
//        ImageView bullet = new ImageView(new Image("file:src/main/resources/images/smoky_stone.png"));
//        bullet.setLayoutX(plant.getPlantView().getLayoutX() + 20);
//        bullet.setLayoutY(plant.getPlantView().getLayoutY() + 10);
//        bullet.setFitWidth(25);
//        bullet.setFitHeight(25);
//        gameLayer.getChildren().add(bullet);
//
//        final boolean[] hasHit = {false};
//        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), bullet);
//        transition.setFromX(0);
//        transition.setToX(1000 - bullet.getLayoutX());
//
//        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
//            if (hasHit[0] || gameIsOver) return;
//            for (Zombie z : new ArrayList<>(zombies)) {
//                ImageView zView = z.getZombieView();
//                if (bullet.getBoundsInParent().intersects(zView.getBoundsInParent())) {
//                    hasHit[0] = true;
//                    gameLayer.getChildren().remove(bullet);
//                    z.takeDamage(1);
//                    transition.stop();
//                    break;
//                }
//            }
//        });
//
//        transition.setOnFinished(e -> gameLayer.getChildren().remove(bullet));
//        transition.play();
        if (gameIsOver) return;
        ImageView bullet = new ImageView(new Image("file:src/main/resources/images/smoky_stone.png"));
        bullet.setLayoutX(plant.getPlantView().getLayoutX() + 20);
        bullet.setLayoutY(plant.getPlantView().getLayoutY() + 10);
        bullet.setFitWidth(25);
        bullet.setFitHeight(25);
        gameLayer.getChildren().add(bullet);

        final boolean[] hasHit = {false};
        double startX = bullet.getLayoutX();
        double endX = 1000;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), bullet);
        transition.setFromX(0);
        transition.setToX(endX - startX);

        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (hasHit[0] || gameIsOver) return;
            for (Zombie z : new ArrayList<>(zombies)) {
                ImageView zView = z.getZombieView();
                double bulletX = bullet.getLayoutX() + bullet.getTranslateX();
                double zombieX = zView.getLayoutX() + zView.getTranslateX();

                if (bulletX + bullet.getFitWidth() > zombieX + 75 &&
                        bulletX < zombieX + zView.getFitWidth() - 10 &&
                        Math.abs(bullet.getLayoutY() - zView.getLayoutY()) < 45) {
                    hasHit[0] = true;
                    gameLayer.getChildren().remove(bullet);
                    z.takeDamage(1);
                    transition.stop();
                    break;
                }
            }
        });

        transition.setOnFinished(e -> gameLayer.getChildren().remove(bullet));
        transition.play();
    }

    private void fireFumeBullet(Fume_shroom fumeShroom) {
        if (gameIsOver) return;
        ImageView bullet = new ImageView(new Image("file:src/main/resources/images/fume_stone.png"));
        bullet.setLayoutX(fumeShroom.getPlantView().getLayoutX() + 20);
        bullet.setLayoutY(fumeShroom.getPlantView().getLayoutY() + 10);
        bullet.setFitWidth(25);
        bullet.setFitHeight(25);
        gameLayer.getChildren().add(bullet);

        double range = 320; // 4 cells
        double startX = bullet.getLayoutX();
        double endX = startX + range;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), bullet);
        transition.setFromX(0);
        transition.setToX(range);

        List<Zombie> hitZombies = new ArrayList<>();
        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (gameIsOver) return;
            List<Zombie> zombiesInRow = zombies.stream()
                    .filter(z -> !z.isDead() && Math.abs(z.getZombieView().getLayoutY() - bullet.getLayoutY()) < 45)
                    .collect(Collectors.toList());

            for (Zombie z : zombiesInRow) {
                if (!hitZombies.contains(z) && bullet.getBoundsInParent().intersects(z.getZombieView().getBoundsInParent())) {
                    z.takeDamage(1);
                    hitZombies.add(z);
                }
            }
        });

        transition.setOnFinished(e -> gameLayer.getChildren().remove(bullet));
        transition.play();
    }

    private void generateSun(Sun_shroom sunShroom) {
        if (gameIsOver) return;
        ImageView plantView = sunShroom.getPlantView();
        ImageView sun = new ImageView(new Image("file:src/main/resources/images/IMG_20250702_131427_034.png"));
        sun.setFitWidth(50);
        sun.setFitHeight(50);
        sun.setLayoutX(plantView.getLayoutX() + plantView.getFitWidth() - 20);
        sun.setLayoutY(plantView.getLayoutY() + plantView.getFitHeight() - 10);

        int currentCount = sunShroomProductionCount.getOrDefault(sunShroom, 0);
        int sunValue = (currentCount < 5) ? 15 : 25;

        sun.setOnMouseClicked(e -> {
            sunAmount += sunValue;
            updateSunCount();
            gameLayer.getChildren().remove(sun);
            e.consume();
        });

        gameLayer.getChildren().add(sun);
        sun.toFront();

        sunShroomProductionCount.put(sunShroom, currentCount + 1);
        if (currentCount + 1 == 5) {
            plantView.setFitWidth(100);
            plantView.setFitHeight(100);
        }

        executor.schedule(() -> Platform.runLater(() -> gameLayer.getChildren().remove(sun)), 8, TimeUnit.SECONDS);
    }

    private void spawnZombie(double x, double y) {
        if (gameIsOver) return;
        Zombie zombie;
        double rand = Math.random();
        if (rand < 0.5) {
            zombie = new Flag_Zombie();
        } else {
            zombie = new Normal_Zombie();
        }

        zombie.setMapController(this);
        ImageView view = new ImageView(zombie.getAnimationFrames());
        view.setLayoutX(x);
        view.setLayoutY(y - 20);
        view.setFitWidth(140);
        view.setFitHeight(120);
        zombie.setZombieView(view);
        zombies.add(zombie);
        gameLayer.getChildren().add(view);
        startZombieThread(zombie);
    }

    private void startZombieWaves() {
        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            int totalWaves = 3;
            int zombiesPerWave = 4;
            int totalZombiesInGame = totalWaves * zombiesPerWave;
            final int[] spawnedZombiesTotal = {0};

            Platform.runLater(() -> zombieWaveBar.setProgress(0.0));

            for (int wave = 1; wave <= totalWaves && !gameIsOver; wave++) {
                for (int i = 0; i < zombiesPerWave && !gameIsOver; i++) {
                    final int row = (int) (Math.random() * 5);
                    spawnedZombiesTotal[0]++;
                    final int currentSpawned = spawnedZombiesTotal[0];

                    Platform.runLater(() -> {
                        double y = switch (row) {
                            case 0 -> a11.getLayoutY();
                            case 1 -> a21.getLayoutY();
                            case 2 -> a31.getLayoutY();
                            case 3 -> a41.getLayoutY();
                            default -> a51.getLayoutY();
                        };
                        spawnZombie(1000, y);
                        double progress = (double) currentSpawned / totalZombiesInGame;
                        zombieWaveBar.setProgress(progress);
                    });
                    try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
                }
                try { Thread.sleep(8000); } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void startZombieThread(Zombie zombie) {
        ImageView zombieView = zombie.getZombieView();
        double startX = zombieView.getLayoutX();
        double endX = 150;
        double distance = startX - endX;
        double speed = 12;
        double durationSeconds = distance / speed;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(durationSeconds), zombieView);
        transition.setFromX(0);
        transition.setToX(endX - startX);
        transition.setInterpolator(javafx.animation.Interpolator.LINEAR);

        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (zombie.isDead() || gameIsOver) {
                transition.stop();
                return;
            }

            double zombieX = zombieView.getLayoutX() + zombieView.getTranslateX();
            double zombieY = zombieView.getLayoutY();

            Plant closestPlant = activePlants.stream()
                    .filter(p -> p.getPlantView() != null && p.getPlantView().getParent() != null)
                    .filter(p -> Math.abs(p.getPlantView().getLayoutY() - zombieY) < 30)
                    .filter(p -> zombieX < p.getPlantView().getLayoutX() + p.getPlantView().getFitWidth())
                    .min((p1, p2) -> Double.compare(p1.getPlantView().getLayoutX(), p2.getPlantView().getLayoutX()))
                    .orElse(null);

            boolean isColliding = false;
            if (closestPlant != null) {
                double plantX = closestPlant.getPlantView().getLayoutX();
                if (zombieX <= plantX + 5) {
                    isColliding = true;
                    if (!zombie.isEating()) {
                        zombie.setEating(true);
                        if (zombie.getEatingAnimation() != null) {
                            zombieView.setImage(zombie.getEatingAnimation());
                        }
                        startEatingPlant(zombie, closestPlant, transition);
                    }
                    transition.pause();
                }
            }

            if (!isColliding && zombie.isEating()) {
                zombie.setEating(false);
                zombieView.setImage(zombie.getAnimationFrames());
                transition.play();
            }
        });

        transition.setOnFinished(e -> {
            if (!zombie.isDead() && !gameIsOver) {
                gameLayer.getChildren().remove(zombieView);
                zombies.remove(zombie);
                endGame();
            } else if (zombie.isDead()) {
                Platform.runLater(this::checkWinCondition);
            }
        });

        transition.play();
    }

    private void startEatingPlant(Zombie zombie, Plant plant, TranslateTransition transition) {
        new Thread(() -> {
            while (zombie.isEating() && plant.isAlive() && !zombie.isDead() && !gameIsOver) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                plant.takeDamage(zombie.getDamagePower().getAmount());
            }

            Platform.runLater(() -> {
                if (!plant.isAlive()) {
                    ImageView cellToRemove = null;
                    for (Map.Entry<ImageView, Plant> entry : occupiedCells.entrySet()) {
                        if (entry.getValue() == plant) {
                            cellToRemove = entry.getKey();
                            break;
                        }
                    }
                    if (cellToRemove != null) {
                        occupiedCells.remove(cellToRemove);
                    }

                    gameLayer.getChildren().remove(plant.getPlantView());
                    activePlants.remove(plant);
                    if (!zombie.isDead()) {
                        zombie.setEating(false);
                        zombie.getZombieView().setImage(zombie.getAnimationFrames());
                        transition.play();
                    }
                }
            });
        }).start();
    }

    private void endGame() {
        if (gameIsOver) return;
        gameIsOver = true;
        executor.shutdownNow();

        Platform.runLater(() -> {
            Rectangle overlay = new Rectangle(gameLayer.getWidth(), gameLayer.getHeight(), Color.BLACK);
            overlay.setOpacity(0.5);

            ImageView loseImage = new ImageView(new Image("file:src/main/resources/images/lose.png"));
            loseImage.setFitWidth(400);
            loseImage.setFitHeight(300);
            loseImage.layoutXProperty().bind(gameLayer.widthProperty().subtract(loseImage.getFitWidth()).divide(2));
            loseImage.layoutYProperty().bind(gameLayer.heightProperty().subtract(loseImage.getFitHeight()).divide(2));

            StackPane endPane = new StackPane(overlay, loseImage);
            gameLayer.getChildren().add(endPane);

            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ignored) {}
                Platform.runLater(this::returnToMainMenu);
            }).start();
        });
    }

    private void returnToMainMenu() {
        if (playerController.lastSignedUpPlayer.getCurrentStage() == 5){
            playerController.decreaseScore(1);
        }
        try {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleZombieDeath(Zombie zombie) {
        zombies.remove(zombie);
        Platform.runLater(this::checkWinCondition);
    }

    private void checkWinCondition() {
        if (zombies.isEmpty() && gameIsOver == false) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}

                Platform.runLater(() -> {
                    if (zombies.isEmpty()) {
                        winGame();
                    }
                });
            }).start();
        }
    }

    private void winGame() {
        if (gameIsOver) return;
        gameIsOver = true;
        executor.shutdownNow();

        Platform.runLater(() -> {
            Rectangle overlay = new Rectangle(gameLayer.getWidth(), gameLayer.getHeight(), Color.BLACK);
            overlay.setOpacity(0.5);

            ImageView winImage = new ImageView(new Image("file:src/main/resources/images/win.png"));
            winImage.setFitWidth(400);
            winImage.setFitHeight(300);
            winImage.layoutXProperty().bind(gameLayer.widthProperty().subtract(winImage.getFitWidth()).divide(2));
            winImage.layoutYProperty().bind(gameLayer.heightProperty().subtract(winImage.getFitHeight()).divide(2));

            StackPane winPane = new StackPane(overlay, winImage);
            gameLayer.getChildren().add(winPane);

            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ignored) {}
                Platform.runLater(this::loadStepNight);
            }).start();
        });
    }

    @FXML
    void menuBtn(MouseEvent event) throws IOException{
        HelloApplication.pauseMusic();
        String path = "src/main/resources/musics/02. Crazy Dave (Intro Theme).mp3";
        Media media = new Media(new File(path).toURI().toString());
        HelloApplication.mediaPlayer = new MediaPlayer(media);
        HelloApplication.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        HelloApplication.mediaPlayer.setVolume(0.3);
        HelloApplication.playMusic();
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsNight.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadStepNight() {
        if (playerController.lastSignedUpPlayer.getCurrentStage() == 5){
            playerController.updateCurrentStage(playerController.lastSignedUpPlayer.getCurrentStage() + 1 , playerController.lastSignedUpPlayer.getId());
            playerController.increaseScore(10);
        }
        try {
            HelloApplication.pauseMusic();
            String path = "src/main/resources/musics/02. Crazy Dave (Intro Theme).mp3";
            Media media = new Media(new File(path).toURI().toString());
            HelloApplication.mediaPlayer = new MediaPlayer(media);
            HelloApplication.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            HelloApplication.mediaPlayer.setVolume(0.3);
            HelloApplication.playMusic();
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsNight.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}