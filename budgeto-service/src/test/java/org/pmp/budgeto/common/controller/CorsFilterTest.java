package org.pmp.budgeto.common.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class CorsFilterTest {

    @Mock
    public ServletRequest req;

    @Mock
    public HttpServletResponse res;

    @Mock
    public FilterChain chain;

    @Test
    public void doFilterDefault() throws Exception {
        doFilter(new CorsFilter(null), null);
    }

    @Test
    public void doFilterEmptyString() throws Exception {
        doFilter(new CorsFilter("     "), null);
    }

    @Test
    public void doFilterOtherValue() throws Exception {
        doFilter(new CorsFilter("www.thedomain.com"), "www.thedomain.com");
    }

    @Test
    public void init(){
        new CorsFilter(null).init(null);
    }

    @Test
    public void destroy(){
        new CorsFilter(null).destroy();
    }

    private void doFilter(CorsFilter corsFilter, String allowOrigin) throws Exception {

        Map<String, String> headers = new HashMap<>();

        Mockito.doNothing().when(chain).doFilter(req, res);
        HeaderAnswer answer = new HeaderAnswer();
        Mockito.doAnswer(answer).when(res).setHeader(Matchers.anyString(), Matchers.anyString());

        corsFilter.doFilter(req, res, chain);

        int nbSetHeader = 3;
        if (allowOrigin != null) {
            nbSetHeader = 4;
            Assertions.assertThat(answer.headers.get("Access-Control-Allow-Origin")).isEqualTo(allowOrigin);
        }
        Assertions.assertThat(answer.headers).hasSize(nbSetHeader);
        Assertions.assertThat(answer.headers.get("Access-Control-Allow-Methods")).isEqualTo("POST, GET, OPTIONS, DELETE");
        Assertions.assertThat(answer.headers.get("Access-Control-Max-Age")).isEqualTo("3600");
        Assertions.assertThat(answer.headers.get("Access-Control-Allow-Headers")).isEqualTo("x-requested-with");

        Mockito.verify(chain).doFilter(req, res);
        Mockito.verify(res, Mockito.times(nbSetHeader)).setHeader(Matchers.anyString(), Matchers.anyString());
        Mockito.verifyNoMoreInteractions(req, res, chain);
    }

    private static class HeaderAnswer implements Answer<Void> {

        Map<String, String> headers = new HashMap<>();

        public Void answer(InvocationOnMock invocation) throws Throwable {
            headers.put((String) invocation.getArguments()[0], (String) invocation.getArguments()[1]);
            return null;
        }
    }

}
