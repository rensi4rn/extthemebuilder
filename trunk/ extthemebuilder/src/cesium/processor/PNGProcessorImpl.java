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

import cesium.factory.ResourcesProcessorFactoryImpl;
import cesium.holder.AbstractResourcesHolder;
import cesium.holder.PNGHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.holder.ThemeParametersHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import com.sun.imageio.plugins.png.PNGImageReader;
import com.sun.imageio.plugins.png.PNGImageWriter;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Node;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PNGProcessorImpl extends AbstractImageProcessor {
    public PNGProcessorImpl(ApplicationContext context,
                            ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public ResourcesHolder process(ResourcesHolder resHolder,
                                   ThemeParametersHolder themeParametersHolder, ExtJSRescaleOp brightenOp, ForegroundShiftOp foregroundOp,
                                   ExtJSRescaleOp liteOp, ExtJSRescaleOp bgOp,
                                   ExtJSRescaleOp fontOp,
                                   ExtJSRescaleOp transparencyOp, ExtJSRescaleOp borderOp,
                                   AffineTransformOp affineTransformOp, ExtJSRescaleOp headerFontOp,
                                   BufferedImageOp shadowTransparencyOp, ExtJSRescaleOp headerOp,
                                   ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont,
                                   String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        //png processing exclusion
        String resourcesPath = resHolder.getResourcesPath();

        boolean processingNotNeeded = resHolder.isProcessingNotNeeded();
        boolean isDrawable = resHolder.isDrawable();
        boolean isDrawableColorIndependent = resHolder.isDrawableColorIndependent();
        if (null!=resourcesPath
                && processingNotNeeded
                && !(isDrawable||isDrawableColorIndependent)
                )
            return resHolder;
        //end png processing exclusion

        ResourcesHolder newResourcesHolder = super.process(resHolder,
                themeParametersHolder, brightenOp, foregroundOp, liteOp, bgOp, fontOp, transparencyOp,
                borderOp, (AffineTransformOp) affineTransformOp, headerFontOp, shadowTransparencyOp,
                headerOp, toolsetSchemaHolder, toolsetName,
                familyHeaderFont, weightHeaderFont, sizeHeaderFont,
                familyFont, weightFont, sizeFont, drawableSchemaHolder);

        PNGHolderImpl pngHolder = (PNGHolderImpl) resHolder;
        String path = pngHolder.getPath();
        ((PNGHolderImpl)newResourcesHolder).setPath(path);

        boolean isExternalToolset = pngHolder.isToolset()
                && null != toolsetName && !"".equals(toolsetName) && !"default".equals(toolsetName);

        ResourcesHolder resourceByPath=null;
        byte[] data = null;
        if (isExternalToolset){
            String fileName = path.substring(path.lastIndexOf("/"));
            resourceByPath = toolsetSchemaHolder.findResourceByFileEnds("/"+toolsetName+ fileName);
            if (null!=resourceByPath)
                data = (byte[]) resourceByPath.getContent();
            else
                data = (byte[]) resHolder.getContent();
        }else{
            data = (byte[]) resHolder.getContent();
        }

        byte[] newData = null;
/*        ByteArrayInputStream is = new ByteArrayInputStream(data);
        ByteArrayOutputStream os = new ByteArrayOutputStream();*/

        BufferedImage image = null;
        try {
            ImageIO.setUseCache(false);

            byte[] drawableData=null;
            ByteArrayInputStream dis = null;
            BufferedImage drawableImage = null;
            IIOMetadata diioMetadata =null;
            boolean closeToBlack = foregroundOp.isCloseToBlack(); // Drawing only for dark base color themes
            if (isDrawable && closeToBlack || isDrawableColorIndependent){
                String drawableFileName = path.substring(path.lastIndexOf("/"));
                ResourcesHolder drawableResource = drawableSchemaHolder.findResourceByFileEnds(drawableFileName);
                if (null!=drawableResource){
/*
                    drawableData = (byte[]) drawableResource.getContent();
                    dis = new ByteArrayInputStream(drawableData);
                    drawableImage = ImageIO.read(dis);
*/
                    drawableData = (byte[]) drawableResource.getContent();
                    dis = new ByteArrayInputStream(drawableData);
                    PNGImageReader drawableImageReader = (PNGImageReader) ImageIO.getImageReadersBySuffix("png").next();
                    drawableImageReader.setInput(ImageIO.createImageInputStream(dis));
                    drawableImage = drawableImageReader.read(0);
                    diioMetadata = drawableImageReader.getImageMetadata(0);

                }else{
                    drawableData = (byte[]) resHolder.getContent();
                    throw new Exception("Drawable resource WEB_INF/resources_drawable/"
                            +drawableFileName+" mapped but not found!");
                }
            }


            ((PNGHolderImpl)newResourcesHolder).setPath(path);

            /*image = ImageIO.read(is);*/
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream(os);

            PNGImageReader imageReader = (PNGImageReader) ImageIO.getImageReadersBySuffix("png").next();
            imageReader.setInput(ImageIO.createImageInputStream(is));
            PNGImageWriter imageWriter = (PNGImageWriter) ImageIO.getImageWritersByFormatName("png").next();
            imageWriter.setOutput(mos);
            image = imageReader.read(0);
            IIOMetadata iioMetadata = imageReader.getImageMetadata(0);
            
            IIOMetadata metadata=null;
            ImageWriteParam writeParam=null;
            writeParam = imageWriter.getDefaultWriteParam();

            //image processsing
            if (null==resourceByPath||!isExternalToolset){
                if (processingNotNeeded){
                   //do nothing
                } else if (pngHolder.isWindow()&&null!=transparencyOp){
                    image = transparencyOp.filter(image, image, borderOp.getOffsets(null)
                            , ((AbstractResourcesHolder)pngHolder).getBorderColor()
                            , pngHolder.isBorderColor(), pngHolder.isWindow());
                }/* else if (pngHolder.isShadow()){
                image = shadowTransparencyOp.filter(image, image);
            }*/
                if (pngHolder.isHeader()){
                    image = headerOp.filter(image, image
                            , borderOp.getOffsets(null)
                            , ((AbstractResourcesHolder)pngHolder).getBorderColor()
                            , pngHolder.isBorderColor(), false);
                } else if (!pngHolder.isForeground()){
/*                ColorModel colorModel = image.getColorModel();
                if (colorModel instanceof ComponentColorModel)
                    image = convertType(image, BufferedImage.TYPE_BYTE_INDEXED);*/
                    image = brightenOp.filter(image, image, borderOp.getOffsets(null)
                            , ((AbstractResourcesHolder)pngHolder).getBorderColor()
                            , pngHolder.isBorderColor(), false);
                }else {
                    image = liteOp.filter(image, image, borderOp.getOffsets(null)
                            , ((AbstractResourcesHolder)pngHolder).getBorderColor()
                            , pngHolder.isBorderColor(), false);
                }
                // Draw picture to image!!!!
                if (isDrawable&&closeToBlack || isDrawableColorIndependent){
                    image = foregroundOp.draw(image, drawableImage, (byte)16);
                }
                
            }
/*            if (pngHolder.isBorderColor()){
    ColorModel colorModel = getColorModel(image);
    //image = colorModel.convertToIntDiscrete(image.getRaster(),true);
    //System.out.println(colorModel+"   " + pngHolder.getPath());
    if (colorModel instanceof IndexColorModel){
        IndexColorModel indexColorModel = (IndexColorModel) colorModel;
        IndexColorModel newIndexColorModel=borderFilter.filterIndexColorModel(indexColorModel);
        image= new BufferedImage(image.getWidth(), image.getHeight(),image.getType(), newIndexColorModel);
    }
}*/
            //image.setData(raster);
            //end image processing

            /*ImageIO.write(image, "PNG", os);*/
            ImageTypeSpecifier imageTypeSpecifier = new
                    ImageTypeSpecifier(image);
            metadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier,writeParam);

            //configure(metadata, "10",0);
            imageWriter.write(new IIOImage(image, null, metadata));

            //imageWriter.write(image);
            //start writing
            mos.flush();
            imageWriter.dispose();
            mos.close();
            
            newData = os.toByteArray();
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        if (null!=image){
            newResourcesHolder.setContent(newData);
        }
        return newResourcesHolder;
    }

    private void configure(IIOMetadata metadata, String delayTime, int imageIndex) {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        if (!("javax_imageio_png_1.0").equals(metaFormatName)) {
            throw new IllegalArgumentException(
                    "Unfamiliar png metadata format: " + metaFormatName);
        }
        IIOMetadataNode root =(IIOMetadataNode)
                metadata.getAsTree(metaFormatName);
        //find the GraphicControlExtension node
        Node child = root.getFirstChild();
        while (child != null) {
            if ("GraphicControlExtension".equals(child.getNodeName())) {
                break;
            }
            child = child.getNextSibling();
        }
        if (null!=child){
            IIOMetadataNode gce = (IIOMetadataNode) child;
            gce.setAttribute("userInputFlag", "FALSE");
            gce.setAttribute("delayTime", delayTime);
        }
        //only the first node needs the ApplicationExtensions node
        if (imageIndex == 0) {
            IIOMetadataNode aes =
                    new IIOMetadataNode("ApplicationExtensions");
            IIOMetadataNode ae =
                    new IIOMetadataNode("ApplicationExtension");
            ae.setAttribute("applicationID", "CHENTSOV");
            ae.setAttribute("authenticationCode", "1.0");
            byte[] uo = new byte[]{
                    //last two bytes is an unsigned short (little endian) that
                    //indicates the the number of times to loop.
                    //0 means loop forever.
                    0x1, 0x0, 0x0
            };
            ae.setUserObject(uo);
            aes.appendChild(ae);
            root.appendChild(aes);
        }

        try {
            metadata.setFromTree(metaFormatName, root);
        } catch (IIOInvalidTreeException e) {
            //shouldn't happen
            throw new Error(e);
        }

    }
}
