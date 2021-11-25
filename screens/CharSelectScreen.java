package com.toh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.toh.TOHGame;

public class CharSelectScreen implements Screen {

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	BitmapFont font;
	TextButton botonx;
	Skin piel1;
	Stage estado1;
	
	long elapsedTime;
	
	public CharSelectScreen(final TOHGame game) {
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
		estado1= new Stage();
		Gdx.input.setInputProcessor(estado1);
		Table tabla1= new Table();
		tabla1.setPosition(1024/3,450);
		tabla1.setFillParent(true);
		tabla1.setHeight(500);
		estado1.addActor(tabla1);
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		
		game.spriteBatch.begin();

		font.draw(game.spriteBatch, "Pantalla de\nSeleccion de personajes", 600/2, 700);
		piel1=new Skin(Gdx.files.internal("glassy-ui.json"));

		botonx= new TextButton("Personaje 1",piel1,"small");
		botonx.setPosition(100,150);
		botonx.setHeight(40);
		botonx.setWidth(150);
		botonx.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new LevelSelectScreen(game));
				dispose();
				return true;
			}
		});
		tabla1.add(botonx);
		estado1.draw();
		game.spriteBatch.end();
		
		//Si ha pasado 1 segundo desde la creación de la pantalla y se toca la pantalla
		//se cambia a la siguiente pantalla
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
