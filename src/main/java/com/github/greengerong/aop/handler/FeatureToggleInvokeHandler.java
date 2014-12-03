package com.github.greengerong.aop.handler;

import com.github.greengerong.aop.InvokeHandlerBase;
import com.github.greengerong.aop.ProxyArguments;
import com.github.greengerong.aop.ProxyInvokeHandler;
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
public class FeatureToggleInvokeHandler extends InvokeHandlerBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureToggleInvokeHandler.class);

    public FeatureToggleInvokeHandler() {
        this(null);
    }

    public FeatureToggleInvokeHandler(ProxyInvokeHandler proxyInvokeHandler) {
        super(proxyInvokeHandler);
    }

    @Override
    public Object invoke(ProxyArguments proxyArguments) throws Exception {
        final FeatureToggle annotation = proxyArguments.getMethod().getAnnotation(FeatureToggle.class);
        if (annotation != null && isClosingFeature(proxyArguments, annotation)) {
            LOGGER.debug(String.format("%s does not run due to feature toggle close this feature.",
                    proxyArguments.getMethod()));
            return null;
        }

        return innerInvoke(proxyArguments);
    }

    private boolean isClosingFeature(ProxyArguments proxyArguments, FeatureToggle annotation) throws Exception {
        final Class<? extends Runner> value = annotation.value();
        return !value.newInstance().run(proxyArguments);
    }
}
