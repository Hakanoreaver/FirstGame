package Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.first.game.FirstGame;
import com.first.game.MyInputProcessor;
import com.first.game.Tree;

import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameScreen implements Screen, InputProcessor{
    private Stage stage;
    private Game game;
    ArrayList<Tree> trees;
    Tree t;
    Window pause;
    boolean paused = false;
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
        Gdx.input.setInputProcessor(this);
        t = new Tree(50, 50);
        stage.addActor(t);
        t.addAction(Actions.moveTo(300, 300, 5));

        pause = new Window("Paused", FirstGame.skin);
        pause.add(new TextButton("Unpause", FirstGame.skin)); //Add a new text button that unpauses the game.
        pause.pack(); //Important! Correctly scales the window after adding new elements.
        float newWidth = 400, newHeight = 200;
        pause.setBounds((Gdx.graphics.getWidth() - newWidth ) / 2,
                (Gdx.graphics.getHeight() - newHeight ) / 2, newWidth , newHeight ); //Center on screen.
        stage.addActor(pause);
        pause.setVisible(false);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
            rendering();
            update();
    }

    private void rendering() {
        switch (state) {
            case RUN :
                Gdx.graphics.requestRendering();
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.act();
                stage.draw();
                break;
            case PAUSE :
                break;
        }
    }

    private void update() {
        switch (state) {
            case RUN :
                System.out.println("Running");
                break;
            case PAUSE :
                System.out.println("Paused");
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        state = State.PAUSE;
    }

    @Override
    public void resume() {
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
        if (keycode == 131) {
            if (state == state.RUN){
                pause.setVisible(true);
                pause();
                return true;
            }
            else if (state == state.PAUSE) {
                pause.setVisible(false);
                resume();
                return true;
            }
        }
        return true;
    }
    @Override
    public boolean keyUp (int keycode) {
        return true;
    }
    @Override
    public boolean keyTyped (char character) {
        return true;
    }
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (state == state.RUN) {
            t.clearActions();
            System.out.println("X " + x + " Y " + y);
            t.addAction(Actions.moveTo(x, 1080 - y, 1));
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
