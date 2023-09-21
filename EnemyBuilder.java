package invaders.entities;

import invaders.physics.Vector2D;
import invaders.rendering.Animator;

public class EnemyBuilder {
   private Enemy enemy = new Enemy();

   public EnemyBuilder setWidth(double width){
      enemy.setWidth(width);
      return this;
   }

    public EnemyBuilder setHeight(double height){
        enemy.setHeight(height);
        return this;
    }

    public EnemyBuilder setImage(String image, double width, double height){
        enemy.setImage(image, width, height);
        return this;
    }

    public EnemyBuilder setPosition(Vector2D position){
        enemy.setPosition(position);
        return this;
    }

    public EnemyBuilder setProjectile(String projectile){
        enemy.setProjectile(projectile);
        return this;
    }

    public EnemyBuilder setAnimator(Animator animator){
        enemy.setAnimator(animator);
        return this;
    }

    public EnemyBuilder setDirection(int direction){
        enemy.setDirection(direction);
        return this;
    }

    public EnemyBuilder setDescendCounter(int descendCounter){
        enemy.setDescendCounter(descendCounter);
        return this;
    }

    public EnemyBuilder setDescending(boolean descending){
        enemy.setDescending(descending);
        return this;
    }

    public EnemyBuilder setStart(){
        enemy.start();
        return this;
    }

    public Enemy build(){
        return enemy;
    }
}

