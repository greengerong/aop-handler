package com.github.greengerong.aop.handler;

import com.github.greengerong.aop.ProxyArguments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeatureToggleInvokeHandlerTest {

    @Mock
    private ProxyArguments proxyArguments;
    private static boolean isMockMethodRunner;

    @Test
    public void should_run_this_feature_when_feature_opening() throws Exception {
        //given
        when(proxyArguments.getMethod()).thenReturn(getClass().getMethod("say"));
        isMockMethodRunner = true;
        when(proxyArguments.invoke()).thenReturn("hello");
        //when
        final Object invokeHandler = new FeatureToggleInvokeHandler().invoke(proxyArguments);
        //then
        assertThat(invokeHandler.toString(), is("hello"));
    }

    @Test
    public void should_not_run_this_feature_when_feature_close() throws Exception {
        //given
        when(proxyArguments.getMethod()).thenReturn(getClass().getMethod("say"));
        isMockMethodRunner = false;
        when(proxyArguments.invoke()).thenReturn("hello");
        //when
        final Object invokeHandler = new FeatureToggleInvokeHandler().invoke(proxyArguments);
        //then
        assertThat(invokeHandler, is(nullValue()));
    }


    @Test
    public void should_run_this_feature_when_not_feature_toggle_tag() throws Exception {
        //given
        when(proxyArguments.getMethod()).thenReturn(getClass().getMethod("sayNoneRunner"));
        isMockMethodRunner = false;
        when(proxyArguments.invoke()).thenReturn("hello");
        //when
        final Object invokeHandler = new FeatureToggleInvokeHandler().invoke(proxyArguments);
        //then
        assertThat(invokeHandler.toString(), is("hello"));
    }

    @FeatureToggle(MockRunner.class)
    public String say() {
        return "hello";
    }

    public String sayNoneRunner() {
        return "hello";
    }

    public static class MockRunner implements Runner {

        @Override
        public boolean run(ProxyArguments proxyArguments) {
            return isMockMethodRunner;
        }
    }
}