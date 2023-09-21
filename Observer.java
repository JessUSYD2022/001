package invaders.engine;

import invaders.rendering.Renderable;

public interface Observer {
    void onRemoved(Renderable entity);
    void onGameOver();
}
