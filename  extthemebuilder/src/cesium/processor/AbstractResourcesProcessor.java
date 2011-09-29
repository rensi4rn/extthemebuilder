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

import cesium.factory.ResourcesHolderFactory;
import cesium.factory.ResourcesHolderFactoryImpl;
import cesium.factory.ResourcesProcessorFactoryImpl;
import cesium.holder.ResourcesHolder;
import cesium.holder.ThemeParametersHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import org.springframework.context.ApplicationContext;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.Iterator;

public abstract class AbstractResourcesProcessor implements ResourcesProcessor{
    protected ApplicationContext context;
    protected ResourcesProcessorFactoryImpl resourcesProcessorFactory;

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public ResourcesProcessorFactoryImpl getResourcesProcessorFactory() {
        return resourcesProcessorFactory;
    }

    public void setResourcesProcessorFactory(ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.resourcesProcessorFactory = resourcesProcessorFactory;
    }

    public ResourcesHolder process(ResourcesHolder resHolder,
                                   ThemeParametersHolder themeParametersHolder, ExtJSRescaleOp brightenOp, ForegroundShiftOp foregroundOp,
                                   ExtJSRescaleOp liteOp, ExtJSRescaleOp bgOp,
                                   ExtJSRescaleOp fontOp, ExtJSRescaleOp transparencyOp,
                                   ExtJSRescaleOp borderOp, AffineTransformOp affineTransformOp,
                                   ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont,
                                   String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        ResourcesHolderFactory factory = new ResourcesHolderFactoryImpl();
        ResourcesHolder holder = factory.getResourcesHolder(resHolder);
        Object content = resHolder.getContent();
        holder.setContent(content);
        String resourcesPath = resHolder.getResourcesPath();
        holder.setResourcesPath(resourcesPath);
        if (null!=resHolder&& !resHolder.isEmpty()){
            for (Iterator iterator = resHolder.keySet().iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder innerHolder = (ResourcesHolder) resHolder.get(key);
                ResourcesProcessor processor = resourcesProcessorFactory.getResourcesProcessor(innerHolder, context);
                if (null!=processor&&null!=innerHolder){
                    ResourcesHolder newInnerHolder = processor.process(innerHolder, themeParametersHolder, brightenOp, foregroundOp, liteOp, bgOp, fontOp,
                            transparencyOp, borderOp, (AffineTransformOp) affineTransformOp, headerFontOp, shadowTransparencyOp,
                            headerOp, toolsetSchemaHolder, toolsetName, familyHeaderFont, weightHeaderFont, sizeHeaderFont,
                            familyFont, weightFont, sizeFont, drawableSchemaHolder);
                    holder.put(key, newInnerHolder);
                }
            }
        }

        return holder;
    }

    public ResourcesHolder advancedProcess(ResourcesHolder schemaHolder
            , BufferedImageOp brightenOp, BufferedImageOp foregroundOp, BufferedImageOp liteOp) {
        return null;
    }

}
