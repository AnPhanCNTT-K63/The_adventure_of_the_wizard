package com.folder.Object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.GameScreen;
import org.w3c.dom.css.Rect;

public class Ground extends InteractiveObject {
    public Ground(GameScreen screen, RectangleMapObject object) {
        super(screen, object);
        setCategory(Boot.GROUND_BIT);
    }
}
