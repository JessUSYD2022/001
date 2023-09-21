package invaders.entities;

public class PlayerProjectileFactory implements ProjectileFactory{
    @Override
    public PlayerProjectile createProjectile() {
        return new PlayerProjectile();
    }
}
