import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

import java.awt.*;

/**
 * ����������� Button
 */
public class Button extends GCompound {
    GRoundRect butt;
    GLabel name;
    private double width,height;
    private boolean visible = true;
    String font = "GameOver";
    public Button(String buttonName, double width, double height){
        this.width = width;
        this.height = height;
        butt = new GRoundRect(width,height);
        butt.setFilled(true);
        Color background = new Color(41, 41, 41);
        butt.setColor(background);
        add(butt);

        name = new GLabel(buttonName);
        checkNameSize(name,font);
        name.setColor(Color.white);
        double x = width/2.0-name.getWidth()/2.0;
        double y = height/2.0+name.getHeight()/4.0;
        add(name,x,y);
    }

    /**
     * ��������� ������� ������ �����
     * @param name GLabel
     * @param font ����� ������
     */
    private void checkNameSize(GLabel name,String font) {
        int size = 1;
        name.setFont(font+'-'+size);
        while(name.getWidth()*1.1 <= width && name.getHeight()*1.1 <= height){
            size++;
            name.setFont(font+'-'+size);
        }
    }

    /**
     * ����� ���� ������
     * @param isMouse ���� �������� � boolean
     */
    public void changeColor(boolean isMouse){
        if(isMouse){
            Color background = new Color(60, 60, 60);
            butt.setColor(background);
        } else {
            Color background = new Color(41, 41, 41);
            butt.setColor(background);
        }
    }

    /**
     * ����� �����
     * @param font �����
     */
    public void setFont(String font){
        name.setFont(font);
        name.setLocation(width/2.0-name.getWidth()/2.0,height/2.0+name.getHeight()/4.0);
    }

    /**
     * ����� label
     * @param label GLabel
     */
    public void setLabel(String label){
        name.setLabel(label);
        name.setLocation(width/2.0-name.getWidth()/2.0,height/2.0+name.getHeight()/4.0);
    }


}
