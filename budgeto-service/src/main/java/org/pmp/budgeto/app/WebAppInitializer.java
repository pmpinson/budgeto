package org.pmp.budgeto.app;

import org.pmp.budgeto.common.controller.ControllerDispatcherConfig;
import org.pmp.budgeto.common.controller.CorsFilter;
import org.pmp.budgeto.domain.account.AccountConfig;
import org.pmp.budgeto.domain.budget.BudgetConfig;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * specific web app start for spring to configure web app context / servlet context
 * web app context is WebAppConfig, servlet context are RestDispatcherConfig and SwaggerDispatcherConfig
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String ALLOW_ORIGIN = "app.allow.origin";
    private static final String ENCODING = "UTF-8";
    private static final String ENCODING_FILTER_NAME = "characterEncoding";
    private static final String ENCODING_FILTER_MAPPING = "/*";
    private static final String SERVLET_MAPPING = "/*";

    private WebApplicationContext webApplicationContext;

    /**
     * get webapp context config class
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebAppConfig.class};
    }

    /**
     * get servlet context config class : rest and swagger
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ControllerDispatcherConfig.class, SwaggerDispatcherConfig.class, AccountConfig.class, BudgetConfig.class};
    }

    /**
     * mapping for all
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{SERVLET_MAPPING};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        webApplicationContext = super.createRootApplicationContext();
        return webApplicationContext;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new RequestContextListener());

        final CharacterEncodingFilter enc = new CharacterEncodingFilter();
        enc.setEncoding(ENCODING);
        servletContext.addFilter(ENCODING_FILTER_NAME, enc).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, ENCODING_FILTER_MAPPING);

        String allowOrigin = webApplicationContext.getEnvironment().getProperty(ALLOW_ORIGIN);
        //test in app and if ok test to null, it zith spring ?
        final CorsFilter cors = new CorsFilter(allowOrigin);
        servletContext.addFilter("cors", cors).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, ENCODING_FILTER_MAPPING);
    }
}
