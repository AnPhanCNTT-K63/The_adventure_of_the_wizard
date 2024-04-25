package com.folder.Object.InteractiveObject;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;
import com.folder.Object.InteractiveObject.InteractiveObject;
import com.folder.Screen.GameScreen;

public class Wall extends InteractiveObject {
    public Wall(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.WALL_BIT);
    }
}
