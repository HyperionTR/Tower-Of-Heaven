package com.toh.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.toh.TOHGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
//Jbump
import com.dongbat.jbump.*;
import com.dongbat.jbump.util.*;
//Shape drawer
import space.earlygrey.shapedrawer.*;
//Pantallas del juego
import com.toh.screens.*;

/**
 * 
 * @author einhander
 *Esta es la pantalla donde se mostrará dialogo e imágenes que introduscan al jugador
 *a distintos puntos de la historia. Se utiliza al inicio del juego y al terminar los primeros 3 niveles
 *
 */
public class CutsceneScreen implements Screen {

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	
	BitmapFont font;
	long elapsedTime; //Simplemente para esperar 1 segundo
	
	public CutsceneScreen(final TOHGame game) {
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
		
		ScreenUtils.clear(0.2f, 0, 0, 1);
		
		
		game.spriteBatch.begin();
		font.draw(game.spriteBatch, "Pantalla de introduccion y escenas", 600/2, 800/2);
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
