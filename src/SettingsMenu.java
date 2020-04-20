import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.util.SoundClip;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Меню налаштувань
 */
public class SettingsMenu extends GCompound {

    private double wight;
    private double height;
    private GLabel musicLabel = new GLabel("MUSIC:");
    private GLabel soundEffectsLabel = new GLabel("SOUND EFFECTS:");
    private GLabel difficultLabel = new GLabel("DIFFICULT:");
    private Button musicButton;
    private Button soundEffectsButton;
    private Button difficultButton;
    public boolean musicPlay;
    public boolean soundEffectsPlay;
    public int difficultNum;
    private double buttonSize;
    private SoundClip buttonSound;

    /**
     * конструктор SettingsMenu
     * @param wight ширина вікна
     * @param height висота вікна
     * @param musicPlay музика повинна грати
     * @param soundEffectsPlay звуки повінні грати
     * @param difficultNum номер складності
     */
    public SettingsMenu(double wight, double height, boolean musicPlay, boolean soundEffectsPlay, int difficultNum){
        this.wight = wight;
        this.height = height;
        this.musicPlay = musicPlay;
        this.soundEffectsPlay = soundEffectsPlay;
        this.difficultNum = difficultNum;
        buttonSize = wight/7;
        initAllElements();
    }

    /**
     * звук натискання на кнопку
     */
    public void buttonSoundPlay(){
        if (soundEffectsPlay){
            buttonSound = new SoundClip("buttonSound.au");
            buttonSound.setVolume(0.2);
            if (!buttonSound.isStereo()) buttonSound.play();
            else {
                buttonSound.stop();
                buttonSound.rewind();
                buttonSound.play();
            }
        }
    }

    /**
     * ініціалізує всі елементи
     */
    private void initAllElements() {
            musicButton = new Button(musicPlay?"ON":"OFF",buttonSize,buttonSize);
            soundEffectsButton = new Button(soundEffectsPlay?"ON":"OFF",buttonSize,buttonSize);
            difficultButton = new Button(difficultToString(),buttonSize*2.8,buttonSize);
            initBackground();
            String font = "GameOver-"+(int)(wight/4.5);
            musicLabel.setFont(font);
            soundEffectsLabel.setFont(font);
            difficultLabel.setFont(font);
            musicLabel.setColor(Color.white);
            soundEffectsLabel.setColor(Color.white);
            difficultLabel.setColor(Color.white);
            double y = height/4.0;
            double x = wight/18;
            add(musicLabel,x,y);
            add(soundEffectsLabel, x,y+musicLabel.getHeight()*1.5);
            add(difficultLabel,x,y+musicLabel.getHeight()*3.0);
            double xB = x+soundEffectsLabel.getWidth()+musicButton.getWidth()/1.5;
            double yB = y-musicLabel.getHeight()/4.0-musicButton.getHeight()/2.0;
            musicButton.setFont(font);
            soundEffectsButton.setFont(font);
            difficultLabel.setFont(font);
            add(musicButton,xB,yB);
            add(soundEffectsButton,xB,yB+musicLabel.getHeight()*1.5);
            add(difficultButton,xB+buttonSize-difficultButton.getWidth(),yB+musicLabel.getHeight()*3.0);
    }

    /**
     * повертає складність гри в String
     * @return складність in String
     */
    private String difficultToString() {
        switch (difficultNum){
            case 0:
                return "EASY";
            case 1:
                return "NORMAL";
            case 2:
                return "HARD";
            default:
                return "NOT FOUND";
        }
    }

    /**
     * ініціалізує прозорий фон
     */
    private void initBackground() {
        GRect backgound = new GRect(wight,height);
        backgound.setVisible(false);
        add(backgound,0,0);
    }

    GObject obj;
    Button lastButton;

    /**
     * перевіряє наведення на кнопки
     * @param e mouseEvent
     */
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

    /**
     * змінює настройки відносно якої кнопки натиснув користувач
     * @param e MouseEvent
     */
    public void mouseCLicked(MouseEvent e){
        if(lastButton != null){
            if(lastButton == musicButton){
                buttonSoundPlay();
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
                buttonSoundPlay();
            } else if(lastButton == difficultButton){
                buttonSoundPlay();
                difficultNum = nextDifficult();
                difficultButton.setLabel(difficultToString());
            }
        }
    }

    /**
     * змінює складність на наступну
     * @return складність в int
     */
    private int nextDifficult() {
        int countOfDifficulties = 3;
        return (difficultNum+1)%countOfDifficulties;
    }


}
