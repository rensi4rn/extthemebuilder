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

public class ResourcesHolderFactoryImpl implements ResourcesHolderFactory{
    public ResourcesHolder getResourcesHolder(ResourcesHolder resourcesHolder) {
        ResourcesHolder result = null;
        if (resourcesHolder instanceof SchemaResourcesHolderImpl)
            result = new SchemaResourcesHolderImpl();

        if (resourcesHolder instanceof CSSHolderImpl)
            result = new CSSHolderImpl();

        if (resourcesHolder instanceof CSSRuleHolderImpl)
            result = new CSSRuleHolderImpl();

        if (resourcesHolder instanceof CSSPropertyHolderImpl)
            result = new CSSPropertyHolderImpl();

        if (resourcesHolder instanceof GIFHolderImpl)
            result = new GIFHolderImpl();

        if (resourcesHolder instanceof PNGHolderImpl)
            result = new PNGHolderImpl();
        return result;
    }
}

