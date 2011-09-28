package springapp.web.controller.theme.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 26.08.2009
 * @Time: 5:16:44
 */
public class TabsController implements Controller {
    protected final Log logger = LogFactory.getLog(getClass());
    
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        logger.info("TabsController ! IP="+httpServletRequest.getRemoteAddr());

        return new ModelAndView("window/examples/tabs_js");
    }
}