/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package springapp.web.controller.theme;

import cesium.factory.ResourcesProcessorFactory;
import cesium.holder.ResourcesHolder;
import cesium.holder.ThemeParametersHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import cesium.pool.ResourceHolderPool;
import cesium.processor.ResourcesProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springapp.constants.ApplicationConstants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.HashMap;

public class ProcessThemeController implements Controller {
    protected final Log logger = LogFactory.getLog(getClass());

    private ResourceHolderPool holder23Pool;
    private ResourceHolderPool holderPool;
    private ResourceHolderPool holder31Pool;
    private ResourceHolderPool holder32Pool;
    private ResourceHolderPool holder33Pool;
    private ResourceHolderPool holderGray23Pool;
    private ResourceHolderPool holderGrayPool;
    private ResourceHolderPool holderGray31Pool;
    private ResourceHolderPool holderGray32Pool;
    private ResourceHolderPool holderGray33Pool;

    private ResourceHolderPool holderToolsetPool;
    private ResourceHolderPool holderDrawablePool;
    ResourcesProcessorFactory resourcesProcessorFactory ;

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        HttpSession session = httpServletRequest.getSession();

        String templateIdString = httpServletRequest.getParameter(ApplicationConstants.TEMPLATE_ID);
        byte templateId = 0;

        int oldColor=0;
        int newColor=0;
        int oldHeaderColor=0;
        int newHeaderColor=0;
        int oldBgColor=0;
        int newBgColor=0;
        int oldFontColor=0;
        int newFontColor=0;
        int oldHeaderFontColor=0;
        int newHeaderFontColor=0;
        int oldBorderColor=0;
        int newBorderColor=0;
        int oldTransp=0;
        int newTransp=0;
        byte sizeHeaderFontDiff =11;
        byte sizeFontDiff =11;

        ThemeParametersHolder themeParametersHolder = null;
        String toolsetName = httpServletRequest.getParameter(ApplicationConstants.TOOLSET_NAME);

        String versionString = httpServletRequest.getParameter(ApplicationConstants.VERSION);
        String version = (null==versionString)?ApplicationConstants.DEFAULT_EXTJS_VERSION:versionString;
        String familyHeaderFont = httpServletRequest.getParameter(ApplicationConstants.FAMILY_HEADER_FONT);
        String familyFont = httpServletRequest.getParameter(ApplicationConstants.FAMILY_FONT);
        String weightHeaderFont = httpServletRequest.getParameter(ApplicationConstants.WEIGHT_HEADER_FONT);
        String weightFont = httpServletRequest.getParameter(ApplicationConstants.WEIGHT_FONT);
        try{
            templateId = null!=templateIdString?Byte.parseByte(templateIdString):0;

            String oldColorString = (0==templateId)?"#DFE8F6":"#F1F1F1";
            String newColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_COLOR);

