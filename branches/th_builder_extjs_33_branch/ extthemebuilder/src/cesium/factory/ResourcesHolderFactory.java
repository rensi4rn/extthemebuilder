package cesium.factory;

import cesium.holder.ResourcesHolder;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 17:24:44
 */
public interface ResourcesHolderFactory {
    public ResourcesHolder getResourcesHolder(ResourcesHolder holder);
}
