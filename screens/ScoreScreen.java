package com.toh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.toh.TOHGame;

public class ScoreScreen implements Screen {

	final TOHGame game;
	BitmapFont font;
	long elapsedTime;
	public ScoreScreen(final TOHGame game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		font = game.font;
		elapsedTime = TimeUtils.millis();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		ScreenUtils.clear(0.3f, 1, 0, 1);


		game.spriteBatch.begin();


		font.draw(game.spriteBatch, "Pantalla de mostrar puntaje xd jaja", 600/2, 800/2);
		game.spriteBatch.end();

		//Si ha pasado 1 segundo desde la creación de la pantalla y se toca la pantalla
		//se cambia a la siguiente pantalla
		if( Gdx.input.isTouched() && TimeUtils.millis() - elapsedTime > 1e3){
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}

	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
