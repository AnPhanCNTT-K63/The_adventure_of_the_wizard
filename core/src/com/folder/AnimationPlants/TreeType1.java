package com.folder.AnimationTileSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;

import com.folder.Screen.GameScreen;
import com.folder.Tool.AnimationTileSetCreate;

public class TreeType1 extends AnimationTileSetCreate {
    public TreeType1(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 256 / Boot.INSTANCE.getPPM(), 288 / Boot.INSTANCE.getPPM());
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Tree1"), j * 256, i * 288, 256, 288));
        animation = new Animation<TextureRegion>(1 / 12f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
