package com.toh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;

/**
 * Clase de entidad, la cual todo elemento que se pueda mover (o existir) dentro de la pantalla
 * de Dungeon, es decir, los elementos principales del videojuego, heredan.<br>
 * <br>
 * En ella se encuentran varios vectores que pueden ser utilizados para el movimiento y cálculo
 * de algunas posiciones, respecto al objeto actual, así como elementos para su simulación dentro
 * del mundo JBump (Item, Rect y Collisions) y otras variables útiles para toda entidad.
 * @author einhander
 *
 */
public class Entity {

	/**
	 * Este campo lo tiene casi todas las clases del juego, para que puedan accedes
	 * a las instancias del mundo JBump a los manejadores por lotes.<br>
	 * Las clases que lo utilizan simplemente lo van a recibir como parámetro en su contructor
	 * mas NUNCA van a crear una nueva instancia de éste
	 */
	final TOHGame game;
	
	Rectangle drawRect;			//Rectángulo para el dibujado
	Rect jbRect;				//Rectángulo de simulación JBump
	Item<Entity> jbItem;		//Entidad dentro del Mundo JBump (game.jbWorld)
	Result jbColResult;			//Resultado de una colision JBump (slide, cross, etc...)
	Collisions jbCollisionArray;//Arreglo de todas las colisiones de JBump
	OrthographicCamera camera;	//Camara para el dibujado (definida en DungeonScreen)
	TOHCollisionFilter colFilter;//Filtro de colisión customizado
	
	/**
	 * Vectores para el movimiento
	 */
	Vector2 position;		//Pocisión del objeto dentro del juego
	Vector2 speed;			//Velocidad actual (cambio de la pocision)
	Vector2 targetSpeed;	//Velocidad máxima a la que interpolará el vector 'speed'
	Vector2 direction;		//Vector direccional de uso general
	Vector2 lastDirection;	//Vector direccional de uso general
	Vector3 cursorPos;		//Pocisión del cursor (en 3D para ser des-proyectado por la cámara)
	
	Color commonColor;		//Color normal de la entidad
	Color damageColor;		//Color cuando la entidad recibe daño
	
	int rectWidth = 32, rectHeight = 32; //Alto y ancho de los rectángulos de esta entidad
	int health = 100;					 //Puntos de vida
	float maxSpeed = 300;				 //Velocidad máxima de la entidad
	float acceleration = 0.2f;			 //Velocidad a la que se realiza la interpolación entre speed hacia targetSpeed
	public boolean beingAttacked = false;//Verdadero si la entidad esta siendo atacada
	public boolean alive = true;		 //Verdadero si la entidad esta viva
	
	/**
	 * Constructor más especifico de la entidad
	 * @param game La instancia del juego
	 * @param camera La camara en la que se observará esta entidad
	 * @param startPosition Pocisión inicial de la entidad
	 * @param width Ancho del rectángulo de esta entidad
	 * @param height Alto del rectángulo de esta entidad
	 * @param health Vida de esta entidad
	 */
	public Entity(final TOHGame game, OrthographicCamera camera, Vector2 startPosition, int width, int height, int health) {
		this.game = game;
		this.camera = camera;
		
		this.position = new Vector2(startPosition);
		this.speed = new Vector2(0, 0);
		this.targetSpeed = new Vector2(0, 0);
		this.direction = new Vector2(1, 0).setLength(50);
		this.lastDirection = new Vector2(direction);
		this.cursorPos = new Vector3(0, 0, 0);
		this.rectWidth = width;
		this.rectHeight = height;
		this.drawRect = new Rectangle(position.x, position.y, rectWidth, rectHeight);
		this.colFilter = new TOHCollisionFilter();
		
		this.health = health;
	}

