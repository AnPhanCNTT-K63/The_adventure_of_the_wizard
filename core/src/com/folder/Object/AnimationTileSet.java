package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.GameScreen;

import java.awt.*;

public class AnimationTileSet extends Sprite {
    Animation<TextureRegion> tileSet;
    TextureRegion region;
    Array<TextureRegion> frames;
    float stateTime;

    public AnimationTileSet(GameScreen screen) {
        frames = new Array<TextureRegion>();
        stateTime = 0;
        setBounds(0, 0, 256 / Boot.PPM, 288 / Boot.PPM);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                frames.add(new TextureRegion(screen.getAnimationTileSetAtlas().findRegion("Tree1"), j * 256, i * 288, 256, 288));
        tileSet = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        setBounds((28f - 30f) / Boot.PPM, (45.33f - 20) / Boot.PPM, 256 / Boot.PPM, 288 / Boot.PPM);
        region = tileSet.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
