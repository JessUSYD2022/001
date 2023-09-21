package invaders.entities;


public class SlowStraightStrategy implements ProjectileStrategy {
    private static final double SPEED = 1.5;

    @Override
    public void shoot(Projectile slowProjectile) {
        slowProjectile.getPosition().setY(slowProjectile.getPosition().getY() + SPEED);
    }
}
