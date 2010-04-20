package cesium.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 19.08.2009
 * @Time: 19:11:52
 */
public class ImageUtilities {

    /** Create Image from a file, then turn that into a BufferedImage.
     */

    public static BufferedImage getBufferedImage(String imageFile,
                                                 Component c) {
        Image image = c.getToolkit().getImage(imageFile);
        waitForImage(image, c);
        BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(c), image.getHeight(c),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, c);
        return(bufferedImage);
    }

    public static BufferedImage getBufferedImage(byte[] data,
                                                 Component c) {
        Image image = c.getToolkit().createImage(data);
        waitForImage(image, c);
        BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(c), image.getHeight(c),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, c);
        return(bufferedImage);
    }

    public static BufferedImage getBufferedImage(byte[] data) {
/*        Toolkit toolkit = new WToolkit();
        Image image = toolkit.createImage(data);*/
        ImageIcon icon = new ImageIcon(data);
        Image image = icon.getImage();
        BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(null),
                        image.getHeight(null),
                        BufferedImage.TYPE_INT_RGB);

        // Draw Image into BufferedImage
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);

        return(bufferedImage);
    }

    /** Take an Image associated with a file, and wait until it is
     *  done loading. Just a simple application of MediaTracker.
     *  If you are loading multiple images, don't use this
     *  consecutive times; instead use the version that takes
     *  an array of images.
     */

    public static boolean waitForImage(Image image, Component c) {
        MediaTracker tracker = new MediaTracker(c);
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        } catch(InterruptedException ie) {}
        return(!tracker.isErrorAny());
    }

    /** Take some Images associated with files, and wait until they
     *  are done loading. Just a simple application of MediaTracker.
     */

    public static boolean waitForImages(Image[] images, Component c) {
        MediaTracker tracker = new MediaTracker(c);
        for(int i=0; i<images.length; i++)
            tracker.addImage(images[i], 0);
        try {
            tracker.waitForAll();
        } catch(InterruptedException ie) {}
        return(!tracker.isErrorAny());
    }
}

