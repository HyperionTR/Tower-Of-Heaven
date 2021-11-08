package com.toh;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;


/**
 * Clase en la que se definen los distintos tipos de ataque, por ejemplo el rectángulo
 * del guerrero, los proyectiles el mago o el arquero.<br>
 * <br>
 * Actualmente, sólamente es una entidad que existe con los parámetros por defecto de una entidad.
 * @author einhander
 *
 */
public class Attack extends Entity {

	public Attack(TOHGame game, OrthographicCamera camera, Vector2 startPosition, int width, int height) {
		super(game, camera, startPosition, width, height);
	}

	public Attack(TOHGame game, OrthographicCamera camera, Vector2 startPosition) {
		super(game, camera, startPosition);
	}

}
