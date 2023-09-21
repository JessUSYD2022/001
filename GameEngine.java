package invaders.engine;


import invaders.GameData;
import invaders.GameObject;

import invaders.entities.*;
import invaders.entities.Bunker;
import invaders.entities.BunkerBuilder;
import invaders.entities.Enemy;
import invaders.entities.EnemyBuilder;
import invaders.entities.PlayerProjectile;
import invaders.entities.Projectile;
import invaders.entities.SlowStraightStrategy;
import invaders.entities.Slow_straight;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements Subject{

	private ArrayList<Enemy> enemy_list = new ArrayList<Enemy>();
	private ArrayList<Bunker> bunker_list = new ArrayList<Bunker>();
	private ArrayList<PlayerProjectile> player_projectile_list = new ArrayList<PlayerProjectile>();
	private ArrayList<Fast_straight> fast_projectile_list = new ArrayList<>();
	private ArrayList<Slow_straight> slow_projectile_list = new ArrayList<>();
	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	// observer list
	private List<Observer> observers = new ArrayList<>();
	private Player player;
	private boolean left;
	private boolean right;
	private boolean shoot;
	private GameWindow window;
	private Timeline shootTimeline;
	private boolean gameover = false;
	private double Destroy_enemies = 0;
	private double enemy_speed = 1;


	public GameEngine(String config) {
		// read the config here
		GameData data = new GameData(config);

		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();

		// create player
		SpawnPlayer(data);

		// create enemies
		SpawnEnemy(data);
		startShooting();

		// create bunkers
		generateBunkers(data);
	}


	/**
	 * Updates the game/simulation
	 */
	public void update() {
		if (!gameover){
			movePlayer(); // move player

			// move enemies
			moveEnemies(enemy_speed+Destroy_enemies);
			checkGameOver();
			checkEnemyBunkerCollisions();

			// move Enemy projectiles
			moveProjectile(slow_projectile_list);
			moveProjectile(fast_projectile_list);

			moveProjectile(player_projectile_list); // move player projectiles

			// check collides with bunker
			checkBunkerProjectileCollider(player_projectile_list);
			checkBunkerProjectileCollider(slow_projectile_list);
			checkBunkerProjectileCollider(fast_projectile_list);

			// check collides with player
			checkEnemyProjectilePlayerCollision(fast_projectile_list);
			checkEnemyProjectilePlayerCollision(slow_projectile_list);

			// check both projectile collides
			checkProjectileCollisions(fast_projectile_list);
			checkProjectileCollisions(slow_projectile_list);

			//check collides with enemy
			checkEnemyPlayerProjectileCollisions();

			for (GameObject go : gameobjects) {
				go.update(); // update game objects
			}

			if (enemy_list.size() == 0){
				gameover = true;
				gameOver();
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		for (Renderable ro : renderables) {
			if (!ro.getLayer().equals(Renderable.Layer.FOREGROUND)) {
				continue;
			}
			if (ro.getPosition().getX() + ro.getWidth() >= 600) {
				ro.getPosition().setX(599 - ro.getWidth());
			}

			if (ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if (ro.getPosition().getY() + ro.getHeight() >= 800) {
				ro.getPosition().setY(799 - ro.getHeight());
			}

			if (ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	public List<Renderable> getRenderables() {
		return renderables;
	}

	public void setWindow(GameWindow window) {
		this.window = window;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased() {
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}

	public void rightPressed() {
		this.right = true;
	}

	public void shootPressed() {
		if (player_projectile_list.isEmpty()){
			player_projectile_list = player.shoot();
			this.shoot = true;
		}
	}

	public ArrayList<PlayerProjectile> getPlayer_projectile_list(){
		return player_projectile_list;
	}

	public void shootReleased() {
		this.shoot = false;
	}

	private void movePlayer() {
		if (left) {
			player.left();
		}

		if (right) {
			player.right();
		}
	}

	public int getLives(){
		return player.getLives();
	}

	// add observer
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	// remove observer
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}

	// notify observers
	public void notifyRemoved(Renderable entity) {
		for (Observer observer : observers) {
			observer.onRemoved(entity);
		}
	}

	// create player
	private void SpawnPlayer(GameData data) {
		JSONObject playerData = data.getPlayerData();
		JSONObject position = (JSONObject) playerData.get("position");
		double p_X = Double.parseDouble(position.get("x").toString());
		double p_Y = Double.parseDouble(position.get("y").toString());

		double speed = Double.parseDouble(playerData.get("speed").toString());

		int lives = Integer.parseInt(playerData.get("lives").toString());

		player = new Player(new Vector2D(p_X, p_Y));
		player.setOriginalPosition(new Vector2D(p_X,p_Y));
		player.setSpeed(speed);
		player.setLives(lives);
		player.start();
		renderables.add(player);
		gameobjects.add(player);
	}

	// create enemies
	private void SpawnEnemy(GameData data) {
		JSONArray enemiesData = data.getEnemiesData();
		int enemyCount = 0;
		for (Object enemyObj : enemiesData) {
			JSONObject enemy = (JSONObject) enemyObj;
			JSONObject position = (JSONObject) enemy.get("position");
			double e_X = Double.parseDouble(position.get("x").toString());
			double e_Y = Double.parseDouble(position.get("y").toString());
			String projectileType = (String) enemy.get("projectile");

			if (enemyCount >= enemiesData.size() / 2) {
				e_Y += 50;
			}

			Enemy enemy_entity = new EnemyBuilder()
					.setWidth(25)
					.setHeight(20)
					.setPosition(new Vector2D(e_X, e_Y))
					.setProjectile(projectileType)
					.setStart()
					.setDescendCounter(0)
					.setDescending(false)
					.setDirection(1)
					.build();

			enemy_list.add(enemy_entity);
			renderables.add(enemy_entity);
			gameobjects.add(enemy_entity);
			enemyCount++;
		}
	}

	// move enemies
	private void moveEnemies(double speed) {
		for (Enemy enemy : enemy_list) {
			if (enemy.isDescending()) {
				if (enemy.getDescendCounter() < enemy.getHeight()) {
					enemy.moveDown(speed);
					enemy.incrementDescendCounter();
					continue;
				} else {
					enemy.setDescending(false);
					enemy.setDescendCounter(0);
				}
			}

			if (enemy.getDirection() == 1) {
				enemy.moveRight(speed);
				if (enemy.getPosition().getX() + enemy.getWidth() >= 600) {
					enemy.setDescending(true);
					enemy.setDirection(-1);
				}
			} else if (enemy.getDirection() == -1) {
				enemy.moveLeft(speed);
				if (enemy.getPosition().getX() <= 0) {
					enemy.setDescending(true);
					enemy.setDirection(1);
				}
			}
		}
	}



	// create bunkers
	private void generateBunkers(GameData data) {
		// create bunkers
		JSONArray bunkersData = data.getBunkersData();
		for (Object bunkerobj : bunkersData) {
			JSONObject bunker = (JSONObject) bunkerobj;

			JSONObject position = (JSONObject) bunker.get("position");
			double pos_X = Double.parseDouble(position.get("x").toString());
			double pos_Y = Double.parseDouble(position.get("y").toString());

			JSONObject size = (JSONObject) bunker.get("size");
			double size_X = Double.parseDouble(size.get("x").toString());
			double size_Y = Double.parseDouble(size.get("y").toString());

			Bunker bunker_entity = new BunkerBuilder()
					.setPosition(new Vector2D(pos_X, pos_Y))
					.setSize_x(size_X)
					.setSize_y(size_Y)
					.setDamage(0)
					.setStart()
					.build();

			bunker_list.add(bunker_entity);
			renderables.add(bunker_entity);
			gameobjects.add(bunker_entity);
		}
	}

	// move projectiles
	private <T extends Projectile> void moveProjectile(ArrayList<T> projectileList){
		Iterator<T> iterator = projectileList.iterator();
		while (iterator.hasNext()) {
			T projectile = iterator.next();
			if (projectile.getProjectileStrategy() == null){
				projectile.shoot(); // player
			} else projectile.getProjectileStrategy().shoot(projectile); // enemy
			projectile.getCollider().setPosition(projectile.getPosition());

			if (projectile.getPosition().getY() + projectile.getHeight() >= 800 || projectile.getPosition().getY() <= 0.0){
				notifyRemoved(projectile);
				renderables.remove(projectile);
				iterator.remove();
				//projectileList.remove(projectile);
			} else {
				if (!renderables.contains(projectile)) {
					renderables.add(projectile);
				}
			}
		}
	}

	// Enemy randomly shooting
	private void shootRandomEnemies() {
		int maxAttempts = enemy_list.size();
		int attempts = 0;

		List<Integer> selectedIndices = new ArrayList<>();

		while (selectedIndices.size() < 3 && attempts < maxAttempts) {
			int randomIndex = new Random().nextInt(enemy_list.size());
			if (!selectedIndices.contains(randomIndex)) {
				Enemy randomEnemy = enemy_list.get(randomIndex);
				selectedIndices.add(randomIndex);

				if (randomEnemy.getProjectile().equals("fast_straight")) {
					//randomEnemy.setProjectileStrategy(new FastStraightStrategy());
					FastStraightStrategy strategy = new FastStraightStrategy();
					Fast_straight fastStraight = randomEnemy.shootFast();
					fastStraight.setProjectileStrategy(strategy);
					fast_projectile_list.add(fastStraight);
				} else if (randomEnemy.getProjectile().equals("slow_straight")) {
					//randomEnemy.setProjectileStrategy(new SlowStraightStrategy());
					SlowStraightStrategy strategy = new SlowStraightStrategy();
					Slow_straight slowStraight = randomEnemy.shootSlow();
					slowStraight.setProjectileStrategy(strategy);
					slow_projectile_list.add(slowStraight);
				}
			}
			attempts++;
		}
	}

	// add interval for enemy shooting: bigger than 4s, smaller than 7s
	private void startShooting() {
		double randomInterval = 4 + new Random().nextDouble() * 3;

		shootTimeline = new Timeline(new KeyFrame(Duration.seconds(randomInterval), e -> {
			shootRandomEnemies();
			startShooting();
		}));
		shootTimeline.setCycleCount(1);
		shootTimeline.play();
	}

	// check collision between bunker and projectile
	private void checkBunkerProjectileCollider(List<? extends Projectile> projectiles) {
		Iterator<Bunker> bunkerIterator = bunker_list.iterator();
		while (bunkerIterator.hasNext()) {
			Bunker bunker = bunkerIterator.next();
			Iterator<? extends Projectile> projectileIterator = projectiles.iterator();
			while (projectileIterator.hasNext()) {
				Projectile projectile = projectileIterator.next();
				if (bunker.getCollider().isColliding(projectile.getCollider())) {
					//System.out.println("get collision");
					//System.out.println("BUNKER: "+ bunker.getPosition().getX()+" "+bunker.getPosition().getY());
					//System.out.println("Projectile: "+ projectile.getPosition().getX()+" "+projectile.getPosition().getY());
					bunker.setDamage(bunker.getDamage() + 1);
					notifyRemoved(projectile);
					renderables.remove(projectile);
					projectileIterator.remove();
					if (bunker.getDamage() == 3){
						notifyRemoved(bunker);
						renderables.remove(bunker);
						bunkerIterator.remove();
						break;
					}
				}
			}
		}
	}

	// check collision between enemy projectile and player
	private void checkEnemyProjectilePlayerCollision(List<? extends Projectile> projectiles) {
		Iterator<? extends Projectile> enemyProjectileIterator = projectiles.iterator();
		while (enemyProjectileIterator.hasNext()) {
			Projectile enemyProjectile = enemyProjectileIterator.next();
			if (player.getCollider().isColliding(enemyProjectile.getCollider())) {
				player.setLives(player.getLives() - 1);

				window.updatePlayerLivesLabel();
				notifyRemoved(player);
				renderables.remove(player);
				
				renderables.add(player);
				player.setPosition(player.getOriginalPosition());



				notifyRemoved(enemyProjectile);
				renderables.remove(enemyProjectile);

				enemyProjectileIterator.remove();
				if (player.getLives() <= 0) {
					gameover = true;
					gameOver();
				}
			}
		}
	}

	// check collision between enemy projectile and player projectile
	private <T extends Projectile> void checkProjectileCollisions(ArrayList<T> enemy_projectile_list) {
		Iterator<PlayerProjectile> playerProjectileIterator = player_projectile_list.iterator();
		while (playerProjectileIterator.hasNext()) {
			PlayerProjectile playerProjectile = playerProjectileIterator.next();

			Iterator<T> fastProjectileIterator = enemy_projectile_list.iterator();
			while (fastProjectileIterator.hasNext()) {
				T enemyProjectile = fastProjectileIterator.next();
				if (playerProjectile.getCollider().isColliding(enemyProjectile.getCollider())) {

					notifyRemoved(playerProjectile);
					notifyRemoved(enemyProjectile);
					renderables.remove(playerProjectile);
					renderables.remove(enemyProjectile);
					playerProjectileIterator.remove();
					fastProjectileIterator.remove();
					break;
				}
			}
		}
	}

	// check collision between enemy and player projectile
	private void checkEnemyPlayerProjectileCollisions() {
		List<Enemy> enemiesToRemove = new ArrayList<>();
		List<PlayerProjectile> projectilesToRemove = new ArrayList<>();

		for (Enemy enemy : enemy_list) {
			for (PlayerProjectile playerProjectile : player_projectile_list) {
				if (enemy.getCollider().isColliding(playerProjectile.getCollider())) {
					Destroy_enemies += 0.1;
					enemiesToRemove.add(enemy);
					projectilesToRemove.add(playerProjectile);
				}
			}
		}

		enemy_list.removeAll(enemiesToRemove);
		player_projectile_list.removeAll(projectilesToRemove);

		for (Enemy enemy : enemiesToRemove) {
			renderables.remove(enemy);
			notifyRemoved(enemy);
		}
		for (PlayerProjectile projectile : projectilesToRemove) {
			renderables.remove(projectile);
			notifyRemoved(projectile);
		}
	}

	// check collision between bunker and enemy
	private void checkEnemyBunkerCollisions() {
		Iterator<Bunker> bunkerIterator = bunker_list.iterator();
		while (bunkerIterator.hasNext()) {
			Bunker bunker = bunkerIterator.next();
			for (Enemy enemy : enemy_list) {
				if (bunker.getCollider().isColliding(enemy.getCollider())) {
					notifyRemoved(bunker);
					renderables.remove(bunker);
					bunkerIterator.remove();
					break;
				}
			}
		}
	}

	// if enemy's position is equal to player's position or enemy arrives the bottom, Game will over
	private void checkGameOver() {
		for (Enemy enemy : enemy_list) {
			if (enemy.getPosition().getY() + enemy.getHeight() >= 800) {
				gameover = true;
				gameOver();
				return;
			}

			if (enemy.getCollider().isColliding(player.getCollider())) {
				gameover = true;
				gameOver();
				return;
			}
		}
	}
	private void gameOver(){
		shootTimeline.stop();

		for (Observer observer : observers) {
			observer.onGameOver();
		}
	}
}


