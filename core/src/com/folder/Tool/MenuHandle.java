package com.folder.Tool;

import com.badlogic.gdx.Gdx;
import com.folder.Tool.KeyUpHandle;

public class MenuHandle {
    public static boolean isMenu;
    public static boolean escape;

    public MenuHandle() {
        isMenu = false;
        escape = false;
        Gdx.input.setInputProcessor(new KeyUpHandle());
    }
}
