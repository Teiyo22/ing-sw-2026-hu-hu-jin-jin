package it.polimi.ingsw.view.gui.util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardCache {
    private final Map<String, ImageIcon> front;
    private final Map<String, ImageIcon> back;

    public CardCache(){
        front = new HashMap<>();
        back = new HashMap<>();
    }

    public ImageIcon getFront(String resource){
        return getImageIcon(resource, front);
    }

    public ImageIcon getBack(String resource){
        return getImageIcon(resource, back);
    }

    private ImageIcon getImageIcon(String resource, Map<String, ImageIcon> cache) {
        if(cache.containsKey(resource)){
            return cache.get(resource);
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int height = (int) (screenSize.height * 0.2);
            int width = (int) (height * (2.0 / 3.0));

            Image img = new ImageIcon(getClass().getResource(resource)).getImage();
            ImageIcon icon = new ImageIcon(img.getScaledInstance(width-6, height-6, Image.SCALE_DEFAULT));
            cache.put(resource, icon);

            return icon;
        }
    }
}
