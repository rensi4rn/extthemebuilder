package cesium.holder;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:29:36
 */
public class GIFHolderImpl extends AbstractResourcesHolder  implements ResourcesHolder {
    private Object resource;
    private String path;

    public GIFHolderImpl(Object resource, String path, boolean isForeground,
                         boolean isBackground, boolean isBorder, int[] borderColor,
                         boolean isHeader, boolean isToolset, boolean isProcessingNotNeeded,
                         boolean isWhitable, boolean isDrawable, boolean isDrawableColorIndependent) {
        this.resource = resource;
        this.path = path;
        this.setForeground(isForeground);
        this.setWhitable(isWhitable);
        this.setDrawable(isDrawable);
        this.setDrawableColorIndependent(isDrawableColorIndependent);
        this.setHeader(isHeader);
        this.setBackground(isBackground);
        this.setBorderColor(isBorder);
        this.setBorderColor(borderColor);
        this.setToolset(isToolset);
        this.setProcessingNotNeeded(isProcessingNotNeeded);
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResourcesPath() {
        return getPath();
    }

    public void setResourcesPath(String path) {
        setPath(path);
    }

    public GIFHolderImpl() {
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public Object getContent() {
        return this.getResource();
    }

    public void setContent(Object obj) {
        this.setResource( obj);
    }
}
