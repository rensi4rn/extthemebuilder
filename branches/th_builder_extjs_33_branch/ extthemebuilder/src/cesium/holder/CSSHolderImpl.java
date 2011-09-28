package cesium.holder;

import java.io.InputStream;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 2:32:51
 */
public class CSSHolderImpl extends AbstractResourcesHolder implements CSSHolder{
    private InputStream resource;
    private String resourcesPath;

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public CSSHolderImpl() {
    }

    public CSSHolderImpl(InputStream resource, String resourcePath) {
        this.resource = resource;
        resourcesPath = resourcePath;
    }

    public InputStream getResource() {
        return resource;
    }

    public void setResource(InputStream resource) {
        this.resource = resource;
    }

    public Object getContent() {
        return this.resource;
    }

    public void setContent(Object obj) {
        this.resource = (InputStream) obj;
    }

    public String getResourceName() {
        String path = resourcesPath;
        String result = null;
        int pointIndex = path.lastIndexOf(".");
        int slashIndex = path.lastIndexOf("/");
        result = path.substring(slashIndex+1, pointIndex);
        return result;
    }
}
