package it.polimi.ingsw.view.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private final Map<String, Image> cache;

    public ImageCache(){
        cache = new HashMap<>();
    }

    public Image getImage(String resource) {
        if(cache.containsKey(resource)){
            return cache.get(resource);
        } else {
            Image img = loadImage(resource);
            cache.put(resource, img);

            return img;
        }
    }

    public static Image loadImage(String resource) {
        URL imageURL = ImageCache.class.getResource(resource);

        if (imageURL != null)
            return new ImageIcon(imageURL).getImage();
        else
            return defaultImage();
    }

    private static Image defaultImage() {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1, 1);

        return img;
    }
}
