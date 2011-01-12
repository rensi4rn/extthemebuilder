package cesium.holder;

import java.util.List;
import java.util.Map;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 13:46:29
 */
public interface ResourcesHolder extends Map {

    Object getContent();

    void setContent(Object obj);

    public String getResourcesPath();

    public int getResourcesPathHashCode();

    public void setResourcesPath(String path);

    public ResourcesHolder findResourceByPath(String resourcePath);

    public ResourcesHolder findResourceByPathHashCode(Integer hashCode);

    public ResourcesHolder findFirstCSSResource();

    public List findAllCSSResources();

    public ResourcesHolder findResourceByFileEnds(String fileName);

    public boolean isProcessingNotNeeded();
    
    public boolean isDrawable();
    
    public boolean isDrawableColorIndependent();

    void setThemeParametersHolder(ThemeParametersHolder themeParametersHolder);

    public ThemeParametersHolder getThemeParametersHolder();
}
