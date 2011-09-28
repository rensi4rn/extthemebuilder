package springapp.web.controller.theme;

import cesium.factory.ResourcesLoaderFactory;
import cesium.pool.ResourceHolderPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 20.08.2009
 * @Time: 22:15:31
 */
public class ThemeSettingsController implements Controller {
    ResourcesLoaderFactory loaderFactory ;
    protected final Log logger = LogFactory.getLog(getClass());
    private ResourceHolderPool holderPool;
    private ResourceHolderPool holderGrayPool;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Returning simplewindow view");

        return new ModelAndView("window/simplewindow");
    }

    public ResourcesLoaderFactory getLoaderFactory() {
        return loaderFactory;
    }

    public void setLoaderFactory(ResourcesLoaderFactory loaderFactory) {
        this.loaderFactory = loaderFactory;
    }

    public ResourceHolderPool getHolderPool() {
        return holderPool;
    }

    public void setHolderPool(ResourceHolderPool holderPool) {
        this.holderPool = holderPool;
    }

    public ResourceHolderPool getHolderGrayPool() {
        return holderGrayPool;
    }

    public void setHolderGrayPool(ResourceHolderPool holderGrayPool) {
        this.holderGrayPool = holderGrayPool;
    }
}
