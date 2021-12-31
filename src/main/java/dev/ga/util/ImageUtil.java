package dev.ga.util;

import java.awt.image.BufferedImage;

public class ImageUtil {

    // ITU BT.709 Photometric/digital luminance
    public static float getLuminance(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);
        var r = (rgb >> 16) & 0xff;
        var g = (rgb >> 8) & 0xff;
        var b = rgb & 0xff;
        return 0.2126f * r + 0.7152f * g + 0.0722f * b;
    }


}
