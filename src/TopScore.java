import acm.graphics.*;
import acm.program.*;
import acm.util.ErrorException;

import java.awt.*;
import java.io.*;

public class TopScore extends GCompound{
    private String s1 = "1#  ";
    private String s2 = "2#  ";
    private String s3 = "3#  ";
    private String s4 = "4#  ";
    private String s5 = "5#  ";
    private String s6 = "6#  ";
    private String s7 = "7#  ";
    private String s8 = "8#  ";
    private String s9 = "9#  ";

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
        for(int i = 0; i< 9;i++){
            GLabel label = new GLabel(nextString(i));
            label.setFont(font);
            label.setColor(Color.white);
            add(label,wight/2.0-label.getWidth()/2.0,labelHeight*(i+1)+sep*i);
        }
    }

    private String nextString(int i) {
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
        return "notFindStr";
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
            s1 = writeLineInto(reader,s1);
            s2 = writeLineInto(reader,s2);
            s3 = writeLineInto(reader,s3);
            s4 = writeLineInto(reader,s4);
            s5 = writeLineInto(reader,s5);
            s6 = writeLineInto(reader,s6);
            s7 = writeLineInto(reader,s7);
            s8 = writeLineInto(reader,s8);
            s9 = writeLineInto(reader,s9);
        } catch (FileNotFoundException ignored){
        }
    }

    private String writeLineInto(BufferedReader reader, String s) {
        try {
            String str = reader.readLine();
            if(str != null) {
                for(int i = 0;i < (3-str.length());i++){
                    s+="0";
                }
                s+=str;
                return s;
            } else return s+"000";
        }catch (IOException e){
            throw new ErrorException(e);
        }
    }

}
