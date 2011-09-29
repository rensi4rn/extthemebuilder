/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.holder;

import java.util.List;
import java.util.Map;

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
