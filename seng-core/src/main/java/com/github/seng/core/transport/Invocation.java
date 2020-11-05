package com.github.seng.core.transport;

import lombok.Data;

import java.util.StringJoiner;

import static com.github.seng.core.utils.ReflectUtils.EMPTY_PARAM;

/**
 * @author wangyongxu
 */
@Data
public class Invocation {

    private String serviceName;

    private String methodName;

    private Object[] args;


    public String getArgsDesc() {
        StringJoiner sj = new StringJoiner(",");
        for (Object arg : args) {
            if (arg == null) {
                sj.add(null);
            }else {
                sj.add(arg.getClass().getName());
            }
        }
        return sj.toString();
    }

}
