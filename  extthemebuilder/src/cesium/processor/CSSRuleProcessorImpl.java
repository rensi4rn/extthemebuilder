package cesium.processor;

import cesium.factory.ResourcesProcessorFactoryImpl;
import cesium.holder.ResourcesHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import org.springframework.context.ApplicationContext;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 16:55:56
 */
public class CSSRuleProcessorImpl extends AbstractResourcesProcessor {
    public CSSRuleProcessorImpl(ApplicationContext context,
                                ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    //todo: sc fake process method to exclude some rules from processing
    public ResourcesHolder process(ResourcesHolder resHolder, ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp, ExtJSRescaleOp liteOp,
                                   ExtJSRescaleOp bgOp, ExtJSRescaleOp fontOp,
                                   ExtJSRescaleOp transparencyOp, ExtJSRescaleOp borderOp,
                                   AffineTransformOp affineTransformOp, ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont,
                                   String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        //String resourcesPath = resHolder.getResourcesPath();
        //System.out.println("prop path = "+ resourcesPath);
            return super.process(resHolder, brightenOp, foregroundOp, liteOp,
                    bgOp, fontOp, transparencyOp, borderOp, (AffineTransformOp) affineTransformOp,
                    headerFontOp, shadowTransparencyOp, headerOp, toolsetSchemaHolder, toolsetName,
                    familyHeaderFont, weightHeaderFont, sizeHeaderFont, familyFont, weightFont, sizeFont, drawableSchemaHolder);

    }
}
