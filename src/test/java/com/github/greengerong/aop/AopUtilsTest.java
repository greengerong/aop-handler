package com.github.greengerong.aop;

import com.github.greengerong.stub.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static com.github.greengerong.aop.ExceptionInvokeHandler.exception;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AopUtilsTest {
    @Mock
    private Logger logger;
    @Mock
    private StudentDao studentDao;

    @Test
    public void should_get_proxy_instance_and_get_right_result() throws Exception {
        //given
        final String result = "hello joe";
        when(studentDao.say("joe")).thenReturn(result);

        //when
        final StudentDao proxyInstance = AopUtils.proxy(studentDao, exception());

        //then
        assertThat(proxyInstance, is(instanceOf(StudentDao.class)));
        assertThat(proxyInstance.say("joe"), is(result));
    }

    @Test(expected = Exception.class)
    public void should_log_error_when_method_exception() throws Exception {
        //given
        when(studentDao.say(anyString())).thenThrow(new RuntimeException());

        //when

        //then
        try {
            AopUtils.<StudentDao>proxy(studentDao, getMockedLoggerExceptionInvokeHandler()).say("joe");
        } catch (Exception ex) {
            final String error = "Invoke method 'public abstract java.lang.String com.github.greengerong.stub." +
                    "StudentDao.say(java.lang.String)' error with: java.lang.RuntimeException";
            verify(logger).error(contains(error), Matchers.any(Throwable.class));
            throw ex;
        }
    }

    @Test
    public void should_log_enter_return_info_when_method_invoke() throws Exception {
        //given
        final String result = "hello joe";
        when(studentDao.say(anyString())).thenReturn(result);
        //when
        AopUtils.<StudentDao>proxy(studentDao, getMockedLoggerExceptionInvokeHandler()).say("joe");

        //then
        verify(logger).info(contains("Invoking method 'public abstract java.lang.String com.github.greengerong.stub." +
                "StudentDao.say(java.lang.String)' with: [(joe)]"));
        verify(logger).info(contains("Invoked method 'public abstract java.lang.String com.github.greengerong.stub." +
                "StudentDao.say(java.lang.String)' return: [hello joe]"));
    }

    private ExceptionInvokeHandler getMockedLoggerExceptionInvokeHandler() {
        return new ExceptionInvokeHandler() {
            @Override
            protected Logger getLogger() {
                return logger;
            }
        };
    }
}