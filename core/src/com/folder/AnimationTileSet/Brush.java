package com.folder.AnimationTileSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Tool.AnimationTileSetCreate;

public class Brush extends AnimationTileSetCreate {
    public Brush(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 128 / Boot.PPM, 96 / Boot.PPM);
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Bush"), j * 128, i * 96, 128, 96));
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
