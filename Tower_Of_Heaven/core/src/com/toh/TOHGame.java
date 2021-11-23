package com.toh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dongbat.jbump.World;
import space.earlygrey.shapedrawer.*;

import com.toh.screens.CutsceneScreen;
import com.toh.screens.DungeonScreen;

/**
 * Esta clase será como la "principal" del juego.
 * Se encargará de mantener las instancias de varios objetos
 * que no deben o no necesitan ser declarados nuevamente dentro de otras
 * clases
 * @author einhander
 *
 */
public class TOHGame extends Game {
	
	public World<Entity> jbWorld;	//Mundo de simulación JBump que tiene Entidades como objetos de datos
	public SpriteBatch spriteBatch; //Manejador de sprites por lotes
	public PolygonSpriteBatch polyBatch; //Sprites por lotes optimizados para polígonos
	public ShapeDrawer shapeDrawer;		 //Dibujador de polígonos básicos (extensión de libGDX)
	public BitmapFont font;			//Manejador de fuentes tipo bitmap (sin buen escalado)
	public Player player;
	
	//Necesario para ShapeDrawer
	Texture whitePixel;
	
	public void create () {
		
		jbWorld = new World<Entity>();
		spriteBatch =  new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();
		
		//Leemos la textura de pixel en blanco necesario para ShapeDrawer
		whitePixel = new Texture( Gdx.files.internal("white_pixel.bmp") );
		shapeDrawer = new ShapeDrawer(polyBatch, new TextureRegion(whitePixel, 1, 1));
		//Usamos la fuente por defecto y la escalamos al doble
		font = new BitmapFont();
		font.getData().setScale(2); 
		//Cambiamos a la pantalla de Cutscenes
		this.setScreen( new DungeonScreen(this) );
	}

	public void render () {
		super.render();		//IMPORTANTE DE NO REMOVER
	}
	
	//Nos deshacemos de los elementos creados anteriormente, asi com de la pantalla actual
	public void dispose () {
		spriteBatch.dispose();
		polyBatch.dispose();
		whitePixel.dispose();
		font.dispose();
		getScreen().dispose();
	}
}
