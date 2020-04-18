import acm.graphics.GCompound;
import acm.graphics.GLabel;

import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;

public class Score extends GCompound {
    private static int count = 1;
    private int num;
    private String scoreNumString = "000";
    private int scoreNum = 0;
    private int difficultNum = -1;
    private GLabel main;
    public Score(){
        num = count;
        count++;
        main = new GLabel(toString());
        add(main);
    }

    public String toString(){
        String str = (num+"#  ");
        if(num == 1) str+=" ";
        str+=(scoreNumString + "   "+difficultToString());
        return str;
    }

    public int getNum(){
        return scoreNum;
    }

    public void setNum(int n){
        scoreNum = n;
        if(scoreNum == -1) scoreNumString = "000";
        else scoreNumString = String.valueOf(n);
        while (scoreNumString.length() < 3) scoreNumString = "0"+scoreNumString;
        main.setLabel(toString());
    }

    public void setNum(String n){
        if(n.length() > 0) scoreNum = Integer.valueOf(n);
        if(scoreNum == -1) scoreNumString = "000";
        else scoreNumString = n;
        while (scoreNumString.length() < 3) scoreNumString = "0"+scoreNumString;
        main.setLabel(toString());
    }

    public void setDifficulty(int difficult){
        difficultNum = difficult;
        main.setLabel(toString());
    }
    public void setDifficulty(String difficult){
        if(difficult.length() > 0) difficultNum = Integer.valueOf(difficult);
        main.setLabel(toString());
    }

    public void setColor(Color col){
        main.setColor(col);
    }

    public void setFont(String s){
        main.setFont(s);
    }

    public int getCount(){
        return count;
    }
    public int getDifficultNum(){
        return difficultNum;
    }

    private String difficultToString() {
        switch (difficultNum){
            case 0:
                return "EASY";
            case 1:
                return "NORMAL";
            case 2:
                return "HARD";
            default:
                return "";
        }
    }

    public void change(String line) {
        setNum(wordIndex(line,0));
        setDifficulty(wordIndex(line,1));
    }
    private String wordIndex(String str, int i) {
        StringTokenizer token = new StringTokenizer(str);
        if(token.countTokens() > 1 ){
            if(i == 0) return token.nextToken();
            else if(i == 1) {
                token.nextToken();
                return token.nextToken();
            }
        }
        return "-1";
    }
}
