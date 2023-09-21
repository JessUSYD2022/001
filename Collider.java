package invaders.physics;

public interface Collider {

    public double getWidth();

    public double getHeight();

    public Vector2D getPosition();


    public default boolean isColliding(Collider col) {
        double minX1 = this.getPosition().getX();
        double maxX1 = this.getPosition().getX() + this.getWidth();
        double minY1 = this.getPosition().getY();
        double maxY1 = this.getPosition().getY() + this.getHeight();

        double minX2 = col.getPosition().getX();
        double maxX2 = col.getPosition().getX() + col.getWidth();
        double minY2 = col.getPosition().getY();
        double maxY2 = col.getPosition().getY() + col.getHeight();

        boolean overlapX = (minX1 < maxX2) && (maxX1 > minX2);

        boolean overlapY = (minY1 < maxY2) && (maxY1 > minY2);

        return overlapX && overlapY;

        //return true;
    }
}