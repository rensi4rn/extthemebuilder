/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package springapp.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AppController extends AbstractLocalizedController {
    protected final Log logger = LogFactory.getLog(getClass());

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) throws Exception {
        logger.info("Returning app_js view");
        return new ModelAndView("app_js");
    }

}
