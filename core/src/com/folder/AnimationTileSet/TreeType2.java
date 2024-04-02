package com.folder.AnimationTileSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Tool.AnimationTileSetCreate;

public class TreeType2 extends AnimationTileSetCreate {
    public TreeType2(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 160 / Boot.PPM, 256 / Boot.PPM);
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 6; j++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Tree2"), j * 160, i * 256, 160, 256));
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
