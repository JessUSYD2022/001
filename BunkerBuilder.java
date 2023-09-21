package invaders.entities;

import invaders.physics.Vector2D;

public class BunkerBuilder{
    private Bunker bunker = new Bunker();

    public BunkerBuilder setPosition(Vector2D position){
        bunker.setPosition(position);
        return this;
    }

    public BunkerBuilder setSize_x(double size_x){
        bunker.setWidth(size_x);
        return this;
    }

    public BunkerBuilder setSize_y(double size_y){
        bunker.setHeight(size_y);
        return this;
    }

    public BunkerBuilder setDamage(int damage){
        bunker.setDamage(damage);
        return this;
    }

    public BunkerBuilder setStart(){
        bunker.start();
        return this;
    }


    public Bunker build(){
        return bunker;
    }
}
