import acm.graphics.*;
import acm.util.ErrorException;
import acm.util.RandomGenerator;
import acm.util.SoundClip;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;

public class Menu extends GCompound {
    //Start menu
    private GLabel nameGame;
    private Button startButton;
    private Button scoreButton;
    private Button settingsButton;
    private Button exitButton;
    //Game Over menu
    private Button restartButton;
    private Button backButton;
    private GLabel gameOverLabel;
    private GLabel winLabel;
    private GLabel loseLabel;
    private GLabel scoreLabel;
    //Top score menu
    private TopScore tops;
    private GLabel topScoreLabel;

    private SettingsMenu settingsM;

    private boolean exitFlag = false;
    private boolean startFlag = false;

    private double wight;
    private double height;
    private String name;
    private double buttonWight;
    private double buttonHeight;
  
    private SoundClip music;
    private SoundClip buttonSound;
    private boolean musicPlay = true;
    private boolean soundEffectsPlay = true;
    private int difficultNum = 1;

    /**
     * конструктор меню
     * @param name назва гри
     * @param wight ширина вікна
     * @param height висота вікна
     */
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

    /**
     * Запуск фонової музики в меню
     */
 private void musicStart(){
        if(musicPlay) {
            music = new SoundClip("menuMusic.au");
            music.setVolume(0.2);
            music.loop();
        }
    }

