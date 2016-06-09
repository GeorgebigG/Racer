package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Enemy;
import com.mygdx.game.Main;

/**
 * Created by gio on 09/04/16.
 */
public class PlayScreen implements Screen {

    public static final int PAVAROTI = 10;
    public OrthographicCamera camera;
    public Viewport gamePort;
    public SpriteBatch batch;
    public Texture baground;
    public Texture PlayerCarTexture;
    public static Rectangle PlayerCar;
    public int v = 5;
    public static Enemy[] enemies;
    public static int firstRoadY;
    public static int secondRoadY;
    public BitmapFont bitmapFont;
    public static Music mainMusic;
    public static long score;
    public float fontScale;
    public static Main main;
    public static double Time;

    public static MyGameCallBack myGameCallBack;

    public static String yourSpeedIs;
    public static String yourKMHFPS;

    public PlayScreen(Main main) {
        this.main = main;

        batch = main.batch;
        baground = new Texture("Road.png");
        PlayerCarTexture = new Texture("PlayerCar.png");

        PlayerCar = new Rectangle();
        PlayerCar.width = Gdx.graphics.getWidth() / 6;
        PlayerCar.height = Gdx.graphics.getHeight() / 8;
        PlayerCar.x = (Gdx.graphics.getWidth() / 2) - (PlayerCar.width / 2);
        PlayerCar.y = Gdx.graphics.getHeight() / 10;

        enemies = new Enemy[3];
        setEnemyOption();

        firstRoadY = 0;
        secondRoadY = Gdx.graphics.getHeight();
        bitmapFont = new BitmapFont();
        PlayerCar.width -= PlayerCar.width / 5;

        //Bazil-Aj-yaj-ya_zabyl_chto_bylo_vchera_no_pomnyu_chto_budet_zavtra_novaya_igra_(mp3.cc).mp3
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("Bazil-Aj-yaj-ya_zabyl_chto_bylo_vchera_no_pomnyu_chto_budet_zavtra_novaya_igra_(mp3.cc).mp3"));
        mainMusic.setLooping(true);
        //mainMusic.play();

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gamePort = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        //new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        bitmapFont.setColor(new Color(2f, 2f, 0, 2f));
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fontScale = bitmapFont.getScaleX();
        bitmapFont.getData().setScale(fontScale, Gdx.graphics.getHeight() / 250);
    }

    public void update(float dt) {
        move();
        enemyLocation();
        checkCarCrashe();
        score++;
    }

    @Override
    public void render (float delta) {
        update(delta);

        Time += delta;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(baground, 0, firstRoadY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(baground, 0, secondRoadY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(PlayerCarTexture, PlayerCar.x, PlayerCar.y, PlayerCar.width, PlayerCar.height);
        for (int i = 0; i < enemies.length; i++)
            batch.draw(new Texture("EnemyCar.png"), enemies[i].EnemyCar.x, enemies[i].EnemyCar.y,
                    enemies[i].EnemyCar.width, enemies[i].EnemyCar.height);

        bitmapFont.draw(batch, yourSpeedIs + v + "km/h  |  Your fps is: " + Gdx.graphics.getFramesPerSecond() + "  |  Your score is: " + score,
                Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 20);
        batch.end();
    }

    public static void setEnemyOption () {
        for (int i = 0; i < enemies.length; i++)
            enemies[i] = new Enemy((int)(PlayerCar.width - (PlayerCar.width / 2.5)), Gdx.graphics.getHeight() / 7,
                    (int)(Math.random() * (Gdx.graphics.getWidth() - PlayerCar.width)),
                    Gdx.graphics.getHeight() + (int)(Math.random() * Gdx.graphics.getHeight() / 3),
                    (int)(Math.random() * 4) + 1, new Texture("EnemyCar.png"));
    }

    public void checkCarCrashe() {
        for (int i = 0; i < enemies.length; i++) {
            if (PlayerCar.overlaps(enemies[i].EnemyCar)) {
                mainMusic.stop();
                myGameCallBack.startActivity(score, (long) Time);
            }
        }
    }

    public void move () {
        firstRoadY -= v;
        secondRoadY -= v;

        for (int i = 0; i < enemies.length; i++) {
            enemies[i].EnemyCar.y += enemies[i].v - v;
        }

        if (secondRoadY - v <= 0) {
            firstRoadY = 0;
            secondRoadY = Gdx.graphics.getHeight();
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android)
            PlayerCar.x -= (Gdx.input.getAccelerometerX()) * 3;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) PlayerCar.x -= PAVAROTI;
        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) PlayerCar.x += PAVAROTI;

        if (PlayerCar.x < 0) PlayerCar.x = 0;
        else if (PlayerCar.x > Gdx.graphics.getWidth() - PlayerCar.width)
            PlayerCar.x = Gdx.graphics.getWidth() - PlayerCar.width;

        if (Gdx.app.getType() == Application.ApplicationType.Android)
            if (Gdx.input.getAccelerometerZ() != 0)
                v = (int) (Gdx.input.getAccelerometerZ() * 2);


        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) v++;
        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) v--;



        if (v < 5) v = 5;
        if (v > 30) v = 30;
    }

    public void enemyLocation () {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].EnemyCar.y <= 0 - enemies[i].EnemyCar.height)
                enemies[i] = new Enemy((int)(PlayerCar.width - (PlayerCar.width / 4)), Gdx.graphics.getHeight() / 7,
                        (int)(Math.random() * (Gdx.graphics.getWidth() - PlayerCar.width)),
                        Gdx.graphics.getHeight() + (int)(Math.random() * Gdx.graphics.getHeight() / 3),
                        (int)(Math.random() * 4) + 1, new Texture("EnemyCar.png"));
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    public interface MyGameCallBack {
        public void startActivity(long score, long Time);
    }
}
