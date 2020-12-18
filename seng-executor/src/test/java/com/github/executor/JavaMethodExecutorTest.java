package com.github.executor;

import com.github.seng.common.utils.ReflectUtils;
import com.github.seng.core.job.AttachConstant;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JavaMethodExecutorTest {

    @org.junit.jupiter.api.Test
    void execute() {
        SengContext sengContext = new SengContext();
        Map<String, String> attachment = new HashMap<>();
        Method method = MethodUtils.getMatchingMethod(EchoService.class, "echo");
        String executorSignature = ReflectUtils.generateExecutorSignature(method);
        attachment.put(AttachConstant.JAVA_EXECUTOR_SIGNATURE, executorSignature);
        sengContext.setAttachment(attachment);
        JavaMethodExecutor javaMethodExecutor = new JavaMethodExecutor();
        javaMethodExecutor.execute(sengContext);
    }

    static class EchoService {
        private static final Logger logger = LoggerFactory.getLogger(EchoService.class);

        public void echo() {
            logger.info("echo...");
        }
    }
}