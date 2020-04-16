import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Menu extends GCompound {
    private Button startButton;
    private Button exitButton;
    private GLabel nameGame;
    private boolean exitFlag = false;
    private boolean startFlag = false;

    private double wight;
    private double height;
    private String name;

    public Menu(String name, double wight, double height){
        this.wight = wight;
        this.height = height;
        this.name = name;
        drawBackGround();
        initStartWindow();
    }

    private void initStartWindow() {
        nameGame = new GLabel(name);
        nameGame.setFont("GameOver-"+(int)(wight/3.0));
        nameGame.setColor(Color.red);
        add(nameGame,wight/2.0-nameGame.getWidth()/2.0,height/5.0+nameGame.getHeight()/4.0);
        startButton = new Button("START",wight/2.0,height/10.0);
        add(startButton,wight/2.0- startButton.getWidth()/2.0,height/2.0- startButton.getHeight()/2.0);
        exitButton = new Button("EXIT",wight/2.0,height/10.0);
        add(exitButton,wight/2.0- startButton.getWidth()/2.0,height/2.0+height/4.0- startButton.getHeight()/2.0);
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
                startFlag = true;
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
