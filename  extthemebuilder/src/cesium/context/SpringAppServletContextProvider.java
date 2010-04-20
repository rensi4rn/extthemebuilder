package cesium.context;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 19.10.2009
 * @Time: 6:12:18
 */
public class SpringAppServletContextProvider implements ServletContextAware {
    public void setServletContext(ServletContext servletContext) {
        SpringAppServletContext.setServletContext(servletContext);
    }
}
