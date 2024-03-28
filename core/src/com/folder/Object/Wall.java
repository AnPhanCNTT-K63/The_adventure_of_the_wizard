package com.folder.Object;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;
import com.folder.GameScreen;

public class Wall extends InteractiveObject {
    public Wall(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.WALL_BIT);
    }
}
