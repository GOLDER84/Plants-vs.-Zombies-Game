package org.example.pvsz_game;

import Controller.PlayerController;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.*;
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

public class Stage2Controller {
    @FXML private ImageView a11, a12, a13, a14, a15, a16, a17, a18, a19;
    @FXML private ImageView a21, a22, a23, a24, a25, a26, a27, a28, a29;
    @FXML private ImageView a31, a32, a33, a34, a35, a36, a37, a38, a39;
    @FXML private ImageView a41, a42, a43, a44, a45, a46, a47, a48, a49;
    @FXML private ImageView a51, a52, a53, a54, a55, a56, a57, a58, a59;
    @FXML private ImageView p1 , p11 , p2 , p21 , p3 , p31 , p4 , p41 , p5 , p51 , p6 , p61;
    @FXML private Label sunCount;
    @FXML private ProgressBar zombieWaveBar;
    @FXML private ImageView waveHead;
    @FXML private AnchorPane gameLayer;
    private Stage stage;

    private String selectedPlant = null;
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Plant> activePlants = new ArrayList<>();
    private final Map<ImageView, Plant> occupiedCells = new HashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final int totalWaves = 3;
    private int totalZombiesInWave;
    private int zombiesKilledInWave;
    private PlayerController playerController = PlayerController.getInstance();
    private boolean gameIsOver = false;

    private int sunAmount = 200;

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
        startFallingSuns();
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
        // Set initial position
        waveHead.setLayoutX((barStartX + barWidth) - (zombieWaveBar.getProgress() * barWidth) - (headWidth / 2));
    }

    private void setupPlantSelection() {
        plantSelectors.put("Sunflower", new ImageView[]{p1, p11});
        plantSelectors.put("Peashooter", new ImageView[]{p2, p21});
        plantSelectors.put("Cherry_bomb", new ImageView[]{p3, p31});
        plantSelectors.put("Wall_nut", new ImageView[]{p4, p41});


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

        int cost = switch (selectedPlant) {
            case "Peashooter" -> 100;
            case "Sunflower" -> 50;
            case "Cherry_bomb" -> 150;
            case "Wall_nut" -> 50;
            default -> 0;
        };

        if (sunAmount < cost) return;

        Plant plant = switch (selectedPlant) {
            case "Peashooter" -> new Peashooter();
            case "Sunflower" -> new Sunflower();
            case "Cherry_bomb" -> new Cherry_bomb();
            case "Wall_nut" -> new Wall_nut();
            default -> null;
        };

        if (plant == null) return;

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
            if (plant instanceof Peashooter peashooter) {
                while (peashooter.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    if (isZombieInRow(peashooter)) {
                        Platform.runLater(() -> fireBullet(peashooter));
                    }
                }

            } else if (plant instanceof Sunflower sunflower) {
                while (sunflower.isAlive() && !gameIsOver) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    Platform.runLater(() -> generateSun(sunflower));
                }
            } else if (plant instanceof Cherry_bomb cherryBomb) {
                try {
                    Thread.sleep(1000); // Wait for explosion
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                Platform.runLater(() -> {
                    explode(cherryBomb);
                    gameLayer.getChildren().remove(cherryBomb.getPlantView());
                    activePlants.remove(cherryBomb);
                    occupiedCells.values().remove(cherryBomb);
                });
            }
            // Wall_nut does not need a thread as it's a passive plant.
        }).start();
    }

    private void explode(Cherry_bomb cherryBomb) {
        ImageView bombView = cherryBomb.getPlantView();
        Bounds bombBounds = bombView.getBoundsInParent();

        // define explosion radius (covers one cell in every direction)
        double radiusX = 55;
        double radiusY = 55;

        // expanded bounds for explosion area
        Rectangle2D explosionArea = new Rectangle2D(
                bombBounds.getMinX() - radiusX,
                bombBounds.getMinY() - radiusY,
                bombBounds.getWidth()  + radiusX * 2,
                bombBounds.getHeight() + radiusY * 2
        );

        // trigger explosion on any zombie whose bounds intersect that area
        zombies.stream()
                .filter(z -> !z.isDead())
                .filter(z -> {
                    Bounds zBounds = z.getZombieView().getBoundsInParent();
                    return explosionArea.intersects(zBounds.getMinX(),
                            zBounds.getMinY(),
                            zBounds.getWidth(),
                            zBounds.getHeight());
                })
                .forEach(Zombie::explodeDeath);

        cherryBomb.setAlive(false);
        // remove bomb view after a delay
        new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            Platform.runLater(() -> {
                gameLayer.getChildren().remove(bombView);
                activePlants.remove(cherryBomb);
                occupiedCells.entrySet().removeIf(e -> e.getValue() == cherryBomb);
            });
        }).start();
    }

