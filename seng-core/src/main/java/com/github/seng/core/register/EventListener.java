package com.github.seng.core.register;

/**
 * event listener
 *
 * @author wangyongxu
 */
public interface EventListener {

    void onEvent(LocalRegisterService.URLChanged context);
}