	/**
	 * Constructor para no especificar vida de la entidad<br>
	 * @param game La instancia del juego
	 * @param camera La camara en la que se observará esta entidad
	 * @param startPosition Pocisión inicial de la entidad
	 * @param widt: Ancho del rectángulo de esta entidad
	 * @param height Alto del rectángulo de esta entidad
	 */
	public Entity(final TOHGame game, OrthographicCamera camera, Vector2 startPosition, int width, int height) {
		this.game = game;
		this.camera = camera;
		
		this.position = new Vector2(startPosition);
		this.speed = new Vector2(0, 0);
		this.targetSpeed = new Vector2(0, 0);
		this.direction = new Vector2(1, 0).setLength(50);
		this.lastDirection = new Vector2(direction);
		this.cursorPos = new Vector3(0, 0, 0);
		this.rectWidth = width;
		this.rectHeight = height;
		this.drawRect = new Rectangle(position.x, position.y, rectWidth, rectHeight);
		this.colFilter = new TOHCollisionFilter();
		
		this.health = 100;
	}
	
	/**
	 * Constructor para no especificar tamaño de la entidad<br>
	 * @param game La instancia del juego
	 * @param camera La camara en la que se observará esta entidad
	 * @param startPosition Pocisión inicial de la entidad
	 * @param health Vida de esta entidad
	 */
	public Entity(final TOHGame game, OrthographicCamera camera, Vector2 startPosition, int health) {
		this.game = game;
		this.camera = camera;
		
		this.position = new Vector2(startPosition);
		this.speed = new Vector2(0, 0);
		this.targetSpeed = new Vector2(0, 0);
		this.direction = new Vector2(1, 0).setLength(50);
		this.lastDirection = new Vector2(direction);
		this.cursorPos = new Vector3(0, 0, 0);
		this.rectWidth = 32;
		this.rectHeight = 32;
		this.drawRect = new Rectangle(position.x, position.y, rectWidth, rectHeight);
		this.colFilter = new TOHCollisionFilter();
		
		this.health = health;
	}
	
	/**
	 * Constructor más general de la entidad<br>
	 * @param game La instancia del juego
	 * @param camera La camara en la que se observará esta entidad
	 * @param startPosition Pocisión inicial de la entidad
	 */
	public Entity(final TOHGame game, OrthographicCamera camera, Vector2 startPosition) {
		this.game = game;
		this.camera = camera;
		
		this.position = new Vector2(startPosition);
		this.speed = new Vector2(0, 0);
		this.targetSpeed = new Vector2(0, 0);
		this.direction = new Vector2(1, 0).setLength(50);
		this.lastDirection = new Vector2(direction);
		this.cursorPos = new Vector3(0, 0, 0);
		this.rectWidth = 32;
		this.rectHeight = 32;
		this.drawRect = new Rectangle(position.x, position.y, rectWidth, rectHeight);
		this.colFilter = new TOHCollisionFilter();
		
		this.health = 100;
	}
	
	/**
	 * Esta función se encargará de llevar a cabo las acciones que no afecten directamente al movimiento.
	 * Sin embargo, puede realizar acciones dependiendo de los resultados de las colisiones detectadas, o de 
	 * algunos otros eventos como cambios de escena o presionado de teclas, según sea necesario.
	 */
	public void act(){}
	/**
	 * Función que permite directamente modificar la posición de nuestro objeto tanto en el mundo de juego
	 * normal, como en la simulación de jbump.<br>
	 * Es importante recordar obtejer el resultado del movimiento en jbump y establecer la posición
	 * del rectángulo de dibujado de la entidad, a las coordenadas del rectángulo de simulación
	 * JBump, luego del movimiento.
	 */
	public void move(){}
	/**
	 * IMPORTANTE: DEBE SER LLAMADA DESPUÉS DE INICIAR EL RENDERIZADO POR LOTES<br>
	 * (SpriteBatch.begin() o similares).<br>
	 * <br>
	 * Funcion que permite mostrar en pantalla el rectángulo de dibujado, así como otros elementos gráficos
	 * dependientes de esta entidad, como líneas, cambios de color o efectos de partículas asociados a los 
	 * parámetros o acciones de la entidad.
	 */
	public void render(){}
	
	
}
