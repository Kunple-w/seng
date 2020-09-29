package com.github.seng.core.rpc;

import lombok.Data;

import java.util.Map;

/**
 * @author wangyongxu
 */
@Data
public class URL {

    private String protocol;

    private String host;

    private int port;

    // interfaceName
    private String path;

    private Map<String, String> parameters;

}
