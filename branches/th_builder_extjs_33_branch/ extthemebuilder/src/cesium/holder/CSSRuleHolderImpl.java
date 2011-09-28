package cesium.holder;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 16.08.2009
 * @Time: 22:18:17
 */
public class CSSRuleHolderImpl extends AbstractResourcesHolder  implements CSSRuleHolder{
    private String resourcesPath;

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public Object getContent() {
        return null;
    }

    public void setContent(Object obj) {

    }
}
