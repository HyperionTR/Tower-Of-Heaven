package com.toh.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.toh.Enemy;
import com.toh.LevelGenerator;
import com.toh.Player;
import com.toh.TOHGame;

import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * 
 * @author einhander
 * Esta clase es la pantalla en la que permanecerá la mayor parte del tiempo el jugador.
 * En ella se mostrarán cada uno de los niveles, y peleas que el usuario puede realizar.
 *
 */
public class DungeonScreen implements Screen{

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	
	SpriteBatch batch;
	PolygonSpriteBatch polyBatch;
	ShapeDrawer shape;
	
	//Camara para observar el mundo 2d
	OrthographicCamera camera;
	
	Player player;
	LevelGenerator lg;
	
	//Constructor de la pantalla
	public DungeonScreen(final TOHGame game) {
		
		//Obtenemos los campos de game
		this.game = game;
		batch = game.spriteBatch;
		polyBatch = game.polyBatch;
		shape = game.shapeDrawer;
		
		//Inicializacion y tamaño de la vista de la camara
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		//Generamos un nuevo nivel y obtenemos la instancia
		//del jugador creda por el generador de niveles
		lg = new LevelGenerator(this.game, camera);
		lg.generateLevel();
		player = lg.player;
		
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		//Limpiamos la pantalla cada frame y actualizamos las matrices de la cámara
		ScreenUtils.clear(0, 0.3f, 0.4f, 1);
		camera.update();
		polyBatch.setProjectionMatrix(camera.combined);
		
		//Acciones y movimiento del jugador
		if( player.alive )
			player.act();
		if( player.alive )
			player.move();
		
		//Del arreglo de enemigos creado por el generador de niveles
		//realizamos las acciones y movimiento de cada enemigo si es que estos siguen vivos
		for( Enemy e: lg.enemies ){
			if( e.alive )
				e.act();
			if( e.alive )
				e.move();
		}
		
		//Iniciamos el dibujado de polígonos por lotes y dibujamos cada pared, enemigo y jugador
		polyBatch.begin();
			player.render();
			
			for( Rectangle r: lg.walls ){
				game.shapeDrawer.rectangle( r.x, r.y, lg.wallWidth, lg.wallHeight );
			}
			for( Enemy e: lg.enemies ){
				if( e.alive )
					e.render();
			}
		polyBatch.end();
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
