package com.github.seng.registry.api;

/**
 * event listener
 *
 * @author wangyongxu
 */
public interface EventListener {

    void onEvent(LocalRegisterService.URLChanged context);
}