            String oldHeaderColorString = (0==templateId)?"#CDDEF3":"#D7D7D7";
            String newHeaderColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_HEADER_COLOR);

            String oldBgColorString = "#FFFFFF";
            String newBgColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_BG_COLOR);

            String oldFontColorString = "#000000";
            String newFontColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_FONT_COLOR);

            String oldHeaderFontColorString = (0==templateId)?"#15428B":"#222222";
            String newHeaderFontColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_HEADER_FONT_COLOR);


            String sizeHeaderFontString = httpServletRequest.getParameter(ApplicationConstants.SIZE_HEADER_FONT);


            String sizeFontString = httpServletRequest.getParameter(ApplicationConstants.SIZE_FONT);

            String oldBorderColorString = (0==templateId)?"#99BBE8":"#D0D0D0";
            String newBorderColorString = httpServletRequest.getParameter(ApplicationConstants.NEW_BORDER_COLOR);

            String oldTranspString = "255";
            String newTranspString = httpServletRequest.getParameter(ApplicationConstants.NEW_TRANSP);



            themeParametersHolder = new ThemeParametersHolder(
                    templateIdString ,
                    newColorString,
                    newHeaderColorString ,
                    newBgColorString ,
                    newFontColorString ,
                    newHeaderFontColorString ,
                    familyHeaderFont ,
                    weightHeaderFont,
                    sizeHeaderFontString ,
                    familyFont ,
                    weightFont ,
                    sizeFontString ,
                    newBorderColorString ,
                    newTranspString ,
                    toolsetName ,
                    version );


            oldColor = null!=oldColorString?Integer.parseInt(oldColorString.replaceFirst("#",""), 16):0;
            newColor = null!=newColorString?Integer.parseInt(newColorString.replaceFirst("#",""), 16):0;
            oldHeaderColor = null!=oldHeaderColorString?Integer.parseInt(oldHeaderColorString.replaceFirst("#",""), 16):0;
            newHeaderColor = null!=newHeaderColorString?Integer.parseInt(newHeaderColorString.replaceFirst("#",""), 16):0;
            oldBgColor = null!=oldBgColorString?Integer.parseInt(oldBgColorString.replaceFirst("#",""), 16):0;
            newBgColor = null!=newBgColorString?Integer.parseInt(newBgColorString.replaceFirst("#",""), 16):0;
            oldFontColor = null!=oldFontColorString?Integer.parseInt(oldFontColorString.replaceFirst("#",""), 16):0;
            newFontColor = null!=newFontColorString?Integer.parseInt(newFontColorString.replaceFirst("#",""), 16):0;
            oldHeaderFontColor = null!=oldHeaderFontColorString?Integer.parseInt(oldHeaderFontColorString.replaceFirst("#",""), 16):0;
            newHeaderFontColor = null!=newHeaderFontColorString?Integer.parseInt(newHeaderFontColorString.replaceFirst("#",""), 16):0;

            oldBorderColor = null!=oldBorderColorString?Integer.parseInt(oldBorderColorString.replaceFirst("#",""), 16):0;
            newBorderColor = null!=newBorderColorString?Integer.parseInt(newBorderColorString.replaceFirst("#",""), 16):0;

            oldTransp = null!=oldTranspString?Integer.parseInt(oldTranspString):0;
            newTransp = null!=newTranspString?Integer.parseInt(newTranspString):0;

            sizeHeaderFontDiff = (byte)((null!=sizeHeaderFontString?Byte.parseByte(sizeHeaderFontString):11)-11);
            sizeFontDiff = (byte)((null!=sizeFontString?Byte.parseByte(sizeFontString):11)-11);
        }catch (Exception e){
            logger.error(e);
        }

        int oldR=(oldColor>>16)&0xff;
        int oldG=(oldColor>>8)&0xff;
        int oldB=oldColor&0xff;

        int newR=(newColor>>16)&0xff;
        int newG=(newColor>>8)&0xff;
        int newB=newColor&0xff;

        int oldHeaderR=(oldHeaderColor>>16)&0xff;
        int oldHeaderG=(oldHeaderColor>>8)&0xff;
        int oldHeaderB=oldHeaderColor&0xff;

        int newHeaderR=(newHeaderColor>>16)&0xff;
        int newHeaderG=(newHeaderColor>>8)&0xff;
        int newHeaderB=newHeaderColor&0xff;

        int oldBgR=(oldBgColor>>16)&0xff;
        int oldBgG=(oldBgColor>>8)&0xff;
        int oldBgB=oldBgColor&0xff;

        int newBgR=(newBgColor>>16)&0xff;
        int newBgG=(newBgColor>>8)&0xff;
        int newBgB=newBgColor&0xff;

        int oldFontR=(oldFontColor>>16)&0xff;
        int oldFontG=(oldFontColor>>8)&0xff;
        int oldFontB=oldFontColor&0xff;

        int newFontR=(newFontColor>>16)&0xff;
        int newFontG=(newFontColor>>8)&0xff;
        int newFontB=newFontColor&0xff;

        int oldHeaderFontR=(oldHeaderFontColor>>16)&0xff;
        int oldHeaderFontG=(oldHeaderFontColor>>8)&0xff;
        int oldHeaderFontB=oldHeaderFontColor&0xff;

        int newHeaderFontR=(newHeaderFontColor>>16)&0xff;
        int newHeaderFontG=(newHeaderFontColor>>8)&0xff;
        int newHeaderFontB=newHeaderFontColor&0xff;


        int oldBorderR=(oldBorderColor>>16)&0xff;
        int oldBorderG=(oldBorderColor>>8)&0xff;
        int oldBorderB=oldBorderColor&0xff;

        int newBorderR=(newBorderColor>>16)&0xff;
        int newBorderG=(newBorderColor>>8)&0xff;
        int newBorderB=newBorderColor&0xff;


        ServletContext servletContext = session.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);


        ResourcesProcessorFactory resourcesProcessorFactory = (ResourcesProcessorFactory)
                context.getBean("processorFactory");

        ResourcesHolder schemaHolder;
        ResourcesProcessor processor;
