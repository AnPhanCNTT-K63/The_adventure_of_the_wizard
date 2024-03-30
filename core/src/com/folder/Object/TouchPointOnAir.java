package com.folder.Object;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;
import com.folder.GameScreen;

public class TouchPointOnAir extends InteractiveObject {

    public TouchPointOnAir(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.TOUCH_POINT_ON_AIR);
    }
}