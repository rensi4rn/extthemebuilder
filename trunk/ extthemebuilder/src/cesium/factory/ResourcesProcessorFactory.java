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

import cesium.holder.ResourcesHolder;
import cesium.processor.ResourcesProcessor;
import org.springframework.context.ApplicationContext;

public interface ResourcesProcessorFactory {

    public ResourcesProcessor getResourcesProcessor(ResourcesHolder resourcesHolder
            , ApplicationContext context);
}
