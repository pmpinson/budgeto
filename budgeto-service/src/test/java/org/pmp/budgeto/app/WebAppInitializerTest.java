package org.pmp.budgeto.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.pmp.budgeto.common.controller.ControllerDispatcherConfig;
import org.pmp.budgeto.domain.account.AccountConfig;
import org.pmp.budgeto.domain.budget.BudgetConfig;
import org.pmp.budgeto.test.TestTools;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import java.util.EnumSet;


@RunWith(MockitoJUnitRunner.class)
public class WebAppInitializerTest {

    @InjectMocks
    private WebAppInitializer webAppInitializer;

    private Filter filter;

    @Test
    public void structure() throws Exception {

        Assertions.assertThat(WebAppInitializer.class.getSuperclass()).isEqualTo(AbstractAnnotationConfigDispatcherServletInitializer.class);
    }

    @Test
    public void getRootConfigClasses() throws Exception {

        Class<?>[] classes = webAppInitializer.getRootConfigClasses();

        Assertions.assertThat(classes).hasSize(1);
        Assertions.assertThat(classes).contains(WebAppConfig.class);
    }

    @Test
    public void getServletConfigClasses() throws Exception {

        Class<?>[] classes = webAppInitializer.getServletConfigClasses();

        Assertions.assertThat(classes).hasSize(4);
        Assertions.assertThat(classes).contains(ControllerDispatcherConfig.class);
        Assertions.assertThat(classes).contains(SwaggerDispatcherConfig.class);
        Assertions.assertThat(classes).contains(AccountConfig.class);
        Assertions.assertThat(classes).contains(BudgetConfig.class);
    }

    @Test
    public void getServletMappings() throws Exception {

        String[] classes = webAppInitializer.getServletMappings();

        Assertions.assertThat(classes).hasSize(1);
        Assertions.assertThat(classes).contains("/*");
    }

    @Test
    public void onStartup() throws Exception {

        ServletContext servletContext = Mockito.mock(ServletContext.class);
        // servlet
        Dynamic servlet = Mockito.mock(Dynamic.class);
        Mockito.when(servletContext.addServlet(Mockito.anyString(), Mockito.any(DispatcherServlet.class))).thenReturn(servlet);
        Mockito.doNothing().when(servlet).setLoadOnStartup(1);
        Mockito.when(servlet.addMapping(new String[]{"/*"})).thenReturn(null);
        Mockito.doNothing().when(servlet).setAsyncSupported(true);
        // encoding filter
        final javax.servlet.FilterRegistration.Dynamic characterEncodingFilter = Mockito.mock(javax.servlet.FilterRegistration.Dynamic.class);
        Mockito.when(servletContext.addFilter(Mockito.eq("characterEncoding"), Mockito.any(Filter.class))).then(new Answer<javax.servlet.FilterRegistration.Dynamic>() {
            public javax.servlet.FilterRegistration.Dynamic answer(InvocationOnMock invocation) throws Throwable {
                filter = (Filter) invocation.getArguments()[1];
                return characterEncodingFilter;
            }
        });
        Mockito.doNothing().when(characterEncodingFilter).addMappingForUrlPatterns(Mockito.any(EnumSet.class), Mockito.anyBoolean(), Mockito.eq("/*"));
        // cors filter
        final javax.servlet.FilterRegistration.Dynamic corsFilter = Mockito.mock(javax.servlet.FilterRegistration.Dynamic.class);
        Mockito.when(servletContext.addFilter(Mockito.eq("cors"), Mockito.any(Filter.class))).thenReturn(corsFilter);
        Mockito.doNothing().when(corsFilter).addMappingForUrlPatterns(Mockito.any(EnumSet.class), Mockito.anyBoolean(), Mockito.eq("/*"));

        webAppInitializer.onStartup(servletContext);

        Assertions.assertThat(filter).isNotNull();
        Assertions.assertThat(filter).isInstanceOf(CharacterEncodingFilter.class);
        Assertions.assertThat(TestTools.getField(filter, "encoding", String.class)).isEqualTo("UTF-8");
        Mockito.verify(servletContext).addServlet(Mockito.anyString(), Mockito.any(DispatcherServlet.class));
        Mockito.verify(servlet).setLoadOnStartup(1);
        Mockito.verify(servlet).addMapping(new String[]{"/*"});
        Mockito.verify(servlet).setAsyncSupported(true);
        Mockito.verify(servletContext).addFilter("characterEncoding", filter);
        Mockito.verify(characterEncodingFilter).addMappingForUrlPatterns(Mockito.any(EnumSet.class), Mockito.anyBoolean(), Mockito.eq("/*"));
        Mockito.verify(corsFilter).addMappingForUrlPatterns(Mockito.any(EnumSet.class), Mockito.anyBoolean(), Mockito.eq("/*"));
    }
}