/*        schemaHolder = getResourcesHolderForProcessing(templateId, "");
        processor = resourcesProcessorFactory.getResourcesProcessor(schemaHolder, context);*/

        //operation definition
        //hints
        HashMap hints = new HashMap();
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        RenderingHints renderingHints = new RenderingHints(hints);

        int rDiff = newR - oldR;
        int gDiff = newG - oldG;
        int bDiff = newB - oldB;
        int rHeaderDiff = newHeaderR - oldHeaderR;
        int gHeaderDiff = newHeaderG - oldHeaderG;
        int bHeaderDiff = newHeaderB - oldHeaderB;
        int rBgDiff = newBgR - oldBgR;
        int gBgDiff = newBgG - oldBgG;
        int bBgDiff = newBgB - oldBgB;
        int rFontDiff = newFontR - oldFontR;
        int gFontDiff = newFontG - oldFontG;
        int bFontDiff = newFontB - oldFontB;
        int rHeaderFontDiff = newHeaderFontR - oldHeaderFontR;
        int gHeaderFontDiff = newHeaderFontG - oldHeaderFontG;
        int bHeaderFontDiff = newHeaderFontB - oldHeaderFontB;

        int rBorderDiff = newBorderR - oldBorderR;
        int gBorderDiff = newBorderG - oldBorderG;
        int bBorderDiff = newBorderB - oldBorderB;

        float transparencyDiff = newTransp-oldTransp;

        float[] offsets = {rDiff, gDiff, bDiff, 0f};
        float[] offsetsHeader = {rHeaderDiff, gHeaderDiff, bHeaderDiff, 0f};
        float[] offsetsBg = {rBgDiff, gBgDiff, bBgDiff, 0f};
        float[] offsetsFont = {rFontDiff, gFontDiff, bFontDiff, 0f};
        float[] offsetsHeaderFont = {rHeaderFontDiff, gHeaderFontDiff, bHeaderFontDiff, 0f};
        float[] offsetsBorder = {rBorderDiff, gBorderDiff, bBorderDiff, 0f};
        float[] offsetsTranceparency = (0==transparencyDiff)?null:new float[]{0, 0, 0, transparencyDiff};
        float[] offsetsShadowTransparency = {0, 0, 0, transparencyDiff/5};

        float liteDivider = 2.5f;
        float[] liteoffsets = {rDiff/liteDivider, gDiff/liteDivider, bDiff/liteDivider, 0f};
        float[] scaleFactors = {1.0f, 1.0f, 1.0f, 1.0f};
        ExtJSRescaleOp brightenOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsets,
                renderingHints);

        ExtJSRescaleOp headerOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsHeader,
                renderingHints);

        ExtJSRescaleOp liteOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                liteoffsets,
                renderingHints);

        ExtJSRescaleOp bgOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsBg,
                renderingHints);

        ExtJSRescaleOp fontOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsFont,
                renderingHints);

        ExtJSRescaleOp headerFontOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsHeaderFont,
                renderingHints);


        ExtJSRescaleOp borderOp = new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsBorder,
                renderingHints);

        ExtJSRescaleOp transparencyOp = (0==transparencyDiff)?null:new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsTranceparency,
                renderingHints);

        ExtJSRescaleOp shadowTransparencyOp = null/*new ExtJSRescaleOp(
                scaleFactors,// 1  ,1   ,1
                offsetsShadowTransparency,
                renderingHints)*/;



