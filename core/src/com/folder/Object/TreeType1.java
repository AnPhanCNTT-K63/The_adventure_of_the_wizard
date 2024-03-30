package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;
import com.folder.GameScreen;

public class TreeType1 extends AnimationTileSet {
    public TreeType1(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 256 / Boot.PPM, 288 / Boot.PPM);
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Tree1"), j * 256, i * 288, 256, 288));
        animation = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
