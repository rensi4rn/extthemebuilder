package cesium.factory;

import cesium.holder.ResourcesHolder;
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
 * @Time: 11:21:21
 */
public interface ResourcesLoaderFactory {
    public static final String DOT_SYMBOL = ".";
    public static final String CSS_EXTENSION = "css";
    public static final String GIF_EXTENSION = "gif";
    public static final String PNG_EXTENSION = "png";
    public static final String PSD_EXTENSION = "psd";

    ResourcesLoader getResourcesLoader(String resourcesPath, ApplicationContext context);
    ResourcesLoader getResourcesLoader(ResourcesHolder holder, ApplicationContext context);

    boolean isValidResourceFileName(String name);

    boolean isValidCSSFileName(String name);
    boolean isValidGIFFileName(String name);
    boolean isValidPNGFileName(String name);
    boolean isValidPSDFileName(String name);
}
