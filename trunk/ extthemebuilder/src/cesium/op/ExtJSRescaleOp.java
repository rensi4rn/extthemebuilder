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

import sun.awt.image.ImagingLib;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.Arrays;

public class ExtJSRescaleOp implements BufferedImageOp, RasterOp {
    float[] scaleFactors;
    float[] offsets;
    int length ;
    RenderingHints hints;

    private int srcNbits;
    private int dstNbits;

    float[] borderScaleFactors=new float[]{1,1,1,1};
    float[] borderOffsets;
    int[] borderColor=new int[]{-1};
    boolean isBorderColor ;
    boolean isTransparencyProcessing ;

    /**
     * Constructs a new ExtJSRescaleOp with the desired scale factors
     * and offsets.  The length of the scaleFactor and offset arrays
     * must meet the restrictions stated in the class comments above.
     * The RenderingHints argument may be null.
     * @param scaleFactors the specified scale factors
     * @param offsets the specified offsets
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public ExtJSRescaleOp (float[] scaleFactors, float[] offsets,
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

    /**
     * Constructs a new ExtJSRescaleOp with the desired scale factor
     * and offset.  The scaleFactor and offset will be applied to
     * all bands in a source Raster and to all color (but not alpha)
     * components in a BufferedImage.
     * The RenderingHints argument may be null.
     * @param scaleFactor the specified scale factor
     * @param offset the specified offset
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public ExtJSRescaleOp (float scaleFactor, float offset, RenderingHints hints) {
        length = 1;
        this.scaleFactors = new float[1];
        this.offsets      = new float[1];
        this.scaleFactors[0] = scaleFactor;
        this.offsets[0]       = offset;
        this.hints = hints;
    }

    /**
     * Returns the scale factors in the given array. The array is also
     * returned for convenience.  If scaleFactors is null, a new array
     * will be allocated.
     * @param scaleFactors the array to contain the scale factors of
     *        this <code>ExtJSRescaleOp</code>
     * @return the scale factors of this <code>ExtJSRescaleOp</code>.
     */
    final public float[] getScaleFactors (float scaleFactors[]) {
        if (scaleFactors == null) {
            return (float[]) this.scaleFactors.clone();
        }
        System.arraycopy (this.scaleFactors, 0, scaleFactors, 0,
                Math.min(this.scaleFactors.length,
                        scaleFactors.length));
        return scaleFactors;
    }

    /**
     * Returns the offsets in the given array. The array is also returned
     * for convenience.  If offsets is null, a new array
     * will be allocated.
     * @param offsets the array to contain the offsets of
     *        this <code>ExtJSRescaleOp</code>
     * @return the offsets of this <code>ExtJSRescaleOp</code>.
     */
    final public float[] getOffsets(float offsets[]) {
        if (offsets == null) {
            return (float[]) this.offsets.clone();
        }

        System.arraycopy (this.offsets, 0, offsets, 0,
                Math.min(this.offsets.length, offsets.length));
        return offsets;
    }

    /**
     * Returns the number of scaling factors and offsets used in this
     * ExtJSRescaleOp.
     * @return the number of scaling factors and offsets of this
     *         <code>ExtJSRescaleOp</code>.
     */
    final public int getNumFactors() {
        return length;
    }


    /**
     * Creates a ByteLookupTable to implement the rescale.
     * The table may have either a SHORT or BYTE input.
     * @param nElems    Number of elements the table is to have.
     *                  This will generally be 256 for byte and
     *                  65536 for short.
     */
    private ByteLookupTable createByteLut(float scale[],
                                          float off[],
                                          int   nBands,
                                          int   nElems) {

        byte[][]        lutData = new byte[scale.length][nElems];

        for (int band=0; band<scale.length; band++) {
            float  bandScale   = scale[band];
            float  bandOff     = off[band];
            byte[] bandLutData = lutData[band];
            for (int i=0; i<nElems; i++) {
                int val = (int)(i*bandScale + bandOff);
                if ((val & 0xffffff00) != 0) {
                    if (val < 0) {
                        val = 0;
                    } else {
                        val = 255;
                    }
                }
                bandLutData[i] = (byte)val;
            }

        }

        return new ByteLookupTable(0, lutData);
    }

