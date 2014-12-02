package com.github.greengerong.aop;

import java.lang.reflect.Method;

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
public interface ProxyArguments {

    Object invoke() throws Exception;

    Method getMethod();

    Object getProxyObj();

    Object[] getArgs();

    Object getSelfObj();
}
