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
 * @Time: 14:00:48
 */
public class CSSProcessorImpl extends AbstractResourcesProcessor{

    public CSSProcessorImpl(ApplicationContext context,
                            ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public ResourcesHolder process(ResourcesHolder resHolder, ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp, ExtJSRescaleOp liteOp,
                                   ExtJSRescaleOp bgOp, ExtJSRescaleOp fontOp, ExtJSRescaleOp transparencyOp,
                                   ExtJSRescaleOp borderOp, AffineTransformOp affineTransformOp,
                                   ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFont,
                                   String familyFont, String weightFont, byte sizeFont, ResourcesHolder drawableSchemaHolder) {
        ResourcesHolder holder = super.process(resHolder, brightenOp, foregroundOp, liteOp, bgOp, fontOp,
                transparencyOp, borderOp, affineTransformOp, headerFontOp,
                shadowTransparencyOp, headerOp, toolsetSchemaHolder, toolsetName,
                familyHeaderFont, weightHeaderFont, sizeHeaderFont,
                familyFont, weightFont, sizeFont, drawableSchemaHolder);
        if ("vista".equals(toolsetName)){  //for vista like toolset
            ResourcesHolder cssVistaWindowToolsetResHolder = toolsetSchemaHolder.findResourceByFileEnds("/window.css");//one css file in resources

            holder.putAll(cssVistaWindowToolsetResHolder);
/*
            if (null!=cssVistaWindowToolsetResHolder&&cssVistaWindowToolsetResHolder.size()>0){
                for (Iterator iterator = cssVistaWindowToolsetResHolder.keySet().iterator(); iterator.hasNext();) {
                    Object key = iterator.next();
                    ResourcesHolder innerHolder = (ResourcesHolder) cssVistaWindowToolsetResHolder.get(key);
                    ResourcesProcessor processor = resourcesProcessorFactory.getResourcesProcessor(innerHolder, context);
                    if (null!=processor&&null!=innerHolder){
                        ResourcesHolder newInnerHolder = processor.process(innerHolder, brightenOp, foregroundOp, liteOp, bgOp, fontOp,
                                transparencyOp, borderOp, (AffineTransformOp) affineTransformOp, headerFontOp, shadowTransparencyOp,
                                headerOp, toolsetSchemaHolder, toolsetName, familyHeaderFont, weightHeaderFont, sizeHeaderFont,
                                familyFont, weightFont, sizeFont);
                        holder.put(key, newInnerHolder);
                    }
                }
            }
*/
        }

        return holder;
    }
}
