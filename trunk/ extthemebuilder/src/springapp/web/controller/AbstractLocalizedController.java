package springapp.web.controller;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.AbstractCommandController;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 14.05.2009
 * @Time: 14:15:35
 */
public abstract class AbstractLocalizedController
        extends AbstractController {
    
    protected Locale getLocale(HttpServletRequest request) {
        Locale locale = request.getLocale();

        HttpSession session = request.getSession();
        if (null != session) {
            locale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        }
        return locale;
    }
}
