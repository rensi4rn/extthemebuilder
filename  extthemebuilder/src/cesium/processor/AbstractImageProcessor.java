/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.processor;

import cesium.op.ShiftOp;
import cesium.utils.ExtJSPixelGrabber;

import java.awt.*;
import java.awt.image.*;

public abstract class AbstractImageProcessor extends AbstractResourcesProcessor{

    // This method returns the color model of an image
    protected ColorModel getColorModel(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
        ColorModel cm = pg.getColorModel();
        return cm;
    }

    public static BufferedImage convertType(BufferedImage image, int type) {
        if (image.getType() == type)
            return image;
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result;

        //image.copyData(result.getRaster());
/*        Graphics2D g = result.createGraphics();
        g.drawRenderedImage(image, null);
        g.dispose();*/
        ExtJSPixelGrabber pixelGrabber = new ExtJSPixelGrabber(image);
        pixelGrabber.grabPixels();
        Object pixels = pixelGrabber.getPixels();
        if (type != BufferedImage.TYPE_BYTE_INDEXED){
            result = new BufferedImage(width,
                    height, type);
            for(int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    int pixel = ((int[]) pixels)[x + (y*width)];
                    int newA=pixelGrabber.getAlpha(pixel);
                    int newR=pixelGrabber.getRed(pixel);
                    int newG=pixelGrabber.getGreen(pixel);
                    int newB=pixelGrabber.getBlue(pixel);
                    int rgb = ((newA<<24)& ShiftOp.a_mask)
                            |((newR<<16)& ShiftOp.r_mask)
                            |((newG<<8)&ShiftOp.g_mask)
                            |(newB&ShiftOp.b_mask);
                    result.setRGB(x,y,rgb);
                }
            }
        }else if (image.getColorModel() instanceof ComponentColorModel){
            int size = width * height;
            byte r[] = new byte[size];
            byte g[] = new byte[size];
            byte b[] = new byte[size];
            byte a[] = new byte[size];
            Raster raster = image.getData();
            DataBuffer dataBuffer = raster.getDataBuffer();
            for(int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    int pixel = x + (y * width);
                    int index = 4* pixel;
                    r[pixel] = (byte)dataBuffer.getElem(index);
                    g[pixel] = (byte)dataBuffer.getElem(index+1);
                    b[pixel] = (byte)dataBuffer.getElem(index+2);
                    a[pixel] = (byte)dataBuffer.getElem(index+3);
                }
            }
            IndexColorModel indexColorModel = new IndexColorModel(8, size,
                    r,  g,  b, a);
            result = new BufferedImage(width,
                    height, type, indexColorModel);
/*            result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
            Graphics2D g = result.createGraphics();
            g.drawImage(image, )*/
        }else{
            int size = width * height;
            byte r[] = new byte[size];
            byte g[] = new byte[size];
            byte b[] = new byte[size];
            byte a[] = new byte[size];
            for(int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    int pixel = ((int[]) pixels)[x + (y*width)];
                    a[pixel] = (byte)pixelGrabber.getAlpha(pixel);
                    r[pixel] = (byte)pixelGrabber.getRed(pixel);
                    g[pixel] = (byte)pixelGrabber.getGreen(pixel);
                    b[pixel] = (byte)pixelGrabber.getBlue(pixel);
                }
            }
            IndexColorModel indexColorModel = new IndexColorModel(8, size,
                    r,  g,  b, a);
            result = new BufferedImage(width,
                    height, type, indexColorModel);

        }
        return result;
    }

}
