/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.context;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class SpringAppServletContextProvider implements ServletContextAware {
    public void setServletContext(ServletContext servletContext) {
        SpringAppServletContext.setServletContext(servletContext);
    }
}
