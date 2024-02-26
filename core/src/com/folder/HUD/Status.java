package com.folder.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.Boot;

public class Status {

    private Texture texture;
    private BitmapFont font;

    public Stage stage;
    private Viewport viewport;

    private static int score;

    static Label scoreLabel;
    Label freestyleTest;

    private UI ui;

    public Status(SpriteBatch batch) {
        viewport = new FitViewport(Boot.screenWidth, Boot.screenHeight);
        stage = new Stage(viewport, batch);

        texture = new Texture("hud.png");
        ui = new UI(texture, -50, Boot.screenHeight - (float) texture.getHeight() / 3, Boot.screenWidth * 3 / 8, Boot.screenHeight / 4);

        font = new BitmapFont();
        font.getData().setScale(2);


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        BitmapFont fontFree = generator.generateFont(parameter);

        scoreLabel = new Label(String.format("%6d", score), new Label.LabelStyle(fontFree, Color.WHITE));
        freestyleTest = new Label("Test", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreLabel).padTop(75).padRight(820);
        stage.addActor(table);
        stage.addActor(ui);

    }

    public static void addScore(int scoreAdded) {
        score += scoreAdded;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void dispose() {
        texture.dispose();
        stage.dispose();
    }
}
