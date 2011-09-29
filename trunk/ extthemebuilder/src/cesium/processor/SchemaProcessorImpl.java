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

import cesium.factory.ResourcesLoaderFactory;
import cesium.factory.ResourcesProcessorFactory;
import cesium.factory.ResourcesProcessorFactoryImpl;
import cesium.holder.ResourcesHolder;
import cesium.loader.ResourcesLoader;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ShiftOp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import java.util.HashMap;

public class SchemaProcessorImpl extends AbstractResourcesProcessor{
    private static String springConfigLocation = "src\\spring-config.xml";


    public SchemaProcessorImpl(ApplicationContext context, ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public static void main(String[] args) {
        System.out.println("springConfigLocation = "+ springConfigLocation);

        ApplicationContext context = new FileSystemXmlApplicationContext(springConfigLocation);

        String resourcesPath = (String) context.getBean("resourcesPath");
        String outputPath = (String) context.getBean("outputPath");
        ResourcesLoaderFactory loaderFactory = (ResourcesLoaderFactory)
                context.getBean("loaderFactory");

        ResourcesLoader loader = loaderFactory.getResourcesLoader(resourcesPath, context);

        ResourcesHolder schemaHolder = loader.loadResources(resourcesPath, context, null);

        ResourcesProcessorFactory resourcesProcessorFactory = (ResourcesProcessorFactory)
                context.getBean("processorFactory");

        ResourcesProcessor processor = resourcesProcessorFactory.getResourcesProcessor(schemaHolder, context);

        //operation definition
        //hints
        HashMap hints = new HashMap();
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        //hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        RenderingHints renderingHints = new RenderingHints(hints);

        //to gray schema color convert op
        int csRGB = ColorSpace.CS_sRGB;
        int csGRAY = ColorSpace.CS_GRAY;
        int csLINEAR_RGB = ColorSpace.CS_LINEAR_RGB;
        int csPYCC = ColorSpace.CS_PYCC;
        ColorSpace srcCS = ColorSpace.getInstance(csRGB);

        ColorSpace destCS = ColorSpace.getInstance(csPYCC);
        
        ColorConvertOp op = new ColorConvertOp(srcCS ,destCS, renderingHints);

        //to shift op
        ShiftOp shiftOp = new ShiftOp(new float[]{0f, 0f, 0f, 0f}
                , new float[]{0f, 0f, 0f, 0f}, renderingHints, false);

        //to invert colors
/*
        RescaleOp brightenOp = new RescaleOp(
                1.0f,// 1  ,1   ,1
                -50f,// 20 ,-20 ,-50
                renderingHints);
*/
        ExtJSRescaleOp brightenOp = new ExtJSRescaleOp(
                new float[]{1.0f,1.0f,1.0f,1.0f},// 1  ,1   ,1
                new float[]{-100f,-150.0f,0.0f,0f},// 20 ,-20 ,-50
                renderingHints);
        ExtJSRescaleOp liteOp = new ExtJSRescaleOp(
                new float[]{1.0f,1.0f,1.0f,1.0f},// 1  ,1   ,1
                new float[]{-100f/3,-150.0f/3,0.0f/3,0f},// 20 ,-20 ,-50
                renderingHints);
        //end operation  definition

        ResourcesHolder resultHolder = processor.process(schemaHolder, null, brightenOp
                , null, liteOp, null, null, null, null, null, null, null, null,
                null, null, null, null, (byte)0, null, null, (byte)0, null);

        loader.unloadResources(resultHolder, context, outputPath);
        // destination parameter

        System.out.println("schemaHolder "+schemaHolder);
        System.out.println("resultHolder "+resultHolder);



    }


}
