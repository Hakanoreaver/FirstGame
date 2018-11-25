package Screens;

import Algorithms.FrameRate;
import Algorithms.QuadTree;
import Sprites.Player;
import Sprites.Terrain;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.first.game.FirstGame;
import com.first.game.Resource;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen, InputProcessor{
    static boolean up = false, down = false, left = false, right = false;
    private Stage stage;
    private Game game;
    Body body;
    ArrayList<Resource> resources;
    Resource t;
    Window pause;
    Player p, p2;
    boolean paused = false;
    Animation<ImageIcon> anim;
    QuadTree tree = new QuadTree(0, new Rectangle(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    FrameRate frameRate = new FrameRate();
    public enum State {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }
    private State state = State.RUN;

    public GameScreen(Game aGame) {
        game = aGame;
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
        stage = new Stage(new ScreenViewport());
        Gdx.graphics.setResizable(true);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor( stage );
        multiplexer.addProcessor( this ); // Your screen
        Gdx.input.setInputProcessor( multiplexer );
        createPauseMenu();
        p = new Player(300,500);
        stage.addActor(p);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
            rendering();
            update();
    }

    private void createPauseMenu() {
        pause = new Window("Paused", FirstGame.skin);
        VerticalGroup v = new VerticalGroup();
        v.space(10);
        TextButton temp = new TextButton("Resume", FirstGame.skin);
        temp.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();
            }
        } );
        v.addActor(temp);//Add a new text button that unpauses the game.
        TextButton temp3 = new TextButton("Load", FirstGame.skin);
        temp3.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quit();
            }
        } );
        v.addActor(temp3);//Add a new text button that quits the game.
        TextButton temp4 = new TextButton("Save", FirstGame.skin);
        temp4.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quit();
            }
        } );
        v.addActor(temp4);//Add a new text button that quits the game.
        TextButton temp2 = new TextButton("Exit", FirstGame.skin);
        temp2.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quit();
            }
        } );
        v.addActor(temp2);//Add a new text button that quits the game.
        pause.add(v);
        pause.pack();
        float newWidth = 400, newHeight = 600;
        pause.setBounds((Gdx.graphics.getWidth() - newWidth ) / 2, (Gdx.graphics.getHeight() - newHeight ) / 2, newWidth , newHeight );
        stage.addActor(pause);
        pause.setVisible(false);
        addActors();
        fillCollisionTable();

    }

    private void addActors() {
        for(int y = 0; y < 48; y+=4) {
            for (int i = 0; i < 1920; i += 4) {
                Terrain temp = new Terrain(i, y + 300);
                stage.addActor(temp);
            }
        }
    }

    public void fillCollisionTable() {
        tree.clear();
        for(Actor s : stage.getActors()) {
            if(s != p && s != pause)
                tree.insert(new Rectangle((int) s.getX(), (int) s.getY(), (int) s.getWidth(), (int) s.getHeight()));
            System.out.println(s.getHeight());

        }
    }

    public void checkCollisions() {
        ArrayList<Rectangle> returnObjects = new ArrayList<Rectangle>();
            returnObjects.clear();
            tree.retrieve(returnObjects, new Rectangle((int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight()));
            System.out.println(returnObjects.size());
            Rectangle temp1 = new Rectangle((int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight());
            for (int x = 0; x < returnObjects.size(); x++) {
                Rectangle temp2 = returnObjects.get(x);
                if(temp1.intersects(temp2) || temp2.intersects(temp1)){
                    p.collide(temp2);
                    p.setDy(0);
                    p.setGrounded(true);
                }
            }


    }

    private void rendering() {
        switch (state) {
            case RUN :
                checkCollisions();
                Gdx.graphics.requestRendering();
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.act();
                stage.draw();
                frameRate.render();
                break;
            case PAUSE :
                Gdx.graphics.requestRendering();
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.act();
                stage.draw();
                break;
        }
    }

    public void quit() {
        System.out.println("Exiting");
        System.exit(0);
    }
    private void update() {
        switch (state) {
            case RUN :
                frameRate.update();
                break;
            case PAUSE :
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        FirstGame.paused = true;
        pause.setVisible(true);
        pause.toFront();
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        FirstGame.paused = false;
        pause.setVisible(false);
        state = State.RUN;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public boolean keyDown (int keycode) {
        System.out.println(keycode);
        if (keycode == 131) {
            if (state == state.RUN){
                pause();
                return true;
            }
            else if (state == state.PAUSE) {
                resume();
                return true;
            }
        }
        if(keycode == 51) { //up
            p.setDy(10);
            up = true;
        }
        if(keycode == 29) { //right
            p.setDx(-10);
            right = true;
        }
        if(keycode == 32) { //left
            p.setDx(10);
            left = true;
        }
        if(keycode == 47) { //down
            p.setDy(-10);
            down = true;
        }

        if(keycode == 62) { //down
            p.jump();
        }
        return true;
    }
    @Override
    public boolean keyUp (int keycode) {
        if(keycode == 51) { //up
            Player.setDy(0);
            if(down == true) {
                Player.setDy(-10);
            }
            up = false;
        }
        if(keycode == 29) { //right
            Player.setDx(0);
            if(left == true) {
                Player.setDx(10);
            }
            right = false;
        }
        if(keycode == 32) { //left
            Player.setDx(0);
            if(right == true) {
                Player.setDx(-10);
            }
            left = false;
        }
        if(keycode == 47) { //down
            Player.setDy(0);
            if(up == true) {
                Player.setDy(10);
            }
            down = false;
        }
        return true;
    }
    @Override
    public boolean keyTyped (char character) {
        return true;
    }
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (state == state.RUN) {
            System.out.println("X " + x + " Y " + y);
            return true;
        }
        return true;
    }
    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return true;
    }
    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return true;
    }
    @Override
    public boolean mouseMoved (int x, int y) {
        return true;
    }
    @Override
    public boolean scrolled (int amount) {
        return true;
    }
}
