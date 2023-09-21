package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

import java.io.File;

public class FastProjectileFactory implements ProjectileFactory {
    @Override
    public Fast_straight createProjectile() {
        return new Fast_straight();
    }

    public Fast_straight spawn(Enemy enemy,Fast_straight fast_projectile){
        fast_projectile.setPosition(new Vector2D(enemy.getPosition().getX(), enemy.getPosition().getY()));
        fast_projectile.setWidth(9);
        fast_projectile.setHeight(29);
        fast_projectile.setImage(new Image(new File("src/main/resources/missile.png").toURI().toString(), fast_projectile.getWidth(), fast_projectile.getHeight(), true, true));
        fast_projectile.setCollider(new BoxCollider(fast_projectile.getWidth(),fast_projectile.getHeight(),fast_projectile.getPosition()));
        fast_projectile.shoot();
        return fast_projectile;
    }
}
