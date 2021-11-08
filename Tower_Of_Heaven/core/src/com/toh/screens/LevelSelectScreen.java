package com.toh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.toh.TOHGame;

public class LevelSelectScreen implements Screen {

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	
	long elapsedTime;
	
	public LevelSelectScreen(final TOHGame game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		elapsedTime = TimeUtils.millis();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		ScreenUtils.clear(0, 0.2f, 0.2f, 1);
		
		game.spriteBatch.begin();
		game.font.draw(game.spriteBatch, "Pantalla de\nSeleccion de nivel", 600/2, 800/2);
		game.spriteBatch.end();
		
		//Si ha pasado 1 segundo desde la creación de la pantalla y se toca la pantalla
		//se cambia a la siguiente pantalla
		if( Gdx.input.isTouched() && TimeUtils.millis() - elapsedTime > 1e3 ){
			game.setScreen(new DungeonScreen(game));
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
