package EtruarutaGUI;


import warlords2600.Ball;
import warlords2600.General;

/**
 * Created by adilb on 31/03/2017.
 */
public class AIController {
    private General general;
    private int tickCounter = 0;

    public void AIController(){

    }

    public void setGeneral(General general){
        this.general = general;
    }

    public void movePaddle(Ball ball){

            if (tickCounter <= 0) {//Make sure that movement occurs only every 5th tick or so
                double distanceBefore = calculateDistance(ball);
                if (distanceBefore > 75) {//Stops paddle moving when close to ball
                    //System.out.println(distanceBefore);
                    tickCounter = 5;
                    general.paddle.moveLeft();

                    double distanceAfter = calculateDistance(ball);

                    if (distanceAfter < distanceBefore) {
                        //Do Nothing our move made it get closer to ball in y axis. No further action required.
                    } else {
                        general.paddle.moveRight();
                        general.paddle.moveRight();//Move right twice to compensate for wrong left turn initially
                    }
                }
            }else{
                tickCounter--;
            }
    }

    private double calculateDistance(Ball ball){
        int ballXPos = ball.getXPos();
        int ballYPos = ball.getYPos();
        int ballXVelocity = ball.getXVelocity();
        int ballYVelocity = ball.getYVelocity();
        int yDistanceBefore = Math.abs(general.paddle.calcYPos() - (ballYPos+ballYVelocity*5));//Guess where ball will be by looking at current location and velocity
        int XDistanceBefore = Math.abs(general.paddle.calcXPos() - (ballXPos+ballXVelocity*5));
        return Math.sqrt(Math.pow(XDistanceBefore,2)+Math.pow(yDistanceBefore,2));
    }
}
