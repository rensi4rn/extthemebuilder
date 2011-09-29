/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.loader;

import cesium.factory.ResourcesLoaderFactory;
import cesium.holder.ResourcesHolder;
import cesium.theme.settings.ThemeSettings;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.context.ApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractResourcesLoader implements ResourcesLoader{
    private String resourcesPath;
    private ResourcesLoaderFactory resourcesLoaderFactory;

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public ResourcesHolder loadResources(String resourcesPath
            , ApplicationContext context, ThemeSettings themeSettings){
        FileInputStream resource = null;
        try {
            resource = new FileInputStream(resourcesPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return loadResources(resource, context, resourcesPath, themeSettings);
    }

    public ResourcesLoaderFactory getResourcesLoaderFactory() {
        return resourcesLoaderFactory;
    }

    public void setResourcesLoaderFactory(ResourcesLoaderFactory resourcesLoaderFactory) {
        this.resourcesLoaderFactory = resourcesLoaderFactory;
    }

    public void unloadResources(ResourcesHolder holder, ApplicationContext context, Object destination) {

        ResourcesLoaderFactory loaderFactory = resourcesLoaderFactory;

        if (null != holder){
            Set set = holder.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder resourcesHolder = (ResourcesHolder) holder.get(key);
                ResourcesLoader loader = loaderFactory.getResourcesLoader(resourcesHolder, context);
                loader.unloadResources(resourcesHolder, context, destination);
            }
        }
    }

    public OutputStream outForWeb(ResourcesHolder holder, String thisControllerUrl, String resourcesPath) {
        return null;
    }

    public ZipOutputStream outForZip(ResourcesHolder holder, String resourcesPath
            , ZipOutputStream zipOS, ApplicationContext context, String newSchemaName, String templateName, String version)
            throws IOException {
        ResourcesLoaderFactory loaderFactory = resourcesLoaderFactory;

        if (null != holder){
            Set set = holder.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder resourcesHolder = (ResourcesHolder) holder.get(key);
                ResourcesLoader loader = loaderFactory.getResourcesLoader(resourcesHolder, context);
                zipOS = loader.outForZip(resourcesHolder, resourcesPath, zipOS, context, newSchemaName, templateName, version);
            }
        }
        return zipOS;
    }
}