//    private void explode(Cherry_bomb cherryBomb) {
//        ImageView bombView = cherryBomb.getPlantView();
//        double bombX = bombView.getLayoutX();
//        double bombY = bombView.getLayoutY();
//
//        // Change to explosion GIF
//        bombView.setImage(new Image("file:src/main/resources/Gifs/Cherry_bomb.gif"));
//
//        // Increased explosion radius - making it asymmetric to catch zombies that have passed
//        double cellWidth = 75;
//        double cellHeight = 100;
//        double forwardRadius = cellWidth * 1.5;  // ~112.5px forward
//        double backwardRadius = cellWidth * 3;   // ~225px backward to catch zombies that have passed
//        double verticalRadius = cellHeight * 1.2; // ~120px up and down
//
//        List<Zombie> zombiesToExplode = zombies.stream()
//                .filter(z -> !z.isDead())
//                .filter(z -> {
//                    ImageView zView = z.getZombieView();
//                    double zX = zView.getLayoutX() + zView.getTranslateX();
//                    double zY = zView.getLayoutY();
//
//                    // Enhanced horizontal range check - asymmetric to catch zombies behind
//                    boolean withinHorizontalRange;
//                    if (zX <= bombX) {
//                        // For zombies that already passed the bomb (to the left)
//                        withinHorizontalRange = (bombX - zX) <= backwardRadius;
//                    } else {
//                        // For zombies approaching the bomb (to the right)
//                        withinHorizontalRange = (zX - bombX) <= forwardRadius;
//                    }
//
//                    boolean withinVerticalRange = Math.abs(zY - bombY) <= verticalRadius;
//
//                    return withinHorizontalRange && withinVerticalRange;
//                })
//                .collect(Collectors.toList());
//
//        for (Zombie zombie : zombiesToExplode) {
//            zombie.explodeDeath();
//        }
//
//        // Mark plant as not alive and remove after animation
//        cherryBomb.setAlive(false);
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            Platform.runLater(() -> {
//                // Remove from game layer
//                gameLayer.getChildren().remove(bombView);
//                // Remove from active plants
//                activePlants.remove(cherryBomb);
//                // Remove from occupied cells
//                occupiedCells.entrySet().removeIf(entry -> entry.getValue() == cherryBomb);
//            });
//        }).start();
//    }

    private boolean isZombieInRow(Peashooter peashooter) {
        double plantY = peashooter.getPlantView().getLayoutY();
        double plantX = peashooter.getPlantView().getLayoutX();

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

    private void fireBullet(Peashooter peashooter) {
        if (gameIsOver) return;
        ImageView bullet = new ImageView(new Image("file:src/main/resources/images/stone.png"));
        bullet.setLayoutX(peashooter.getPlantView().getLayoutX() + 20);
        bullet.setLayoutY(peashooter.getPlantView().getLayoutY() + 10);
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

    private void generateSun(Sunflower sunflower) {
        if (gameIsOver) return;
        ImageView plantView = sunflower.getPlantView();
        ImageView sun = new ImageView(new Image("file:src/main/resources/images/IMG_20250702_131427_034.png"));
        sun.setFitWidth(50);
        sun.setFitHeight(50);
        double sunX = plantView.getLayoutX() + plantView.getFitWidth() - 20;
        double sunY = plantView.getLayoutY() + plantView.getFitHeight() - 10;
        sun.setLayoutX(sunX);
        sun.setLayoutY(sunY);

        sun.setOnMouseClicked(e -> {
            sunAmount += sunflower.getAmount();
            updateSunCount();
            gameLayer.getChildren().remove(sun);
            e.consume();
        });

        gameLayer.getChildren().add(sun);
        sun.toFront();

        executor.schedule(() -> Platform.runLater(() -> gameLayer.getChildren().remove(sun)), 8, TimeUnit.SECONDS);
    }

    private void startFallingSuns() {
        new Thread(() -> {
            while (!gameIsOver) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                Platform.runLater(this::spawnFallingSun);
            }
        }).start();
    }

    private void spawnFallingSun() {
        if (gameIsOver) return;
        double x = 100 + Math.random() * 700;
        ImageView sun = new ImageView(new Image("file:src/main/resources/images/IMG_20250702_131427_034.png"));
        sun.setFitWidth(50);
        sun.setFitHeight(50);
        sun.setLayoutX(x);
        sun.setLayoutY(-50);

        gameLayer.getChildren().add(sun);
        sun.toFront();

        TranslateTransition fall = new TranslateTransition(Duration.seconds(3.5), sun);
        fall.setFromY(0);
        fall.setToY(300);

        fall.setOnFinished(e -> {
            executor.schedule(() -> Platform.runLater(() -> gameLayer.getChildren().remove(sun)), 8, TimeUnit.SECONDS);
        });

        sun.setOnMouseClicked(e -> {
            sunAmount += 25;
            updateSunCount();
            gameLayer.getChildren().remove(sun);
            e.consume();
        });

        fall.play();
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
        Image img = zombie.getAnimationFrames();
        if (img == null) {
            img = new Image("file:src/main/resources/images/placeholder.png");
        }
        ImageView view = new ImageView(img);
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

            int totalZombiesInGame = 0;
            for (int wave = 1; wave <= totalWaves; wave++) {
                totalZombiesInGame += (3 + wave);
            }
            final int finalTotalZombies = totalZombiesInGame;
            final int[] spawnedZombiesTotal = {0};

            Platform.runLater(() -> zombieWaveBar.setProgress(0.0));

            for (int wave = 1; wave <= totalWaves && !gameIsOver; wave++) {
                int zombiesForThisWave = 3 + wave;
                for (int i = 0; i < zombiesForThisWave && !gameIsOver; i++) {
                    final int row = i % 5;
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
                        double progress = (double) currentSpawned / finalTotalZombies;
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
//30 بود
            Plant closestPlant = activePlants.stream()
                    .filter(p -> p.getPlantView() != null && p.getPlantView().getParent() != null)
                    .filter(p -> Math.abs(p.getPlantView().getLayoutY() - zombieY) < 50)
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
        if (playerController.lastSignedUpPlayer.getCurrentStage() == 2){
            playerController.decreaseScore(2);
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
        zombiesKilledInWave++;
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
                Platform.runLater(this::loadStepDay);
            }).start();
        });
    }
    @FXML
    void menuBtn(MouseEvent event) throws IOException{
        HelloApplication.playMusic();
        this.stage = HelloApplication.primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsDay.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadStepDay() {
        if (playerController.lastSignedUpPlayer.getCurrentStage() == 2){
            playerController.updateCurrentStage(playerController.lastSignedUpPlayer.getCurrentStage() + 1 , playerController.lastSignedUpPlayer.getId());
            playerController.increaseScore(20);
        }
        try {
            this.stage = HelloApplication.primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stepsDay.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}