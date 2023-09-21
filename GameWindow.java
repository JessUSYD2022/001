package invaders.engine;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import invaders.physics.Vector2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameWindow implements Observer{
    private final int width;
    private final int height;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Renderable background;

    private Text playerLivesLabel;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;
    private Timeline timeline;

    public GameWindow(GameEngine model, int width, int height){
        this.width = width;
        this.height = height;
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        playerLivesLabel = new Text("LIVES: "+model.getLives());
        playerLivesLabel.setFill(Color.WHITE);
        playerLivesLabel.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        playerLivesLabel.setX(width - 100);
        playerLivesLabel.setY(30);
        pane.getChildren().add(playerLivesLabel);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();

    }

    public void run() {
        timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void draw(){
        model.update();

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
                //System.out.println("Added new EntityView to pane and entityViews");
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
                //System.out.println("Removed EntityView from pane due to markForDelete");
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
        //System.out.println(entityViews.size());
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void onRemoved(Renderable entity) {
        markEntityViewForDelete(entity);
    }

    private void markEntityViewForDelete(Renderable entity) {
        for (EntityView view : entityViews) {
            if (view.matchesEntity(entity)) {
                pane.getChildren().remove(view.getNode());
                view.markForDelete();
                entityViews.remove(view);
                break;
            }
        }
    }

    public void updatePlayerLivesLabel() {
        playerLivesLabel.setText("LIVES: " + model.getLives());
    }

    public void setModel(GameEngine model) {
        this.model = model;
    }

    @Override
    public void onGameOver() {
        if (timeline != null) {
            timeline.stop();
        }
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.RED);
        gameOverText.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 40));
        gameOverText.setX(width / 2 - 100);
        gameOverText.setY(height / 2);
        pane.getChildren().add(gameOverText);
    }
}

