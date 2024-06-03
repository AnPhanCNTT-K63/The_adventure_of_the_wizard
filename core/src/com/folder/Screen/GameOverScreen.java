package com.folder.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.Boot;
import com.folder.Tool.GameOverHandle;

public class GameOver implements Screen {

    private SpriteBatch batch;

    private Viewport viewport;
    private Stage stage;

    private TextButton exitToMenu;
    private TextButton playAgain;

    private Boot boot;

    public GameOver(Boot boot) {
        this.boot = boot;
        batch = boot.getBatch();
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setDebug(false);
        table.setFillParent(true);
        table.top();

        Skin skin = new Skin(Gdx.files.internal("skin/skin.json"));

        exitToMenu = new TextButton("EXIT TO MENU SCREEN", skin);
        playAgain = new TextButton("PLAY AGAIN", skin);

        exitToMenu.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameOverHandle.escape = true;
                MenuScreen.INSTANCE.setMenu(true);
            }
        });

        playAgain.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameOverHandle.escape = true;
            }
        });

        table.add(playAgain).width(300).height(100);
        table.row();
        table.add(exitToMenu).width(300).height(100);
        table.padTop(300);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GameOverHandle.escape) {
            stage.dispose();
            boot.setScreen(new GameScreen(boot));
            GameOverHandle.escape = false;
        }

        stage.draw();

    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {

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
