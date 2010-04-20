package cesium.factory;

import cesium.holder.ResourcesHolder;
import cesium.processor.ResourcesProcessor;
import org.springframework.context.ApplicationContext;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 15:13:29
 */
public interface ResourcesProcessorFactory {

    public ResourcesProcessor getResourcesProcessor(ResourcesHolder resourcesHolder
            , ApplicationContext context);
}
