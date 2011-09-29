/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.op;

import cesium.utils.GammaUtil;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;

public class ShiftOp implements BufferedImageOp {
    public static final int a_mask = 0xff000000;
    public static final int r_mask = 0xff0000;
    public static final int g_mask = 0xff00;
    public static final int b_mask = 0xff;

    private boolean isSumMod256 ;
    
    float[] scaleFactors;
    float[] offsets;

    int length ;


    /**
     * Constructs a new RescaleOp with the desired scale factors
     * and offsets.  The length of the scaleFactor and offset arrays
     * must meet the restrictions stated in the class comments above.
     * The RenderingHints argument may be null.
     * @param scaleFactors the specified scale factors
     * @param offsets the specified offsets
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public ShiftOp(float[] scaleFactors, float[] offsets,
                   RenderingHints hints) {

        length = scaleFactors.length;
        if (length > offsets.length) length = offsets.length;

        this.scaleFactors = new float[length];
        this.offsets      = new float[length];
        for (int i=0; i < length; i++) {
            this.scaleFactors[i] = scaleFactors[i];
            this.offsets[i]      = offsets[i];
        }
        this.hints = hints;
    }

    public ShiftOp(float[] scaleFactors, float[] offsets,
                   RenderingHints hints, boolean isSumMod256) {
        this(scaleFactors, offsets, hints);
        this.isSumMod256 = isSumMod256;
    }

    RenderingHints hints;


    /**
     * Constructs a new ExtJSImageOp with the desired scale factor
     * and offset.  The scaleFactor and offset will be applied to
     * all bands in a source Raster and to all color (but not alpha)
     * components in a BufferedImage.
     * The RenderingHints argument may be null.
     * @param scaleFactor the specified scale factor
     * @param offset the specified offset
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public ShiftOp(float scaleFactor, float offset, RenderingHints hints) {
        length = 1;
        this.scaleFactors = new float[1];
        this.offsets      = new float[1];
        this.scaleFactors[0] = scaleFactor;
        this.offsets[0]       = offset;
        this.hints = hints;
    }



    /**
     * Rescales the source BufferedImage.
     * If the color model in the source image is not the same as that
     * in the destination image, the pixels will be converted
     * in the destination.  If the destination image is null,
     * a BufferedImage will be created with the source ColorModel.
     * An IllegalArgumentException may be thrown if the number of
     * scaling factors/offsets in this object does not meet the
     * restrictions stated in the class comments above, or if the
     * source image has an IndexColorModel.
     * @param src the <code>BufferedImage</code> to be filtered
     * @param dst the destination for the filtering operation
     *            or <code>null</code>
     * @return the filtered <code>BufferedImage</code>.
     * @throws IllegalArgumentException if the <code>ColorModel</code>
     *         of <code>src</code> is an <code>IndexColorModel</code>,
     *         or if the number of scaling factors and offsets in this
     *         <code>ExtJSImageOp</code> do not meet the requirements
     *         stated in the class comments.
     */
    public final BufferedImage filter (BufferedImage src, BufferedImage dst) {
        BufferedImage origDst = src;
        int width = src.getWidth();
        int height = src.getHeight();

        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                int oldRgb = src.getRGB(x, y);

                int oldA=(oldRgb&a_mask)>>24;
                int oldR=(oldRgb&r_mask)>>16;
                int oldG=(oldRgb&g_mask)>>8;
                int oldB=(oldRgb&b_mask);


                int newA;
                int newR;
                int newG;
                int newB;
                if (isSumMod256){
                    newA = GammaUtil.sumMod256(oldA , (int)offsets[3]);
                    newR = GammaUtil.sumMod256(oldR , (int)offsets[0]);
                    newG = GammaUtil.sumMod256(oldG , (int)offsets[1]);
                    newB = GammaUtil.sumMod256(oldB , (int)offsets[2]);
                }else{
                    newA = oldA;//GammaUtil.sumMinMax(oldA , (int)offsets[3]);
                    newR = GammaUtil.sumMinMax(oldR , (int)offsets[0]);
                    newG = GammaUtil.sumMinMax(oldG , (int)offsets[1]);
                    newB = GammaUtil.sumMinMax(oldB , (int)offsets[2]);
                }
                int newRgb = ((newA<<24)&a_mask)
                        |((newR<<16)&r_mask)
                        |((newG<<8)&g_mask)
                        |(newB&b_mask);


                src.setRGB(x,y,newRgb);
            }
        }

        return origDst;
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return null;
    }

    /**
     * Creates a zeroed destination image with the correct size and number of
     * bands.
     * @param src       Source image for the filter operation.
     * @param destCM    ColorModel of the destination.  If null, the
     *                  ColorModel of the source will be used.
     * @return the zeroed-destination image.
     */
    public BufferedImage createCompatibleDestImage (BufferedImage src,
                                                    ColorModel destCM) {
        BufferedImage image;
        if (destCM == null) {
            ColorModel cm = src.getColorModel();
            image = new BufferedImage(cm,
                    src.getRaster().createCompatibleWritableRaster(),
                    cm.isAlphaPremultiplied(),
                    null);
        }
        else {
            int w = src.getWidth();
            int h = src.getHeight();
            image = new BufferedImage (destCM,
                    destCM.createCompatibleWritableRaster(w, h),
                    destCM.isAlphaPremultiplied(), null);
        }

        return image;
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return null;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    /**
     * Creates a zeroed-destination <code>Raster</code> with the correct
     * size and number of bands, given this source.
     * @param src       the source <code>Raster</code>
     * @return the zeroed-destination <code>Raster</code>.
     */
    public WritableRaster createCompatibleDestRaster (Raster src) {
        return src.createCompatibleWritableRaster(src.getWidth(), src.getHeight());
    }

}
