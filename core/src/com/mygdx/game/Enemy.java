package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by gio on 13/03/16.
 */
public class Enemy {
    public Texture EnemyTexture;
    public Rectangle EnemyCar;
    public int v;

    public Enemy (int width, int height, int x, int y, int v, Texture EnemyTexture) {
        this.EnemyTexture = EnemyTexture;
        //new Texture("EnemyCar.png");
        this.EnemyCar = new Rectangle();
        this.EnemyCar.x = x;
        this.EnemyCar.y = y;
        this.EnemyCar.width = width;
        this.EnemyCar.height = height;
        this.v = v;
    }
}
