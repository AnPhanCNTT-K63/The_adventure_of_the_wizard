package com.folder.Object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.GameScreen;

public class HangPoint extends InteractiveObject {

    public HangPoint(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.HANG_POINT_BIT);
    }

    public void setPositionHang() {
        MainCharacter.xHang = rect.getX() / Boot.PPM;
        MainCharacter.yHang = rect.getY() / Boot.PPM;
    }
}
