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
import cesium.loader.ResourcesLoader;
import org.springframework.context.ApplicationContext;

public interface ResourcesLoaderFactory {
    public static final String DOT_SYMBOL = ".";
    public static final String CSS_EXTENSION = "css";
    public static final String GIF_EXTENSION = "gif";
    public static final String PNG_EXTENSION = "png";

    ResourcesLoader getResourcesLoader(String resourcesPath, ApplicationContext context);
    ResourcesLoader getResourcesLoader(ResourcesHolder holder, ApplicationContext context);

    boolean isValidResourceFileName(String name);

    boolean isValidCSSFileName(String name);
    boolean isValidGIFFileName(String name);
    boolean isValidPNGFileName(String name);
}
