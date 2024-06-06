package com.folder.Object.InteractiveObject;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;
import com.folder.Screen.GameScreen;

public class Door extends InteractiveObject {
    public Door(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.NEXT_DOOR_BIT);
    }
}