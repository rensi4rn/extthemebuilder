package cesium.factory;

import cesium.holder.*;
import cesium.processor.*;
import org.springframework.context.ApplicationContext;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 15:13:49
 */
public class ResourcesProcessorFactoryImpl implements ResourcesProcessorFactory {
    public ResourcesProcessor getResourcesProcessor(ResourcesHolder resourcesHolder
            , ApplicationContext context){
        ResourcesProcessor result = null;
        if (resourcesHolder instanceof SchemaResourcesHolderImpl)
            result = new SchemaProcessorImpl(context,this);

        if (resourcesHolder instanceof CSSHolderImpl)
            result = new CSSProcessorImpl(context, this);

        if (resourcesHolder instanceof CSSRuleHolderImpl)
            result = new CSSRuleProcessorImpl(context,this);

        if (resourcesHolder instanceof CSSPropertyHolderImpl)
            result = new CSSPropertyProcessorImpl(context,this);

        if (resourcesHolder instanceof GIFHolderImpl)
            result = new GIFProcessorImpl(context,this);

        if (resourcesHolder instanceof PNGHolderImpl)
            result = new PNGProcessorImpl(context,this);
        return result;
    }
}
