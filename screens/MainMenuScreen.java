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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.toh.TOHGame;

public class MainMenuScreen implements Screen {

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	
	BitmapFont font;
	long elapsedTime;
	TextButton boton1,boton2,boton3;
	Skin piel;
	Stage estado;

	
	public MainMenuScreen(final TOHGame game) {
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
		estado= new Stage();
		Gdx.input.setInputProcessor(estado);
		Table tabla= new Table();
		tabla.setPosition(1024/3,450);
		tabla.setFillParent(true);
		tabla.setHeight(500);
		estado.addActor(tabla);
		ScreenUtils.clear(0, 0.2f, 0, 1);
		

		game.spriteBatch.begin();

		font.draw(game.spriteBatch, "TOWER OF HEAVEN", 600/2, 700);

		piel=new Skin(Gdx.files.internal("glassy-ui.json"));

		boton1= new TextButton("Comenzar",piel,"small");
		boton1.setPosition(100,150);
		boton1.setHeight(40);
		boton1.setWidth(150);
		boton1.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new CharSelectScreen(game));
				dispose();
				return true;

			}
		});

		tabla.addActor(boton1);

		boton2= new TextButton("Puntaje",piel,"small");
		boton2.setPosition(100,50);
		boton2.setHeight(40);
		boton2.setWidth(150);
		boton2.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new ScoreScreen(game));
				dispose();
				return true;

			}
		});

		tabla.addActor(boton2);





		boton3= new TextButton("Salir del juego",piel,"small");
		boton3.setPosition(100,-50);
		boton3.setHeight(40);
		boton3.setWidth(150);
        boton3.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.exit(0);
				return true;
			}
		});

		tabla.addActor(boton3);

		estado.draw();
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
