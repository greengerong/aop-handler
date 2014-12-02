package com.github.greengerong.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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
public final class AopUtils {
    private AopUtils() {
    }

    public static <T> T proxy(final Object obj, final ProxyInvokeHandler invokeHandler, Class<?>... interfaces) {
        checkNotNull(obj, "Proxy object should be not null.");
        checkNotNull(invokeHandler, "InvokeHandler should be not null.");
        checkState(interfaces.length > 0, "Should be have at less one interfaces.");
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces, new InvocationHandler() {

            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                return invokeHandler.invoke(new ProxyArgumentsImpl(method, obj, proxy, args));
            }
        });
    }

    public static <T> T proxy(final Object obj, ProxyInvokeHandler invokeHandler) {
        return proxy(obj, invokeHandler, obj.getClass().getInterfaces());
    }

    private static class ProxyArgumentsImpl implements ProxyArguments {

        private final Method method;
        private final Object proxyObj;
        private Object selfObj;
        private final Object[] args;

        public ProxyArgumentsImpl(Method method, Object proxyObj, Object selfObj, Object[] args) {
            this.method = method;
            this.proxyObj = proxyObj;
            this.selfObj = selfObj;
            this.args = args;
        }

        @Override
        public Object invoke() throws java.lang.Exception {
            return method.invoke(proxyObj, args);
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object getProxyObj() {
            return proxyObj;
        }

        @Override
        public Object[] getArgs() {
            return args;
        }

        @Override
        public Object getSelfObj() {
            return selfObj;
        }

    }
}