    /**
     * Creates a ShortLookupTable to implement the rescale.
     * The table may have either a SHORT or BYTE input.
     * @param nElems    Number of elements the table is to have.
     *                  This will generally be 256 for byte and
     *                  65536 for short.
     */
    private ShortLookupTable createShortLut(float scale[],
                                            float off[],
                                            int   nBands,
                                            int   nElems) {

        short[][]        lutData = new short[scale.length][nElems];

        for (int band=0; band<scale.length; band++) {
            float   bandScale   = scale[band];
            float   bandOff     = off[band];
            short[] bandLutData = lutData[band];
            for (int i=0; i<nElems; i++) {
                int val = (int)(i*bandScale + bandOff);
                if ((val & 0xffff0000) != 0) {
                    if (val < 0) {
                        val = 0;
                    } else {
                        val = 65535;
                    }
                }
                bandLutData[i] = (short)val;
            }
        }

        return new ShortLookupTable(0, lutData);
    }


    /**
     * Determines if the rescale can be performed as a lookup.
     * The dst must be a byte or short type.
     * The src must be less than 16 bits.
     * All source band sizes must be the same and all dst band sizes
     * must be the same.
     */
    private boolean canUseLookup(Raster src, Raster dst) {
        //
        // Check that the src datatype is either a BYTE or SHORT
        //
        int datatype = src.getDataBuffer().getDataType();
        if(datatype != DataBuffer.TYPE_BYTE &&
                datatype != DataBuffer.TYPE_USHORT) {
            return false;
        }

        //
        // Check dst sample sizes. All must be 8 or 16 bits.
        //
        SampleModel dstSM = dst.getSampleModel();
        dstNbits = dstSM.getSampleSize(0);

        if (!(dstNbits == 8 || dstNbits == 16)) {
            return false;
        }
        for (int i=1; i<src.getNumBands(); i++) {
            int bandSize = dstSM.getSampleSize(i);
            if (bandSize != dstNbits) {
                return false;
            }
        }

        //
        // Check src sample sizes. All must be the same size
        //
        SampleModel srcSM = src.getSampleModel();
        srcNbits = srcSM.getSampleSize(0);
        if (srcNbits > 16) {
            return false;
        }
        for (int i=1; i<src.getNumBands(); i++) {
            int bandSize = srcSM.getSampleSize(i);
            if (bandSize != srcNbits) {
                return false;
            }
        }

        return true;
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
     *         <code>ExtJSRescaleOp</code> do not meet the requirements
     *         stated in the class comments.
     */
    public final BufferedImage filter (BufferedImage src, BufferedImage dst) {
        ColorModel srcCM = src.getColorModel();
        ColorModel dstCM;
        int numBands = srcCM.getNumColorComponents();


        if (srcCM instanceof IndexColorModel) {
            IndexColorModel icm = (IndexColorModel) src.getColorModel();
            return new BufferedImage(rescale(icm, scaleFactors, offsets), src
                    .getRaster(), false, null);
/*            throw new
                IllegalArgumentException("Rescaling cannot be "+
                                         "performed on an indexed image");*/
        }
        if (srcCM instanceof ComponentColorModel){
            ComponentColorModel icm = (ComponentColorModel) src.getColorModel();
            return new BufferedImage(icm, rescaleCCM( scaleFactors, offsets, src), false, null);
        }

        if (length != 1 && length != numBands &&
                length != srcCM.getNumComponents())
        {
            throw new IllegalArgumentException("Number of scaling constants "+
                    "does not equal the number of"+
                    " of color or color/alpha "+
                    " components");
        }

        boolean needToConvert = false;

        // Include alpha
        if (length > numBands && srcCM.hasAlpha()) {
            length = numBands+1;
        }

        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
            dstCM = srcCM;
        }
        else {
            if (width != dst.getWidth()) {
                throw new
                        IllegalArgumentException("Src width ("+width+
                        ") not equal to dst width ("+
                        dst.getWidth()+")");
            }
            if (height != dst.getHeight()) {
                throw new
                        IllegalArgumentException("Src height ("+height+
                        ") not equal to dst height ("+
                        dst.getHeight()+")");
            }

            dstCM = dst.getColorModel();
            if(srcCM.getColorSpace().getType() !=
                    dstCM.getColorSpace().getType()) {
                needToConvert = true;
                dst = createCompatibleDestImage(src, null);
            }

        }

        BufferedImage origDst = dst;

        //
        // Try to use a native BI rescale operation first
        //
        if (ImagingLib.filter(this, src, dst) == null) {
            //
            // Native BI rescale failed - convert to rasters
            //
            WritableRaster srcRaster = src.getRaster();
            WritableRaster dstRaster = dst.getRaster();

            if (srcCM.hasAlpha()) {
                if (numBands-1 == length || length == 1) {
                    int minx = srcRaster.getMinX();
                    int miny = srcRaster.getMinY();
                    int[] bands = new int[numBands-1];
                    for (int i=0; i < numBands-1; i++) {
                        bands[i] = i;
                    }
                    srcRaster =
                            srcRaster.createWritableChild(minx, miny,
                                    srcRaster.getWidth(),
                                    srcRaster.getHeight(),
                                    minx, miny,
                                    bands);
                }
            }
            if (dstCM.hasAlpha()) {
                int dstNumBands = dstRaster.getNumBands();
                if (dstNumBands-1 == length || length == 1) {
                    int minx = dstRaster.getMinX();
                    int miny = dstRaster.getMinY();
                    int[] bands = new int[numBands-1];
                    for (int i=0; i < numBands-1; i++) {
                        bands[i] = i;
                    }
                    dstRaster =
                            dstRaster.createWritableChild(minx, miny,
                                    dstRaster.getWidth(),
                                    dstRaster.getHeight(),
                                    minx, miny,
                                    bands);
                }
            }

            //
            // Call the raster filter method
            //
            filter(srcRaster, dstRaster);

        }

        if (needToConvert) {
            // ColorModels are not the same
            ColorConvertOp ccop = new ColorConvertOp(hints);
            ccop.filter(dst, origDst);
        }

        return origDst;
    }

