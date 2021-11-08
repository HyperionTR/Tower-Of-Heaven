package com.toh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
//Jbump
import com.dongbat.jbump.*;
import com.dongbat.jbump.util.*;
//Shape drawer
import space.earlygrey.shapedrawer.*;

/**
 * CLASE POSIBLEMENTE TEMPORAL PARA EL MANEJO DEL JUGADOR
 * @author einhander
 *
 */
public class Player extends Entity{

	Attack attack;			//Entidad de ataque
	long attackTime = 300;	//en milisegundos
	long swordOutTime;		//Tiempo que ha estado atacando
	boolean isAttacking = false;
	
	public Player(final TOHGame game, OrthographicCamera camera, Vector2 startPosition ) {
		
		super(game, camera, startPosition);
		
		//Añadir al ítem al mundo JBump y establecer su UserData a esta instancia
		jbItem = game.jWorld.add(new Item<Entity>(), position.x, position.y, rectWidth, rectHeight);
		
		//Este parámetro de Item, nos permite identificar cualquier cosa que necesitemos
		//para cualquier motivo, en este caso, la usaremos para el filtro de colisión 
		//en la clase TOHCollisionFilter
		jbItem.userData = this;
		
		System.out.print(jbItem.userData instanceof Player);
		
	}
	
	public void move(){
		//Implementar movimiento con las teclas
		//E interpolación con el targetSpeed, así como
		//el movimiento en el mundo Jbump
		
		if( Gdx.input.isKeyPressed(Keys.W) )
			targetSpeed.y = maxSpeed * Gdx.graphics.getDeltaTime();
		else if( Gdx.input.isKeyPressed(Keys.S) )
			targetSpeed.y = -maxSpeed * Gdx.graphics.getDeltaTime();
		else
			targetSpeed.y = 0;
		
		if( Gdx.input.isKeyPressed(Keys.D) )
			targetSpeed.x = maxSpeed * Gdx.graphics.getDeltaTime();
		else if( Gdx.input.isKeyPressed(Keys.A) )
			targetSpeed.x = -maxSpeed * Gdx.graphics.getDeltaTime();
		else
			targetSpeed.x = 0;
		
		//Interpolar la velocidad hacia targetSpeed según la aceleración
		speed.lerp(targetSpeed, acceleration);
		
		//Obtenemos la posicion el cursor y la convertimos al sistema de coordenadas del juego
		//Es decir, con Y positiva hacia arriba y X positiva a la derecha
		cursorPos.x = Gdx.input.getX();
		cursorPos.y = Gdx.input.getY();
		camera.unproject(cursorPos);
		
		//Cambio de la posicion
		position.x += speed.x;
		position.y += speed.y;

		//Movimiento de la entidad mediante su Item en JBump
		game.jWorld.move( jbItem, position.x, position.y, colFilter);
		//Obtener el rectángulo simulado en JBump
		jbRect = game.jWorld.getRect(jbItem);
		
		//Hacer la posicion de la entidad coincidir según la simulación
		position.x = jbRect.x;
		position.y = jbRect.y;

		//Hacer las coordenadas del rectángulo de simulación coincidir la posicion de la entidad
		drawRect.x = position.x;
		drawRect.y = position.y;

		//álgebra de vectores  cursorPos - position = direction
		direction.set(cursorPos.x, cursorPos.y);
		direction.sub(position);
		direction = direction.setLength(64f);
		
		//Si está atacando, establecer las coordenadas del rectángulo de ataque
		//respecto al jugador y moverlo por el mundo JBump
		if( isAttacking ){
			attack.drawRect.x = lastDirection.x + 16 + position.x - attack.drawRect.width/2;
			attack.drawRect.y = lastDirection.y + 16 + position.y - attack.drawRect.height/2;
			game.jWorld.move(attack.jbItem, attack.drawRect.x, attack.drawRect.y, colFilter);
			
			attack.jbRect = game.jWorld.getRect(attack.jbItem);
			attack.drawRect.x = attack.jbRect.x;
			attack.drawRect.y = attack.jbRect.y;
		}
	}
	
	public void act(){
		//Crear el rectángulo de ataque según el vector de dirección y cambiarle las dimensiones
		//si es que el vetor apunta mas lateralmente que verticalmente
		if( Gdx.input.isButtonJustPressed(Buttons.LEFT) && !isAttacking){
			isAttacking = true;
			lastDirection.set(direction);
			attack = new Attack(game, camera, new Vector2(0, 0), 96, 40);
			
			attack.drawRect.x = lastDirection.x + 16 + position.x - attack.rectWidth/2;
			attack.drawRect.y = lastDirection.y + 16 + position.y - attack.rectHeight/2;
			
			if( Math.abs(direction.x) > Math.abs(direction.y) ){
				attack.drawRect.width = attack.rectHeight;
				attack.drawRect.height = attack.rectWidth;
			} else {
				attack.drawRect.width = attack.rectWidth;
				attack.drawRect.height = attack.rectHeight;
			}
			
			//Añadir el rectángulo de ataque al mundo jBump
			attack.jbItem = game.jWorld.add( new Item<Entity>(), attack.drawRect.x, attack.drawRect.y, attack.drawRect.width, attack.drawRect.height);
			attack.jbItem.userData = attack;
			swordOutTime = TimeUtils.millis();
			
		} else if( TimeUtils.millis() - swordOutTime > attackTime && isAttacking ){
			
			//Luego de pasar una determinada cantidad de milisegundos, se elimina de dibujado y de la simulación
			attack.drawRect.set(0, 0, 0, 0);
			game.jWorld.remove(attack.jbItem);
			isAttacking = false;
		}
	}
	
	public void render(){
		game.shapeDrawer.setDefaultLineWidth(5);
		game.shapeDrawer.filledRectangle(drawRect, Color.GREEN);
		game.shapeDrawer.rectangle(drawRect, Color.LIME);
		game.shapeDrawer.line(position.x + 16, position.y + 16, direction.x +16 + position.x, direction.y +16 + position.y, Color.RED, 2f);
		//game.shapeDrawer.line(0, 0, direction.x-16, direction.y-16, Color.RED, 2f);
		
		//Interpolacion de colores al dibujar el rectángulo de ataque
		if( isAttacking ){
			game.shapeDrawer.rectangle(attack.drawRect, Color.RED.lerp(new Color(0, 0.2f, 0.2f, 0), 0.05f), 2f);
		} else {
			Color.RED.lerp(new Color(1,0,0,1), 0.5f);
		}
	}

}
