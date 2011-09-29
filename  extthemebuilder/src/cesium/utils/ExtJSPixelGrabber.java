/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.utils;

import java.awt.*;
import java.awt.image.*;

// notice here that I extends an image observer, this is
// not necessary, but I need the ImageObserver to be passed as
// a paramater to get the width and height of the image
public final class ExtJSPixelGrabber implements ImageObserver
{
    private Image m_image ;   // pointer to original image
    private Object m_pixels ; // will contains either array of
    // (for gif, it will be bytes)
    private int m_iNumOfColors ;
    private int m_iWidth, m_iHeight;

    private ColorModel m_colorModel ;

    public ExtJSPixelGrabber(Image img)
    {
        m_image=img;
    }

    public void grabPixels()
    {
        m_iWidth=m_image.getWidth(this);
        m_iHeight=m_image.getHeight(this);
        // the parameter false below tells java that we want to open indexed
        // file.  When used with a gif file, this will cause
        // palletized mode grab.  When used with jpg, you'll get DirectColorModel
        // data
        // you can set that to true and it will cause all images to be grabbed
        // in DirectColorModel

        // CONSTRUCT
        PixelGrabber pixelGrabber=new PixelGrabber(m_image, 0,0,
                m_iWidth, m_iHeight, true);

        // GRAB
        try
        {
            pixelGrabber.grabPixels();
        }
        catch (Exception e)
        {
            System.out.println("PixelGrabber exception");
        }

        // GET
        m_pixels=(Object)pixelGrabber.getPixels();
        // get the palette of the image, if possible
        m_colorModel=pixelGrabber.getColorModel();
        // if loading a gif, you will get IndexedColorModel, if jpg, you will
        // get DirectColorModel
        if (!(m_colorModel instanceof IndexColorModel))
        {
            // not an indexed file (ie: not a gif file)
        }
        else
        {
            m_iNumOfColors=((IndexColorModel)m_colorModel).getMapSize();
        }
    }

    // you'd need to cast the return values, which will be an array of bytes
    // or an array of ints.  if the file is a gif file, it will return an
    // array of bytes, if jpg, you will get an array of ints
    public Object getPixels()
    {
        return m_pixels;
    }

    public int getWidth()
    {
        return m_iWidth;
    }

    public int getHeight()
    {
        return m_iHeight;
    }

    // this won't contain a valid value if you're grabbing
    // a non palletized image (such as jpgs)
    public int getNumOfColors()
    {
        return m_iNumOfColors;
    }

    // returns the red component value of a pixel
    public int getRed(int pixel)
    {
        if ((m_colorModel instanceof IndexColorModel))
            return ((IndexColorModel)m_colorModel).getRed(pixel);
        else
            return ((DirectColorModel)m_colorModel).getRed(pixel);
    }

    // returns the green component value of a pixel
    public int getGreen(int pixel)
    {
        if ((m_colorModel instanceof IndexColorModel))
            return ((IndexColorModel)m_colorModel).getGreen(pixel);
        else
            return ((DirectColorModel)m_colorModel).getGreen(pixel);
    }

    // returns the blue component value of a pixel
    public int getBlue(int pixel)
    {
        if ((m_colorModel instanceof IndexColorModel))
            return ((IndexColorModel)m_colorModel).getBlue(pixel);
        else
            return ((DirectColorModel)m_colorModel).getBlue(pixel);
    }

    // returns the alpha component value of a pixel
    public int getAlpha(int pixel)
    {
        if ((m_colorModel instanceof IndexColorModel))
            return ((IndexColorModel)m_colorModel).getAlpha(pixel);
        else
            return ((DirectColorModel)m_colorModel).getAlpha(pixel);
    }

    public void destroy()
    {
        m_image=null;
        m_pixels=null;
    }

    // we need this method just because we're extending ImageObserver.
    public boolean imageUpdate(Image img, int infoflags, int x, int y,
                               int width, int height)
    {
        return true;
    }
}
