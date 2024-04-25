package com.folder.Object.InteractiveObject;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;

import com.folder.Object.InteractiveObject.InteractiveObject;
import com.folder.Screen.GameScreen;

public class Ladder extends InteractiveObject {
    public Ladder(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.LADDER_POINT_BIT);
    }
}
