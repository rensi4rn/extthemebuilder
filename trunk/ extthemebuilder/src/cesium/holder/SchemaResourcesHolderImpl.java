package cesium.holder;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 2:31:57
 */
public class SchemaResourcesHolderImpl extends AbstractResourcesHolder
        implements SchemaResourcesHolder {
    private String resourcesPath;
    private String outputPath;

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String path) {
        this.resourcesPath = path;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Object getContent() {
        return this.getResourcesPath();
    }

    public void setContent(Object obj) {
        this.setResourcesPath((String)obj);
    }
}
