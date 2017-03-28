package EtruarutaGUI;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import warlords2600.*;

public class PlayNow implements SceneInterface {
    private SceneManager sceneManager;
    private Scene playNowScene;
    private Group root;
    private Game game;

    /**
     * Constructor for PlayNow class
     * @param sceneManager SceneManager currently being used
     */
    public PlayNow(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the PlayNow Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        playNowScene = new Scene(root, width, height, Color.ORANGE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = renderGame(gc);
        HandleInputs();

        return playNowScene;
    }

    public void HandleInputs() {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent keyEvent) {
                 switch(keyEvent.getCode()) {
                     case LEFT:
                         game.generals[0].paddle.moveLeft();
                         break;
                     case RIGHT:
                         game.generals[0].paddle.moveRight();
                         break;
                 }
             }

        });
    }

    /**
     *
     * @param gc GraphicsContext to draw animation onto
     * @return GraphicsContext with the animation for the game drawn on to
     */
    public GraphicsContext renderGame(GraphicsContext gc) {
        Image ballImage = new Image( "ball.png" );
        Image paddleImages[] = new Image[4];
        paddleImages[0] = new Image( "paddleA.png" );
        paddleImages[1] = new Image( "paddleB.png" );
        paddleImages[2] = new Image( "paddleC.png" );
        paddleImages[3] = new Image( "paddleD.png" );

        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0, Main.WIDTH, Main.HEIGHT);

        Ball ball = new Ball(50,50);
        Paddle paddleA = new Paddle();
        General generalA = new General(0, 0, paddleA);
        Paddle paddleB = new Paddle();
        General generalB = new General(0, 0, paddleB);
        Paddle paddleC = new Paddle();
        General generalC = new General(0, 0, paddleC);
        Paddle paddleD = new Paddle();
        General generalD = new General(0, 0, paddleD);
        Brick brick = new Brick();

        ball.setXVelocity(5);
        ball.setYVelocity(0);

        game = new Game(ball, generalA, generalB, generalC, generalD, brick);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight() );
                gc.drawImage( paddleImages[0], game.generals[0].paddle.calcXPos(), game.generals[0].paddle.calcYPos(), game.generals[0].paddle.getWidth(), game.generals[0].paddle.getHeight());
                gc.drawImage( paddleImages[1], Main.WIDTH  - game.generals[1].paddle.calcXPos(), game.generals[1].paddle.calcYPos(), game.generals[1].paddle.getWidth(), game.generals[1].paddle.getHeight());
                gc.drawImage( paddleImages[2], Main.WIDTH - game.generals[2].paddle.calcXPos(), Main.HEIGHT - game.generals[2].paddle.calcYPos(), game.generals[2].paddle.getWidth(), game.generals[2].paddle.getHeight());
                gc.drawImage( paddleImages[3], game.generals[3].paddle.calcXPos(), Main.HEIGHT - game.generals[3].paddle.calcYPos(), game.generals[3].paddle.getWidth(), game.generals[3].paddle.getHeight());
                game.tick();
            }
        }.start();

        return gc;
    }
}