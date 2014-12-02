package com.github.greengerong.aop;

/**
 * ***************************************
 * *
 * Auth: green gerong                     *
 * Date: 2014                             *
 * blog: http://greengerong.github.io/    *
 * github: https://github.com/greengerong *
 * *
 * ****************************************
 */
public abstract class InvokeHandlerBase implements ProxyInvokeHandler {
    private ProxyInvokeHandler proxyInvokeHandler;

    protected InvokeHandlerBase() {
        this(null);
    }

    protected InvokeHandlerBase(ProxyInvokeHandler proxyInvokeHandler) {
        this.proxyInvokeHandler = proxyInvokeHandler;
    }

    protected Object innerInvoke(ProxyArguments proxyArguments) throws Exception {
        return proxyInvokeHandler != null ? proxyInvokeHandler.invoke(proxyArguments) : proxyArguments.invoke();
    }
}
