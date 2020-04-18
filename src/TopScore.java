import acm.graphics.*;
import acm.program.*;
import acm.util.ErrorException;
import com.sun.source.tree.Scope;

import java.awt.*;
import java.io.*;
import java.util.StringTokenizer;

public class TopScore extends GCompound{

    private Score s1 = new Score();
    private Score s2 = new Score();
    private Score s3 = new Score();
    private Score s4 = new Score();
    private Score s5 = new Score();
    private Score s6 = new Score();
    private Score s7 = new Score();
    private Score s8 = new Score();
    private Score s9 = new Score();

    private double wight;
    private double height;
    public TopScore(String filePath, double wight, double height){
        this.wight = wight;
        this.height = height;
        initScores(filePath);
        drawScores();
    }

    private void drawScores() {
        double labelHeight = (height/9.0)*0.95;
        double sep = (height-(labelHeight*10.0))/10.0;
        String font = findFont("GameOver", labelHeight);
        double x = wight/4.0;
        for(int i = 0; i< 9;i++){
            Score temp = nextScore(i);
            temp.setFont(font);
            temp.setColor(Color.white);
            add(temp,x,labelHeight*(i+1)+sep*i);
        }
    }

    private Score nextScore(int i) {
        switch (i){
            case 0:
                return s1;
            case 1:
                return s2;
            case 2:
                return s3;
            case 3:
                return s4;
            case 4:
                return s5;
            case 5:
                return s6;
            case 6:
                return s7;
            case 7:
                return s8;
            case 8:
                return s9;
        }
        return null;
    }

    private String findFont(String font, double maxHeight) {
        int fontSize = 1;
        GLabel temp = new GLabel("temp");
        String returnFont = font+"-"+fontSize;
        temp.setFont(returnFont);
        while (temp.getHeight() < maxHeight){
            fontSize++;
            returnFont = font+"-"+fontSize;
            temp.setFont(returnFont);
        }
        return returnFont;
    }

    private void initScores(String filePath) {
        try{
            BufferedReader reader = new BufferedReader( new FileReader(filePath));
            s1.change(writeLine(reader));
            s2.change(writeLine(reader));
            s3.change(writeLine(reader));
            s4.change(writeLine(reader));
            s5.change(writeLine(reader));
            s6.change(writeLine(reader));
            s7.change(writeLine(reader));
            s8.change(writeLine(reader));
            s9.change(writeLine(reader));
            reader.close();

        } catch (FileNotFoundException ignored){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshScore(int score, int difficulty){
        int tempScore = score;
        int tempDifficult = difficulty;
        boolean refreshFlag = false;
        for(int i = 0;i < s1.getCount()-1;i++){
            Score sc = nextScore(i);
            if(tempScore > sc.getNum()){
                int swapScore = sc.getNum();
                int swapDifficult = sc.getDifficultNum();
                sc.setNum(tempScore);
                sc.setDifficulty(tempDifficult);
                tempScore = swapScore;
                tempDifficult = swapDifficult;
                refreshFlag = true;
            }
        }
        if(refreshFlag){
            saveScores();
            redrawScores();
        }
    }

    private void redrawScores() {
        double x = wight/4.0;
        for(int i = 0; i< 9;i++){
            Score temp = nextScore(i);
            double y = temp.getY();
            temp.setLocation(x,y);
        }
    }

    private void saveScores() {
        try {
            PrintWriter wr = new PrintWriter(new FileWriter("topScore.txt"));
            for(int i = 0;i < s1.getCount()-1;i++){
                Score temp = nextScore(i);
                wr.println(temp.getNum()+" "+temp.getDifficultNum());
            }
            wr.close();
        }catch (IOException e){
            throw new ErrorException(e);
        }
    }

    private String writeLine(BufferedReader reader) {
        try {
            String str = reader.readLine();
            if(str != null) {
                return str;
            } else return "0";
        }catch (IOException e){
            throw new ErrorException(e);
        }
    }

}
