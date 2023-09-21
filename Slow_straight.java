package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public class Slow_straight implements Projectile {
    private static final double SPEED = 1.5;
    private double height;
    private double width;
    private Vector2D position;
    private Image image;
    private String projectile;
    private BoxCollider collider;
    private ProjectileStrategy projectileStrategy;

    public void setCollider(BoxCollider collider) {
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    public String getProjectile() {
        return this.projectile;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void shoot() {
        this.position.setY(this.position.getY() + SPEED);
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setProjectile(String projectile) {
        this.projectile = projectile;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    public ProjectileStrategy getProjectileStrategy() {
        return projectileStrategy;
    }
}
