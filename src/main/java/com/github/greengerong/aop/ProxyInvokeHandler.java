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
public interface ProxyInvokeHandler {
    Object invoke(ProxyArguments proxyArguments) throws Exception;
}