/*
        ShiftOp shiftOp = new ShiftOp(new float[]{1, 1, 1, 1}
                , offsets, renderingHints, true);

        int csRGB = ColorSpace.CS_sRGB;
        int csGRAY = ColorSpace.CS_GRAY;
        ColorSpace srcCS = ColorSpace.getInstance(csRGB);

        ColorSpace destCS = ColorSpace.getInstance(csGRAY);
*/
        //operation with inversion of color #for heading font color
        ForegroundShiftOp foregroundOp = new ForegroundShiftOp(newR, newG, newB);

        AffineTransformOp affineTransformOp = null/*new AffineTransformOp(
                AffineTransform.getScaleInstance(2,2), AffineTransformOp.TYPE_BICUBIC)*/;
        //end operation  definition

        //get toolset holder
        ResourceHolderPool holderToolsetPool = this.holderToolsetPool;
        ResourcesHolder toolsetSchemaHolder = holderToolsetPool.checkOut();
        //
        //get drawable holder
        ResourceHolderPool holderDrawablePool = this.holderDrawablePool;
        ResourcesHolder drawableSchemaHolder = holderDrawablePool.checkOut();
        //
/*        process(processor, templateId, schemaHolder, brightenOp, foregroundOp, liteOp, bgOp,
                fontOp, transparencyOp, session, borderOp, affineTransformOp, headerFontOp,
                shadowTransparencyOp, headerOp, toolsetSchemaHolder,
                toolsetName, familyHeaderFont, weightHeaderFont, sizeHeaderFontDiff,
                familyFont, weightFont, sizeFontDiff,
                drawableSchemaHolder, "3.2"*//*version*//*);
        if (!"3.2".equals(version)){*/
            schemaHolder = getResourcesHolderForProcessing(templateId, version);
            processor = resourcesProcessorFactory.getResourcesProcessor(schemaHolder, context);
        process(processor, themeParametersHolder, templateId, schemaHolder, brightenOp, foregroundOp, liteOp, bgOp,
                    fontOp, transparencyOp, session, borderOp, affineTransformOp, headerFontOp,
                    shadowTransparencyOp, headerOp, toolsetSchemaHolder,
                    toolsetName, familyHeaderFont, weightHeaderFont, sizeHeaderFontDiff,
                    familyFont, weightFont, sizeFontDiff,
                    drawableSchemaHolder, version);

