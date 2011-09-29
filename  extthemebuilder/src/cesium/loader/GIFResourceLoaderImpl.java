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

import cesium.holder.GIFHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.theme.settings.BorderSet;
import cesium.theme.settings.ThemeSettings;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Arrays;


public class GIFResourceLoaderImpl extends AbstractResourcesLoader {
    public GIFResourceLoaderImpl() {
    }

    public GIFResourceLoaderImpl(String resourcesPath) {
        this.setResourcesPath(resourcesPath);
    }

    public ResourcesHolder loadResources(String resourcesPath,
                                         ApplicationContext context,
                                         ServletContext servletContext, ThemeSettings themeSettings) {
        InputStream stream = servletContext.getResourceAsStream(resourcesPath);
        return loadResources(stream, context, resourcesPath, themeSettings);
    }

    public ResourcesHolder loadResources(InputStream resource,
                                         ApplicationContext context,
                                         String resourcePath, ThemeSettings themeSettings) {

        boolean isProcessingNotNeeded = themeSettings.isProcessingNotNeededGIF(resourcePath);

        boolean isForeground = themeSettings.isForegroundGIF(resourcePath);

        boolean isWhitable = themeSettings.isWhitableGIF(resourcePath);

        boolean isDrawable = themeSettings.isDrawableGIF(resourcePath);

        boolean isDrawableColorIndependent = themeSettings.isDrawableColorIndependentGIF(resourcePath);

        boolean isHeader = themeSettings.isHeaderGIF(resourcePath);

        boolean isBackground = themeSettings.isBackgroundGIF(resourcePath);

        //border settings section

        BorderSet borderSet = themeSettings.getBorderSetGIF(resourcePath);
        boolean isBorder = borderSet.isBorder();
        int[] borderColor = borderSet.getBorderColor();

        //toolset
        boolean isToolset = themeSettings.isToolsetGIF(resourcePath);

        String path2resource = resourcePath;
        byte[] data=null;
        try {
            data = new byte[resource.available()];
            resource.read(data);
            resource.close();
            //ResourcesLoader loader = getResourcesLoaderFactory().getResourcesLoader(path2resource, context);
            //holder = loader.loadResources(path2resource, context);
            //holder.setContent(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Arrays.sort(borderColor);
        return new GIFHolderImpl(data, path2resource, isForeground, isBackground
                , isBorder, borderColor, isHeader, isToolset, isProcessingNotNeeded
                , isWhitable, isDrawable, isDrawableColorIndependent);
    }

    public void unloadResources(ResourcesHolder imageHolder,
                                ApplicationContext context,
                                Object destination) {
        String resourcesPath = (String) context.getBean("resourcesPath");
        String outputPath = (String) context.getBean("outputPath");
        byte[] data = (byte[]) imageHolder.getContent();

        if (null==data) return;

        String filePath = ((GIFHolderImpl)imageHolder).getPath() ;
        filePath = filePath.replace(resourcesPath, outputPath);
        File imageFile = new File(filePath);
        try {
            if (!imageFile.exists()||!imageFile.isFile()){
                imageFile.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OutputStream outForWeb(ResourcesHolder holder, String thisControllerUrl, String resourcesPath) {
        OutputStream out = null;

        byte[] data = (byte[]) holder.getContent();

        if (null==data) return out;

        try {
            out = new ByteArrayOutputStream(data.length);
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }


    public ZipOutputStream outForZip(ResourcesHolder holder, String resourcesPath,
                                     ZipOutputStream zipOS, ApplicationContext context, String newSchemaName, String templateName, String version) throws IOException {
        byte[] data = (byte[]) holder.getContent();

        if (null==data) return zipOS;

        String innerZipPath = holder.getResourcesPath().replaceFirst(resourcesPath + "/", "").replaceFirst("default",newSchemaName);
        ZipEntry zipEntry = new ZipEntry(innerZipPath);
        zipOS.putNextEntry(zipEntry);
        zipOS.write(data);
        zipOS.closeEntry();
        return zipOS;
    }
}
