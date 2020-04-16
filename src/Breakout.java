/*
 * File: Breakout.java
 * -------------------
 * Name: Serdyuk Fedir & Soloviov Dmytro
 * Section Leader: Pyechkurova Olena
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	
	/** Number of lives */

	private  int lives;
	private boolean isFinishGame = false;
    private RandomGenerator rgen = RandomGenerator.getInstance();
	private GImage heart1;
	private GImage heart2;
	private GImage heart3;
	private GOval ball;
	private double vx,vy;
	private int speedLevel;
	private double upSpeedCoefficient = 1.25;
	private int brickCount;
	private int score;
	private GLabel scoreLabel;
	private GRect paddle;
	private boolean win = false;
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		this.setSize(WIDTH+19,HEIGHT+62);
		Color backgroundCol = new Color(48,48,48);
		this.setBackground(backgroundCol);
		initAllElements();
		addMouseListeners();
        startGame();
	}

	private void startGame() {
		while (true) {
			pause(100);
			if(!isFinishGame) {
				timer();
				while (!isFinishGame) {
					ball.move(vx, vy);
					checkCollision();
					pause(10);
				}
				finishGame();
			}
		}
	}

	private void initAllElements() {
		lives = 3;
		score = 0;
		speedLevel = 1;
		brickCount = NBRICK_ROWS*NBRICKS_PER_ROW;
		initScore();
		initHearts();
		drawBricks();
		initPaddle();
		initBall();
	}

	private void finishGame(){
		GRect finishRect = new GRect(0,BRICK_Y_OFFSET,this.getWidth(),this.getHeight());
		finishRect.setFilled(true);
		Color back = new Color(48,48,48);
		finishRect.setColor(back);
		add(finishRect);
		if (win){
			GImage youWin = new GImage("youWin.png");
			youWin.scale(0.3);
			add(youWin,WIDTH/2.0-youWin.getWidth()/2.0,HEIGHT/2.0-youWin.getHeight()/2.0);
//			GLabel winLabel = new GLabel("YOU WIN!");
//			winLabel.setFont("Bahnschrift-60");
//			winLabel.setColor(Color.white);
//			add(winLabel,this.getWidth()/2.0-winLabel.getWidth()/2,this.getHeight()/2.0+winLabel.getHeight()/2);
		}
		else{
			GImage gameOver = new GImage("gameOver.png");
			gameOver.scale(0.25);
			add(gameOver,WIDTH/2.0-gameOver.getWidth()/2.0,HEIGHT/2.0-gameOver.getHeight()/2.0);
//			GLabel loseLabel = new GLabel("YOU LOSE");
//			GLabel loseLabel2 = new GLabel("YOUR SCORE: "+score);
//			loseLabel.setFont("Bahnschrift-50");
//			loseLabel2.setFont("Bahnschrift-50");
//			loseLabel.setColor(Color.white);
//			loseLabel2.setColor(Color.white);
//			add(loseLabel,this.getWidth()/2.0-loseLabel.getWidth()/2,this.getHeight()/2.0-loseLabel.getHeight()/2);
//			add(loseLabel2,this.getWidth()/2.0-loseLabel2.getWidth()/2,this.getHeight()/2.0+loseLabel2.getHeight()/2);
		}
		GImage clickATR = new GImage("ClickATR.png");
		clickATR.scale(0.6);
		add(clickATR,WIDTH/2.0-clickATR.getWidth()/2.0,
				(HEIGHT/4.0)*3-clickATR.getHeight()/2.0);
	}

	private void checkCollision() {
		if(ball.getX() < 0 || ball.getX()+ball.getHeight() > WIDTH) vx *= -1;
		else if(ball.getY() < 0) {
			upSpeed(4, upSpeedCoefficient);
			vy *= -1;
		}
		else if(findObjectForward()) vy *= -1;
		else if(ball.getY()>HEIGHT){
			lives--;
			speedLevel = 1;
			if (lives==2)
				remove(heart3);
			else if (lives==1)
				remove(heart2);
			else if (lives==0)
				remove(heart1);

			if(lives <= 0) {
				isFinishGame = true;
			} else {
				remove(ball);
				timer();
				initBall();
			}
		}

		if(brickCount <= 0) {
			isFinishGame = true;
			win=true;
		}
	}

	private boolean findObjectForward() {
		GObject obj;
		obj = getElementAt(ball.getX()+ball.getWidth()/2.0,ball.getY());
		if(isBrickOrPaddle(obj)) return true;
		obj = getElementAt(ball.getX(),ball.getY());
		if(isBrickOrPaddle(obj)) return true;
		obj = getElementAt(ball.getX(),ball.getY()+ball.getHeight());
		if(isBrickOrPaddle(obj)) return true;
		obj = getElementAt(ball.getX()+ball.getWidth(),ball.getY()+ball.getHeight());
		if(isBrickOrPaddle(obj)) return true;
		obj = getElementAt(ball.getX()+ball.getWidth(),ball.getY());
		if(isBrickOrPaddle(obj)) return true;
		return false;
	}

	private boolean isBrickOrPaddle(GObject obj) {
		if(obj != null){
			if(obj == paddle) {
				if(ball.getY()+ball.getHeight() > paddle.getY()){
					double dif = (ball.getY()+ball.getHeight())-paddle.getY();
					ball.move(0,-dif);
				}
				return true;
			}
			else if(obj.getHeight() == BRICK_HEIGHT && obj.getWidth() == BRICK_WIDTH){
				remove(obj);
				brickCount--;
				updateScore(obj.getColor());
				updateSpeed(obj.getColor());
				return true;
			} else return false;
		}
		return false;
	}

	private void updateSpeed(Color brickColor) {
		if(brickColor == Color.red){
			upSpeed(4, upSpeedCoefficient);
		} else if(brickColor == Color.orange){
			upSpeed(3, upSpeedCoefficient);
		}else if(brickColor == Color.yellow){
			upSpeed(2, upSpeedCoefficient);
		}else if(brickColor == Color.green){
			upSpeed(1, upSpeedCoefficient);
		}
	}

	private void upSpeed(int n, double k) {
		if(n >= speedLevel){
			speedLevel++;
			vy*=k;
			vx*=k;
		}
	}

	private void initPaddle() {
		paddle = CreatePaddle();
		add(paddle);
	}

	private void initBall() {
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) vx = -vx;
        vy = 3.0;
        ball = new GOval(BALL_RADIUS*2,BALL_RADIUS*2);
        double startX = WIDTH/2.0-ball.getWidth()/2.0;
        double startY = HEIGHT/2.0-ball.getHeight()/2.0;
        ball.setFilled(true);
        Color ballCol = new Color(203,41,244);
        ball.setColor(ballCol);
        add(ball,startX,startY);
    }

    private Color returnCol (int num){
			switch (num){
				case 0: return Color.red;
				case 1: return Color.orange;
				case 2: return Color.yellow;
				case 3: return Color.green;
				case 4: return Color.cyan;
				default: return null;
		}
	}

	private void drawBricks() {
		int yBrick = BRICK_Y_OFFSET;
		for(int i = 0;i < NBRICK_ROWS;i++){
			yBrick+=(BRICK_HEIGHT+BRICK_SEP);
			int xBrick = BRICK_SEP;
			for(int a = 0;a < NBRICKS_PER_ROW;a++) {
				GRect rect = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				rect.setFilled(true);
				rect.setColor(nextColor(i));
				add(rect,xBrick,yBrick);
				xBrick+=(BRICK_WIDTH+BRICK_SEP);
			}
		}

	}

	private Color nextColor(int i) {
		int numberOfColors = 5;
		if(NBRICK_ROWS > numberOfColors-1) {
			int boundaryNumberOfRows = NBRICK_ROWS / numberOfColors;
			int ColorsNum = i / boundaryNumberOfRows;
			return returnCol(ColorsNum);
		}else return returnCol(i);
	}

	private GRect CreatePaddle(){
		Color paddleColor = new Color(223,245,119);
		GRect paddle = new GRect(0,getHeight()- Breakout.PADDLE_Y_OFFSET - Breakout.PADDLE_HEIGHT,
				Breakout.PADDLE_WIDTH, Breakout.PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(paddleColor);
		return paddle;
	}

	public void mouseMoved(MouseEvent e) {
		if(paddle != null) {
			while (e.getX() - paddle.getWidth()/2.0 > paddle.getX() && paddle.getX() < this.getWidth() - paddle.getWidth()) {
				paddle.move(1, 0);
			}
			while (e.getX() - paddle.getWidth()/2.0 < paddle.getX() && paddle.getX() > 0) {
				paddle.move(-1, 0);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(isFinishGame) {
			this.removeAll();
			initAllElements();
			win = false;
			isFinishGame = false;
		}
	}

	private void initHearts(){
		if(lives > 0) {
			heart1 = new GImage("Heart.png");
			heart1.scale(0.08);
			add(heart1, this.getWidth() - heart1.getWidth(), 0);
		}
		if(lives > 1) {
			heart2 = new GImage("Heart.png");
			heart2.scale(0.08);
			add(heart2, this.getWidth() - heart2.getWidth() * 2, 0);
		}
		if(lives > 2) {
			heart3 = new GImage("Heart.png");
			heart3.scale(0.08);
			add(heart3, this.getWidth() - heart3.getWidth() * 3, 0);
		}
	}

	private void  initScore(){
		GImage scoreImage = new GImage("ScorePNG.png", 5, 10);
		scoreImage.scale(1.1);
		add(scoreImage);
		scoreLabel = new GLabel(""+score,110,25);
		scoreLabel.setFont("Bahnschrift-bold-23");
		scoreLabel.setColor(Color.white);
		add(scoreLabel);
	}

	private void updateScore(Color brickColor){
	if(brickColor == Color.red)
		score += 5;
	if(brickColor == Color.orange)
		score += 4;
	if(brickColor == Color.yellow)
		score += 3;
	if(brickColor == Color.green)
		score += 2;
	if(brickColor == Color.cyan)
		score++;
	scoreLabel.setLabel(""+score);
	}

	private void timer(){
		int time = 3;
		GLabel timerLabel = new GLabel(""+time);
		timerLabel.setColor(Color.white);
		while (time!=0){
			for (int i = 0; i<100;i++){
				pause(10);
				remove(timerLabel);
				timerLabel.setFont("Bahnschrift-"+i*3);
				add(timerLabel,WIDTH/2.0-timerLabel.getWidth()/2.0,50+HEIGHT/2.0+timerLabel.getHeight()/4.0);
				timerLabel.setLabel(""+time);
			}
			time--;
		}
		remove(timerLabel);
	}
}
