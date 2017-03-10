package com.gdx.phils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    static final int RADIUS = 300, RADIUS_MIN = 150;
    static final int DT_ANGLE = 90;
    public static Texture thinking, waiting, eating, fork, noodles;
    Array<Sprite> sphagetti = new Array<>();

    Array<Sprite> all = new Array<>();

    ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    Movable movable = new Movable() {
        @Override
        public void moveLeftFork(Sprite sprite, int direction) {
            float angle = sprite.getRotation() + DT_ANGLE;

            if (direction == DIRECTION_NEAR) {
                angle += 10;
            } else if (direction == DIRECTION_AWAY) {
                angle -= 10;
            }
            float x, y, w, h;
            float r = RADIUS;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = sprite.getRegionWidth();
            h = sprite.getRegionHeight();
            sprite.setPosition(x - w / 2f, y - h / 2f);
            sprite.setRotation(angle - DT_ANGLE);
        }

        @Override
        public void moveRightFork(Sprite sprite, int direction) {
            float angle = sprite.getRotation() + DT_ANGLE;

            if (direction == DIRECTION_NEAR) {
                angle -= 10;
            } else if (direction == DIRECTION_AWAY) {
                angle += 10;
            }
            float x, y, w, h;
            float r = RADIUS;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = sprite.getRegionWidth();
            h = sprite.getRegionHeight();
            sprite.setPosition(x - w / 2f, y - h / 2f);
            sprite.setRotation(angle - DT_ANGLE);
        }

        @Override
        public void changeSprite(Sprite sprite, AbstractObject.STATE state) {
            if (state == AbstractObject.STATE.EATING) {
                sprite.setTexture(eating);
            } else if (state == AbstractObject.STATE.THINKING) {
                sprite.setTexture(thinking);
            }
        }
    };

    @Override
    public void create() {
        cx = Gdx.app.getGraphics().getWidth() / 2f;
        cy = Gdx.app.getGraphics().getHeight() / 2f;

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(cx, cy, 0);

        thinking = new Texture("thinking.jpg");
        waiting = new Texture("waiting.jpg");
        eating = new Texture("eathing.jpg");
        fork = new Texture("fork2.png");
        noodles = new Texture("noodles.png");

        shapeRenderer = new ShapeRenderer();
        /**
         * game logic
         */
        int size = 5;
        DiningPhilosopher dp = new DiningPhilosopher(size);

        Sprite forkSprite = new Sprite(fork);
        for (Fork f : dp.forks) {
            f.sprite.set(forkSprite);
            f.sprite.setSize(fork.getWidth(), fork.getHeight());
            f.sprite.flip(false, true);

        }

        Sprite thinkingSprite = new Sprite(thinking);
        for (Philosopher phil : dp.phils) {
            phil.sprite.set(thinkingSprite);
            phil.addMovable(movable);
            Sprite sprite = new Sprite(noodles);
            sphagetti.add(sprite);
        }

        for (int i = 0; i < dp.phils.length; i++) {
            all.add(dp.forks[i].sprite);
            all.add(dp.phils[i].sprite);
        }

        final int deltaAngle = 360 / all.size;

        for (int i = 0; i < all.size; i++) {
            int angle = i * deltaAngle;
            Sprite es = all.get(i);

            float x, y, w, h;
            float r = RADIUS;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = es.getRegionWidth();
            h = es.getRegionHeight();
            es.setPosition(x - w / 2f, y - h / 2f);
            es.setRotation(angle - DT_ANGLE);
        }

        for (int i = 0; i < dp.phils.length; i++) {
            Sprite es = dp.phils[i].sprite;
            float angle = es.getRotation() + DT_ANGLE;
            Sprite sprite = sphagetti.get(i);

            float x, y, w, h;
            float r = RADIUS_MIN;
            x = cx + r * MathUtils.cosDeg(angle);
            y = cy + r * MathUtils.sinDeg(angle);
            w = sprite.getRegionWidth();
            h = sprite.getRegionHeight();
            sprite.setPosition(x - w / 2f, y - h / 2f);
            all.add(sprite);
        }


        System.out.println("Starting the eating...");
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(dp.phils[i]);
            thread.start();
        }
    }

    float cx, cy;

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setColor(0, 0, 0, .3f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(cx, cy, 300);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        all.forEach(sprite -> sprite.draw(batch));
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
