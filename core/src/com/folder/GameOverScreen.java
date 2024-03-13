package com.folder;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.folder.Boot.screenHeight;
import static com.folder.Boot.screenWidth;

public class GameOverScreen implements Screen {

    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Xóa màn hình với màu đen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Xóa bộ nhớ đệm màu

        // Hiển thị hình ảnh 'over'
        Texture overTexture = new Texture("gameOverScreen.jpg"); // Tải hình ảnh 'over' từ tệp 'over_image.png'
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        batch.draw(overTexture, (screenWidth-overTexture.getWidth())/2,(screenHeight-overTexture.getHeight())/2); // Vẽ hình ảnh 'over' tại vị trí giữa trên màn hình
        batch.end();

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
