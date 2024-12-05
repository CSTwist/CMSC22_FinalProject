import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration; // Import the Duration class
import com.almasb.fxgl.entity.EntityFactory;
import java.lang.Math;

public class AZTANK extends GameApplication {
    double player1deltaX, player1deltaY;
    double player2deltaX, player2deltaY;
    private static Entity player1, player2;
    private AZTANKMainMenu myMainMenu;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("AZ TANK");
        settings.setVersion("1.0");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                myMainMenu = new AZTANKMainMenu();
                return myMainMenu;
            }
        });
        
    }
    
    @Override
    protected void initInput(){ //This is Just input

        FXGL.getInput().addAction(new UserAction("Player 1 Move Left") {
            @Override
            protected void onAction() {
                player1.rotateBy(-5); // Move left
            }
        }, KeyCode.LEFT);

        FXGL.getInput().addAction(new UserAction("Player 1 Move Right") {
            @Override
            protected void onAction() {
                player1.rotateBy(5); // Move right
            }
        }, KeyCode.RIGHT);

        FXGL.getInput().addAction(new UserAction("Player 1 Move Forward") {
            @Override
            protected void onAction() {
                double angle = player1.getRotation();

                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * -5; // Move 10 units forward
                double deltaY = Math.sin(radians) * -5;
                player1deltaX = deltaX;
                player1deltaY = deltaY;
    
                // Apply the translation
                player1.translate(new Point2D(deltaX, deltaY));
            }
        }, KeyCode.UP);

        FXGL.getInput().addAction(new UserAction("Player 1 Move Backwards") {
            @Override
            protected void onAction() {
                double angle = player1.getRotation();

                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * 5; // Move 10 units Backwards
                double deltaY = Math.sin(radians) * 5;
                player1deltaX = deltaX;
                player1deltaY = deltaY;
    
                // Apply the translation
                player1.translate(new Point2D(deltaX, deltaY));
            }
        }, KeyCode.DOWN);

        FXGL.getInput().addAction(new UserAction("Player 1 Shoot") {
            private TimerAction bulletTimer;

            @Override
            protected void onActionBegin() {
                // Start the bullet spawning interval when the action begins
                bulletTimer = FXGL.getGameTimer().runAtInterval(() -> {
                    double angle = player1.getRotation();
        
                    // Convert the angle to radians
                    double radians = Math.toRadians(angle);
        
                    // Calculate the tank's center position
                    double tankCenterX = player1.getX() + player1.getWidth() / 2;
                    double tankCenterY = player1.getY() + player1.getHeight() / 2;
        
                    // Calculate the offset for the bullet spawn position
                    double bulletOffsetDistance = player1.getWidth() / 2 - 80; // Adjust offset distance
                    double bulletSpawnX = tankCenterX + Math.cos(radians) * bulletOffsetDistance;
                    double bulletSpawnY = tankCenterY + Math.sin(radians) * bulletOffsetDistance;
        
                    // Spawn the bullet at the calculated position
                    FXGL.spawn("Player 1 bullet", new SpawnData(bulletSpawnX, bulletSpawnY).put("angle", angle));
                }, Duration.seconds(0.1)); // Fire a bullet every 0.7 seconds (increased space between shots)
            }
        
            @Override
            protected void onActionEnd() {
                // Stop the bullet spawning interval when the action ends (key release)
                if (bulletTimer != null) {
                    bulletTimer.expire(); // Stop the interval timer
                }
            }
        }, KeyCode.SPACE);

        FXGL.getInput().addAction(new UserAction("Player 2 Move Left") {
            @Override
            protected void onAction() {
                player2.rotateBy(-5); // Move Left
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Player 2 Move Right") {
            @Override
            protected void onAction() {
                player2.rotateBy(5); // Move right
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Player 2 Move Forward") {
            @Override
            protected void onAction() {
                double angle = player2.getRotation();

                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * -5; // Move 10 units forward
                double deltaY = Math.sin(radians) * -5;
                player2deltaX = deltaX;
                player2deltaY = deltaY;
    
                // Apply the translation
                player2.translate(new Point2D(deltaX, deltaY));
            }
        }, KeyCode.W);
        
        FXGL.getInput().addAction(new UserAction("Player 2 Move Down") {
            @Override
            protected void onAction() {
                double angle = player2.getRotation();
                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * 5; // Move 10 units forward
                double deltaY = Math.sin(radians) * 5;
                player2deltaX = deltaX;
                player2deltaY = deltaY;
    
                // Apply the translation
                player2.translate(new Point2D(deltaX, deltaY));
            }
        }, KeyCode.S);

        FXGL.getInput().addAction(new UserAction("Player 2 Shoot") {
            private TimerAction bulletTimer;

            @Override
            protected void onActionBegin() {
                // Start the bullet spawning interval when the action begins
                bulletTimer = FXGL.getGameTimer().runAtInterval(() -> {
                    double angle = player2.getRotation();
        
                    // Convert the angle to radians
                    double radians = Math.toRadians(angle);
        
                    // Calculate the tank's center position
                    double tankCenterX = player2.getX() + player2.getWidth() / 2;
                    double tankCenterY = player2.getY() + player2.getHeight() / 2;
        
                    // Calculate the offset for the bullet spawn position
                    double bulletOffsetDistance = player2.getWidth() / 2 - 80; // Adjust offset distance
                    double bulletSpawnX = tankCenterX + Math.cos(radians) * bulletOffsetDistance;
                    double bulletSpawnY = tankCenterY + Math.sin(radians) * bulletOffsetDistance;
        
                    // Spawn the bullet at the calculated position
                    FXGL.spawn("Player 2 bullet", new SpawnData(bulletSpawnX, bulletSpawnY).put("angle", angle));
                }, Duration.seconds(0.1)); // Fire a bullet every 0.7 seconds (increased space between shots)
            }
        
            @Override
            protected void onActionEnd() {
                // Stop the bullet spawning interval when the action ends (key release)
                if (bulletTimer != null) {
                    bulletTimer.expire(); // Stop the interval timer
                }
            }
        }, KeyCode.Q);
    }

    @Override
    protected void initGame() {
        // Initialize the player
        player1 = FXGL.entityBuilder()
                .at(400, 550) // Starting position
                .viewWithBBox(Player1Tank.tankInstantiator()) // Player appearance
                .with(new CollidableComponent(true))
                .bbox(new HitBox("Top", BoundingShape.box(50, 10)))  // Top HitBox
                .bbox(new HitBox("Bottom", BoundingShape.box(50, 10)))  // Bottom HitBox
                .type(EntityType.PLAYER)
                .buildAndAttach();

        player2 = FXGL.entityBuilder()
                .at(200, 550) // Starting position
                .viewWithBBox(Player2Tank.tankInstantiator()) // Player appearance
                .with(new CollidableComponent(true))
                .bbox(new HitBox("Top", BoundingShape.box(50, 10)))  // Top HitBox
                .bbox(new HitBox("Bottom", BoundingShape.box(50, 10)))  // Bottom HitBox
                .type(EntityType.PLAYER)
                .buildAndAttach();

        // Create boundary walls
        borderSetter();

        //Create Maze
        maze();

        // Register factories
        FXGL.getGameWorld().addEntityFactory(new BulletObjectFactory());
    }

    protected void borderSetter(){
        Entity topWall = FXGL.entityBuilder()
            .at(0, -10) // slightly off-screen
            .bbox(new HitBox(BoundingShape.box(FXGL.getAppWidth(), 10)))
            .with(new CollidableComponent(true))
            .type(EntityType.BORDER)
            .buildAndAttach();

        Entity bottomWall = FXGL.entityBuilder()
            .at(0, FXGL.getAppHeight())
            .bbox(new HitBox(BoundingShape.box(FXGL.getAppWidth(), 10)))
            .with(new CollidableComponent(true))
            .type(EntityType.BORDER)
            .buildAndAttach();

        Entity leftWall = FXGL.entityBuilder()
            .at(-10, 0) // slightly off-screen
            .bbox(new HitBox(BoundingShape.box(10, FXGL.getAppHeight())))
            .with(new CollidableComponent(true))
            .type(EntityType.BORDER)
            .buildAndAttach();

        Entity rightWall = FXGL.entityBuilder()
            .at(FXGL.getAppWidth(), 0)
            .bbox(new HitBox(BoundingShape.box(10, FXGL.getAppHeight())))
            .with(new CollidableComponent(true))
            .type(EntityType.BORDER)
            .buildAndAttach();
    }

    public void maze(){
        Entity Wall1 = FXGL.entityBuilder()
            .at(FXGL.getAppWidth()/2, FXGL.getAppHeight()/2)
            .viewWithBBox(new Rectangle(150,5))
            .with(new CollidableComponent(true))
            .type(EntityType.WALL)
            .buildAndAttach();

        Entity Wall2 = FXGL.entityBuilder()
            .at(FXGL.getAppWidth()/2+250, FXGL.getAppHeight()/2)
            .viewWithBBox(new Rectangle(5,150))
            .with(new CollidableComponent(true))
            .type(EntityType.WALL)
            .buildAndAttach();

        Entity Wall3 = FXGL.entityBuilder()
            .at(FXGL.getAppWidth()/2-300, FXGL.getAppHeight()/2)
            .viewWithBBox(new Rectangle(5,150))
            .with(new CollidableComponent(true))
            .type(EntityType.WALL)
            .buildAndAttach();
    }

    @Override
    protected void initPhysics() {
        // Collision detection: Player vs Falling Object
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BULLET) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fallingObject) {
                FXGL.showMessage("Skill Issue!", () -> {
                    FXGL.getGameController().gotoMainMenu();
                });
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BORDER) {
            @Override
            protected void onCollision(Entity player, Entity borderEntity) {
                double playerX = player.getX();
                double playerY = player.getY();

                double newX = Math.max(0, Math.min(playerX, FXGL.getAppWidth() - player.getWidth()));
                double newY = Math.max(0, Math.min(playerY, FXGL.getAppHeight() - player.getHeight()));

                player.setPosition(newX, newY);
            }
        });
        
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WALL) {
            @Override
            protected void onCollision(Entity player, Entity wallEntity) {
    
                // Apply the translation
                if (player == player1) player.translate(new Point2D(-player1deltaX, -player1deltaY));
                if (player == player2) player.translate(new Point2D(-player2deltaX, -player2deltaY));
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.WALL) {
            @Override
            protected void onCollision(Entity bullet, Entity wallEntity) {
                bullet.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
        // Display the score
        var scoreText = FXGL.getUIFactoryService().newText("", 24);
        scoreText.setTranslateX(10);
        scoreText.setTranslateY(30);
        scoreText.setFill(Color.BLACK);

        // Initialize score property if it does not exist
        try {
            FXGL.geti("Time");  // Try to get the score
        } catch (IllegalArgumentException e) {
            FXGL.set("Time", 0);  // Initialize score to 0 if it does not exist
        }

        // Increment score and update the UI every second
        FXGL.getGameTimer().runAtInterval(() -> {
            FXGL.inc("Time", 1); // Increment score
            scoreText.setText("Time: " + FXGL.geti("Time"));
        }, Duration.seconds(1)); // Use Duration for time interval

        FXGL.addUINode(scoreText);
    }


    @Override
    protected void onPreInit() {
        // Initialize score property before the game starts
        FXGL.set("score", 0);  // Initialize score to 0 if it hasn't been set already
    }

    // Define entity types
    public enum EntityType {
        PLAYER, BULLET, BORDER, WALL
    }

    // Define entity factory for spawning bullet objects
    public static class BulletObjectFactory implements EntityFactory {

        @Spawns("Player 1 bullet")
        public Entity newPlayer1BulletObject(SpawnData data) {
            Entity bulletObject = FXGL.entityBuilder(data)
                    .type(EntityType.BULLET)
                    .viewWithBBox(new Circle(5, Color.BLACK)) // Bullet object appearance
                    .with(new CollidableComponent(true))
                    .build();

            // Add custom movement component to make it fall
            bulletObject.addComponent(new Player1MovementComponent());

            return bulletObject;
        }

        @Spawns("Player 2 bullet")
        public Entity newPlayer2Object(SpawnData data) {
            Entity bulletObject = FXGL.entityBuilder(data)
                    .type(EntityType.BULLET)
                    .viewWithBBox(new Circle(5, Color.BLACK)) // Bullet object appearance
                    .with(new CollidableComponent(true))
                    .build();

            // Add custom movement component
            bulletObject.addComponent(new Player2MovementComponent());

            return bulletObject;
        }
    }

    public static class Player1MovementComponent extends Component {
        private double speed = 8; // Movement speed
        double angle = player1.getRotation();

        @Override
        public void onUpdate(double tpf) {
            // Move at the specified speed

                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * -speed; // Move 10 units forward
                double deltaY = Math.sin(radians) * -speed;
    
                // Apply the translation
                entity.translate(new Point2D(deltaX, deltaY));
        }
    }

    public static class Player2MovementComponent extends Component {
        private double speed = 8; // Movement speed
        double angle = player2.getRotation();

        @Override
        public void onUpdate(double tpf) {
            // Move at the specified speed

                // Convert the angle to radians
                double radians = Math.toRadians(angle);
    
                // Calculate the translation vector
                double deltaX = Math.cos(radians) * -speed; // Move 10 units forward
                double deltaY = Math.sin(radians) * -speed;
    
                // Apply the translation
                entity.translate(new Point2D(deltaX, deltaY));
        }
    }
}