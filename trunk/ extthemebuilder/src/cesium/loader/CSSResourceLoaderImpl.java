package cesium.loader;

import cesium.factory.ResourcesLoaderFactory;
import cesium.handler.CSSDocHandler;
import cesium.holder.CSSHolderImpl;
import cesium.holder.CSSPropertyHolderImpl;
import cesium.holder.CSSRuleHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.theme.settings.ThemeSettings;
import org.apache.batik.css.parser.CSSSelectorList;
import org.apache.batik.css.parser.CustomParser;
import org.apache.batik.css.parser.Parser;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.context.ApplicationContext;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Selector;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:13:49
 */
public class CSSResourceLoaderImpl extends AbstractResourcesLoader{
    public CSSResourceLoaderImpl() {
    }

    public CSSResourceLoaderImpl(String resourcesPath) {
        this.setResourcesPath(resourcesPath);
    }

    public ResourcesHolder loadResources(String resourcesPath
            , ApplicationContext context
            , ServletContext servletContext, ThemeSettings themeSettings) {
        InputStream stream = servletContext.getResourceAsStream(resourcesPath);
        return loadResources(stream, context,  resourcesPath, themeSettings);
    }

    public ResourcesHolder loadResources(InputStream resource,
                                         ApplicationContext context
            , String resourcePath, ThemeSettings themeSettings) {
        ResourcesHolder result = new CSSHolderImpl(resource, resourcePath);
        InputStreamReader fileReader = null;
        try {
            fileReader = new InputStreamReader(resource);
            Parser parser = new CustomParser();
            InputSource inputSource = new InputSource(fileReader/*normalizingReader*/);
            ResourcesLoaderFactory loaderFactory = getResourcesLoaderFactory();
            CSSDocHandler docHandler = new CSSDocHandler(
                    loaderFactory
                    , context, themeSettings);
            parser.setDocumentHandler(docHandler);
            parser.parseStyleSheet(inputSource);
            HashMap resources = docHandler.getResources();

            if (null!=resources) result.putAll(resources);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("path = "+resourcePath);
        return result;
    }

    public void unloadResources(ResourcesHolder holder,
                                ApplicationContext context,
                                Object destination) {
        File cssFile = null;
        if (holder instanceof CSSHolderImpl){
            CSSHolderImpl cssHolder = (CSSHolderImpl) holder;
            Object content = cssHolder.getContent();
            String resPath = cssHolder.getResourcesPath();
            String resourcesPath = (String) context.getBean("resourcesPath");
            String outputPath = (String) context.getBean("outputPath");
            resPath = resPath.replaceFirst(resourcesPath, outputPath);
            cssFile = new File(resPath);
            if (!cssFile.exists()||!cssFile.isFile()){
                try {
                    cssFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (cssFile.exists()&&cssFile.isFile()){
                Set set = cssHolder.keySet();
                FileWriter writer;
                try {
                    writer = new FileWriter(cssFile);
                    for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                        Object keySelectorList = iterator.next();
                        CSSRuleHolderImpl cssRuleHolder = (CSSRuleHolderImpl)
                                cssHolder.get(keySelectorList);
                        CSSSelectorList selectorList = (CSSSelectorList) keySelectorList;
                        for (int i= 0; null!=selectorList&&i<selectorList.getLength();i++){
                            if (i>0) writer.append(", ");
                            Selector selector = selectorList.item(i);
                            writer.append(selector.toString());
                        }

                        Set propertiesSet = cssRuleHolder.keySet();
                        writer.append("{\r\n");
                        for (Iterator iterator1 = propertiesSet.iterator(); iterator1.hasNext();) {
                            String s = (String) iterator1.next();
                            CSSPropertyHolderImpl propertyHolder = (CSSPropertyHolderImpl)
                                    cssRuleHolder.get(s);
                            String propValue = propertyHolder.getPropertyStringValue();
                            writer.append(s);
                            writer.append(":");
                            writer.append(propValue);
                            writer.append(";\r\n");


/*                            if (null!=propValue&&propValue.indexOf("/btn.gif")>=0){
                                writer.append(s);
                                writer.append(":expression((navigator.appName=='Microsoft Internet Explorer')?'");
                                writer.append(propValue.replaceAll("/btn.gif","/ie6-btn.gif"));
                                writer.append("':'");
                                writer.append(propValue);
                                writer.append("');\r\n");
                            }*/
                            //unload Image files
/*                            if (null!=propertyHolder&&propertyHolder.size()>0){
                                Set fileKeys = propertyHolder.keySet();
                                for (Iterator iterator2 = fileKeys.iterator(); iterator2.hasNext();) {
                                    String filePath = (String) iterator2.next();

                                    ResourcesHolder imageHolder =(ResourcesHolder) propertyHolder.get(filePath);
                                    byte[] data = (byte[]) imageHolder.getContent();
                                    filePath = filePath.replace(resourcesPath, outputPath);
                                    File imageFile = new File(filePath);
                                    if (!imageFile.exists()||!imageFile.isFile()){
                                        imageFile.createNewFile();
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                                    fileOutputStream.write(data);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                }
                            }*/
                        }
                        writer.append("}\r\n");

                    }
                    writer.flush();
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public OutputStream outForWeb(ResourcesHolder holder, String thisControllerUrl, String resourcesPath) {
        OutputStream out = null;

        if (holder instanceof CSSHolderImpl){
            CSSHolderImpl cssHolder = (CSSHolderImpl) holder;
            Set set = cssHolder.keySet();
            out = new ByteArrayOutputStream();
            try {
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    Object keySelectorList = iterator.next();
                    CSSRuleHolderImpl cssRuleHolder = (CSSRuleHolderImpl)
                            cssHolder.get(keySelectorList);
                    CSSSelectorList selectorList = (CSSSelectorList) keySelectorList;
                    for (int i= 0; null!=selectorList&&i<selectorList.getLength();i++){
                        if (i>0) out.write(", ".getBytes());
                        Selector selector = selectorList.item(i);
                        out.write(selector.toString().getBytes());
                    }

                    Set propertiesSet = cssRuleHolder.keySet();
                    out.write("{\r\n".getBytes());
                    for (Iterator iterator1 = propertiesSet.iterator(); iterator1.hasNext();) {
                        String s = (String) iterator1.next();
                        CSSPropertyHolderImpl propertyHolder = (CSSPropertyHolderImpl)
                                cssRuleHolder.get(s);
                        String propValue = propertyHolder.getPropertyStringValueForWeb(thisControllerUrl, resourcesPath);
                        out.write(s.getBytes());
                        out.write(":".getBytes());
                        out.write(propValue.getBytes());
                        out.write(";\r\n".getBytes());


/*                        if (null!=propValue&&propValue.indexOf("/btn.gif")>=0){
                                out.write(s.getBytes());
                                out.write(":expression((navigator.appName=='Microsoft Internet Explorer')?'".getBytes());
                                out.write(propValue.replaceAll("/btn.gif","/ie6-btn.gif").getBytes());
                                out.write("':'".getBytes());
                                out.write(propValue.getBytes());
                                out.write("');\r\n".getBytes());
                        }*/
                    }
                    out.write("}\r\n".getBytes());

                }
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return out;
    }

    public ZipOutputStream outForZip(ResourcesHolder holder, String resourcesPath,
                                     ZipOutputStream zipOS, ApplicationContext context,
                                     String newSchemaName, String templateName, String version) throws IOException {
        ByteArrayOutputStream out = null;

        if (holder instanceof CSSHolderImpl){
            CSSHolderImpl cssHolder = (CSSHolderImpl) holder;
            Set set = cssHolder.keySet();
            out = new ByteArrayOutputStream();
            try {
                String fileHeader = new StringBuilder()
                        .append("/* Document   : xtheme-")
                        .append(newSchemaName).append("\n")
                        .append("    Created on  : ")
                        .append(new Formatter().format("%1$tm/%1$td/%1$tY, %1$tH:%1$tM:%1$tS", new Date()))
                        .append("\n")
                        .append("    Generated by: Online Ext Theme builder\n")
                        .append("    Author      : Sergei Chentsov aka Ivan Ekker\n")
                        .append("    email       : sergchentsov@gmail.com\n")
                        .append("    Description:\n")
                        .append("        ExtJS ")
                        .append((null == version ) ? "3.2" : version)
                        .append(" theme ")
                        .append(newSchemaName)
                        .append(" \n")
                        .append("    License    : Dual Licensed\n")
                        .append("                 1.- GNU GPL Licensed, Free of charge for any non-commercial products.\n")
                        .append("                 2.- Contact me if you are using the theme in a commercial project.\n")
                        .append("                 Contact me for any questions or request in the email above.\n")
                        .append("*/").toString();
                out.write(fileHeader.getBytes());
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    Object keySelectorList = iterator.next();
                    CSSRuleHolderImpl cssRuleHolder = (CSSRuleHolderImpl)
                            cssHolder.get(keySelectorList);
                    CSSSelectorList selectorList = (CSSSelectorList) keySelectorList;
                    for (int i= 0; null!=selectorList&&i<selectorList.getLength();i++){
                        if (i>0) out.write(", ".getBytes());
                        Selector selector = selectorList.item(i);
                        out.write(selector.toString().getBytes());
                    }

                    Set propertiesSet = cssRuleHolder.keySet();
                    out.write("{\r\n".getBytes());
                    for (Iterator iterator1 = propertiesSet.iterator(); iterator1.hasNext();) {
                        String s = (String) iterator1.next();
                        CSSPropertyHolderImpl propertyHolder = (CSSPropertyHolderImpl)
                                cssRuleHolder.get(s);
                        String propValue = propertyHolder.getPropertyStringValueForZip(newSchemaName);
                        out.write(s.getBytes());
                        out.write(":".getBytes());
                        out.write(propValue.getBytes());
                        out.write(";\r\n".getBytes());

                    }
                    out.write("}\r\n".getBytes());

                }
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        String innerZipPath = holder.getResourcesPath().replaceFirst(resourcesPath + "/", "").replaceFirst(templateName,newSchemaName);
        ZipEntry zipEntry = new ZipEntry(innerZipPath);
        zipOS.putNextEntry(zipEntry);
        byte[] data = out.toByteArray();
        zipOS.write(data);
        zipOS.closeEntry();

        return zipOS;
    }
}