    /**
     * Rescales the pixel data in the source Raster.
     * If the destination Raster is null, a new Raster will be created.
     * The source and destination must have the same number of bands.
     * Otherwise, an IllegalArgumentException is thrown.
     * Note that the number of scaling factors/offsets in this object must
     * meet the restrictions stated in the class comments above.
     * Otherwise, an IllegalArgumentException is thrown.
     * @param src the <code>Raster</code> to be filtered
     * @param dst the destination for the filtering operation
     *            or <code>null</code>
     * @return the filtered <code>WritableRaster</code>.
     * @throws IllegalArgumentException if <code>src</code> and
     *         <code>dst</code> do not have the same number of bands,
     *         or if the number of scaling factors and offsets in this
     *         <code>ExtJSRescaleOp</code> do not meet the requirements
     *         stated in the class comments.
     */
    public final WritableRaster filter (Raster src, WritableRaster dst)  {
        int numBands = src.getNumBands();
        int width  = src.getWidth();
        int height = src.getHeight();
        int[] srcPix = null;
        int step = 0;
        int tidx = 0;

        // Create a new destination Raster, if needed
        if (dst == null) {
            dst = createCompatibleDestRaster(src);
        }
        else if (height != dst.getHeight() || width != dst.getWidth()) {
            throw new
                    IllegalArgumentException("Width or height of Rasters do not "+
                    "match");
        }
        else if (numBands != dst.getNumBands()) {
            // Make sure that the number of bands are equal
            throw new IllegalArgumentException("Number of bands in src "
                    + numBands
                    + " does not equal number of bands in dest "
                    + dst.getNumBands());
        }
        // Make sure that the arrays match
        // Make sure that the low/high/constant arrays match
        if (length != 1 && length != src.getNumBands()) {
            throw new IllegalArgumentException("Number of scaling constants "+
                    "does not equal the number of"+
                    " of bands in the src raster");
        }


        //
        // Try for a native raster rescale first
        //
        if (ImagingLib.filter(this, src, dst) != null) {
            return dst;
        }

        //
        // Native raster rescale failed.
        // Try to see if a lookup operation can be used
        //
        if (canUseLookup(src, dst)) {
            int srcNgray = (1 << srcNbits);
            int dstNgray = (1 << dstNbits);

            if (dstNgray == 256) {
                ByteLookupTable lut = createByteLut(scaleFactors, offsets,
                        numBands, srcNgray);
                LookupOp op = new LookupOp(lut, hints);
                op.filter(src, dst);
            } else {
                ShortLookupTable lut = createShortLut(scaleFactors, offsets,
                        numBands, srcNgray);
                LookupOp op = new LookupOp(lut, hints);
                op.filter(src, dst);
            }
        } else {
            //
            // Fall back to the slow code
            //
            if (length > 1) {
                step = 1;
            }

            int sminX = src.getMinX();
            int sY = src.getMinY();
            int dminX = dst.getMinX();
            int dY = dst.getMinY();
            int sX;
            int dX;

            //
            //  Determine bits per band to determine maxval for clamps.
            //  The min is assumed to be zero.
            //  REMIND: This must change if we ever support signed data types.
            //
            int nbits;
            int dstMax[] = new int[numBands];
            int dstMask[] = new int[numBands];
            SampleModel dstSM = dst.getSampleModel();
            for (int z=0; z<numBands; z++) {
                nbits = dstSM.getSampleSize(z);
                dstMax[z] = (1 << nbits) - 1;
                dstMask[z] = ~(dstMax[z]);
            }

            int val;
            for (int y=0; y < height; y++, sY++, dY++) {
                dX = dminX;
                sX = sminX;
                for (int x = 0; x < width; x++, sX++, dX++) {
                    // Get data for all bands at this x,y position
                    srcPix = src.getPixel(sX, sY, srcPix);
                    tidx = 0;
                    for (int z=0; z<numBands; z++, tidx += step) {
                        val = (int)(srcPix[z]*scaleFactors[tidx]
                                + offsets[tidx]);
                        // Clamp
                        if ((val & dstMask[z]) != 0) {
                            if (val < 0) {
                                val = 0;
                            } else {
                                val = dstMax[z];
                            }
                        }
                        srcPix[z] = val;

                    }

                    // Put it back for all bands
                    dst.setPixel(dX, dY, srcPix);
                }
            }
        }
        return dst;
    }

