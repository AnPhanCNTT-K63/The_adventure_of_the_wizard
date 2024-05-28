package com.folder.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.Boot;
import com.folder.Screen.GameScreen;

public class Status {

    private Texture texture;

    private Stage stage;
    private Viewport viewport;

    public static int score;

    public static Label scoreLabel;
    private Label textLabel;

    private CoinHUD ui;
    private HeartHUD heartHUD;

    public Status(SpriteBatch batch, GameScreen screen) {
        viewport = new FitViewport(Boot.screenWidth, Boot.screenHeight);
        stage = new Stage(viewport, batch);
        texture = new Texture("SpriteSheet/coin.png");
        ui = new CoinHUD(texture, 40, Boot.screenHeight - (float) texture.getHeight() / 2 - 10, 128, 128);

        heartHUD = new HeartHUD();

        Table table = new Table();
        table.setDebug(true);
        table.setFillParent(true);
        table.top();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HalloweenSlimePersonalUse-4B80D.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        BitmapFont fontFree = generator.generateFont(parameter);

        scoreLabel = new Label(String.format("%6d", score), new Label.LabelStyle(fontFree, Color.WHITE));
        scoreLabel.setText(String.format("%06d", score));
        textLabel = new Label("This is a test", new Label.LabelStyle(fontFree, Color.WHITE));
        table.add(scoreLabel).padTop(170).padRight(1450);
        table.row();
        stage.addActor(ui);
        stage.addActor(heartHUD);
        stage.addActor(table);
    }

    public static void addScore(int scoreAdded) {
        score += scoreAdded;
    }

    public void update() {
        heartHUD.update();
    }

    public void dispose() {
        heartHUD.dispose();
        texture.dispose();
        stage.dispose();
    }


    public Stage getStage() {
        return stage;
    }
}
