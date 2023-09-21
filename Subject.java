package invaders.entities;

import invaders.engine.Observer;
import invaders.rendering.Renderable;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyRemoved(Renderable entity);
}
