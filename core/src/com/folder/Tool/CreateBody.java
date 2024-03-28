package com.folder.Tool;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.folder.GameScreen;
import com.folder.Object.Ground;
import com.folder.Object.Wall;

public class CreateBody {
    public CreateBody(GameScreen screen) {
        TiledMap map = screen.getMap();

        for (RectangleMapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class))
            new Ground(screen, object);

        for (RectangleMapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class))
            new Wall(screen, object);

    }
}