/*        }*/

        logger.info("ProcessThemeController ! IP="+httpServletRequest.getRemoteAddr());

        return new ModelAndView("json/success");
    }

    public ResourcesProcessorFactory getResourcesProcessorFactory() {
        return resourcesProcessorFactory;
    }

    public void setResourcesProcessorFactory(ResourcesProcessorFactory resourcesProcessorFactory) {
        this.resourcesProcessorFactory = resourcesProcessorFactory;
    }

    protected ResourcesHolder getResourcesHolderForProcessing(byte themeTemplateId, String version){
        ResourceHolderPool resourceHolderPool=null;
        if ("3.0".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGrayPool: this.holderPool;
        }else if ("3.1".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray31Pool: this.holder31Pool;
        }else if ("3.2".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray32Pool: this.holder32Pool;
        }else if ("2.3".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray23Pool: this.holder23Pool;
        }else {
            resourceHolderPool = 1==themeTemplateId? this.holderGray33Pool: this.holder33Pool;
        }  

        return resourceHolderPool.checkOut();
    }

    protected void process(ResourcesProcessor processor
            , ThemeParametersHolder themeParametersHolder, byte themeTemplateId, ResourcesHolder schemaHolder, ExtJSRescaleOp brightenOp
            , ForegroundShiftOp foregroundOp, ExtJSRescaleOp liteOp
            , ExtJSRescaleOp bgOp, ExtJSRescaleOp fontOp
            , ExtJSRescaleOp transparencyOp, HttpSession session
            , ExtJSRescaleOp borderOp, AffineTransformOp affineTransformOp
            , ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp
            , ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName
            , String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFontDiff
            , String familyFont, String weightFont, byte sizeFontDiff
            , ResourcesHolder drawableSchemaHolder, String version){

        ResourcesHolder resultHolder = processor.process(schemaHolder, themeParametersHolder, (ExtJSRescaleOp)brightenOp,
                (ForegroundShiftOp)foregroundOp, (ExtJSRescaleOp)liteOp, (ExtJSRescaleOp)bgOp,
                (ExtJSRescaleOp)fontOp, transparencyOp, borderOp,
                (AffineTransformOp) affineTransformOp, (ExtJSRescaleOp) headerFontOp,
                shadowTransparencyOp, headerOp, toolsetSchemaHolder, toolsetName
                , familyHeaderFont, weightHeaderFont, sizeHeaderFontDiff, familyFont, weightFont, sizeFontDiff,
                drawableSchemaHolder);
        ResourceHolderPool resourceHolderPool=null;
        ResourceHolderPool holderToolsetPool= this.holderToolsetPool;
        ResourceHolderPool holderDrawablePool= this.holderDrawablePool;

        if ("3.0".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGrayPool: this.holderPool;
        }else if ("3.1".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray31Pool: this.holder31Pool;
        }else if ("3.2".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray32Pool: this.holder32Pool;
        }else if ("2.3".equals(version)){
            resourceHolderPool = 1==themeTemplateId? this.holderGray23Pool: this.holder23Pool;
        }else {
            resourceHolderPool = 1==themeTemplateId? this.holderGray33Pool: this.holder33Pool;
        }  

        resourceHolderPool.checkIn(schemaHolder);//return holder to pool of unlocked
        holderToolsetPool.checkIn(toolsetSchemaHolder);//return holder to pool of unlocked
        holderDrawablePool.checkIn(drawableSchemaHolder);//return holder to pool of unlocked

        session.setAttribute(ApplicationConstants.CURRENT_SCHEMA_ATTRIBUTE_NAME/*+version*/,resultHolder);

    }

    public ResourceHolderPool getHolderPool() {
        return holderPool;
    }

    public void setHolderPool(ResourceHolderPool holderPool) {
        this.holderPool = holderPool;
    }

    public ResourceHolderPool getHolderGrayPool() {
        return holderGrayPool;
    }

    public void setHolderGrayPool(ResourceHolderPool holderGrayPool) {
        this.holderGrayPool = holderGrayPool;
    }

    public ResourceHolderPool getHolderToolsetPool() {
        return holderToolsetPool;
    }

    public void setHolderToolsetPool(ResourceHolderPool holderToolsetPool) {
        this.holderToolsetPool = holderToolsetPool;
    }

    public ResourceHolderPool getHolderDrawablePool() {
        return holderDrawablePool;
    }

    public void setHolderDrawablePool(ResourceHolderPool holderDrawablePool) {
        this.holderDrawablePool = holderDrawablePool;
    }

    public ResourceHolderPool getHolder31Pool() {
        return holder31Pool;
    }

    public void setHolder31Pool(ResourceHolderPool holder31Pool) {
        this.holder31Pool = holder31Pool;
    }

    public ResourceHolderPool getHolderGray31Pool() {
        return holderGray31Pool;
    }

    public void setHolderGray31Pool(ResourceHolderPool holderGray31Pool) {
        this.holderGray31Pool = holderGray31Pool;
    }

    public ResourceHolderPool getHolderGray32Pool() {
        return holderGray32Pool;
    }

    public void setHolderGray32Pool(ResourceHolderPool holderGray32Pool) {
        this.holderGray32Pool = holderGray32Pool;
    }

    public ResourceHolderPool getHolder32Pool() {
        return holder32Pool;
    }

    public void setHolder32Pool(ResourceHolderPool holder32Pool) {
        this.holder32Pool = holder32Pool;
    }

    public void setHolder33Pool(ResourceHolderPool holder33Pool) {
        this.holder33Pool=holder33Pool;
    }

    public ResourceHolderPool getHolder33Pool() {
        return holder33Pool;
    }

    public void setHolderGray33Pool(ResourceHolderPool holderGray33Pool) {
        this.holderGray33Pool=holderGray33Pool;
    }

    public ResourceHolderPool getHolderGray33Pool() {
        return holderGray33Pool;
    }

    public void setHolder23Pool(ResourceHolderPool holder23Pool) {
        this.holder23Pool = holder23Pool;
    }

    public ResourceHolderPool getHolder23Pool() {
        return holder23Pool;
    }

    public void setHolderGray23Pool(ResourceHolderPool holderGray23Pool) {
        this.holderGray23Pool = holderGray23Pool;
    }

    public ResourceHolderPool getHolderGray23Pool() {
        return holderGray23Pool;
    }
}
