/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.pool;

import cesium.context.AppContext;
import cesium.context.SpringAppServletContext;
import cesium.factory.ResourcesLoaderFactory;
import cesium.holder.ResourcesHolder;
import cesium.loader.ResourcesLoader;
import cesium.theme.settings.ThemeSettings;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class ResourceHolderPool extends ObjectPool<ResourcesHolder>{
    private static ResourceHolderPool resourceHolderPool = null;

    private String resourcesPath;
    private ResourcesLoaderFactory loaderFactory;
    private ThemeSettings themeSettings;

    public static synchronized ResourceHolderPool getInstance(long expirationTime, int initialCapacity,
                              String resourcesPath, ResourcesLoaderFactory loaderFactory,
                              ThemeSettings themeSettings) {
        if (null==resourceHolderPool){
            return new ResourceHolderPool(expirationTime, initialCapacity,
                               resourcesPath, loaderFactory,
                               themeSettings);
        }else {return resourceHolderPool;}
    }

    private ResourceHolderPool(long expirationTime, int initialCapacity,
                              String resourcesPath, ResourcesLoaderFactory loaderFactory,
                              ThemeSettings themeSettings) {
        super(expirationTime);
        this.resourcesPath = resourcesPath;
        this.loaderFactory = loaderFactory;
        this.themeSettings = themeSettings;

        for (int c=0; c<initialCapacity; c++){
            this.checkIn(create());
        }

    }

    protected ResourcesHolder create() {
        WebApplicationContext context = (WebApplicationContext) AppContext.getApplicationContext();

        ServletContext servletContext = SpringAppServletContext.getServletContext();

        ResourcesLoader loader = loaderFactory.getResourcesLoader(resourcesPath, context);

        ResourcesHolder resourcesHolder = loader.loadResources(resourcesPath, context,
                servletContext, themeSettings);
        return resourcesHolder;
    }

    public boolean validate(ResourcesHolder o) {
        return true;
    }

    public void expire(ResourcesHolder o) {

    }
}
