import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

import java.awt.*;

public class Button extends GCompound {
    GRoundRect butt;
    GLabel name;
    private double width,height;
    private boolean visible = true;
    public Button(String buttonName, double width, double height){
        this.width = width;
        this.height = height;
        butt = new GRoundRect(width,height);
        butt.setFilled(true);
        Color background = new Color(41, 41, 41);
        butt.setColor(background);
        add(butt);

        name = new GLabel(buttonName);
        int fontSize = (int)(width/2.0);
        checkNameSize(name,"GameOver", fontSize);
        name.setColor(Color.white);
        double x = width/2.0-name.getWidth()/2.0;
        double y = height/2.0+name.getHeight()/4.0;
        add(name,x,y);
    }

    private void checkNameSize(GLabel name,String font, int fontSize) {
        int size = fontSize;
        name.setFont(font+'-'+size);
        while(name.getWidth()*1.1 > width){
            size--;
            name.setFont(font+'-'+size);
        }
    }

    public double diagonal(double a, double b){
        return Math.sqrt(a*a+b*b);
    }

    public void changeColor(boolean isMouse){
        if(isMouse){
            Color background = new Color(60, 60, 60);
            butt.setColor(background);
        } else {
            Color background = new Color(41, 41, 41);
            butt.setColor(background);
        }
    }

    public void setVisible(boolean flag){
        butt.setVisible(flag);
        name.setVisible(flag);
    }


}
