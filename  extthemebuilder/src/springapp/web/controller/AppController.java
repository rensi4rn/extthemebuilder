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
