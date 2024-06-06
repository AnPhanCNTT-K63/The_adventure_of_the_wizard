package com.folder.AnimationTileSet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.folder.Boot;
import com.folder.Screen.GameScreen;
import com.folder.Tool.AnimationTileSetCreate;

public class WaterFall extends AnimationTileSetCreate {
    public WaterFall(GameScreen screen, float posX, float posY) {
        super(screen, posX, posY);
        setBounds(posX, posY, 32 / Boot.INSTANCE.getPPM(), 128 / Boot.INSTANCE.getPPM());
        create();
    }

    @Override
    public void create() {
        for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("waterfall"), i*32, 0, 32, 64));
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
