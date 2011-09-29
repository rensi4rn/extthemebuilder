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
import cesium.holder.SchemaResourcesHolderImpl;
import cesium.theme.settings.ThemeSettings;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

public class SchemaResourcesLoaderImpl extends AbstractResourcesLoader{

    public SchemaResourcesLoaderImpl() {
    }

    public SchemaResourcesLoaderImpl(String resourcesPath) {
        this.setResourcesPath(resourcesPath );
    }

    public ResourcesHolder loadResources(String resourcesPath,
                                         ApplicationContext context,
                                         ServletContext servletContext, ThemeSettings themeSettings) {
        ResourcesHolder result = null;

        final ResourcesLoaderFactory loaderFactory = getResourcesLoaderFactory();

        result = new SchemaResourcesHolderImpl();
        result.setContent(resourcesPath);
        Set paths = null;

        paths = servletContext.getResourcePaths(resourcesPath);
        for (Iterator iterator = paths.iterator(); iterator.hasNext();) {
            String path = (String) iterator.next();
            if (!".svn".equals(path)){
                ResourcesLoader dirLoader = loaderFactory.getResourcesLoader(
                        path, context);
                ResourcesHolder dirHolder = dirLoader.loadResources(path, context,servletContext, themeSettings);
                result.put(new Integer(path.hashCode()),dirHolder);
            }
        }


        String filename = resourcesPath;
        System.out.println("dirname or filename = "+filename);

        return result;
    }

    public ResourcesHolder loadResources(String resourcesPath
            , ApplicationContext context, ThemeSettings themeSettings) {
        ResourcesHolder result = null;

        final ResourcesLoaderFactory loaderFactory = getResourcesLoaderFactory();
        File resource = new File (resourcesPath);
        result = new SchemaResourcesHolderImpl();
        result.setContent(resourcesPath);
        File[] dirs = null;

        // This filter only returns directories
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory()&&!".svn".equals(file.getName());
            }
        };
        dirs = resource.listFiles(fileFilter);
        for (int i = 0; null!=dirs&&i < dirs.length; i++) {
            File dir = dirs[i];
            String path = dir.getPath();
            ResourcesLoader dirLoader = loaderFactory.getResourcesLoader(
                    path, context);
            ResourcesHolder dirHolder = dirLoader.loadResources(path, context, themeSettings);
            result.put(new Integer(path.hashCode()),dirHolder);
        }

        File[] files = null;

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return loaderFactory.isValidResourceFileName(name);
            }
        };

        files = resource.listFiles(filter);

        for (int i=0; null!=files&&i<files.length; i++) {
            // system process resources ( files )
            File currentFile = files[i];
            String path = currentFile.getPath();
            ResourcesLoader fileLoader = loaderFactory.getResourcesLoader(
                    path, context);
            ResourcesHolder fileHolder = fileLoader.loadResources(path, context, themeSettings);
            result.put(new Integer(path.hashCode()),fileHolder);
        }


        String filename = resourcesPath;
        System.out.println("dirname or filename = "+filename);

        return result;
    }

    public ResourcesHolder loadResources(InputStream resource,
                                         ApplicationContext context
            , String resourcePath, ThemeSettings themeSettings){
        return null;
    }

    public void unloadResources(ResourcesHolder holder,
                                ApplicationContext context, Object destination) {
        if (holder instanceof SchemaResourcesHolderImpl){
            SchemaResourcesHolderImpl schemaholder = (SchemaResourcesHolderImpl) holder;
            String path = (String)schemaholder.getContent();
            String resourcesPath = (String) context.getBean("resourcesPath");
            String outputPath = (String) context.getBean("outputPath");
            path = path.replaceFirst(resourcesPath, outputPath);
            File schemaDir = new File(path);
            if ((!schemaDir.exists())||(!schemaDir.isDirectory())){
                schemaDir.mkdir();
            }
        }
        super.unloadResources(holder, context, destination);
    }
}
