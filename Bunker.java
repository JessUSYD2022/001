package invaders.entities;

import invaders.GameObject;
import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class Bunker implements Renderable, GameObject{
    private Vector2D position;
    private double width;
    private double height;
    private int damage;
    private Image image;
    private BunkerState currentState;
    private String currentColor;
    private BoxCollider collider;

    @Override
    public Image getImage() {
        return image;
    }

    public void setImage(String image, double width, double height){
        this.image = new Image(new File(image).toURI().toString(), width, height, true, true);
    }

    @Override
    public double getWidth() {
        return width;
    }
    @Override
    public double getHeight() {
        return height;
    }
    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }
    public Vector2D getPosition() {
        return position;
    }
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSate(BunkerState state){
        this.currentState = state;
    }

    public void updateColor(){
        currentColor = currentState.changecolor(width);
    }

    public void setCollider(BoxCollider collider) {
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }


    //Game Object
    @Override
    public void start() {
        // 中文注释: 初始化状态 initialize state
        currentState = new GreenBunkerState();
        currentColor = currentState.changecolor(width);
        setImage(currentColor,width,height);

        // 中文注释: 初始化碰撞器 initialize collider
        collider = new BoxCollider(width, height, position);
        setCollider(collider);
    }

    @Override
    public void update() {
        updateColor();

        if (damage == 1){
            YellowBunkerState yellowState = new YellowBunkerState();
            setSate(yellowState);
            setImage(currentColor,width,height);
        }

        else if (damage == 2){
            RedBunkerState redState = new RedBunkerState();
            setSate(redState);
            setImage(currentColor,width,height);
        }
    }
}
