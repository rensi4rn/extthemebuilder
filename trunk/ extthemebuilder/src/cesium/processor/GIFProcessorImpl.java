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
import cesium.holder.GIFHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.holder.ThemeParametersHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageWriter;
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

public class GIFProcessorImpl extends AbstractImageProcessor {
    public GIFProcessorImpl(ApplicationContext context,
                            ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public ResourcesHolder process(ResourcesHolder resHolder,
                                   ThemeParametersHolder themeParametersHolder, ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp,
                                   ExtJSRescaleOp liteOp,
                                   ExtJSRescaleOp bgOp, ExtJSRescaleOp fontOp,
                                   ExtJSRescaleOp transparencyOp, ExtJSRescaleOp borderOp,
                                   AffineTransformOp affineTransformOp, ExtJSRescaleOp headerFontOp,
                                   BufferedImageOp shadowTransparencyOp, ExtJSRescaleOp headerOp,
                                   ResourcesHolder toolsetSchemaHolder, String toolsetName, String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont, String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        // exclusion for processing
        String resourcesPath = resHolder.getResourcesPath();
        boolean isDrawable = resHolder.isDrawable();
        boolean isDrawableColorIndependent = resHolder.isDrawableColorIndependent();
        boolean processingNotNeeded = resHolder.isProcessingNotNeeded();

        if (null!=resourcesPath
                && processingNotNeeded
                && !(isDrawable||isDrawableColorIndependent)
                )
            return resHolder;
        //end processing exclusion

        ResourcesHolder newResourcesHolder = super.process(resHolder,
                themeParametersHolder, brightenOp, foregroundOp, liteOp, bgOp, fontOp, transparencyOp, borderOp
                , (AffineTransformOp) affineTransformOp, headerFontOp, shadowTransparencyOp
                , headerOp, toolsetSchemaHolder, toolsetName, familyHeaderFont, weightHeaderFont, sizeHeaderFont
                , familyFont, weightFont, sizeFont, drawableSchemaHolder);

        byte[] data;

        GIFHolderImpl gifOldHolder = (GIFHolderImpl) resHolder;

        String path = gifOldHolder.getPath();
        boolean isExternalToolset = gifOldHolder.isToolset()
                && null != toolsetName && !"".equals(toolsetName) && !"default".equals(toolsetName);
        ResourcesHolder resourceByPath=null;
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
        BufferedImage image = null;

        try {
            ImageIO.setUseCache(false);

            byte[] drawableData=null;
            ByteArrayInputStream dis = null;
            BufferedImage drawableImage = null;

            boolean closeToBlack = foregroundOp.isCloseToBlack(); // Drawing only for dark base color themes
            if (isDrawable && closeToBlack || isDrawableColorIndependent){
                String drawableFileName = path.substring(path.lastIndexOf("/"));
                ResourcesHolder drawableResource = drawableSchemaHolder.findResourceByFileEnds(drawableFileName);
                if (null!=drawableResource){
                    drawableData = (byte[]) drawableResource.getContent();
                    dis = new ByteArrayInputStream(drawableData);
                    GIFImageReader drawableImageReader = (GIFImageReader) ImageIO.getImageReadersBySuffix("gif").next();
                    drawableImageReader.setInput(ImageIO.createImageInputStream(dis));
                    drawableImage = drawableImageReader.read(0);

                }else{
                    drawableData = (byte[]) resHolder.getContent();
                    throw new Exception("Drawable resource WEB_INF/resources_drawable/"
                            +drawableFileName+" mapped but not found!");
                }
            }


            ((GIFHolderImpl)newResourcesHolder).setPath(path);
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream(os);

            //start reading
            //image = ImageIO.read(is);
            //end reading
            GIFImageReader imageReader = (GIFImageReader) ImageIO.getImageReadersBySuffix("gif").next();
            imageReader.setInput(ImageIO.createImageInputStream(is));
            GIFImageWriter imageWriter = (GIFImageWriter) ImageIO.getImageWritersByFormatName("gif").next();
            imageWriter.setOutput(mos);

            int numImages = imageReader.getNumImages(true);

            IIOMetadata metadata=null;
            ImageWriteParam writeParam=null;
            if (numImages>1){
                writeParam = imageWriter.getDefaultWriteParam();
                IIOMetadata streamMetadata = imageReader.getStreamMetadata();
                imageWriter.prepareWriteSequence(streamMetadata);
            }

            for (int i=0;i<numImages; i++){
                image = imageReader.read(i);
                //start processing

                if (null==resourceByPath||!isExternalToolset){
                    if (processingNotNeeded){
                        // do nothing for drawing picture
                    } else if (gifOldHolder.isBackground()&&null!=bgOp){
                        image = bgOp.filter(image, image, borderOp.getOffsets(null)
                                , ((AbstractResourcesHolder)gifOldHolder).getBorderColor()
                                , gifOldHolder.isBorderColor(), false);
                    } else if (gifOldHolder.isHeader()){
                        image = headerOp.filter(image, image
                                , borderOp.getOffsets(null)
                                , ((AbstractResourcesHolder)gifOldHolder).getBorderColor()
                                , gifOldHolder.isBorderColor(), false);
                    }else if (gifOldHolder.isWhitable()){
                        image = foregroundOp.filterToWhite(image,image);
                    } else if (!gifOldHolder.isForeground()){
                        image = brightenOp.filter(image, image
                                , borderOp.getOffsets(null)
                                , ((AbstractResourcesHolder)gifOldHolder).getBorderColor()
                                , gifOldHolder.isBorderColor(), false);
                    }else{
                        image = liteOp.filter(image, image, borderOp.getOffsets(null)
                                , ((AbstractResourcesHolder)gifOldHolder).getBorderColor()
                                , gifOldHolder.isBorderColor(), false);
                    }
                    
                    // Draw picture to image!!!!
                    if (isDrawable&&closeToBlack || isDrawableColorIndependent){
                        image = foregroundOp.draw(image, drawableImage, (byte)16);
                    }

                }
                //affine transform
/*                if (gifOldHolder.isBorderColor()){
                    BufferedImage postTransImg = null;

                    postTransImg = affineTransformOp.filter(image, null);
                    image=postTransImg;
                }*/
                //end affine transform
                if (numImages>1){
                    ImageTypeSpecifier imageTypeSpecifier = new
                            ImageTypeSpecifier(image);
                    metadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier,writeParam);

                    configure(metadata, "10",i);
                    imageWriter.writeToSequence(new IIOImage(image, null, metadata),null);
                }else{
                    //imageWriter.write(new IIOImage(image, null, metadata));
                    imageWriter.write(image);
                }
            }
            if (numImages>1) imageWriter.endWriteSequence();

            //finish processing

            //start writing
            mos.flush();
            imageWriter.dispose();
            mos.close();

            //ImageIO.write(image, "GIF", os);
            newData = os.toByteArray();
            os.flush();
            os.close();

            //end writing
        } catch (IOException e) {
            System.out.println("Before IOException Path = "+path);
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("Before Exception Path = "+path);
            e.printStackTrace();
        } /*catch (Error e){
            e.printStackTrace();
        }*/

        if (null!=image){
            newResourcesHolder.setContent(newData);
        }
        return newResourcesHolder;
    }

    private void configure(IIOMetadata metadata, String delayTime, int imageIndex) {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        if (!("javax_imageio_gif_image_1.0").equals(metaFormatName)) {
            throw new IllegalArgumentException(
                    "Unfamiliar gif metadata format: " + metaFormatName);
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

            IIOMetadataNode ces = new IIOMetadataNode("CommentExtensions");
            IIOMetadataNode ce = new IIOMetadataNode("CommentExtension");
            ce.setAttribute("value", "GENERATED BY THEME BUILDER FOR EXTJS.AUTHOR:SERGEI CHENTSOV.");
            ces.appendChild(ce);
            root.appendChild(ces);
        }

        try {
            metadata.setFromTree(metaFormatName, root);
        } catch (IIOInvalidTreeException e) {
            //shouldn't happen
            throw new Error(e);
        }

    }
}
