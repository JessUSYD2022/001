package invaders.entities;

import javafx.scene.Node;
import invaders.rendering.Renderable;

public interface EntityView {
    void update(double xViewportOffset, double yViewportOffset); // 中文注释: 更新视图 update view

    boolean matchesEntity(Renderable entity);

    void markForDelete();

    Node getNode();

    boolean isMarkedForDelete();
}
