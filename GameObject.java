package invaders;

// contains basic methods that all GameObjects must implement
// 中文注释: 所有游戏对象都必须实现的基本方法

public interface GameObject {

    public void start(); // called when the object is created
    public void update(); // called every frame

}