    /**
     * Returns the bounding box of the rescaled destination image.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     */
    public final Rectangle2D getBounds2D (BufferedImage src) {
        return getBounds2D(src.getRaster());
    }

    /**
     * Returns the bounding box of the rescaled destination Raster.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     * @param src the rescaled destination <code>Raster</code>
     * @return the bounds of the specified <code>Raster</code>.
     */
    public final Rectangle2D getBounds2D (Raster src) {
        return src.getBounds();
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

    /**
     * Creates a zeroed-destination <code>Raster</code> with the correct
     * size and number of bands, given this source.
     * @param src       the source <code>Raster</code>
     * @return the zeroed-destination <code>Raster</code>.
     */
    public WritableRaster createCompatibleDestRaster (Raster src) {
        return src.createCompatibleWritableRaster(src.getWidth(), src.getHeight());
    }

    /**
     * Returns the location of the destination point given a
     * point in the source.  If dstPt is non-null, it will
     * be used to hold the return value.  Since this is not a geometric
     * operation, the srcPt will equal the dstPt.
     * @param srcPt a point in the source image
     * @param dstPt the destination point or <code>null</code>
     * @return the location of the destination point.
     */
    public final Point2D getPoint2D (Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Float();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());
        return dstPt;
    }

    /**
     * Returns the rendering hints for this op.
     * @return the rendering hints of this <code>ExtJSRescaleOp</code>.
     */
    public final RenderingHints getRenderingHints() {
        return hints;
    }

    public void rescale(byte[] compsR, byte[] compsG, byte[] compsB, byte[] compsA
            , float[] scaleFactor
            , float[] offset
            , int[] borderColor, float[] borderOffset) {
        float localOffsetR;
        float localOffsetG;
        float localOffsetB;
        for (int i = 0; i < compsR.length; ++i) {
            localOffsetR = offset[0];
            localOffsetG = offset[1];
            localOffsetB = offset[2];
            int compR = 0xff & compsR[i];
            int compG = 0xff & compsG[i];
            int compB = 0xff & compsB[i];
            int comp = ((compR<<16)&0xff0000)|((compG<<8)&0xff00)|(compB&0xff);

            boolean isNeedChangeAlpha=true;// system should not change alpha for borders
            if (this.isBorderColor
                    && (!(borderOffset[0]==0&&borderOffset[1]==0&&borderOffset[2]==0))
                    && Arrays.binarySearch(borderColor, comp)>=0){
                if (!this.isTransparencyProcessing){
                    localOffsetR=borderOffset[0];
                    localOffsetG=borderOffset[1];
                    localOffsetB=borderOffset[2];
                }else {
                    isNeedChangeAlpha = false;// system should not change alpha for borders
                    localOffsetR=0;
                    localOffsetG=0;
                    localOffsetB=0;
                }
            }

            int newCompR = Math.round(compR * scaleFactor[0] + localOffsetR);
            if (newCompR < 0)
                newCompR = 0;
            else if (newCompR > 255)
                newCompR = 255;
            compsR[i] = (byte) newCompR;

            int newCompG = Math.round(compG * scaleFactor[1] + localOffsetG);
            if (newCompG < 0)
                newCompG = 0;
            else if (newCompG > 255)
                newCompG = 255;
            compsG[i] = (byte) newCompG;

            int newCompB = Math.round(compB * scaleFactor[2] + localOffsetB);
            if (newCompB < 0)
                newCompB = 0;
            else if (newCompB > 255)
                newCompB = 255;
            compsB[i] = (byte) newCompB;

            if (isNeedChangeAlpha&&null!=compsA){
                int newCompA = Math.round((0xff & compsA[i]) * scaleFactor[3] + offset[3]);
                if (newCompA < 0)
                    newCompA = 0;
                else if (newCompA > 255)
                    newCompA = 255;
                compsA[i] = (byte) newCompA;
            }
        }
    }

