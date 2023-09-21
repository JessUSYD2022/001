package invaders.rendering;

import javafx.scene.image.Image;

import java.util.List;

public class EnemyAnimation implements Animation {
    private String name;
    private List<Image> frames;
    private int currentFrameIndex;

    public EnemyAnimation(String name, List<Image> frames) {
        this.name = name;
        this.frames = frames;
        this.currentFrameIndex = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getCurrentFrame() {
        return frames.get(currentFrameIndex);
    }

    // 中文注释: 下一帧 next frame
    @Override
    public void next() {
        currentFrameIndex = (currentFrameIndex + 1) % frames.size();
    }
}
