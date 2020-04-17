import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Menu extends GCompound {
    private Button startButton;
    private Button scoreButton;
    private Button exitButton;

    private Button restartButton;
    private Button backButton;


    private GLabel nameGame;

    private GLabel gameOverLabel = new GLabel("GAME OVER");
    private GLabel winLabel;
    private GLabel loseLabe;
    private GLabel scoreLabel;

    private boolean exitFlag = false;
    private boolean startFlag = false;

    private double wight;
    private double height;
    private String name;

    private double buttonWight;
    private double buttonHeight;

    public Menu(String name, double wight, double height){
        this.wight = wight;
        this.height = height;
        this.name = name;
        buttonWight = wight/1.5;
        buttonHeight = height/10.0;
        drawBackGround();
        initAllElements();
        initStartMenu();
    }

    private void initAllElements() {

        nameGame = new GLabel(name);
        nameGame.setFont("GameOver-"+(int)(wight/3.0));
        nameGame.setColor(Color.red);
        startButton = new Button("START",buttonWight,buttonHeight);
        scoreButton = new Button("TOP SCORE",buttonWight,buttonHeight);
        exitButton = new Button("EXIT",buttonWight,buttonHeight);

        restartButton = new Button("RESTART",buttonWight,buttonHeight);
        backButton = new Button("BACK",buttonWight,buttonHeight);
        winLabel = new GLabel("YOU WIN");
        loseLabe = new GLabel("YOU LOSE");
        scoreLabel = new GLabel("your score: 0");
        gameOverLabel.setFont("GameOver-"+(int)(wight/2.5));
        winLabel.setFont("GameOver-"+(int)(wight/2.5));
        loseLabe.setFont("GameOver-"+(int)(wight/2.5));
        scoreLabel.setFont("GameOver-"+(int)(wight/5));
        gameOverLabel.setColor(Color.white);
        winLabel.setColor(Color.white);
        loseLabe.setColor(Color.white);
        scoreLabel.setColor(Color.white);
    }

    public void initAfterGameMenu(int score, boolean win) {
        double yB = height/2.0- startButton.getHeight()/2.0;
        double sep = (startButton.getHeight()/4.0);
        add(restartButton,wight/2.0- restartButton.getWidth()/2.0,yB);
        add(backButton,wight/2.0- backButton.getWidth()/2.0,yB+sep+buttonHeight);
        scoreLabel.setLabel("your score: "+ score);
        double y = height/6;
        add(gameOverLabel,wight/2.0-gameOverLabel.getWidth()/2.0,y);
        if(win) add(winLabel,wight/2.0-winLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/1.5);
        else add(loseLabe,wight/2.0-winLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/1.5);
        add(scoreLabel,wight/2.0-scoreLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/0.9);

    }
    private void removeAfterGameMenu(){
        remove(restartButton);
        remove(backButton);
        remove(gameOverLabel);
        remove(winLabel);
        remove(loseLabe);
        remove(scoreLabel);
    }

    private void initStartMenu() {
        add(nameGame,wight/2.0-nameGame.getWidth()/2.0,height/5.0+nameGame.getHeight()/4.0);
        double y = height/2.0- startButton.getHeight()/2.0;
        double sep = (startButton.getHeight()/4.0);
        add(startButton,wight/2.0- startButton.getWidth()/2.0,y);
        add(scoreButton,wight/2.0-scoreButton.getWidth()/2.0,y+sep+buttonHeight);
        add(exitButton,wight/2.0- startButton.getWidth()/2.0,y+(2*sep)+(2*buttonHeight));
    }

    private void removeStartMenu(){
        remove(nameGame);
        remove(startButton);
        remove(scoreButton);
        remove(exitButton);
    }

    private void initScoreMenu(){
        add(backButton,wight/2.0- backButton.getWidth()/2.0,height/2.0+height/4.0- backButton.getHeight()/2.0);
    }

    private void removeScoreMenu(){
        remove(backButton);
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
                removeScoreMenu();
                initStartMenu();
            } else if(lastButton == scoreButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeStartMenu();
                initScoreMenu();
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
