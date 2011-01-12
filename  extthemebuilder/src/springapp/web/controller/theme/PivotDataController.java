package springapp.web.controller.theme;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project: Theme Builder for ExtJS 3.0
 * @package: springapp.web.controller.theme
 * @class: PivotDataController
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 20.10.2010
 * @Time: 0:55:46
 */
public class PivotDataController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("json/pivot_data");
    }
}
