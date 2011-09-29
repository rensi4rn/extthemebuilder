/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.factory;

import cesium.holder.*;
import cesium.processor.*;
import org.springframework.context.ApplicationContext;

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
