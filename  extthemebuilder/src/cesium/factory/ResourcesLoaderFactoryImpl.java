package cesium.factory;

import cesium.holder.*;
import cesium.loader.ResourcesLoader;
import org.springframework.context.ApplicationContext;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 10:55:59
 */
public class ResourcesLoaderFactoryImpl implements ResourcesLoaderFactory {
    static String css_ext = DOT_SYMBOL + CSS_EXTENSION;
    static String gif_ext = DOT_SYMBOL + GIF_EXTENSION;
    static String psd_ext = DOT_SYMBOL + PSD_EXTENSION;
    static String png_ext = DOT_SYMBOL + PNG_EXTENSION;

    public boolean isValidResourceFileName(String fileName){

        return isValidCSSFileName(fileName)
                || isValidGIFFileName(fileName)
                || isValidPNGFileName(fileName)
                /*|| isValidPSDFileName(fileName)*/;
    }

    public boolean isValidCSSFileName(String name) {
        return name.endsWith(css_ext);
    }

    public boolean isValidGIFFileName(String name) {
        return name.endsWith(gif_ext);
    }

    public boolean isValidPNGFileName(String name) {
        return name.endsWith(png_ext);
    }

    public boolean isValidPSDFileName(String name) {
        return name.endsWith(psd_ext);
    }

    public ResourcesLoader getResourcesLoader(String resourcesPath, ApplicationContext context) {
        ResourcesLoader result = null;
        if (isValidCSSFileName(resourcesPath))
            result = (ResourcesLoader)
                context.getBean("cssLoader");
        else if (isValidGIFFileName(resourcesPath))
            result = (ResourcesLoader)
                context.getBean("gifLoader");
        else if (isValidPNGFileName(resourcesPath))
            result = (ResourcesLoader)
                context.getBean("pngLoader");
        else if (isValidPSDFileName(resourcesPath))
            result = (ResourcesLoader)
                context.getBean("psdLoader");
        else result = (ResourcesLoader)
                context.getBean("schemaLoader");
        return result;
    }

    public ResourcesLoader getResourcesLoader(ResourcesHolder holder,
    ApplicationContext context) {
        ResourcesLoader result = null;
        if (holder instanceof CSSHolderImpl)
            result = (ResourcesLoader)
                context.getBean("cssLoader");
        else if (holder instanceof GIFHolderImpl)
            result = (ResourcesLoader)
                context.getBean("gifLoader");
        else if (holder instanceof PNGHolderImpl)
            result = (ResourcesLoader)
                context.getBean("pngLoader");
        else if (holder instanceof PSDHolderImpl)
            result = (ResourcesLoader)
                context.getBean("psdLoader");
        else result = (ResourcesLoader)
                context.getBean("schemaLoader");
        return result;
    }

}
