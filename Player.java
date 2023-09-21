package invaders.entities;

import invaders.GameObject;
import invaders.logic.Damagable;
import invaders.physics.BoxCollider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class Player implements Moveable, Damagable, Renderable, GameObject {

    private Vector2D position;
    private final Animator anim = null;
    private Vector2D originalPosition;

    private final double width = 25;
    private final double height = 30;
    private Image image;
    private double speed;
    private int lives;
    private ArrayList<PlayerProjectile> projectiles = new ArrayList<>();
    private BoxCollider collider;

    public Player(Vector2D position){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = position;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives -= amount;
    }

    @Override
    public boolean isAlive() {
        return this.lives > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + speed);
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public int getLives(){
        return lives;
    }

    public ArrayList<PlayerProjectile> shoot(){
        PlayerProjectileFactory projectileFactory = new PlayerProjectileFactory();
        PlayerProjectile projectile = projectileFactory.createProjectile();

        projectile.setPosition(new Vector2D(this.position.getX(), this.position.getY()));
        projectile.setWidth(32);
        projectile.setHeight(32);
        projectile.setImage(new Image(new File("src/main/resources/playerprojectile.png").toURI().toString(), projectile.getWidth(), projectile.getHeight(), true, true));
        projectile.setCollider(new BoxCollider(projectile.getWidth(), projectile.getHeight(), projectile.getPosition()));
        projectiles.add(projectile);
        return projectiles;
    }

    @Override
    public Image getImage() {
        return this.image;
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
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    } // 中文注释: 保证前景对象不会超出屏幕
    public void setCollider(BoxCollider collider) {
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public Vector2D getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(Vector2D originalPosition) {
        this.originalPosition = originalPosition;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
        System.out.println("Player's position set to: " + position);
    }

    @Override
    public void start() {
        collider = new BoxCollider(width, height, position);
        setCollider(collider);
    }

    @Override
    public void update() {
        getCollider().setPosition(getPosition());
    }
}
