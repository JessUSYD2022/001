package invaders.entities;

public class FastStraightStrategy implements ProjectileStrategy {
    private static final double SPEED = 3.0;
    @Override
    public void shoot(Projectile fastProjectile) {
        fastProjectile.getPosition().setY(fastProjectile.getPosition().getY() + SPEED);
    }
}
