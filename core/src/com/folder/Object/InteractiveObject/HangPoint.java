package com.folder.Object.InteractiveObject;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.folder.Boot;

import com.folder.Object.InteractiveObject.InteractiveObject;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;

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
