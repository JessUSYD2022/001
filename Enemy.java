package invaders.entities;


import invaders.GameObject;
import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Animation;
import invaders.rendering.Animator;
import invaders.rendering.EnemyAnimation;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Enemy implements Renderable, GameObject{
    private double width;
    private double height;
    private Vector2D position;
    private Image image;
    private String projectile;
    private List<Image> enemyAnimationList = new ArrayList<>();
    private List<Animation> animations = new ArrayList<>();
    private Animator animator;
    private int direction;
    private boolean descending;
    private int descendCounter;
    private ProjectileStrategy projectileStrategy;
    private BoxCollider collider;


    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public Image getImage() {
        return image;
    }

    public String getProjectile() {
        return projectile;
    }

    public Animator getAnimator() {
        return animator;
    }

    public int getDescendCounter() {
        return descendCounter;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    public void incrementDescendCounter() {
        this.descendCounter++;
    }

    public void setDescendCounter(int descendCounter) {
        this.descendCounter = descendCounter;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public void setProjectile(String projectile) {
        this.projectile = projectile;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
            return direction;
        }

    public void setWidth(double width){
        this.width = width;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setImage(String image, double width, double height){
        this.image = new Image(new File(image).toURI().toString(), width, height, true, true);
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setPosition(Vector2D position){
        this.position = position;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    // move enemy
    public void moveLeft(double speed){
        this.position.setX(this.position.getX() - speed);
    }

    public void moveRight(double speed){
        this.position.setX(this.position.getX() + speed);
    }

    public void moveDown(double speed){this.position.setY(this.position.getY() + speed);}
    public Fast_straight shootFast(){
        FastProjectileFactory fastProjectileFactory = new FastProjectileFactory();
        Fast_straight fastStraight = fastProjectileFactory.createProjectile();
        fastStraight = fastProjectileFactory.spawn(this,fastStraight);
        return fastStraight;
    }

    public Slow_straight shootSlow(){
        SlowProjectileFactory slowProjectileFactory = new SlowProjectileFactory();
        Slow_straight slowStraight = slowProjectileFactory.createProjectile();
        slowStraight = slowProjectileFactory.spawn(this,slowStraight);
        return slowStraight;
    }

    public void setCollider(BoxCollider collider) {
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public void start() {
        //Animation
        enemyAnimationList.add(new Image(new File("src/main/resources/small_invader_a.png").toURI().toString(), getWidth(), getHeight(), true, true));
        enemyAnimationList.add(new Image(new File("src/main/resources/small_invader_b.png").toURI().toString(), getWidth(), getHeight(), true, true));

        Animation enemy_Animation = new EnemyAnimation("enemy", enemyAnimationList);
        animations.add(enemy_Animation);
        animator = new Animator(animations);
        image = animator.getState().getCurrentFrame();

        // initialize collider
        collider = new BoxCollider(width, height, position);
        setCollider(collider);
    }

    public void update() {
        //Animation
        Animator animator1 = getAnimator();
        animator1.update();
        image = animator1.getState().getCurrentFrame();

        //collider
        getCollider().setPosition(getPosition());
    }

}
