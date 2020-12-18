package com.github.executor;

import com.github.seng.common.exception.SengRuntimeException;
import com.github.seng.common.utils.ReflectUtils;
import com.github.seng.core.job.AttachConstant;
import com.github.seng.core.job.SengContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @author wangyongxu
 */
public class JavaMethodExecutor implements SengExecutor {
    private static final Logger logger = LoggerFactory.getLogger(JavaMethodExecutor.class);

    private ApplicationContext applicationContext;

    @Override
    public void execute(SengContext sengContext) {
        String executorSignature = sengContext.getAttachment().get(AttachConstant.JAVA_EXECUTOR_SIGNATURE);
        if (StringUtils.isEmpty(executorSignature)) {
            return;
        }
        Method method = ReflectUtils.getMethod(executorSignature);
        Class<?> declaringClass = method.getDeclaringClass();
        boolean dependOnSpring = dependOnSpring(sengContext);
        if (logger.isDebugEnabled()) {
            logger.debug("class {} from spring: {}", declaringClass, dependOnSpring);
        }
        Object bean = getBean(declaringClass, dependOnSpring);
        if (logger.isDebugEnabled()) {
            logger.debug("bean {} from spring: {}", bean, dependOnSpring);
        }
        try {
            Object o = MethodUtils.invokeMethod(bean, true, method.getName());
            if (logger.isDebugEnabled()) {
                logger.debug("{} invoke method {} result: {}", bean, method.getName(), o);
            }
        } catch (Exception e) {
            throw new SengRuntimeException(e);
        }
    }


    private Object getBean(Class<?> declaringClass, boolean spring) {
        if (spring) {
            return applicationContext.getBean(declaringClass);
        } else {
            return BeanUtils.instantiateClass(declaringClass);
        }
    }

    private boolean dependOnSpring(SengContext sengContext) {
        String fromSpring = sengContext.getAttachment().get(AttachConstant.JAVA_EXECUTOR_BEAN_FROM_SPRING);
        return BooleanUtils.toBoolean(fromSpring);
    }
}
