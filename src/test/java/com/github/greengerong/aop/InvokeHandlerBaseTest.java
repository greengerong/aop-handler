package com.github.greengerong.aop;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InvokeHandlerBaseTest {

    @Mock
    private ProxyArguments proxyArguments;
    @Mock
    private ProxyInvokeHandler invokeHandler;

    private InvokeHandlerBase getInvokeHandlerBase(ProxyInvokeHandler invokeHandler) {
        return new InvokeHandlerBase(invokeHandler) {
            @Override
            public Object invoke(ProxyArguments proxyArguments) throws Exception {
                return null;
            }
        };
    }

    @Test
    public void should_invoke_the_method_form_proxy_arguments_when_no_more_invoke_handler() throws Exception {
        //given

        //when
        getInvokeHandlerBase(null).innerInvoke(proxyArguments);
        //then
        verify(proxyArguments).invoke();
        verify(invokeHandler, never()).invoke(proxyArguments);

    }

    @Test
    public void should_invoke_the_method_form_invoke_handler_when_given_a_invoke_handler() throws Exception {
        //given

        //when
        getInvokeHandlerBase(invokeHandler).innerInvoke(proxyArguments);
        //then
        verify(proxyArguments, never()).invoke();
        verify(invokeHandler).invoke(proxyArguments);
    }
}