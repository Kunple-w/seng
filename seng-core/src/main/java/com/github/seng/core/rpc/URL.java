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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol);
        sb.append("://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(path);
//        sb.append("")
        return "URL{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
