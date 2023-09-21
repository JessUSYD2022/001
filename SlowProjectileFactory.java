package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

import java.io.File;

public class SlowProjectileFactory implements ProjectileFactory {
    @Override
    public Slow_straight createProjectile() {
        return new Slow_straight();
    }

    public Slow_straight spawn(Enemy enemy,Slow_straight slow_projectile){
        slow_projectile.setPosition(new Vector2D(enemy.getPosition().getX(), enemy.getPosition().getY()));
        slow_projectile.setWidth(9);
        slow_projectile.setHeight(29);
        slow_projectile.setImage(new Image(new File("src/main/resources/missile.png").toURI().toString(), slow_projectile.getWidth(), slow_projectile.getHeight(), true, true));
        slow_projectile.setCollider(new BoxCollider(slow_projectile.getWidth(),slow_projectile.getHeight(),slow_projectile.getPosition()));
        slow_projectile.shoot();
        return slow_projectile;
    }
}
