import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingsMenu extends GCompound {

    private double wight;
    private double height;
    GLabel musicLabel = new GLabel("MUSIC:");
    GLabel soundEffectsLabel = new GLabel("SOUND EFFECTS:");
    Button musicButton;
    Button soundEffectsButton;
    public boolean musicPlay = true;
    public boolean soundEffectsPlay = true;
    private double buttonSize;

    public SettingsMenu(double wight, double height){
        this.wight = wight;
        this.height = height;
        buttonSize = wight/6;
        initAllElements();
    }

    private void initAllElements() {
            musicButton = new Button("ON",buttonSize,buttonSize);
            soundEffectsButton = new Button("ON",buttonSize,buttonSize);
            initBackground();
            String font = "GameOver-"+(int)(wight/4.5);
            musicLabel.setFont(font);
            soundEffectsLabel.setFont(font);
            musicLabel.setColor(Color.white);
            soundEffectsLabel.setColor(Color.white);
            double y = height/4.0;
            double x = wight/15;
            add(musicLabel,x,y);
            add(soundEffectsLabel, x,y+musicLabel.getHeight()*1.5);
            double xB = x+soundEffectsLabel.getWidth()+musicButton.getWidth()/2.0;
            double yB = y-musicLabel.getHeight()/4.0-musicButton.getHeight()/2.0;
            musicButton.setFont(font);
            soundEffectsButton.setFont(font);
            add(musicButton,xB,yB);
            add(soundEffectsButton,xB,yB+musicLabel.getHeight()*1.5);
    }

    private void initBackground() {
        GRect backgound = new GRect(wight,height);
        backgound.setVisible(false);
        add(backgound,0,0);
    }

    GObject obj;
    Button lastButton;
    public void mouseMoved(MouseEvent e){
        obj = getElementAt(e.getX(),e.getY());
        if(obj != null) {
            if (lastButton == null) {
                if (obj.getClass() == musicButton.getClass()) {
                    lastButton = (Button) obj;
                    lastButton.changeColor(true);
                }
            } else {
                if (obj != lastButton) {
                    lastButton.changeColor(false);
                    lastButton = null;
                }
                if (obj.getClass() == musicButton.getClass()) {
                    lastButton = (Button) obj;
                    lastButton.changeColor(true);
                }
            }
        }
    }

    public void mouseCLicked(MouseEvent e){
        if(lastButton != null){
            if(lastButton == musicButton){
                if(musicPlay){
                    musicButton.setLabel("OFF");
                }else {
                    musicButton.setLabel("ON");
                }
                musicPlay = !musicPlay;

            } else if(lastButton == soundEffectsButton){
                if(soundEffectsPlay){
                    soundEffectsButton.setLabel("OFF");
                }else soundEffectsButton.setLabel("ON");
                soundEffectsPlay = !soundEffectsPlay;
            }
        }
    }




}