    public IndexColorModel rescale(IndexColorModel icm,
                                   float[] scaleFactor, float[] offset) {
        int size = icm.getMapSize();
        byte[] reds = new byte[size], greens = new byte[size], blues = new byte[size], alphas = new byte[size];
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);
        icm.getAlphas(alphas);
/*
        boolean valid = this.isBorderColor
                && null != this.borderOffsets
                && this.borderOffsets[0] != 0 && this.borderOffsets[1] != 0 && this.borderOffsets[2] != 0;

        int[] rBorderColor=new int[this.borderColor.length];
        int[] gBorderColor=new int[this.borderColor.length];
        int[] bBorderColor=new int[this.borderColor.length];
        for (int i = 0; i < this.borderColor.length; i++) {
            int color = this.borderColor[i];

            rBorderColor[i] = valid?(color>>16)&0xff:-1;
            gBorderColor[i] = valid?(color>>8)&0xff:-1;
            bBorderColor[i] = valid?color&0xff:-1;
        }


        float rBorderOffset= valid ?this.borderOffsets[0]:0;
        float gBorderOffset= valid?this.borderOffsets[1]:0;
        float bBorderOffset= valid?this.borderOffsets[2]:0;*/

        rescale(reds, greens, blues, alphas
                , scaleFactor
                , offset
                , this.borderColor
                , this.borderOffsets);
        //rescale(greens, scaleFactor[1], offset[1], gBorderColor, gBorderOffset);
        //rescale(blues, scaleFactor[2], offset[2], bBorderColor, bBorderOffset);
        //rescale(alphas, scaleFactor[3], offset[3],-1,0);

        return new IndexColorModel(8, size, reds, greens, blues, alphas);
    }
    public WritableRaster rescaleCCM(float[] scaleFactor, float[] offset, BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int size = width * height;
        byte r[] = new byte[size];
        byte g[] = new byte[size];
        byte b[] = new byte[size];
        byte a[] = new byte[size];
        Raster raster = src.getData();
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

        rescale(r, g, b, a
                , scaleFactor
                , offset
                , this.borderColor
                , this.borderOffsets);

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        int[] bits = {8, 8, 8, 8};
        ColorModel cm = new ComponentColorModel(cs, bits, true, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        WritableRaster wr = cm.createCompatibleWritableRaster(src.getWidth(),
                src.getHeight());

        DataBufferByte dbf = (DataBufferByte) wr.getDataBuffer();

        byte[] data = dbf.getData();
        /*int n = ((int[]) src.getPixels()).length;*/
        for (int i = 0; i < size; ++i) {
            int pix = i * 4;
            data[pix] = r[i];
            data[pix + 1] = g[i];
            data[pix + 2] = b[i];
            data[pix + 3] = a[i];
        }

        return wr;
    }

    public BufferedImage filter(BufferedImage image, BufferedImage dst,
                                float[] borderOffsets, int[] borderColor,
                                boolean isBorderColor, boolean isTransparencyProcessing) {
        this.borderOffsets=borderOffsets;
        this.borderColor=borderColor;
        this.isBorderColor=isBorderColor;
        this.isTransparencyProcessing=isTransparencyProcessing;
        BufferedImage result = this.filter(image, dst);
        this.borderOffsets=null;
        this.borderColor=new int[]{-1};
        this.isBorderColor=false;
        this.isTransparencyProcessing=false;
        return result;
    }
}

