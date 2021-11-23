package com.toh;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
//Jbump
import com.dongbat.jbump.*;
import com.dongbat.jbump.util.*;
//Shape drawer
import space.earlygrey.shapedrawer.*;

/**
 * Esta clase se encargará de generar los niveles aleatorios, estableciendo un
 * jugador, los enemigos y diferentes objetos que permitan avanzar entre niveles, asi como las
 * paredes, etc.<br>
 * <br>
 * Actualmente, esta clase tiene un generador de niveles basado en una cadena, pero este código será 
 * reemplazado por el algoritmo de generación de niveles aleatorios.
 * Lo único que debe ser constante son LOS ARREGLOS Y LA INSTANCIA A CREAR DEL JUGADOR
 * @author einhander
 *
 */
public class LevelGenerator {

	final TOHGame game;
	OrthographicCamera camera;
	public Player player;				//Jugador a crear
	public Array<Enemy> enemies;		//Arreglo de enemigos dentro del mapa
	public Array<Rectangle> walls;		//Arreglo de paredes dentro del mapa
	public Array<Item<Entity>> jbWalls; //Arreglo de Items de simulacion JBump

	int mapX, mapY;
	String testmap =  "+++++++---++++++++++++++++++++++\n"
					+ "+-----+--------+---------------+\n"
					+ "+-----+---e----+---e--e-eeeee--+\n"
					+ "+-e---+--------+----e-e-----e--+\n"
					+ "+-----+--------+++----e-----e--+\n"
					+ "+-----+--+++-----+----e--eeee--+\n"
					+ "+-----+----+-e---++++----------+\n"
					+ "+-----++++++-----+-------------+\n"
					+ "+-------------++++-------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------+++---------+\n"
					+ "+---------++++-------+---------+\n"
					+ "+---------+----------+---------+\n"
					+ "+---------+----------+---------+\n"
					+ "++++------+----------+----++++++\n"
					+ "----+-----+----------+-----+----\n"
					+ "----+++++++----p-----++++++++---\n"
					+ "----+-----+----------+-------+--\n"
					+ "++++------+----------+-----+++++\n"
					+ "+---------+----------+---------+\n"
					+ "+---------+-------++++---------+\n"
					+ "+---------++++-----------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+------------------------------+\n"
					+ "+--------------------e-----e---+\n"
					+ "+------------------------------+\n"
					+ "+----+----------+----e-----e---+\n"
					+ "+-e--+----------+---ee---------+\n"
					+ "+----++++++++++++--------------+\n"
					+ "+------e---e-----e-------------+\n"
					+ "++++++++++++++++++++++++++++++++\n";
	public int wallWidth = 32;
	public int wallHeight = 32;
	
	public LevelGenerator(final TOHGame game, OrthographicCamera camera) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.camera = camera;
		
		jbWalls = new Array<Item<Entity>>();
		enemies = new Array<Enemy>();
		walls = new Array<Rectangle>();
	}
	
	/**
	 * Esta función creará los niveles de manera aleatoria según los parámetros indicados
	 */
	public void generateLevel(){
		String[] lines = testmap.split("\n");
		for( int i=lines.length-1; i >= 0; i-- ){
			for( int j = 0; j < lines[i].length(); j++ ){
				
				int mapX = j*wallWidth;
				int mapY = (lines.length-i)*wallHeight;
				
				/**
				 * + : pared
				 * p : jugador
				 * e : enemigo
				 */
				
				if( lines[i].charAt(j) == '+' ){
					walls.add( new Rectangle(mapX, mapY, wallWidth, wallHeight) );
					jbWalls.add( game.jbWorld.add(new Item<Entity>(), mapX, mapY, wallWidth, wallHeight) );
				} else if( lines[i].charAt(j) == 'p' ){
					player = new Player(game, camera, new Vector2(mapX, mapY) );
					game.player = player;
				} else if( lines[i].charAt(j) == 'e' ){
					enemies.add( new Enemy( game, camera, new Vector2(mapX, mapY) ) );
				}
			}
		}
	}
}
