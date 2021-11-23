package com.toh;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;

/**
 * Filtro custom para la simulacion JBump
 * Colision: Enemigo - Jugador: Slide
 * Colision: Cualquiera - Ataque: Cross
 * Por defecto: Slide
 * @author einhander
 *
 */
public class TOHCollisionFilter implements CollisionFilter {

	public TOHCollisionFilter() {}

	@Override
	public Response filter(Item item, Item other) {
		if( item.userData instanceof Player && other.userData instanceof Enemy)
			return Response.bounce;
		else if ( item.userData instanceof Attack || other.userData instanceof Attack )
			return Response.cross;
		else
			return Response.slide;
	}

}
