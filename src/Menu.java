import acm.graphics.*;
import acm.util.RandomGenerator;
import acm.util.SoundClip;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Menu extends GCompound {
    private Button startButton;
    private Button exitButton;

    private Button restartButton;
    private Button backButton;

    private GLabel nameGame;

    private GLabel gameOverLabel = new GLabel("GAME OVER");
    private GLabel winLabel;
    private GLabel scoreLabel;

    private boolean exitFlag = false;
    private boolean startFlag = false;

    private double wight;
    private double height;
    private String name;

    private SoundClip music;

    public Menu(String name, double wight, double height){
        this.wight = wight;
        this.height = height;
        this.name = name;
        musicStart();
        drawBackGround();
        initStartMenu();
    }
    private void musicStart(){
        music = new SoundClip("menuMusic.au");
        music.setVolume(0.2);
        music.loop();
    }
    public void initAfterGameMenu(int score, boolean win) {
        musicStart();
        
        restartButton = new Button("RESTART",wight/2.0,height/10.0);
        add(restartButton,wight/2.0- startButton.getWidth()/2.0,height/2.0- startButton.getHeight()/2.0);
        backButton = new Button("BACK",wight/2.0,height/10.0);
        add(backButton,wight/2.0- startButton.getWidth()/2.0,height/2.0+height/4.0- startButton.getHeight()/2.0);


        if(win) winLabel = new GLabel("YOU WIN");
        else winLabel = new GLabel("YOU LOSE");
        scoreLabel = new GLabel("your score: "+ score);

        gameOverLabel.setFont("GameOver-"+(int)(wight/2.5));
        winLabel.setFont("GameOver-"+(int)(wight/2.5));
        scoreLabel.setFont("GameOver-"+(int)(wight/5));

        gameOverLabel.setColor(Color.white);
        winLabel.setColor(Color.white);
        scoreLabel.setColor(Color.white);

        double y = height/6;
        add(gameOverLabel,wight/2.0-gameOverLabel.getWidth()/2.0,y);
        add(winLabel,wight/2.0-winLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/1.5);
        add(scoreLabel,wight/2.0-scoreLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/0.9);

    }
    private void removeAfterGameMenu(){
        music.stop();
        remove(restartButton);
        remove(backButton);
        remove(gameOverLabel);
        remove(winLabel);
        remove(scoreLabel);
    }

    private void initStartMenu() {
        nameGame = new GLabel(name);
        nameGame.setFont("GameOver-"+(int)(wight/3.0));
        nameGame.setColor(Color.red);
        add(nameGame,wight/2.0-nameGame.getWidth()/2.0,height/5.0+nameGame.getHeight()/4.0);
        startButton = new Button("START",wight/2.0,height/10.0);
        add(startButton,wight/2.0- startButton.getWidth()/2.0,height/2.0- startButton.getHeight()/2.0);
        exitButton = new Button("EXIT",wight/2.0,height/10.0);
        add(exitButton,wight/2.0- startButton.getWidth()/2.0,height/2.0+height/4.0- startButton.getHeight()/2.0);
    }

    private void removeStartMenu(){
        music.stop();
        remove(nameGame);
        remove(startButton);
        remove(exitButton);
    }

    private void drawBackGround() {
        GRect rect = new GRect(wight,height);
        rect.setFilled(true);
        Color background = new Color(48,48,48);
        rect.setColor(background);
        add(rect);
    }

    GObject obj;
    Button lastButton;
    public void mouseMoved(MouseEvent e){
        obj = getElementAt(e.getX(),e.getY());
        if(obj != null) {
            if (lastButton == null) {
                if (obj.getClass() == startButton.getClass()) {
                    lastButton = (Button) obj;
                    lastButton.changeColor(true);
                }
            } else {
                if (obj != lastButton) {
                    lastButton.changeColor(false);
                    lastButton = null;
                }
                if (obj.getClass() == startButton.getClass()) {
                    lastButton = (Button) obj;
                    lastButton.changeColor(true);
                }
            }
        }
    }
    RandomGenerator rnd = RandomGenerator.getInstance();

    public void mouseClicked(MouseEvent e) {
        if(lastButton != null){
            if(lastButton == exitButton) {
                exitFlag = true;
            } else if(lastButton == startButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeStartMenu();
                startFlag = true;
            } else if(lastButton == restartButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeAfterGameMenu();
                startFlag = true;
            } else if(lastButton == backButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeAfterGameMenu();
                initStartMenu();
            }
        }
    }


    public void changeNameColor(){
        nameGame.setColor(rnd.nextColor());
    }

    public boolean getExitFlag(){
        return exitFlag;
    }

    public boolean getStartFlag(){
        return startFlag;
    }

    public void changeStartFlag(boolean flag){
        startFlag = flag;
    }
}
