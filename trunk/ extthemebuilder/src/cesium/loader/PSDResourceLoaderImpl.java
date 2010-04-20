package cesium.loader;

import cesium.holder.PSDHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.theme.settings.ThemeSettings;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import java.io.InputStream;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 11.08.2009
 * @Time: 14:31:50
 */
public class PSDResourceLoaderImpl extends AbstractResourcesLoader {
    public PSDResourceLoaderImpl() {
    }

    public PSDResourceLoaderImpl(String resourcesPath) {
        this.setResourcesPath(resourcesPath);
    }

    public ResourcesHolder loadResources(String resourcesPath
            , ApplicationContext context,
              ServletContext servletContext, ThemeSettings themeSettings) {
        return new PSDHolderImpl();
    }

    public ResourcesHolder loadResources(InputStream resource
            , ApplicationContext context, String resourcePath, ThemeSettings themeSettings) {
        return new PSDHolderImpl();
    }

    public void unloadResources(ResourcesHolder holder, ApplicationContext context, Object destination) {

    }
}
