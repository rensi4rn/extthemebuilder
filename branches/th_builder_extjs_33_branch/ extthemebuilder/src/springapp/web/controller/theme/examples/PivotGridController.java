package springapp.web.controller.theme.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project: Theme Builder for ExtJS 3.0
 * @package: springapp.web.controller.theme.examples
 * @class: PivotGridController
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 19.10.2010
 * @Time: 22:44:22
 */
public class PivotGridController implements Controller {
    protected final Log logger = LogFactory.getLog(getClass());

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        logger.info("PivotGridController ! IP="+httpServletRequest.getRemoteAddr());

        return new ModelAndView("window/examples/pivot_js");
    }
}
