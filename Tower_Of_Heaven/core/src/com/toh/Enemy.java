package com.toh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.ScreenUtils;
//Jbump
import com.dongbat.jbump.*;
import com.dongbat.jbump.util.*;
//Shape drawer
import space.earlygrey.shapedrawer.*;
//Pantallas del juego
import com.toh.screens.*;

public class Enemy extends Entity{

	/*En esta clase se definen varios constructores por si son necesarios
	 * pero por ahora, todos realizan la misma tarea que es, establecer algunos valores
	 * de la entidad y añadir la entidad actual a la simulacion JBump, asi como definir
	 * sus colores normales y de daño*/
	
	public Enemy(final TOHGame game, OrthographicCamera camera, Vector2 startPosition, int health) {
		
		super(game, camera, startPosition, health);
		jbItem = game.jbWorld.add(new Item<Entity>(), position.x, position.y, 32, 32);
		jbItem.userData = this;
		
		commonColor = new Color(Color.PURPLE);
		damageColor = new Color(Color.RED);
		
	}
	
	public Enemy(final TOHGame game, OrthographicCamera camera, Vector2 startPosition) {
		
		super(game, camera, startPosition);
		
		jbItem = game.jbWorld.add(new Item<Entity>(), position.x, position.y, 32, 32);
		jbItem.userData = this;
		
		commonColor = new Color(Color.PURPLE);
		damageColor = new Color(Color.RED);
		
	}

	public void render(){
		//Dependiendo si la entidad esta siendo atacada o no, dibujar el rectángulo con sus colores
		//interpolando entre el color normal y el color de daño
		
		game.shapeDrawer.filledRectangle(drawRect, commonColor);
		game.shapeDrawer.rectangle(drawRect, Color.MAGENTA, 5f);
		
		if (beingAttacked){
			commonColor.lerp(damageColor, 0.3f);
			game.shapeDrawer.rectangle(drawRect, new Color(Color.MAGENTA).lerp(new Color(0xb22222ff), 0.3f), 5f);
		}
		
		if(game.player.isAttacking){
			commonColor.lerp(Color.YELLOW, 0.1f);
		} else {
			commonColor.lerp(Color.PURPLE, 0.1f);
		}
		
	}
	
	public void move(){
		//Mover a la entidad en la simulacion
		jbColResult = game.jbWorld.move(jbItem, drawRect.x, drawRect.y, colFilter);
		jbCollisionArray = jbColResult.projectedCollisions;
		
		/*Segun el arreglo de colisiones obtenido, verificar si hay una colision
		con el rectángulo de ataque, si la hay y no estaba siendo atacado el enemigo, 
		le bajamos sus puntos de vida, pero si ya estaba siendo atacada, entonces simplemente
		salimos de la función.
		
		Si no hubo ninguna colisión con un rectángulo de ataque, termina la iteración por el arreglo y
		podemos afirmar que no esta siendo atacada la entidad*/
		for( int i = 0; i < jbCollisionArray.size(); i++ ){
			Collision c = jbCollisionArray.get(i);
			//System.out.print(c.other.userData instanceof Attack);
			if(  !beingAttacked && (c.other.userData instanceof Attack)){
				health-= 25;
				beingAttacked = true;
				return;
			} else if ( (c.other.userData instanceof Attack) )
				return;
		}
		if(beingAttacked)
			beingAttacked = false;
	}
	
	public void act(){
		//La entidad se muere
		if( health < 1 ){
			drawRect.set(0, 0, 0, 0);
			game.jbWorld.remove(jbItem);
			alive = false;
		}
	}
}
