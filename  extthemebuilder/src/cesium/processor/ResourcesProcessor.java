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
 * @Time: 13:56:23
 */
public interface ResourcesProcessor {
    public ResourcesHolder process(ResourcesHolder resHolder,
                                   ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp,
                                   ExtJSRescaleOp liteOp, ExtJSRescaleOp bgOp,
                                   ExtJSRescaleOp fontOp, ExtJSRescaleOp transparencyOp,
                                   ExtJSRescaleOp borderOp, AffineTransformOp affineTransformOp,
                                   ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFontDiff,
                                   String familyFont, String weightFont, byte sizeFontDiff, ResourcesHolder drawableSchemaHolder);
    public ApplicationContext getContext();

    public void setContext(ApplicationContext context);

    public ResourcesProcessorFactoryImpl getResourcesProcessorFactory();

    public void setResourcesProcessorFactory(ResourcesProcessorFactoryImpl resourcesProcessorFactory);

    ResourcesHolder advancedProcess(ResourcesHolder schemaHolder,
                                    BufferedImageOp brightenOp, BufferedImageOp foregroundOp, BufferedImageOp liteOp);
}
