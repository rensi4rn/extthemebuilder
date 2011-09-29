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

public class ForegroundShiftOp implements BufferedImageOp {

    public static final int a_mask = 0xff000000;
    public static final int r_mask = 0xff0000;
    public static final int g_mask = 0xff00;
    public static final int b_mask = 0xff;
    private int newR;
    private int newG;
    private int newB;


    public ForegroundShiftOp(int newR, int newG, int newB) {
        this.newR = newR;
        this.newG = newG;
        this.newB = newB;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        BufferedImage origDst = src;
        int width = src.getWidth();
        int height = src.getHeight();

        //byte[] compsA= new byte[]{0};
        byte[] compsR=new byte[]{0};
        byte[] compsG=new byte[]{0};
        byte[] compsB=new byte[]{0};
        int oldAnotShifted;

        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                int oldRgb = src.getRGB(x, y);

                oldAnotShifted = oldRgb & a_mask;
                //compsA[0]=(byte)(oldAnotShifted >>24);
                compsR[0]=(byte)((oldRgb&r_mask)>>16);
                compsG[0]=(byte)((oldRgb&g_mask)>>8);
                compsB[0]=(byte)(oldRgb&b_mask);


                this.rescale(compsR, compsG, compsB);

                int newRgb = oldAnotShifted
                        |((compsR[0]<<16)&r_mask)
                        |((compsG[0]<<8)&g_mask)
                        |(compsB[0]&b_mask);


                src.setRGB(x,y,newRgb);
            }
        }

        return origDst;
    }
    public BufferedImage filterToWhite(BufferedImage src, BufferedImage dest) {
        BufferedImage origDst = src;

        ColorModel colorModel = src.getColorModel();
        if (colorModel instanceof IndexColorModel){
            IndexColorModel icm = (IndexColorModel) colorModel;
            int size = icm.getMapSize();
            byte[] reds = new byte[size], greens = new byte[size], blues = new byte[size], alphas = new byte[size];
            icm.getReds(reds);
            icm.getGreens(greens);
            icm.getBlues(blues);
            icm.getAlphas(alphas);
            this.rescaleToWhite(reds, greens, blues);
            IndexColorModel newIndexColorModel = new IndexColorModel(8, size, reds, greens, blues, alphas);
            origDst = new BufferedImage(newIndexColorModel, src
                    .getRaster(), false, null);
        }else{
            int width = src.getWidth();
            int height = src.getHeight();
            //byte[] compsA= new byte[]{0};
            byte[] compsR=new byte[]{0};
            byte[] compsG=new byte[]{0};
            byte[] compsB=new byte[]{0};
            int oldAnotShifted;
            for (int x=0; x<width; x++){
                for (int y=0; y<height; y++){
                    int oldRgb = src.getRGB(x, y);

                    oldAnotShifted = oldRgb & a_mask;
                    //compsA[0]=(byte)(oldAnotShifted >>24);
                    compsR[0]=(byte)((oldRgb&r_mask)>>16);
                    compsG[0]=(byte)((oldRgb&g_mask)>>8);
                    compsB[0]=(byte)(oldRgb&b_mask);


                    this.rescaleToWhite(compsR, compsG, compsB);

                    int newRgb = oldAnotShifted
                            |((compsR[0]<<16)&r_mask)
                            |((compsG[0]<<8)&g_mask)
                            |(compsB[0]&b_mask);


                    src.setRGB(x,y,newRgb);
                }
            }
        }

        return origDst;
    }

    public void rescale(byte[] compsR, byte[] compsG, byte[] compsB){
        for (int i = 0; i < compsR.length; ++i) {

            boolean oldCloseToWhite = GammaUtil.closeToWhite(compsR[i], compsG[i], compsB[i]);
            boolean newCloseToWhite = GammaUtil.closeToWhite(this.newR, this.newG, this.newB);

            boolean invertNeeded = !(oldCloseToWhite ^ newCloseToWhite);
            compsR[i] = (byte)(invertNeeded ?255-compsR[i]:compsR[i]);
            compsG[i] = (byte)(invertNeeded ?255-compsG[i]:compsG[i]);
            compsB[i] = (byte)(invertNeeded ?255-compsB[i]:compsB[i]);
        }
    }

    public void rescaleToWhite(byte[] compsR, byte[] compsG, byte[] compsB){
        for (int i = 0; i < compsR.length; ++i) {

            boolean oldCloseToWhite = GammaUtil.closeToWhite(compsR[i], compsG[i], compsB[i]);
            boolean newCloseToWhite = GammaUtil.closeToWhite(this.newR, this.newG, this.newB);

            boolean invertNeeded = !(oldCloseToWhite ^ newCloseToWhite);
            compsR[i] = (byte)(invertNeeded ?255:compsR[i]);
            compsG[i] = (byte)(invertNeeded ?255:compsG[i]);
            compsB[i] = (byte)(invertNeeded ?255:compsB[i]);
        }
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return null;
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return null;
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return null;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    public BufferedImage draw(BufferedImage image, BufferedImage drawableImage, byte nBits) {
        BufferedImage result = null;
        ColorModel colorModel = image.getColorModel();
        ColorModel colorModelForDrawable = drawableImage.getColorModel();
        if (colorModel instanceof IndexColorModel
                && colorModelForDrawable instanceof IndexColorModel){
            IndexColorModel icm = (IndexColorModel) colorModel;
            IndexColorModel dicm = (IndexColorModel) colorModelForDrawable;

            int size = icm.getMapSize();
            int dsize = dicm.getMapSize();
            int resultsize = size+dsize;

            byte[] reds = new byte[size], greens = new byte[size], blues = new byte[size], alphas = new byte[size];
            byte[] dreds = new byte[dsize], dgreens = new byte[dsize], dblues = new byte[dsize], dalphas = new byte[dsize];
            byte[] resultreds = new byte[resultsize], resultgreens = new byte[resultsize], resultblues = new byte[resultsize], resultalphas = new byte[resultsize];
            icm.getReds(reds);
            icm.getGreens(greens);
            icm.getBlues(blues);
            icm.getAlphas(alphas);

            dicm.getReds(dreds);
            dicm.getGreens(dgreens);
            dicm.getBlues(dblues);
            dicm.getAlphas(dalphas);

            System.arraycopy(reds,0,resultreds,0,size);
            System.arraycopy(dreds,0,resultreds,size,dsize);

            System.arraycopy(greens,0,resultgreens,0,size);
            System.arraycopy(dgreens,0,resultgreens,size,dsize);

            System.arraycopy(blues,0,resultblues,0,size);
            System.arraycopy(dblues,0,resultblues,size,dsize);

            System.arraycopy(alphas,0,resultalphas,0,size);
            System.arraycopy(dalphas,0,resultalphas,size,dsize);

            IndexColorModel newIndexColorModel=null;

            WritableRaster raster = image.getRaster();
            WritableRaster draster = drawableImage.getRaster();
            int width = image.getWidth();
            int height = image.getHeight();
            WritableRaster newRaster;

            newIndexColorModel = new IndexColorModel(nBits, resultsize
                    , resultreds, resultgreens, resultblues, resultalphas);
            newRaster = newIndexColorModel.createCompatibleWritableRaster(width, height);

            int[] pixel;
            int[] newIndex = new int[]{0};
            for (int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    pixel = draster.getPixel(x, y, (int[]) null);
                    int alphaIndex = pixel[0];
                    if (dalphas[alphaIndex]==-1){
                        newIndex[0] = alphaIndex + size;
                        newRaster.setPixel(x,y, newIndex);
                    }else{
                        int[] ints = raster.getPixel(x, y, (int[]) null);
                        newRaster.setPixel(x,y,ints);
                    }
                }
            }
            result = new BufferedImage(newIndexColorModel, newRaster, false, null);
            //system should recalc color palette
            //PaletteBuilderStub paletteBuilderStub = new PaletteBuilderStub();
            //result = paletteBuilderStub.getReindexedImage(result);

        }
        return result;
    }

    public boolean isCloseToBlack(){
        return !GammaUtil.closeToWhite(this.newR, this.newG, this.newB);
    }
}
