/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package springapp.web.controller.theme;

import cesium.factory.ResourcesLoaderFactory;
import cesium.holder.ResourcesHolder;
import cesium.loader.ResourcesLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springapp.constants.ApplicationConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DownloadController implements Controller {
    protected final Log logger = LogFactory.getLog(getClass());
    private ResourcesLoaderFactory loaderFactory ;

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        String templateIdString = httpServletRequest.getParameter(ApplicationConstants.TEMPLATE_ID);

        byte templateId = null!=templateIdString?Byte.parseByte(templateIdString):0;
        String templateName = templateId==0?"blue":"gray";

        String shemaNameParam = httpServletRequest.getParameter(ApplicationConstants.NEW_SCHEMA_NAME);
        String newSchemaName = (null!=shemaNameParam)&&(!"".equals(shemaNameParam))
                ?shemaNameParam
                :"default";

        String versionString = httpServletRequest.getParameter("version");
        String version = (null==versionString)?"3.2":versionString;

        HttpSession session = httpServletRequest.getSession();

        ServletContext servletContext = session.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        //String resourcesPath = (String)context.getBean("resourcesPath");

        ResourcesHolder currentSchema =(ResourcesHolder) session.getAttribute(
                ApplicationConstants.CURRENT_SCHEMA_ATTRIBUTE_NAME/*+version*/);
        String resourcesPath = currentSchema.getResourcesPath();
        ResourcesLoader loader = loaderFactory.getResourcesLoader(currentSchema, context);


        httpServletResponse.setContentType("application/zip");

        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Expires", "-1");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"xtheme-"+newSchemaName+".zip\";");
        //httpServletResponse.setContentLength(zipOS.size());
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        ZipOutputStream zipOS = new ZipOutputStream(servletOutputStream);

        zipOS = loader.outForZip(currentSchema, resourcesPath, zipOS, context
                , newSchemaName, templateName, version);

        zipOS.flush();
        zipOS.close();
        servletOutputStream.flush();
        servletOutputStream.close();

        logger.info("DownloadController processed! ");

        return null;
    }

    public ResourcesLoaderFactory getLoaderFactory() {
        return loaderFactory;
    }

    public void setLoaderFactory(ResourcesLoaderFactory loaderFactory) {
        this.loaderFactory = loaderFactory;
    }
}
