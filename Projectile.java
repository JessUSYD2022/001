package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
public interface Projectile extends Renderable {
    public double getHeight();
    public double getWidth();
    public Vector2D getPosition();

    @Override
    Layer getLayer();

    Image getImage();
    void setHeight(double height);
    void setWidth(double width);
    void setImage(Image image);
    void setPosition(Vector2D position);
    void shoot();
    void setCollider(BoxCollider collider);
    BoxCollider getCollider();
    ProjectileStrategy getProjectileStrategy();
}
