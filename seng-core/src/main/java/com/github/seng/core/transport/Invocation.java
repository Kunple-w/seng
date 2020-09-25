package com.github.seng.core.transport;

import lombok.Data;

/**
 * @author wangyongxu
 */
@Data
public class Invocation {

    private String serviceName;

    private String method;

    private Object[] args;

    private Object[] argTypes;

}
