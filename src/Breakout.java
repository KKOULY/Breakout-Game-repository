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

import java.applet.*;
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

/** Number of turns */
	private static final int NTURNS = 3;
	
	/** Number of lives */
	private static final int LIVES = 2;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		this.setSize(WIDTH+16,HEIGHT+62);
		Color backgroundCol = new Color(48,48,48);
		this.setBackground(backgroundCol);
		drawBricks();

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
		int boundaryNumberOfRows = NBRICK_ROWS/numberOfColors;
		int ColorsNum = i/boundaryNumberOfRows;
		return returnCol(ColorsNum);
	}

	private void temp(){
		println();
	}

}