    /**
     * Звук, коли натискаєш на кнопку.
     */
    private void buttonSoundPlay(){
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
     * Ініціалізує всі об'єкти в меню
     */
    private void initAllElements() {
        readSettings();
        musicStart();
        String mainFont = "GameOver";

        nameGame = new GLabel(name);
        nameGame.setFont(mainFont+"-"+(int)(wight/3.0));
        nameGame.setColor(Color.red);
        startButton = new Button("START",buttonWight,buttonHeight);
        scoreButton = new Button("TOP SCORE",buttonWight,buttonHeight);
        settingsButton = new Button("SETTINGS",buttonWight,buttonHeight);
        exitButton = new Button("EXIT",buttonWight,buttonHeight);

        restartButton = new Button("RESTART",buttonWight,buttonHeight);
        backButton = new Button("BACK",buttonWight,buttonHeight);
        winLabel = new GLabel("YOU WIN");
        loseLabel = new GLabel("YOU LOSE");
        scoreLabel = new GLabel("your score: 0");
        gameOverLabel = new GLabel("GAME OVER");
        gameOverLabel.setFont(mainFont+"-"+(int)(wight/2.5));
        winLabel.setFont(mainFont+"-"+(int)(wight/2.5));
        loseLabel.setFont(mainFont+"-"+(int)(wight/2.5));
        scoreLabel.setFont(mainFont+"-"+(int)(wight/5));
        gameOverLabel.setColor(Color.white);
        winLabel.setColor(Color.white);
        loseLabel.setColor(Color.white);
        scoreLabel.setColor(Color.white);

        topScoreLabel = new GLabel("TOP SCORE");
        topScoreLabel.setFont(mainFont+"-"+(int)(wight/3.0));
        topScoreLabel.setColor(Color.white);
        tops = new TopScore("topScore.txt",400,400);

        settingsM = new SettingsMenu(wight,height/1.5, musicPlay, soundEffectsPlay, difficultNum);
    }

    /**
     * Ініціалізує об'єкти в меню закінчення гри.
     * @param score Результат набраний протягом гри
     * @param win Перевіряє чи переміг гравець
     */
    public void initAfterGameMenu(int score, boolean win) {
        musicStart();
        scoreLabel.setLabel("your score: "+ score);
        double y = height/6;
        add(gameOverLabel,wight/2.0-gameOverLabel.getWidth()/2.0,y);
        if(win) add(winLabel,wight/2.0-winLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/1.5);
        else add(loseLabel,wight/2.0-winLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/1.5);
        add(scoreLabel,wight/2.0-scoreLabel.getWidth()/2.0,y+gameOverLabel.getHeight()/0.9);
        double sep = (startButton.getHeight()/4.0);
        double yB = height/2.0- startButton.getHeight()/2.0;
        if(yB < scoreLabel.getY()) yB = scoreLabel.getY()+sep;
        add(restartButton,wight/2.0- restartButton.getWidth()/2.0,yB);
        add(backButton,wight/2.0- backButton.getWidth()/2.0,yB+sep+buttonHeight);

        tops.refreshScore(score, difficultNum);
    }

    /**
     * Прибирає всі об'єкти в меню закінчення гри
     */
    private void removeAfterGameMenu(){
        remove(restartButton);
        remove(backButton);
        remove(gameOverLabel);
        remove(winLabel);
        remove(loseLabel);
        remove(scoreLabel);
    }

    /**
     * Ініціалізує об'єкти в стартовому меню
     */
    private void initStartMenu() {
        nameGame = new GLabel(name);
        nameGame.setFont("GameOver-"+(int)(wight/3.0));
        nameGame.setColor(Color.red);

        add(nameGame,wight/2.0-nameGame.getWidth()/2.0,height/5.0+nameGame.getHeight()/4.0);
        double y = height/2.5- startButton.getHeight()/2.0;
        double sep = (startButton.getHeight()/4.0);
        add(startButton,wight/2.0- startButton.getWidth()/2.0,y);
        add(scoreButton,wight/2.0-scoreButton.getWidth()/2.0,y+sep+buttonHeight);
        add(settingsButton,wight/2.0- startButton.getWidth()/2.0,y+(2*sep)+(2*buttonHeight));
        add(exitButton,wight/2.0- startButton.getWidth()/2.0,y+(3*sep)+(3*buttonHeight));
    }

    /**
     * Видаляє об'єкти стартового меню
     */
    private void removeStartMenu(){
        remove(startButton);
        remove(scoreButton);
        remove(settingsButton);
        remove(exitButton);
        remove(nameGame);
    }

    /**
     * Ініціалізує меню результатів
     */
    private void initScoreMenu(){
        add(topScoreLabel,wight/2.0-topScoreLabel.getWidth()/2.0,height/10);
        add(tops,0,height/8);
        add(backButton,wight/2.0- backButton.getWidth()/2.0,height/2.0+height/4.0 + backButton.getHeight()/2.0);
    }

    /**
     * Видаляє об'єкти меню результатів
     */
    private void removeScoreMenu(){
        remove(topScoreLabel);
        remove(tops);
        remove(backButton);
    }

    /**
     * Ініціалізує меню налаштувань
     */
    private void initSettingsMenu(){
        add(settingsM,0,0);
        add(backButton,wight/2.0- backButton.getWidth()/2.0,height/2.0+height/4.0 + backButton.getHeight()/2.0);
    }

    /**
     * Видаляє об'єкти меню налаштувань
     */
    private void removeSettingsMenu(){
        remove(settingsM);
        remove(backButton);
        musicPlay = settingsM.musicPlay;
        soundEffectsPlay = settingsM.soundEffectsPlay;
        difficultNum = settingsM.difficultNum;
        saveSettings();
    }

    /**
     * Малює фон
     */
    private void drawBackGround() {
        GRect rect = new GRect(wight,height);
        rect.setFilled(true);
        Color background = new Color(48,48,48);
        rect.setColor(background);
        add(rect);
    }

    GObject obj;
    Button lastButton;

    /**
     * Перевіряє чи наведений зараз курсор на кнопку та змінює колір кнопки в залежності від цього.
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e){
        obj = getElementAt(e.getX(),e.getY());
        if(obj != null) {
            if (lastButton == null) {
                if (obj.getClass() == startButton.getClass()) {
                    lastButton = (Button) obj;
                    lastButton.changeColor(true);
                } else if(obj == settingsM){
                    settingsM.mouseMoved(e);
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

    /**
     * Перевіряє на яку кнопку натиснув користувач
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        if(lastButton != null){
            if(lastButton == exitButton) {
                exitFlag = true;
                buttonSoundPlay();
            } else if(lastButton == startButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeStartMenu();
                if(music != null) music.stop();
                startFlag = true;
                buttonSoundPlay();
            } else if(lastButton == restartButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeAfterGameMenu();
                if(music != null) music.stop();
                startFlag = true;
                buttonSoundPlay();
            } else if(lastButton == backButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeAfterGameMenu();
                removeScoreMenu();
                removeSettingsMenu();
                initStartMenu();
                buttonSoundPlay();
            } else if(lastButton == scoreButton){
                lastButton.changeColor(false);
                lastButton = null;
                removeStartMenu();
                initScoreMenu();
                buttonSoundPlay();
            } else if(lastButton == settingsButton){
                settingsButton.changeColor(false);
                lastButton = null;
                removeStartMenu();
                initSettingsMenu();
                buttonSoundPlay();
            }
        } else if(getElementAt(e.getX(),e.getY()) == settingsM){
            settingsM.mouseCLicked(e);
            if(musicPlay != settingsM.musicPlay){
                musicPlay = settingsM.musicPlay;
                if(musicPlay) musicStart();
                else music.stop();
            }
        }
    }

    /**
     * Змінює колір назви гри
     */
    public void changeNameColor(){
        nameGame.setColor(rnd.nextColor());
    }

    /**
     * @return флаг виходу з мгри
     */
    public boolean getExitFlag(){
        return exitFlag;
    }

    /**
     * @return флаг початку гри
     */
    public boolean getStartFlag(){
        return startFlag;
    }

    /**
     * змінює стартовий флаг
     * @param flag флаг
     */
    public void changeStartFlag(boolean flag){
        startFlag = flag;
    }

    /**
     * @return boolean музика грає
     */
    public boolean isMusicPlay(){
        return musicPlay;
    }

    /**
     * @return boolean звуки грають
     */
    public boolean isSoundEffectsPlay(){
        return soundEffectsPlay;
    }

    /**
     * @return складність в int
     */
    public int getDifficult(){
        return difficultNum;
    }

    /**
     * Читає з фойлу налаштування, які були при минулому запуску гри.
     */
    public void readSettings(){
        try{
            BufferedReader reader = new BufferedReader( new FileReader("settings.txt"));
            musicPlay = readBoolean(reader.readLine());
            soundEffectsPlay = readBoolean(reader.readLine());
            difficultNum = readInt(reader.readLine());
            reader.close();
        } catch (FileNotFoundException ignored){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запам'ятовує налаштування користувача
     */
    public void saveSettings(){
        try {
            PrintWriter wr = new PrintWriter(new FileWriter("settings.txt"));
            wr.println(musicPlay?"1":"0");
            wr.println(soundEffectsPlay?"1":"0");
            wr.println(difficultNum);
            wr.close();
        }catch (IOException e){
            throw new ErrorException(e);
        }
    }

    /**
     * читає стрічку та повертає значення int цієї стрічки
     * @param readLine стрічка
     * @return номер int
     */
    private int readInt(String readLine) {
        if(readLine != null) {
            int num = Integer.valueOf(readLine);
            return num;
        }
        return 1;
    }

    /**
     * читає стрічку і повертає false якщо перший символ 0, інкаше true
     * @param line стрічка
     * @return boolean
     */
    private boolean readBoolean(String line) {
        if(line != null){
            return line.charAt(0) != '0';
        }
        return true;
    }
}
