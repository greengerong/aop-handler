package com.github.greengerong.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ExceptionInvokeHandler extends InvokeHandlerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionInvokeHandler.class);

    public ExceptionInvokeHandler() {
        this(null);
    }

    public ExceptionInvokeHandler(ProxyInvokeHandler proxyInvokeHandler) {
        super(proxyInvokeHandler);
    }

    public static ProxyInvokeHandler exception() {
        return new ExceptionInvokeHandler();
    }

    public static ProxyInvokeHandler exception(ProxyInvokeHandler proxyInvokeHandler) {
        return new ExceptionInvokeHandler(proxyInvokeHandler);
    }

    @Override
    public Object invoke(ProxyArguments proxyArguments) throws Exception {
        final String methodName = getMethodName(proxyArguments);
        try {
            getLogger().info(String.format("Invoking method '%s' with: [%s]", methodName, getArguments(proxyArguments)));
            final Object result = innerInvoke(proxyArguments);
            getLogger().info(String.format("Invoked method '%s' return: [%s]", methodName, result));
            return result;
        } catch (Exception ex) {
            getLogger().error(String.format("Invoke method '%s' error with: %s", methodName, ex.getCause()), ex);
            throw ex;
        }
    }

    protected Logger getLogger() {
        return LOGGER;
    }

    private String getArguments(ProxyArguments proxyArguments) {
        StringBuffer sb = new StringBuffer();
        final Object[] args = proxyArguments.getArgs();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                sb.append(String.format(" (%s) ", args[i]));
            }
        }
        return sb.toString().trim();
    }

    private String getMethodName(ProxyArguments proxyArguments) {
        return proxyArguments.getMethod().toString();
    }

}
