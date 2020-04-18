import acm.graphics.GCompound;
import acm.graphics.GLabel;

import javax.swing.*;
import java.awt.*;

public class Score extends GCompound {
    private static int count = 1;
    private int num;
    private String scoreNumString = "000";
    private int scoreNum = 0;
    private GLabel main;
    public Score(){
        num = count;
        count++;
        main = new GLabel(toString());
        add(main);
    }

    public String toString(){
        return (num+"#  "+scoreNumString);
    }

    public int getNum(){
        return scoreNum;
    }

    public void setNum(int n){
        scoreNum = n;
        scoreNumString = String.valueOf(n);
        while (scoreNumString.length() < 3) scoreNumString = "0"+scoreNumString;
        main.setLabel(toString());
    }

    public void setNum(String n){
        if(n.length() > 0) scoreNum = Integer.valueOf(n);
        scoreNumString = n;
        while (scoreNumString.length() < 3) scoreNumString = "0"+scoreNumString;
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
}
