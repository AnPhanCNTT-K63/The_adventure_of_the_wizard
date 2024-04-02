package com.folder.AnimationTileSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Tool.AnimationTileSetCreate;

public class FlowerType1 extends AnimationTileSetCreate {
    public FlowerType1(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 32 / Boot.PPM, 32 / Boot.PPM);
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 4; j++) {
                if (i == 1 && j == 1)
                    break;
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Flower1"), j * 32, i * 32, 32, 32));
            }
        animation = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
