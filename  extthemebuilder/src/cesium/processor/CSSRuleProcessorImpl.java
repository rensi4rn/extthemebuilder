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
import cesium.holder.ResourcesHolder;
import cesium.holder.ThemeParametersHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import org.springframework.context.ApplicationContext;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;

public class CSSRuleProcessorImpl extends AbstractResourcesProcessor {
    public CSSRuleProcessorImpl(ApplicationContext context,
                                ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public ResourcesHolder process(ResourcesHolder resHolder, ThemeParametersHolder themeParametersHolder, ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp, ExtJSRescaleOp liteOp,
                                   ExtJSRescaleOp bgOp, ExtJSRescaleOp fontOp,
                                   ExtJSRescaleOp transparencyOp, ExtJSRescaleOp borderOp,
                                   AffineTransformOp affineTransformOp, ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont,
                                   String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        //String resourcesPath = resHolder.getResourcesPath();
        //System.out.println("prop path = "+ resourcesPath);
            return super.process(resHolder, themeParametersHolder, brightenOp, foregroundOp, liteOp,
                    bgOp, fontOp, transparencyOp, borderOp, (AffineTransformOp) affineTransformOp,
                    headerFontOp, shadowTransparencyOp, headerOp, toolsetSchemaHolder, toolsetName,
                    familyHeaderFont, weightHeaderFont, sizeHeaderFont, familyFont, weightFont, sizeFont, drawableSchemaHolder);

    }
}
