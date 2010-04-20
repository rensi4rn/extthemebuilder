package cesium.factory;

import cesium.holder.*;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 17:25:03
 */
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

